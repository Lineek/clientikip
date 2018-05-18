package com.clientcon;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComponenteConnection {
    public static void main(String[] args) {
        ComponenteConnection componenteConnection = new ComponenteConnection();

        int id = 20;
        try {
//            componenteConnection.ComponenteCpuPost(id);
            componenteConnection.ComponenteRamPost(id);
//            componenteConnection.ComponenteStoragePost(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();
    private GlobalMemory memory = hal.getMemory();
    private OperatingSystem os = si.getOperatingSystem();
    private CentralProcessor cp = hal.getProcessor();

    private UrlConnection urlConnection= new UrlConnection();
    private String url = urlConnection.getComponente_url();

    // Criação da Memória RAM no Banco de Dados
    void ComponenteRamPost(long id_maquina) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(this.url);

        String capacidade = String.valueOf(this.memory.getTotal());
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_componente", "RAM"));
//        params.add(new BasicNameValuePair("capacidade", String.format("%s", memory.getTotal())));
        params.add(new BasicNameValuePair("capacidade", capacidade));
        params.add(new BasicNameValuePair("id_maquina", String.valueOf(id_maquina)));
        params.add(new BasicNameValuePair("descricao_componente", "Memoria RAM"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        System.out.println(capacidade);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
//        HttpEntity entity = response.getEntity();
    }

    // Criação do Disk Storage no Banco de Dados
    void ComponenteStoragePost(long id_maquina) throws IOException {
        FileSystem fileSystem = os.getFileSystem();


        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();

            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            // Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("nome_componente", "Disk"));
            params.add(new BasicNameValuePair("capacidade", String.format("%s", fs.getTotalSpace())));
            params.add(new BasicNameValuePair("id_maquina", String.valueOf(id_maquina)));
            params.add(new BasicNameValuePair("descricao_componente", String.valueOf(fs.getName() + " " + fs.getType() )));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
        }
    }

    void ComponenteCpuPost(long id_maquina) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_componente", "CPU"));
        params.add(new BasicNameValuePair("capacidade", "100"));
        params.add(new BasicNameValuePair("id_maquina", String.valueOf(id_maquina)));
        params.add(new BasicNameValuePair("descricao_componente", "CPU: " + String.valueOf(cp)));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        System.out.println(cp.getSystemCpuLoadBetweenTicks() * 100);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
    }
}
