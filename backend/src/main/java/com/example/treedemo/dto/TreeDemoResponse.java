package com.example.treedemo.dto;

import java.util.List;

public class TreeDemoResponse {
    public TreeNodeDto bstRoot;
    public List<TreeSnapshotDto> snapshots;

    public TreeDemoResponse() {}

    public TreeDemoResponse(TreeNodeDto bstRoot, List<TreeSnapshotDto> snapshots) {
        this.bstRoot = bstRoot;
        this.snapshots = snapshots;
    }
}
