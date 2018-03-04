package com.jszheng.base;

import com.jszheng.node.BinTreeNode;
import com.jszheng.printer.BtPrinter;
import com.jszheng.printer.MyBtPrinter;
import com.jszheng.traversal.Traversable;

public interface BinaryTree<E> extends Tree<E>, Traversable<E>, Cloneable {

    void clearRoot();

    BinaryTree<E> copy(boolean deep);

    int count();

    int count(BinTreeNode<E> node);

    default String getLevelString(int level) {
        return "";
    }

    BinTreeNode<E> getNodeByIndex(int index);

    default String getNodeString(BinTreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() :
                (getRoot() == node ? "⊙" : " "); // Keep one space to mock null.
    }

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

    void setRoot(BinTreeNode<E> root);

    SkewedState skewedState();

    void swap();

    void swap(BinTreeNode<E> node);

    BinTreeNode<E> getLastNode();
}
