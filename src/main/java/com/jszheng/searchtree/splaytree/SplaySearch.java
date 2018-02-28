package com.jszheng.searchtree.splaytree;

import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstSearch;

public class SplaySearch<E extends Comparable<? super E>> extends BstSearch<E> {

    @Override
    protected SplayTree<E> getBt() {
        return (SplayTree<E>) super.getBt();
    }

    @Override
    protected void fixAfterSearch(TreeNode<E> node) {
        super.fixAfterSearch(node);
        getBt().fixAfterOperation(node);
    }

}
