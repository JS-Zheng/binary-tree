package com.jszheng.searchtree.splay;

import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstInsertion;

class SplayInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    @Override
    protected SplayTree<E> getBt() {
        return (SplayTree<E>) super.getBt();
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        getBt().fixAfterOperation(newNode);
    }
}
