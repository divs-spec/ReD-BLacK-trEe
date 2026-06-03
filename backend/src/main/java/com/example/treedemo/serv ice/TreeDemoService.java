package com.example.treedemo.service;

import com.example.treedemo.dto.TreeDemoResponse;
import com.example.treedemo.dto.TreeNodeDto;
import com.example.treedemo.dto.TreeSnapshotDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeDemoService {

    private static final String BST = "BST";

    public TreeDemoResponse buildDemo(List<Integer> values) {
        if (values == null) {
            values = List.of();
        }

        BstNode bstRoot = null;
        RedBlackTree rbTree = new RedBlackTree();
        List<TreeSnapshotDto> snapshots = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);

            bstRoot = insertBst(bstRoot, value);
            rbTree.insert(value);

            snapshots.add(new TreeSnapshotDto(
                    i + 1,
                    "Inserted " + value,
                    value,
                    rbTree.toDto()
            ));
        }

        return new TreeDemoResponse(toDto(bstRoot), snapshots);
    }

    private BstNode insertBst(BstNode root, int value) {
        if (root == null) return new BstNode(value);
        if (value < root.value) {
            root.left = insertBst(root.left, value);
        } else {
            root.right = insertBst(root.right, value); // duplicates go right
        }
        return root;
    }

    private TreeNodeDto toDto(BstNode node) {
        if (node == null) return null;
        TreeNodeDto dto = new TreeNodeDto(node.value, BST);
        dto.left = toDto(node.left);
        dto.right = toDto(node.right);
        return dto;
    }

    private static class BstNode {
        int value;
        BstNode left;
        BstNode right;

        BstNode(int value) {
            this.value = value;
        }
    }

    private static class RedBlackTree {
        private static final String RED = "RED";
        private static final String BLACK = "BLACK";

        private static class Node {
            int value;
            String color;
            Node left;
            Node right;
            Node parent;

            Node(int value, String color) {
                this.value = value;
                this.color = color;
            }
        }

        private final Node NIL = new Node(0, BLACK);
        private Node root = NIL;

        RedBlackTree() {
            NIL.left = NIL;
            NIL.right = NIL;
            NIL.parent = NIL;
        }

        void insert(int value) {
            Node z = new Node(value, RED);
            z.left = NIL;
            z.right = NIL;
            z.parent = NIL;

            Node y = NIL;
            Node x = root;

            while (x != NIL) {
                y = x;
                if (z.value < x.value) {
                    x = x.left;
                } else {
                    x = x.right; // duplicates go right
                }
            }

            z.parent = y;
            if (y == NIL) {
                root = z;
            } else if (z.value < y.value) {
                y.left = z;
            } else {
                y.right = z;
            }

            insertFixup(z);
        }

        private void insertFixup(Node z) {
            while (RED.equals(z.parent.color)) {
                if (z.parent == z.parent.parent.left) {
                    Node y = z.parent.parent.right;
                    if (RED.equals(y.color)) {
                        z.parent.color = BLACK;
                        y.color = BLACK;
                        z.parent.parent.color = RED;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.right) {
                            z = z.parent;
                            leftRotate(z);
                        }
                        z.parent.color = BLACK;
                        z.parent.parent.color = RED;
                        rightRotate(z.parent.parent);
                    }
                } else {
                    Node y = z.parent.parent.left;
                    if (RED.equals(y.color)) {
                        z.parent.color = BLACK;
                        y.color = BLACK;
                        z.parent.parent.color = RED;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.left) {
                            z = z.parent;
                            rightRotate(z);
                        }
                        z.parent.color = BLACK;
                        z.parent.parent.color = RED;
                        leftRotate(z.parent.parent);
                    }
                }
            }
            root.color = BLACK;
        }

        private void leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != NIL) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == NIL) {
                root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        private void rightRotate(Node x) {
            Node y = x.left;
            x.left = y.right;
            if (y.right != NIL) {
                y.right.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == NIL) {
                root = y;
            } else if (x == x.parent.right) {
                x.parent.right = y;
            } else {
                x.parent.left = y;
            }
            y.right = x;
            x.parent = y;
        }

        TreeNodeDto toDto() {
            return toDto(root);
        }

        private TreeNodeDto toDto(Node node) {
            if (node == NIL) return null;
            TreeNodeDto dto = new TreeNodeDto(node.value, node.color);
            dto.left = toDto(node.left);
            dto.right = toDto(node.right);
            return dto;
        }
    }
}
