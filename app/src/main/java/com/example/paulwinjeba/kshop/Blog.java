package com.example.paulwinjeba.kshop;

/**
 * Created by PAULWIN JEBA on 31-01-2018.
 */

public class Blog {

    private String Title;
    private String Price;
    private String Image;

    public Blog(){

    }
    public Blog(String title, String price, String image) {
        Title = title;
        Price = price;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
