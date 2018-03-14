package com.jszheng.search;

import com.jszheng.node.BinTreeNode;

public class SearchResult<E> {

    private BinTreeNode<E> lastNode;
    private BinTreeNode<E> node;
    private int searchCount;
    private boolean nextLeft;

    public SearchResult() {
    }

    public SearchResult(boolean nextLeft, BinTreeNode<E> lastNode, int searchCount) {
        this.lastNode = lastNode;
        this.searchCount = searchCount;
        this.nextLeft = nextLeft;
    }

    public BinTreeNode<E> getLastNode() {
        return lastNode;
    }

    public void setLastNode(BinTreeNode<E> lastNode) {
        this.lastNode = lastNode;
    }

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

    public boolean isNextLeft() {
        return nextLeft;
    }

    public void setNextLeft(boolean nextLeft) {
        this.nextLeft = nextLeft;
    }
}
