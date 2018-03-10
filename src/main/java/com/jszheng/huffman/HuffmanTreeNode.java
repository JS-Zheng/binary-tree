package com.jszheng.huffman;

import com.jszheng.base.LinkedTreeNode;

public class HuffmanTreeNode extends LinkedTreeNode<String> implements Comparable<HuffmanTreeNode> {

    private int weight = 0;

    HuffmanTreeNode() {
        super();
    }

    @Override
    public int compareTo(HuffmanTreeNode n2) {
        Integer w1 = weight;
        Integer w2 = n2.getWeight();

        return w1.compareTo(w2);
    }

    public int getWeight() {
        return weight;
    }

    void setWeight(int weight) {
        this.weight = weight;
    }
}
