package com.jszheng.huffman;

import com.jszheng.base.BinaryTree;
import com.jszheng.insertion.InsertionAlgo;

import java.util.*;

public class HuffmanInsertion implements InsertionAlgo<String> {

    @Override
    public boolean insert(BinaryTree<String> bt, String data) {
        HuffmanTree huffman = (HuffmanTree) bt;
        huffman.clearCodewordMap();

        char[] chars = data.toCharArray();
        CharFreq charFreq = tabulateCharFrequency(chars);

        HashMap<Character, Integer> map = charFreq.map;
        PriorityQueue<HuffmanTreeNode> pq = new PriorityQueue<>();
        List<HuffmanTreeNode> leaves = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            HuffmanTreeNode node = (HuffmanTreeNode) huffman.newNode();
            node.setData(entry.getKey().toString());
            node.setWeight(entry.getValue());

            pq.add(node);
            leaves.add(node);
        }

        int count = charFreq.charCount - 1;

        if (count < 1) {
            HuffmanTreeNode node = leaves.get(0);
            huffman.setRoot(node);
        } else {
            huffman.huffmanAlgo(pq, count);
        }

        for (HuffmanTreeNode node : leaves) {
            StringBuilder builder = new StringBuilder();
            HuffmanTreeNode curr = node;

            while (curr != null && curr.getParent() != null) {
                builder.append(curr.isLeftChild() ? "0" : "1");
                curr = (HuffmanTreeNode) curr.getParent();
            }

            char c = node.getData().charAt(0);
            String codeword = builder.reverse().toString();
            huffman.putCodeword(c, codeword);
            huffman.putDeCodeword(codeword, c);
        }

        return true;
    }


    private CharFreq tabulateCharFrequency(char[] chars) {
        // tabulate frequency counts
        int charCount = 0;
        HashMap<Character, Integer> map = new HashMap<>();

        for (char c : chars) {
            Integer val = map.get(c);
            if (val == null) {
                charCount++;
                val = 1;
                map.put(c, val);
            } else {
                val = val + 1;
                map.put(c, val);
            }
        }


        return new CharFreq(charCount, map);
    }

    private class CharFreq {
        int charCount;
        HashMap<Character, Integer> map;

        CharFreq(int charCount, HashMap<Character, Integer> map) {
            this.charCount = charCount;
            this.map = map;
        }
    }
}
