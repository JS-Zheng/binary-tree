package com.jszheng.base;

import com.jszheng.node.TreeNode;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

public interface Tree<E, Node extends TreeNode<E>> {

    default String getNodeString(Node node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() :
                (getRoot() == node ? "⊙" : " "); // Keep one space to mock null.
    }

    @SuppressWarnings("unchecked")
    void insert(E... data);

    Node newNode();

    default void print() {
        TreePrinter printer = new HorizontalTreePrinter();
        printer.print(this);
    }

    default int size() {
        return size(getRoot());
    }

    int size(Node node);

    Node getRoot();

    boolean isEmpty();
}
