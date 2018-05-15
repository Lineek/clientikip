package com.mkyong;

import oshi.hardware.ComputerSystem;
import oshi.hardware.NetworkIF;
import oshi.hardware.Networks;
import oshi.software.os.NetworkParams;
import oshi.software.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {


    private static HttpURLConnection con;

    private static ComputerSystem hal;
    private static OperatingSystem os;
    private static NetworkIF nt;

    public static void main(String[] args) throws MalformedURLException,
            ProtocolException, IOException {

        String url = "http://localhost:52102/api/maquinas";
        String urlParameters = "id=1&nome_maquina=GuiPC&mac_address=31232";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } finally {

            con.disconnect();
        }
    }
}