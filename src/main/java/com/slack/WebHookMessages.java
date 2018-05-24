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

    private static void webHookConnection(String message) throws IOException {
        // Mensageria no SLACK caso já exista a máquina no Banco de Dados
        HttpClient client = new DefaultHttpClient();
        HttpPost httpRequest = new HttpPost("https://hooks.slack.com/services/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");
        httpRequest.setHeader("Content-Type", "application/json");

        HttpEntity entity = new ByteArrayEntity(message.getBytes("UTF-8"));
        httpRequest.setEntity(entity);
        HttpResponse response = client.execute(httpRequest);
    }

    public static void maquinaStart(long id) throws IOException {
        webHookConnection("{\"text\": Iniciando maquina " + id + ",\"icon_emoji\": \":+1:\"}");
    }

    public static void creatingMaquina(String identifier) throws IOException {
        webHookConnection("{\n" +
                "   \"attachments\":[\n" +
                "      {\n" +
                "         \"fallback\":\"Criando nova maquina no Banco de Dados.\",\n" +
                "         \"pretext\":\"Criando nova maquina no Banco de Dados.\",\n" +
                "         \"color\":\"good\",\n" +
                "         \t\"icon_emoji\": \":+1:\",\n" +
                "         \"fields\":[\n" +
                "            {\n" +
                "               \"title\":\"Maquina\",\n" +
                "               \"value\":" + identifier + "\",\n" +
                "               \"short\":false\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ]\n" +
                "}");
    }

    public static void RamIs80Percent(long id, long id_maquina) throws IOException {
        webHookConnection("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"[Alerta] RAM("+id+") da máquina("+id_maquina+") está em 80%.\",\n" +
                "            \"color\": \"warning\",\n" +
                "            \"pretext\": \"[Alerta] RAM("+id+") da máquina("+id_maquina+") está em 80%.\",\n" +
                "         \t\"icon_emoji\": \":neutral_face:\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"title\": \"RAM\",\n" +
                "                    \"value\": \"80%\",\n" +
                "                    \"short\": false\n" +
                "                }\n" +
                "            ],\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    public static void RamIs90Percent(long id, long id_maquina) throws IOException {
        webHookConnection("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"[Alerta] RAM("+id+") da máquina("+id_maquina+") está em 90%.\",\n" +
                "            \"color\": \"danger\",\n" +
                "            \"pretext\": \"[Alerta] RAM("+id+") da máquina("+id_maquina+") está em 90%.\",\n" +
                "         \t\"icon_emoji\": \":fire:\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"title\": \"RAM\",\n" +
                "                    \"value\": \"90%\",\n" +
                "                    \"short\": false\n" +
                "                }\n" +
                "            ],\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    public static void CPUIs80Percent(long id, long id_maquina) throws IOException {
        webHookConnection("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"[Alerta] CPU("+id+") da máquina("+id_maquina+") está em 80%.\",\n" +
                "            \"color\": \"warning\",\n" +
                "            \"pretext\": \"[Alerta] CPU("+id+") da máquina("+id_maquina+") está em 80%.\",\n" +
                "         \t\"icon_emoji\": \":neutral_face:\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"title\": \"CPU\",\n" +
                "                    \"value\": \"80%\",\n" +
                "                    \"short\": false\n" +
                "                }\n" +
                "            ],\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    public static void CPUIs90Percent(long id, long id_maquina) throws IOException {
        webHookConnection("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"[Alerta] CPU("+id+") da máquina("+id_maquina+") está em 90%.\",\n" +
                "            \"color\": \"danger\",\n" +
                "            \"pretext\": \"[Alerta] CPU("+id+") da máquina("+id_maquina+") está em 90%.\",\n" +
                "         \t\"icon_emoji\": \":fire:\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"title\": \"CPU\",\n" +
                "                    \"value\": \"90%\",\n" +
                "                    \"short\": false\n" +
                "                }\n" +
                "            ],\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    public static void DiskIs80Percent(long id, long id_maquina) throws IOException {
        webHookConnection("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"[Alerta] Disco("+id+") da máquina("+id_maquina+") está em 80%.\",\n" +
                "            \"color\": \"warning\",\n" +
                "            \"pretext\": \"[Alerta] Disco("+id+") da máquina("+id_maquina+") está em 80%.\",\n" +
                "         \t\"icon_emoji\": \":neutral_face:\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"title\": \"Disco\",\n" +
                "                    \"value\": \"80%\",\n" +
                "                    \"short\": false\n" +
                "                }\n" +
                "            ],\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    public static void DiskIs90Percent(long id, long id_maquina) throws IOException {
        webHookConnection("{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"[Alerta] Disco("+id+") da máquina("+id_maquina+") está em 90%.\",\n" +
                "            \"color\": \"danger\",\n" +
                "            \"pretext\": \"[Alerta] Disco("+id+") da máquina("+id_maquina+") está em 90%.\",\n" +
                "         \t\"icon_emoji\": \":fire:\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"title\": \"Disco\",\n" +
                "                    \"value\": \"90%\",\n" +
                "                    \"short\": false\n" +
                "                }\n" +
                "            ],\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

}