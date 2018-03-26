package com.example.paulwinjeba.kshop;

/**
 * Created by PAULWIN JEBA on 23-03-2018.
 */

public class Request{

    private String Title;
    private String Category;

    public Request() {
    }

    public Request(String title, String category) {
        Title = title;
        Category = category;
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
}
