package com.jszheng.huffman;

import com.jszheng.base.LinkedBinaryTree;

public class HuffmanBase extends LinkedBinaryTree<String> {

    @Override
    public HuffmanBase newTree() {
        return new HuffmanBase();
    }

    @Override
    public HuffmanTreeNode getRoot() {
        return (HuffmanTreeNode) super.getRoot();
    }

    @Override
    public HuffmanTreeNode newNode() {
        return new HuffmanTreeNode();
    }
}
