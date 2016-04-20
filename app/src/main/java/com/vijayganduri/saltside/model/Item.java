package com.vijayganduri.saltside.model;

public class Item {

    private String image;
    private String description;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

