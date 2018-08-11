package com.jszheng.searchtree.splay;

import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BstInsertion;

class SplayInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        getBt().fixAfterOperation(newNode);
    }

    @Override
    protected SplayTree<E> getBt() {
        return (SplayTree<E>) super.getBt();
    }
}
