package com.example.paulwinjeba.kshop;

/**
 * Created by PAULWIN JEBA on 23-03-2018.
 */

public class Request{

    private String Title;
    private String Category;
    private String Description;
    private String Expected_Price;

    public Request() {
    }

    public Request(String title, String category, String description, String expected_Price) {
        Title = title;
        Category = category;
        Description = description;
        Expected_Price = expected_Price;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getExpected_Price() {
        return Expected_Price;
    }

    public void setExpected_Price(String expected_Price) {
        Expected_Price = expected_Price;
    }
}
