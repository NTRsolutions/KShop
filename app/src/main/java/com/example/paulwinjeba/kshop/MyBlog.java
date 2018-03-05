package com.example.paulwinjeba.kshop;

/**
 * Created by PAULWIN JEBA on 03-03-2018.
 */

public class MyBlog{

    private String Title;
    private String Price;
    private String Image;
    private String Category;
    private String Description_1;
    private String Description_2;

    public MyBlog(){

    }
    public MyBlog(String title,String price, String image,String category,String description_1,String description_2){

        Title = title;
        Price = price;
        Image = image;
        Category = category;
        Description_1 = description_1;
        Description_2 = description_2;

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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription_1() {
        return Description_1;
    }

    public void setDescription_1(String description_1) {
        Description_1 = description_1;
    }

    public String getDescription_2() {
        return Description_2;
    }

    public void setDescription_2(String description_2) {
        Description_2 = description_2;
    }

}

