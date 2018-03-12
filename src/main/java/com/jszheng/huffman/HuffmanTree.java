package com.jszheng.huffman;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree extends BtDecorator<String> {

    private Map<Character, String> codewordMap = new HashMap<>();
    private Map<String, Character> deCodewordMap = new HashMap<>();
    private String data;

    public HuffmanTree(BinaryTree<String> component) {
        super(component);
    }

    @Override
    public BinaryTree<String> copy(boolean deep) {
        return null;
    }

    @Override
    public BinaryTree<String> newTree() {
        return null;
    }

    public String decode(String codewords) {
        StringBuilder buffer = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < codewords.length(); i++) {
            buffer.append(codewords.charAt(i));
            Character c = deCodewordMap.get(buffer.toString());
            if (c != null) {
                result.append(c);
                buffer.setLength(0);
            }
        }

        if (buffer.length() != 0 && Env.debug) {
            System.out.println("[error] 輸入內容包含「非此霍夫曼樹」之字元");
        }

        if (result.length() != 0)
            System.out.println("解碼：" + result);

        System.out.println();
        return result.toString();
    }

    public String encode() {
        if (data == null) {
            System.out.println("[encode] you need to insert data first.");
            return null;
        }
        int originalLength = data.length();
        System.out.println("原文：" + data);
        System.out.println("長度：" + data.getBytes().length + " bytes");
        System.out.println();
        System.out.print("編碼：");
        StringBuilder builder = new StringBuilder();
        char[] chars = data.toCharArray();
        for (char c : chars) {
            builder.append(getCodeword(c));
        }
        String result = builder.toString();
        System.out.println(result);

        double length = (result.length()) / (double) 8;

        double compress = (1 - (length / (double) originalLength)) * (double) 100;

        System.out.println("長度 (不計字碼表)： " + length + " bytes");
        System.out.println("壓縮率: " + String.format("%.2f", compress) + "%");
        System.out.println();
        return result;
    }

    @Override
    public String getNodeString(BinTreeNode<String> node) {
        HuffmanTreeNode hmNode = (HuffmanTreeNode) node;
        int weight = hmNode.getWeight();

        if (node.degree() == 0)
            return hmNode.getData() + "(" + weight + ")";
        else
            return String.valueOf(weight);
    }

    @Override
    public void insert(String... data) {
        super.insert(data);
        this.data = data[data.length - 1];
    }

    @Override
    protected InsertionAlgo<String> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new HuffmanInsertion();
        return insertionAlgo;
    }

    @Override
    protected SearchAlgo<String> createSearchAlgo() {
        return null;
    }

    @Override
    public HuffmanTreeNode newNode() {
        return new HuffmanTreeNode();
    }

    void clearCodewordMap() {
        codewordMap.clear();
    }

    /**
     * @param pq    最小加權優先佇列
     * @param count 執行次數：字元數 - 1
     */
    void huffmanAlgo(PriorityQueue<HuffmanTreeNode> pq, int count) {
        for (int i = 0; i < count; i++) {
            HuffmanTreeNode newNode = newNode();
            HuffmanTreeNode lChild = pq.poll();
            HuffmanTreeNode rChild = pq.poll();

            if (Env.debug) {
                String lNodeType = "內部";
                String rNodeType = "內部";
                String lData = lChild.getData();
                String rData = rChild.getData();
                if (lData != null) lNodeType = "外部";
                else lData = "";
                if (rData != null) rNodeType = "外部";
                else rData = "";
                System.out.println("[huffman] 取出最小加權" + lNodeType + "節點 " + lData + " 加權: " + lChild.getWeight());
                System.out.println("[huffman] 取出次小加權" + rNodeType + "節點 " + rData + " 加權: " + rChild.getWeight());
            }

            int weight = lChild.getWeight() + rChild.getWeight();
            newNode.setWeight(weight);
            if (Env.debug) {
                System.out.println("[huffman] 建立父節點，並設置加權值: " + weight);
            }

            if (i == count - 1) {
                newNode.setLeftChild(lChild);
                newNode.setRightChild(rChild);
                setRoot(newNode);
                if (Env.debug) {
                    System.out.println("[huffman] 將此父節點作為樹根");
                }

            } else {
                newNode.setLeftChild(lChild);
                newNode.setRightChild(rChild);
                pq.add(newNode);

                if (Env.debug) {
                    System.out.println("[huffman] 將此父節點加入最小加權優先佇列");
                }
            }
            if (Env.debug)
                System.out.println();
        }
    }

    void putCodeword(char c, String s) {
        codewordMap.put(c, s);
    }

    void putDeCodeword(String codeword, char c) {
        deCodewordMap.put(codeword, c);
    }

    private String getCodeword(char c) {
        return codewordMap.get(c);
    }
}
