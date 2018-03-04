package com.jszheng.insertion;

import com.jszheng.node.BinTreeNode;

// parent of insert-target
public class InsertionLocator<E> {
    boolean isLeft = false; // insert as left(right) child
    BinTreeNode<E> parentNode; // insert as child of parentNode
    int searchCount;

    public InsertionLocator(boolean isLeft, BinTreeNode<E> parentNode, int searchCount) {
        this.isLeft = isLeft;
        this.parentNode = parentNode;
        this.searchCount = searchCount;
    }
}
