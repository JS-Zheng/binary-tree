package com.jszheng.traversal.in;

import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.AbstractRecursiveTraversal;

public class InOrderRecursiveTraversal<E> extends AbstractRecursiveTraversal<E> {

    @Override
    public void traverseTree(BinTreeNode<E> node) {
        if (node == null)
            return;

        traverseTree(node.getLeftChild()); // lChild

        dataList.add(node);
        getNodeHandler().handle(node);

        traverseTree(node.getRightChild());
    }
}
