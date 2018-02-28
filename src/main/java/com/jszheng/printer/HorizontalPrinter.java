package com.jszheng.printer;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Code by VasyaNovikov (StackOverflow).
 * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 */
public class HorizontalPrinter implements BtPrinter {

    private StringBuilder result;

    @Override
    public String getPrintString(BinaryTree bt) {
        if (bt == null || bt.isEmpty())
            return "Tree is empty.";

        result = new StringBuilder();
        print(bt.getRoot(), " ", true);
        return result.toString();
    }

    private List<TreeNode> createChildList(TreeNode node) {
        List<TreeNode> children = new ArrayList<>();
        if (node.hasLeftChild()) children.add(node.getLeftChild());
        if (node.hasRightChild()) children.add(node.getRightChild());
        return children;
    }

    private void print(TreeNode node, String prefix, boolean isTail) {
        List<TreeNode> children = createChildList(node);

        result.append(prefix).append(isTail ? "└── " : "├── ")
                .append(node.getData()).append("\n");

        for (int i = 0; i < children.size() - 1; i++) {
            print(children.get(i), prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            print(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
