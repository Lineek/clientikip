package com.clientsystem;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OperatingSystem;

public class Componente {
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();
    private OperatingSystem os = si.getOperatingSystem();
    private GlobalMemory memory = hal.getMemory();
    private CentralProcessor processor = hal.getProcessor();
    private FileSystem fs = os.getFileSystem();

    private int id_componente;
    private String nome_componente;
    private String capacidade;
    private int id_maquina;

    public int getId_componente() {
        return id_componente;
    }

    public void setId_componente(int id_componente) {
        this.id_componente = id_componente;
    }

    public String getNome_componente() {
        return nome_componente;
    }

    public void setNome_componente(String nome_componente) {
        this.nome_componente = nome_componente;
    }

    public String getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }

    public String relatorio() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("== informações do Componente ==\n").append("id = ").append(getId_componente()).
        append("\nNome Componente = ").append(getNome_componente())
                .append("\nCapacidade = ").append(getCapacidade())
                .append("\nID Máquina = ").append(getId_maquina())
                .append("\n");

        return String.valueOf(resultado);
    }
}
