package com.example.test34;

public class Item {
    private String text;
    private int imageResId;

    public Item(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}