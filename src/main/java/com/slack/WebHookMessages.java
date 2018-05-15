package com.slack;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class WebHookMessages {



    private void webHookConnection(String message) throws IOException {
        // Mensageria no SLACK caso já exista a máquina no Banco de Dados
        HttpClient client = new DefaultHttpClient();
        HttpPost httpRequest = new HttpPost("https://hooks.slack.com/services/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");
        httpRequest.setHeader("Content-Type", "application/json");

        HttpEntity entity = new ByteArrayEntity(message.getBytes("UTF-8"));
        httpRequest.setEntity(entity);
        HttpResponse response = client.execute(httpRequest);
    }

    public void maquinaStart(long id) throws IOException {
        webHookConnection("{\"text\": Iniciando maquina " + id);
    }

    public void creatingMaquina(String identifier) throws IOException {
        webHookConnection("{\n" +
                "   \"attachments\":[\n" +
                "      {\n" +
                "         \"fallback\":\"Criando nova maquina no Banco de Dados.\",\n" +
                "         \"pretext\":\"Criando nova maquina no Banco de Dados.\",\n" +
                "         \"color\":\"good\",\n" +
                "         \"fields\":[\n" +
                "            {\n" +
                "               \"title\":\"Maquina\",\n" +
                "               \"value\":\"" + identifier + "\",\n" +
                "               \"short\":false\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ]\n" +
                "}\t");
    }
}
