package com.clientsystem;

import com.clientcon.ComponenteConnection;
import com.clientcon.LeituraConnection;
import com.clientcon.MaquinaConnection;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// TODO: SYSTEM TRAY, colocar tudo em um programa que rode no system tray.
public class main {
    public static void main(String[] args) throws Exception {
        // Iniciando Conexão com a máquina
        MaquinaConnection maquinaConn = new MaquinaConnection();
        System.out.println("Iniciando Máquina");
        maquinaConn.MaquinaStart();
        Maquina maquina = maquinaConn.getMaquina();

        // Iniciando Conexão com os componentes da máquina
        ComponenteConnection compConn = new ComponenteConnection();
        compConn.ComponenteStart(maquina.getId_maquina());
        // Gerando Memória RAM
        Componente ram = compConn.getRam();
        // Gerando CPU
        Componente cpu = compConn.getCpu();
        // Gerando Disco
        Componente disk = compConn.getDisk();

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
        LeituraConnection leituraConn = new LeituraConnection();

        // Adicionando o TimerTask
        // Ele faz com que cada bateria de leitura execute num intervalo de tempo
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // Executando as Leituras

                // Leitura da memória RAM
                try {
                    Leitura leitura = leituraConn.LeituraPostRAM(ram.getId_componente());
                    System.out.println(leitura.relatorio());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Leitura da CPU
                try {
                    Leitura leitura = leituraConn.LeituraPostCPU(cpu.getId_componente());
                    System.out.println(leitura.relatorio());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Leitura do Disco
                try {
                    Leitura leitura = leituraConn.LeituraPostDisk(disk.getId_componente());
                    System.out.println(leitura.relatorio());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay, interval);
    }
}
