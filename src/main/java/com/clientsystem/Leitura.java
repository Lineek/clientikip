package com.clientsystem;

public class Leitura {
    private long id_leitura;
    private String valor_leitura;
    private String momento_leitura;
    private long id_componente;

    public long getId_leitura() {
        return id_leitura;
    }

    public void setId_leitura(long id_leitura) {
        this.id_leitura = id_leitura;
    }

    public String getValor_leitura() {
        return valor_leitura;
    }

    public void setValor_leitura(String valor_leitura) {
        this.valor_leitura = valor_leitura;
    }

    public String getMomento_leitura() {
        return momento_leitura;
    }

    public void setMomento_leitura(String momento_leitura) {
        this.momento_leitura = momento_leitura;
    }

    public long getId_componente() {
        return id_componente;
    }

    public void setId_componente(long id_componente) {
        this.id_componente = id_componente;
    }


    public String relatorio() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("== informações da Leitura ==\n")
                .append("id_leitura = ").append(getId_leitura())
                //.append("\nMomento = ").append(getMomento_leitura())
                .append("\nValor = ").append(getValor_leitura())
                .append("\nid Componente = ").append(getId_componente())
                .append("\n");

        return String.valueOf(resultado);
    }
}
