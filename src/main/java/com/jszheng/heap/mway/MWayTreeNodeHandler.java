package com.jszheng.heap.mway;

public interface MWayTreeNodeHandler<Node extends AbstractMWayTreeNode<E, Node>, E extends Comparable<? super E>> {

    boolean handle(Node node);
}
