package com.jszheng.base;

import com.jszheng.node.TreeNode;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

public interface Tree<E> {

    @SuppressWarnings("unchecked")
    void insert(E... data);

    default void print() {
        TreePrinter printer = new HorizontalTreePrinter();
        printer.print(this);
    }

    TreeNode<E> getRoot();

    boolean isEmpty();
}
