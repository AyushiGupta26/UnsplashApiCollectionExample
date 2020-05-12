package com.example.bipolarfactoryassisgnment;

public class ListItem {
    private String name;
    private String img;

    public ListItem(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public ListItem(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
