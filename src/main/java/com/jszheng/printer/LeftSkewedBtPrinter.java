package com.jszheng.printer;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

class LeftSkewedBtPrinter implements BtPrinter {

    @Override
    public String getPrintString(BinaryTree bt) {
        if (bt == null || bt.isEmpty())
            return "Tree is empty.";

        StringBuilder builder = new StringBuilder();

        BinTreeNode root = bt.getRoot();
        BinTreeNode currentNode = bt.getLastNode();
        int lastLineLen = 0;

        while (currentNode != null) {
            // Print Data.
            String str = bt.getNodeString(currentNode);
            builder.insert(0, str + "\n");

            // Print Margin-left.
            for (int i = 0; i < lastLineLen; i++)
                builder.insert(0, " ");

            // Print Edge (slash).
            if (currentNode.getParent() == null || currentNode == root) break;
            builder.insert(0, "/" + "\n");

            // Print Margin-left of edge.
            lastLineLen += str.length();

            for (int i = 0; i < lastLineLen; i++)
                builder.insert(0, " ");

            lastLineLen++;

            currentNode = currentNode.getParent();
        }

        return builder.toString();
    }
}
