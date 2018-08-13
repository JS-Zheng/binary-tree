package com.jszheng.base;

import com.jszheng.node.BinTreeNode;
import com.jszheng.printer.BtPrinter;
import com.jszheng.printer.MyBtPrinter;
import com.jszheng.traversal.Traversable;

public interface BinaryTree<E> extends Tree<E, BinTreeNode<E>>, Traversable<E>, Cloneable {

    void clearRoot();

    BinaryTree<E> copy(boolean deep);

    default String getLevelString(int level) {
        return "";
    }

    BinTreeNode<E> getNodeByIndex(int index);

    // min is 1.
    int height();

    int height(BinTreeNode<E> node);

    int leavesCount();

    int leavesCount(BinTreeNode<E> node);

    int maxNodes();

    BinaryTree<E> newTree();

    BinTreeNode<E> search(E data);

    default void setDataArr(E[] arr) {
        setDataArr(arr, false);
    }

    void setDataArr(E[] data, boolean nullable);

    SkewedState skewedState();

    void swap();

    void swap(BinTreeNode<E> node);

    BinTreeNode<E> getLastNode();

    BinTreeNode<E> getRoot();

    default void print() {
        BtPrinter printer = new MyBtPrinter();
        printer.print(this);
    }

    void setRoot(E data);
}
