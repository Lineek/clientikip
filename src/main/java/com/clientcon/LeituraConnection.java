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
import oshi.software.os.OperatingSystem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LeituraConnection {
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();
    private GlobalMemory memory = hal.getMemory();
    private OperatingSystem os = si.getOperatingSystem();
    private CentralProcessor cp = hal.getProcessor();

    private UrlConnection urlConnection= new UrlConnection();
    private String url = urlConnection.getComponente_url();

    void LeituraPostRAM(long id_componente) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(this.url);

        String capacidade = String.valueOf(this.memory.getTotal() - this.memory.getAvailable());
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_componente", "RAM"));
//        params.add(new BasicNameValuePair("capacidade", String.format("%s", memory.getTotal())));
        params.add(new BasicNameValuePair("capacidade", capacidade));
        params.add(new BasicNameValuePair("id_leitura", String.valueOf(id_componente)));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
    }
}
