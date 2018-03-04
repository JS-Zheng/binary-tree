package com.jszheng.printer;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

class RightSkewedBtPrinter implements BtPrinter {

    @Override
    public String getPrintString(BinaryTree bt) {
        if (bt == null || bt.isEmpty())
            return "Tree is empty.";

        StringBuilder builder = new StringBuilder();

        BinTreeNode root = bt.getRoot();
        BinTreeNode currentNode = root.getRightChild();

        String rootStr = bt.getNodeString(root);

        builder.append(rootStr).append("\n");

        int lastLineLen = rootStr.length();

        while (currentNode != null) {
            // Print Margin-left of edge.
            for (int i = 0; i < lastLineLen; i++)
                builder.append(" ");

            // Print Edge (Slash).
            builder.append("\\").append("\n");
            lastLineLen++;

            // Print Margin-left.
            for (int i = 0; i < lastLineLen; i++)
                builder.append(" ");

            // Print Data.
            String str = bt.getNodeString(currentNode);
            builder.append(str).append("\n");
            lastLineLen += str.length();

            if (currentNode == root) break;
            currentNode = currentNode.getRightChild();

        }

        return builder.toString();
    }
}
