package com.example.shady.myapplication.data;

/**
 * Created by ShaDy on 29-Mar-17.
 */

public class DrugsModelClass {
    String image;
    String title;

    public DrugsModelClass(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public DrugsModelClass() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
