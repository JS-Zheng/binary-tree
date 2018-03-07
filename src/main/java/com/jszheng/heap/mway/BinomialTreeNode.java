package com.jszheng.heap.mway;

public class BinomialTreeNode<E extends Comparable<? super E>> extends AbstractMWayHeapNode<E, BinomialTreeNode<E>> {

    // 在成為其他節點的子節點後，是否曾失去過 child
    public boolean childCut;

    BinomialTreeNode() {
        this.lLink = this;
        this.rLink = this;
    }

    public boolean isChildCut() {
        return childCut;
    }
}
