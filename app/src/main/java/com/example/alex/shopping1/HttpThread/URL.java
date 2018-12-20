package com.example.alex.shopping1.HttpThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by 15587 on 2018/12/17.
 */
public class URL extends Thread {
    private String re;

    public void run() {
        //http请求
        try {
            java.net.URL url = new java.net.URL("http://119.29.60.170:8080/shopping/product");
            HttpURLConnection httpURLConnection  = (HttpURLConnection) url.openConnection();
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer result = new StringBuffer();
            String temp ;
            while((temp = bufferedReader.readLine()) != null){
                result.append(temp);
            }
            setResult(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return re;
    }

    public void setResult(String re) {
        this.re = re;
    }
}
