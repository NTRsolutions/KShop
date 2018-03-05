package com.example.paulwinjeba.kshop;

/**
 * Created by PAULWIN JEBA on 04-03-2018.
 */

public class List {

    public String Image,Title,Price;

    public List(){
    }

    public List(String image, String title, String price) {
        Image = image;
        Title = title;
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
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
}
