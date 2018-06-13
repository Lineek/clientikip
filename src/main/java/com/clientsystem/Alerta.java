package com.clientsystem;

public class Alerta {

    private long id_alerta;
    private String descricao;
    private long id_leitura;

    public long getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(long id_alerta) {
        this.id_alerta = id_alerta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getId_leitura() {
        return id_leitura;
    }

    public void setId_leitura(long id_leitura) {
        this.id_leitura = id_leitura;
    }

    public String relatorio() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("== informacoes da Maquina ==\n")
                .append("id_alerta = ").append(getId_alerta())
                .append("\nDescricao = ").append(getDescricao())
                .append("\nid_leitura = ").append(getId_leitura());

        return String.valueOf(resultado);
    }

}
