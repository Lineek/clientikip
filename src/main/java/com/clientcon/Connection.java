package com.clientcon;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Connection {
    public static String JsonStringBuilder(HttpResponse response) throws IOException {
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = bufReader.readLine()) != null) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }

        return String.valueOf(builder);
    }
}
