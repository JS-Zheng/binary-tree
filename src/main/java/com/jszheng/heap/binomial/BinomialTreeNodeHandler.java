package com.jszheng.heap.binomial;

public interface BinomialTreeNodeHandler<E extends Comparable<? super E>> {

    boolean handle(BinomialTreeNode<E> node);
}
