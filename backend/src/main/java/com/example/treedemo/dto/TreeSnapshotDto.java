package com.example.treedemo.dto;

public class TreeSnapshotDto {
    public int step;
    public String label;
    public int insertedValue;
    public TreeNodeDto root;

    public TreeSnapshotDto() {}

    public TreeSnapshotDto(int step, String label, int insertedValue, TreeNodeDto root) {
        this.step = step;
        this.label = label;
        this.insertedValue = insertedValue;
        this.root = root;
    }
}
