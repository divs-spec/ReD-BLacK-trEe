package com.example.treedemo.dto;

public class TreeNodeDto {
    public int value;
    public String color; // "BST", "RED", "BLACK"
    public TreeNodeDto left;
    public TreeNodeDto right;

    public TreeNodeDto() {}

    public TreeNodeDto(int value, String color) {
        this.value = value;
        this.color = color;
    }
}
