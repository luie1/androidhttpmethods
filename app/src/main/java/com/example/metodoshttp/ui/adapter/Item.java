package com.example.metodoshttp.ui.adapter;

public class Item {
    private String id;
    private String name;
    private String email;
    private int type;
    public Item(String id, String name, String email, int type){
        this.id=id;
        this.name=name;
        this.email=email;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }
}
