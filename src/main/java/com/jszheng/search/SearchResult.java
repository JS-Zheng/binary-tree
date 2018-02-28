package com.jszheng.search;

import com.jszheng.node.TreeNode;

public class SearchResult<E> {

    private TreeNode<E> node;
    private int searchCount;

    public TreeNode<E> getNode() {
        return node;
    }

    public void setNode(TreeNode<E> node) {
        this.node = node;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }
}
