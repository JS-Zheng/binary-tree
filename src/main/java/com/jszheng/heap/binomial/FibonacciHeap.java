package com.jszheng.heap.binomial;

public class FibonacciHeap<E extends Comparable<? super E>> extends BinomialHeap<E> {

    public FibonacciHeap() {
    }

    public FibonacciHeap(boolean mergeWithSort) {
        super(mergeWithSort);
    }

    @Override
    protected void insertData(E value) {
        BinomialTreeNode<E> node = newNode();
        node.data = value;

        if (isEmpty())
            setRoot(node);
        else {
            concatRootList(node);
            E rootData = root.data;
            if (value.compareTo(rootData) < 0)
                root = node;
        }
    }
}