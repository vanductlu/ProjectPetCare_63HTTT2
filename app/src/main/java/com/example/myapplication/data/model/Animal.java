package com.example.myapplication.data.model;

public class Animal {
    private String name;
    private String breed;
    private int imageResource;
    private String diseases;
    private String checkupFrequency;
    private String careTips;
    private String harmfulFoods;

    public Animal(String name, String breed, int imageResource, String diseases, String checkupFrequency, String careTips, String harmfulFoods) {
        this.name = name;
        this.breed = breed;
        this.imageResource = imageResource;
        this.diseases = diseases;
        this.checkupFrequency = checkupFrequency;
        this.careTips = careTips;
        this.harmfulFoods = harmfulFoods;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getImageResourceId() {
        return imageResource;
    }

    public String getDiseases() {
        return diseases;
    }

    public String getCheckupFrequency() {
        return checkupFrequency;
    }

    public String getCareTips() {
        return careTips;
    }

    public String getHarmfulFoods() {
        return harmfulFoods;
    }
}
