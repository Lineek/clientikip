package com.clientsystem;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class Maquina {
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();
    private CentralProcessor processor = hal.getProcessor();

    private long id_maquina;
    private String nome_maquina;
    private String os = String.valueOf(si.getOperatingSystem().getFamily());
    private String os_version = String.valueOf(si.getOperatingSystem().getVersion());
    private String serial_number = String.valueOf(hal.getComputerSystem().getSerialNumber());
    private String identifier = generateIdentifier();

    public Maquina() throws Exception {
    }


    private String generateIdentifier() throws Exception {
        return this.processor.getProcessorID() + this.hal.getComputerSystem().getSerialNumber();
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getNome_maquina() {
        return nome_maquina;
    }

    public void setNome_maquina(String nome_maquina) {
        this.nome_maquina = nome_maquina;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public long getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(long id_maquina) {
        this.id_maquina = id_maquina;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String relatorio() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("== informações da Maquina ==\n")
                .append("id = ").append(getId_maquina())
                .append("\nNome = ").append(getNome_maquina())
                .append("\nos = ").append(getOs())
                .append("\nOS Version = ").append(getOs_version())
                .append("\nSerial Number = ").append(getSerial_number())
                .append("\nProcessor ID = ").append(this.processor.getProcessorID()).append("\n");

        return String.valueOf(resultado);
    }
}
