package com.jszheng.traversal.post;

import com.jszheng.node.BinTreeNode;
import com.jszheng.traversal.AbstractRecursiveTraversal;

public class PostOrderRecursiveTraversal<E> extends AbstractRecursiveTraversal<E> {

    @Override
    public void traverseTree(BinTreeNode<E> node) {
        if (node == null)
            return;

        traverseTree(node.getLeftChild()); // lChild

        traverseTree(node.getRightChild());

        dataList.add(node);
        getNodeHandler().handle(node);
    }
}
