package com.jszheng.traversal.pre;

import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.AbstractRecursiveTraversal;

public class PreOrderRecursiveTraversal<E> extends AbstractRecursiveTraversal<E> {

    @Override
    public void traverseTree(BinTreeNode<E> node) {
        if (node == null)
            return;

        dataList.add(node);
        getNodeHandler().handle(node);

        traverseTree(node.getLeftChild()); // lChild

        traverseTree(node.getRightChild()); // rChild
    }
}
