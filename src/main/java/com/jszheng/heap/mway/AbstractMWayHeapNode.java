package com.jszheng.heap.mway;

public abstract class AbstractMWayHeapNode<E extends Comparable<? super E>, Self extends AbstractMWayTreeNode<E, Self>>
        extends AbstractMWayTreeNode<E, Self> implements Comparable<Self> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    AbstractMWayHeapNode() {
        // Need to make sure subClz set the self-type correctly
        this.lLink = (Self) this;
        this.rLink = (Self) this;
    }

    @Override
    public int compareTo(Self node) {
        if (node == null || node.data == null) return 1;
        return data.compareTo(node.data);
    }
}
