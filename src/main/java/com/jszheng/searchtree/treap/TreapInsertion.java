package com.jszheng.searchtree.treap;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstInsertion;

/*
 * O(Log n)
 */
public class TreapInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    private boolean specifiedPriority = false;
    private boolean minHeapImplement = true;
    private int priority;

    public TreapInsertion(boolean minHeapImplement) {
        this.minHeapImplement = minHeapImplement;
    }

    public TreapInsertion(boolean minHeapImplement, int priority) {
        this.minHeapImplement = minHeapImplement;
        this.priority = priority;
        specifiedPriority = true;
    }

    @Override
    protected Treap<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (Treap<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        Treap<E> treap = getBt();
        if (specifiedPriority)
            treap.putPriority(newNode, priority);
        else
            treap.putRandomPriority(newNode);

        treap.upHeap(newNode, !minHeapImplement);
    }
}
