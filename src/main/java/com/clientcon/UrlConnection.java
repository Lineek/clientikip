package com.clientcon;

public class UrlConnection {

    private String base_url = "http://localhost:58646/api/"; // String da API Rest http://sedexikip.azurewebsites.net/api/
    private String maquina_url = "maquinas/";
    private String componente_url = "componentes/";
    private String leitura_url = "leituras/";
    private String alerta_url = "alertas/";


    // Todos os GETTERS das Url necessárias para o projeto
    public String getMaquina_url() { return this.base_url + this.maquina_url; }

    public String getComponente_url() {
        return this.base_url + this.componente_url;
    }

    public String getLeitura_url() {
        return this.base_url + this.leitura_url;
    }

    public String getAlerta_url() {
        return this.base_url + this.alerta_url;
    }
}
