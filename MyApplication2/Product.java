package com.example.myapplication.data.model;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private int imageResId;
    private int price;

    public Product(String name, String description, int imageResId, int price) {
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getPrice() {
        return price;
    }
}
//4
