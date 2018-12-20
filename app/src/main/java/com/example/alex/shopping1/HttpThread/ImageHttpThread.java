package com.example.alex.shopping1.HttpThread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.URL;

/**
 * Created by 15587 on 2018/12/17.
 */
public class ImageHttpThread  extends Thread{
    private String  ImageUrl;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setResult(Bitmap result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Bitmap result;
    private String url;

    public  ImageHttpThread(String imageUrl){
        this.ImageUrl=imageUrl;
    }
    public void run() {
        try {
            url = "http://119.29.60.170:8080/shopping/";
            java.net.URL imageUrl = new java.net.URL(url+ImageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            InputStream is = httpURLConnection.getInputStream();
            setResult(BitmapFactory.decodeStream(is));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getResult() {
        return result;
    }
}
