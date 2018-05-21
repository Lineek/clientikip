package com.clientsystem;

import com.clientcon.ComponenteConnection;
import com.clientcon.MaquinaConnection;

public class main {
    public static void main(String[] args) throws Exception {
        MaquinaConnection maquinaConn = new MaquinaConnection();
        System.out.println("Iniciando Máquina");
        maquinaConn.MaquinaStart();
        Maquina maquina = maquinaConn.getMaquina();

        ComponenteConnection compConn = new ComponenteConnection();
        compConn.ComponenteStart(maquina.getId_maquina());
        Componente ram = compConn.getRam();
        Componente cpu = compConn.getCpu();
        Componente disk = compConn.getDisk();
        System.out.println(ram.relatorio());
        System.out.println(cpu.relatorio());
        System.out.println(disk.relatorio());
        System.out.println("\nMáquina iniciada...");

        System.out.println("\nAtivando Leituras...");


    }
}
