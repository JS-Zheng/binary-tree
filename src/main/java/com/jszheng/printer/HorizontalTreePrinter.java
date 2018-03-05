package com.jszheng.printer;

import com.jszheng.base.Tree;
import com.jszheng.node.TreeNode;

import java.util.List;

/*
 * Adapted from VasyaNovikov
 * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class HorizontalTreePrinter implements TreePrinter {

    private StringBuilder result;
    private Tree tree;

    @Override
    public String getPrintString(Tree tree) {
        if (tree == null || tree.isEmpty())
            return "Tree is empty.";
        this.tree = tree;
        result = new StringBuilder();
        print(tree.getRoot(), "", true);
        return result.toString();
    }

    private List<TreeNode> createChildList(TreeNode node) {
        return node.getChildren();
    }

    private void print(TreeNode node, String prefix, boolean isTail) {
        List<TreeNode> children = createChildList(node);

        result.append(prefix).append(isTail ? "└── " : "├── ")
                .append(tree.getNodeString(node)).append("\n");

        for (int i = 0; i < children.size() - 1; i++)
            print(children.get(i), prefix + (isTail ? "    " : "│   "), false);

        if (children.size() > 0) {
            print(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
