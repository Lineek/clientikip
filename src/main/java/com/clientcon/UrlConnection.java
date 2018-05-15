package com.clientcon;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

public class UrlConnection {

    private String base_url = "http://localhost:52102/api/";
    private String maquina_url = "maquinas/";
    private String componente_url = "componentes/";
    private String leitura_url = "leituras/";
    private String alerta_url = "alertas/";

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
