package com.example.jelouscrab.modelo;

public class Producto {
    private String id, name, description, price, fav;
    private byte[] image;

    public Producto(String name, String description, String price, byte[] image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Producto(String id, String name, String description, String price, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Producto(String name, String description, String price, byte[] image, String fav) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.fav = fav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }
}
