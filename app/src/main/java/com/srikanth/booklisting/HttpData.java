package com.srikanth.booklisting;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpData {
    String stream = null;

    public String getData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConenction = (HttpURLConnection) url.openConnection();
            if (urlConenction.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConenction.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                urlConenction.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
