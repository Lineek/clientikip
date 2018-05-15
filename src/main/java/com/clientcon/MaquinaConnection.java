package com.clientcon;

import com.clientsystem.Maquina;
import com.google.gson.Gson;
import com.slack.WebHookMessages;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
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
import oshi.util.FormatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaConnection {


    public static void main(String[] args) throws Exception {
        MaquinaConnection maquinaConn = new MaquinaConnection();
        System.out.println("Começando!");
        maquinaConn.MaquinaStart("");
        System.out.println("\nDone!");
    }
    private UrlConnection urlConnection = new UrlConnection();
    private String url = urlConnection.getMaquina_url();
    private Maquina maquina = new Maquina();

    public MaquinaConnection() throws Exception {
    }

    public void MaquinaStart(String url_params) throws Exception {
        if(!url_params.equals("")) {
            url_params = "?" + url_params;
        }
        url += url_params;

        WebHookMessages webhook = new WebHookMessages();

        if(IsMaquinaAlreadyInDB(this.maquina) > 0) {
            System.out.println("Maquina ESTÁ no Banco de Dados");


            // Mensageria no SLACK caso já exista a máquina no Banco de Dados
            System.out.println(this.maquina.relatorio());
            webhook.maquinaStart(this.maquina.getId_maquina());

        } else {
            System.out.println("Maquina não está no Banco de Dados");
            webhook.creatingMaquina(this.maquina.getIdentifier());

            MaquinaPost();
            System.out.println("Maquina Adicionada");

            IsMaquinaAlreadyInDB(this.maquina);
            System.out.println("Criando Componentes:");
            ComponenteConnection componente = new ComponenteConnection();
            long id = this.maquina.getId_maquina();

            System.out.println(id);
            System.out.println("Cpu...");
            componente.ComponenteCpuPost(id);
            System.out.println("Ram...");
            componente.ComponenteRamPost(id);
            System.out.println("Storage...");
            componente.ComponenteStoragePost(id);

            System.out.println("Maquina Criada!");
        }
    }

    // VERIFICANDO SE JÁ EXISTE A MAQUINA NA DB
    private long IsMaquinaAlreadyInDB(Maquina maquina) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            //
            String verifier = "{\"$id\":\"1\",\"Message\":\"Ocorreu um erro.\",\"ExceptionMessage\":\"A sequência não contém elementos\"";
            URIBuilder urlbuild = new URIBuilder(this.url + "identifier/" + maquina.getIdentifier());

            HttpGet request = new HttpGet(urlbuild.build());

            System.out.println(request);
            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);

            Gson gson = new Gson();
            Maquina maquinaIsEqual = gson.fromJson(String.valueOf(builder), Maquina.class);

            System.out.println(maquinaIsEqual.relatorio());
            System.out.println(this.maquina.relatorio());

            if(maquinaIsEqual.getIdentifier().equals(this.maquina.getIdentifier())) {
                this.maquina = maquinaIsEqual;
                return maquinaIsEqual.getId_maquina();
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // POST HTTP Request for Maquina
    // Criação da Maquina no Banco de Dados
    private void MaquinaPost() throws IOException {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_maquina", "Maquina " + maquina.getIdentifier()));
        params.add(new BasicNameValuePair("os", maquina.getOs()));
        params.add(new BasicNameValuePair("os_version", maquina.getOs_version()));
        params.add(new BasicNameValuePair("serial_number", maquina.getSerial_number()));
        params.add(new BasicNameValuePair("identifier", maquina.getIdentifier()));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
    }
}
