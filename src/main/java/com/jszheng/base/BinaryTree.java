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

    // root is 1.
    int height();

    int height(BinTreeNode<E> node);

    int leavesCount();

    int leavesCount(BinTreeNode<E> node);

    int maxCount();

    BinaryTree<E> newTree();

    default void print() {
        BtPrinter printer = new MyBtPrinter();
        printer.print(this);
    }

    BinTreeNode<E> getRoot();

    void setRoot(E data);

    BinTreeNode<E> search(E data);

    void setDataByArr(E[] data);

    SkewedState skewedState();

    void swap();

    void swap(BinTreeNode<E> node);

    BinTreeNode<E> getLastNode();
}
