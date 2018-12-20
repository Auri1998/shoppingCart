package com.example.alex.shopping1;

/**
 * Created by 15587 on 2018/12/17.
 */
public class Product {
    private  int id;
    private  String title;
    private String price;
    private String image;
    private int  num;

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setId(int id) {
        this.id = id;
    }

}
