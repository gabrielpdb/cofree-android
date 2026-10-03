package com.example.cofree;

public class SelectOption {
    private int id;
    private String name;
    private String rightInfo;

    public SelectOption(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SelectOption(int id, String name, String rightInfo) {
        this.id = id;
        this.name = name;
        this.rightInfo = rightInfo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRightInfo() {
        return rightInfo;
    }

    @Override
    public String toString() {
        return name;
    }

}
