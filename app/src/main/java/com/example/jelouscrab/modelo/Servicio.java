package com.example.jelouscrab.modelo;

public class Servicio {
    private String id, name, description;
    private byte[] image;

    public Servicio(String name, String description, byte[] image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Servicio(String id, String name, String description, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
