package com.jszheng.base;

import com.jszheng.node.TreeNode;
import com.jszheng.printer.HorizontalTreePrinter;
import com.jszheng.printer.TreePrinter;

public interface Tree<E, Node extends TreeNode<E>> {

    // util generic method, no need follow generic type of class<E, Node>
    default <El extends Comparable<? super El>, Nd extends TreeNode<El>> Nd compareNode(Nd t1, Nd t2, boolean findGreater) {
        if (t2 == null) return t1;
        else if (t1 == null) return t2;

        El t1Data = t1.getData();
        El t2Data = t2.getData();

        if (t1Data == null && t2Data == null) return null;
        else if (t1Data == null) return t2;
        else if (t2Data == null) return t1;

        int compareResult = t1Data.compareTo(t2Data);
        boolean t1GreaterThanT2 = compareResult > 0;

        if (findGreater)
            return t1GreaterThanT2 ? t1 : t2;
        else
            return t1GreaterThanT2 ? t2 : t1;
    }

    default String getNodeString(Node node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() :
                (getRoot() == node ? "⊙" : " "); // Keep one space to mock null.
    }

    Node getRoot();

    void setRoot(Node node);

    void insert(E datum);

    void insert(E[] data);

    Node newNode();

    default void print() {
        TreePrinter printer = new HorizontalTreePrinter();
        printer.print(this);
    }

    default int size() {
        return size(getRoot());
    }

    int size(Node node);

    boolean isEmpty();
}
