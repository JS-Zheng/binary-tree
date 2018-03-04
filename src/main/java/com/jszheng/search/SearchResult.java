package com.jszheng.search;

import com.jszheng.node.BinTreeNode;

public class SearchResult<E> {

    private BinTreeNode<E> node;
    private int searchCount;

    public BinTreeNode<E> getNode() {
        return node;
    }

    public void setNode(BinTreeNode<E> node) {
        this.node = node;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }
}
