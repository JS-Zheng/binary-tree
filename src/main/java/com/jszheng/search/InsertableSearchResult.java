package com.jszheng.search;

import com.jszheng.node.BinTreeNode;

public class InsertableSearchResult<E> extends SearchResult<E> {

    private BinTreeNode<E> parentNode;
    private boolean nextLeft;

    public InsertableSearchResult(boolean nextLeft, BinTreeNode<E> parentNode, int searchCount) {
        super(searchCount);
        this.parentNode = parentNode;
        this.nextLeft = nextLeft;
    }

    public BinTreeNode<E> getParentNode() {
        return parentNode;
    }


    public boolean isNextLeft() {
        return nextLeft;
    }
}
