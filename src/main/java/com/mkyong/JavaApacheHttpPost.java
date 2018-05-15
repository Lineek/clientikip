package com.mkyong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.platform.mac.MacHardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class JavaApacheHttpPost {
    public static void main(String[] args) throws IOException {

        SystemInfo si = new SystemInfo();

        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        String macaddress = null;
        for (NetworkIF networkIF : hal.getNetworkIFs()) {
            System.out.format("   MAC Address: %s %n", networkIF.getMacaddr());
            macaddress = networkIF.getMacaddr();
        }

        System.out.println(macaddress);

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://sedexikip.azurewebsites.net/api/maquinas");

// Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("nome_maquina", "GuiPC"));
        params.add(new BasicNameValuePair("mac_address", macaddress));
        params.add(new BasicNameValuePair("os", os + " " + os.getVersion()));
        params.add(new BasicNameValuePair("os_version", os + " " + os.getVersion()));
        params.add(new BasicNameValuePair("serial_number", hal.getComputerSystem().getSerialNumber()));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

//Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
            } finally {
                instream.close();
            }
        }

        System.out.println("Done");
    }
}
