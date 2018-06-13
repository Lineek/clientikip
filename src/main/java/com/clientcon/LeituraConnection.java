package com.clientcon;

import com.clientsystem.Leitura;
import com.google.gson.Gson;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeituraConnection {
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();
    private GlobalMemory memory = hal.getMemory();
    private OperatingSystem os = si.getOperatingSystem();
    private CentralProcessor cp = hal.getProcessor();

    private UrlConnection urlConnection= new UrlConnection();
    private String url = urlConnection.getLeitura_url();

    public Leitura LeituraPostRAM(long id_componente) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(this.url);

        // Formatando a DATA e Hora
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String valor = String.valueOf(this.memory.getTotal() - this.memory.getAvailable());
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("valor_leitura", String.format("%s",valor)));
        params.add(new BasicNameValuePair("id_componente", String.valueOf(id_componente)));
        params.add(new BasicNameValuePair("momento_leitura", String.valueOf(dtf.format(now))));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        return JsonToLeitura(Connection.JsonStringBuilder(response));
    }

    public Leitura LeituraPostCPU(long id_componente) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(this.url);

        // Formatando a DATA e Hora
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        double valor = this.cp.getSystemCpuLoadBetweenTicks() * 100;
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("valor_leitura", String.format("%.2f", valor)));
        params.add(new BasicNameValuePair("id_componente", String.valueOf(id_componente)));
        params.add(new BasicNameValuePair("momento_leitura", String.valueOf(dtf.format(now))));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        return JsonToLeitura(Connection.JsonStringBuilder(response));
    }

    public Leitura LeituraPostDisk(long id_componente) throws IOException {
        FileSystem fileSystem = os.getFileSystem();

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);

            // Formatando a DATA e Hora
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String valor = String.valueOf(fs.getUsableSpace());
            // Parametros e outras propriedades do Request
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("valor_leitura", valor));
            params.add(new BasicNameValuePair("id_componente", String.valueOf(id_componente)));
            //params.add(new BasicNameValuePair("momento_leitura", String.valueOf(dtf.format(now)))); //2016/11/16 12:08:43
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            return JsonToLeitura(Connection.JsonStringBuilder(response));
        }
        return null;
    }

    private Leitura JsonToLeitura(String json) {
        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(json), Leitura.class);
    }
}
