package com.example.mobiledevtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Handler {

    private static final String TAG = Handler.class.getSimpleName();
    private String result="";

    public Handler(){

    }

    public String httpServiceCall(String requestUrl){
        String result = null;
        try {

            URL url = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            //httpsURLConnection.setRequestMethod("GET");

            InputStream inputStream = httpsURLConnection.getInputStream();
                    //new BufferedInputStream(httpsURLConnection.getInputStream());
            result = convertResultToString(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String convertResultToString(InputStream inputStream) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String li;

        StringBuilder result = new StringBuilder();

            try {

                if ((li=bufferedReader.readLine()) != null) {
                    result.append(li);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        return result.toString();

        /*
        while (true){
            try {
                if (!((li=bufferedReader.readLine())!=null)){
                    stringBuilder.append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        }*/
    }

}
