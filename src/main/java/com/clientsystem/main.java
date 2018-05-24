package com.clientsystem;

import com.clientcon.ComponenteConnection;
import com.clientcon.LeituraConnection;
import com.clientcon.MaquinaConnection;
import com.slack.WebHookMessages;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// TODO: SYSTEM TRAY, colocar tudo em um programa que rode no system tray.
public class main {
    public static void main(String[] args) throws Exception {
        // Iniciando Conexão com a máquina
        MaquinaConnection maquinaConn = new MaquinaConnection();
        System.out.println("Iniciando Máquina");

        try {
            maquinaConn.MaquinaStart();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível estabelecer conexão");
        }

        Maquina maquina = maquinaConn.getMaquina();
        // Iniciando Conexão com os componentes da máquina
        ComponenteConnection compConn = new ComponenteConnection();
        compConn.ComponenteStart(maquina.getId_maquina());
        // Gerando Memória RAM
        final Componente ram = compConn.getRam();
        // Gerando CPU
        final Componente cpu = compConn.getCpu();
        // Gerando Disco
        final Componente disk = compConn.getDisk();

        // Relatórios de cada componente
        // O relatório contém: id, nome_componente, capacidade, descrição e id_maquina.
        System.out.println(ram.relatorio());
        System.out.println(cpu.relatorio());
        System.out.println(disk.relatorio());

        System.out.println("\nMáquina iniciada...");

        // ################################
        // Leitura dos componentes contínua
        // ################################
        System.out.println("\nAtivando Leituras...");

        // Setando o delay e interval
        int delay = 5000;   // delay de 5 seg.
        int interval = 10000;  // intervalo de 10 seg.
        Timer timer = new Timer();
        final LeituraConnection leituraConn = new LeituraConnection();

        // Constantes
        final long ram80 = (long)(0.8 * Long.parseLong(ram.getCapacidade()));
        final long ram90 = (long)(0.9 * Long.parseLong(ram.getCapacidade()));
        final long disk80 = (long)(0.8 * Long.parseLong(ram.getCapacidade()));
        final long disk90 = (long)(0.9 * Long.parseLong(ram.getCapacidade()));

        // Mensageria do Slack

        // Adicionando o TimerTask
        // Ele faz com que cada bateria de leitura execute num intervalo de tempo
        timer.scheduleAtFixedRate(new TimerTask() {

            // Contadores para que os alertas não se repitam um atrás do outro
            int countRam = 0;
            int countCPU = 0;
            int countDisk = 0;

            // Executando as Leituras
            public void run() {


                // Leitura da memória RAM
                try {
                    Leitura leitura = leituraConn.LeituraPostRAM(ram.getId_componente());
                    System.out.println(leitura.relatorio());

                    // Caso o contador for maior que 0 diminua o contador em 1
                    countRam = (countRam > 0) ? countRam - 1 : 0;

                    // Mensagem do Slack caso o valor de leitura for muito alto
                    // e só repete depois de 1 min
                    // TODO: POST em Alerta
                    if(Long.parseLong(leitura.getValor_leitura()) >= ram90 && countRam == 0) {
                        WebHookMessages.RamIs90Percent(ram.getId_componente(), maquina.getId_maquina());
                        countRam = 6;
                    } else if (Long.parseLong(leitura.getValor_leitura()) >= ram80 && countRam == 0) {
                        WebHookMessages.RamIs80Percent(ram.getId_componente(), maquina.getId_maquina());
                        countRam = 6;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Leitura da CPU
                try {
                    Leitura leitura = leituraConn.LeituraPostCPU(cpu.getId_componente());
                    System.out.println(leitura.relatorio());

                    // Caso o contador for maior que 0 diminua o contador em 1
                    countCPU  = (countCPU  > 0) ? countCPU - 1 : 0;

                    // Mensagem do Slack caso o valor de leitura for muito alto
                    // e só repete depois de 1 min
                    // TODO: POST em Alerta
                    if(Double.parseDouble(leitura.getValor_leitura().replaceFirst(",", ".")) >= 80 && countCPU== 0) {
                        WebHookMessages.CPUIs90Percent(cpu.getId_componente(), maquina.getId_maquina());
                        countCPU = 6;
                    } else if (Double.parseDouble(leitura.getValor_leitura().replaceFirst(",", ".")) >= 80 && countCPU == 0) {
                        WebHookMessages.CPUIs80Percent(cpu.getId_componente(), maquina.getId_maquina());
                        countCPU = 6;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Leitura do Disco
                try {
                    Leitura leitura = leituraConn.LeituraPostDisk(disk.getId_componente());
                    System.out.println(leitura.relatorio());

                    // Caso o contador for maior que 0 diminua o contador em 1
                    countDisk = (countDisk > 0) ? countDisk - 1 : 0;

                    // Mensagem do Slack caso o valor de leitura for muito alto
                    // e só repete depois de 60 min
                    // TODO: POST em Alerta
                    if(Long.parseLong(leitura.getValor_leitura()) >= disk80 && countDisk == 0) {
                        WebHookMessages.DiskIs90Percent(disk.getId_componente(), maquina.getId_maquina());
                        countDisk = 600;
                    }
                    else if (Long.parseLong(leitura.getValor_leitura()) >= disk90 && countDisk == 0) {
                        WebHookMessages.DiskIs80Percent(disk.getId_componente(), maquina.getId_maquina());
                        countDisk = 600;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay, interval);
    }
}
