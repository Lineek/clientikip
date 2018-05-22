package com.clientcon;

import com.clientsystem.Componente;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ComponenteConnection {
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();
    private GlobalMemory memory = hal.getMemory();
    private OperatingSystem os = si.getOperatingSystem();
    private CentralProcessor cp = hal.getProcessor();

    private UrlConnection urlConnection= new UrlConnection();
    private String url = urlConnection.getComponente_url();

    private Componente ram;
    private Componente disk;
    private Componente cpu;

    public void ComponenteStart(long maquina_id) throws IOException {

        System.out.println("Componentes:");
        System.out.println(maquina_id);

        if (IsRamAlreadyInDB(maquina_id) > 0) {
            System.out.println("Memória RAM ativada");
        } else {
            System.out.println("Ram...");
            this.ram = ComponenteRamPost(maquina_id);
        }

        if(IsCpuAlreadyInDB(maquina_id) > 0) {
            System.out.println("CPU ativada");
        } else {
            System.out.println("Cpu...");
            this.cpu = ComponenteCpuPost(maquina_id);
        }

        if(IsDiskAlreadyInDB(maquina_id) > 0) {
            System.out.println("Disk Storage ativado");
        } else {
            System.out.println("Storage...");
            this.disk = ComponenteStoragePost(maquina_id);
        }

        System.out.println("\nComponentes Criados!");
    }

    // Método para verificar se a RAM está no Banco de Dados
    // Se possível, retorna seu id
    private long IsRamAlreadyInDB(long maquina_id) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            URIBuilder urlbuild = new URIBuilder(this.url + "ram/" + maquina_id);

            HttpGet request = new HttpGet(urlbuild.build());

            System.out.println(request);
            HttpResponse response = client.execute(request);

            this.ram = JsonToComponente(Connection.JsonStringBuilder(response));
            try {
                return this.ram.getId_componente();
            } catch (NullPointerException e) {
                return 0;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Método para verificar se a CPU está no Banco de Dados
    // Se possível, retorna seu id
    private long IsCpuAlreadyInDB(long maquina_id) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            URIBuilder urlbuild = new URIBuilder(this.url + "cpu/" + maquina_id);

            HttpGet request = new HttpGet(urlbuild.build());

            System.out.println(request);
            HttpResponse response = client.execute(request);

            this.cpu = JsonToComponente(Connection.JsonStringBuilder(response));
            try {
                return this.cpu.getId_componente();
            } catch (NullPointerException e) {
                return 0;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Método para verificar se o Disk Storage está no Banco de Dados
    // Se possível, retorna seu id
    private long IsDiskAlreadyInDB(long maquina_id) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            URIBuilder urlbuild = new URIBuilder(this.url + "disk/" + maquina_id);

            HttpGet request = new HttpGet(urlbuild.build());

            System.out.println(request);
            HttpResponse response = client.execute(request);

            this.disk = JsonToComponente(Connection.JsonStringBuilder(response));
            try {
                return this.disk.getId_componente();
            } catch (NullPointerException e) {
                return 0;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Criação da Memória RAM no Banco de Dados
    private Componente ComponenteRamPost(long id_maquina) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(this.url);

        String capacidade = String.valueOf(this.memory.getTotal());
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_componente", "RAM"));
        params.add(new BasicNameValuePair("capacidade", capacidade));
        params.add(new BasicNameValuePair("id_maquina", String.valueOf(id_maquina)));
        params.add(new BasicNameValuePair("descricao_componente", "Memoria RAM"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        return JsonToComponente(Connection.JsonStringBuilder(response));
    }

    // Criação do Disk Storage no Banco de Dados
    private Componente ComponenteStoragePost(long id_maquina) throws IOException {
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
            return JsonToComponente(Connection.JsonStringBuilder(response));
        }
        return null;
    }

    // Criação do Componente CPU no Banco de Dados
    private Componente ComponenteCpuPost(long id_maquina) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_componente", "CPU"));
        params.add(new BasicNameValuePair("capacidade", "100"));
        params.add(new BasicNameValuePair("id_maquina", String.valueOf(id_maquina)));
        params.add(new BasicNameValuePair("descricao_componente", "CPU: " + String.valueOf(cp)));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        return JsonToComponente(Connection.JsonStringBuilder(response));
    }

    private Componente JsonToComponente(String json) {
        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(json), Componente.class);
    }

    public Componente getRam() {
        return ram;
    }

    public Componente getDisk() {
        return disk;
    }

    public Componente getCpu() {
        return cpu;
    }
}
