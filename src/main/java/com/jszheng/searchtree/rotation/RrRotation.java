package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

public class RrRotation extends AbstractRotation {

    @Override
    <E> BinTreeNode<E> rotateTree(BinaryTree<E> bt, BinTreeNode<E> parent) {
        BinTreeNode<E> pivot = parent.getRightChild();
        if (pivot == null) return null;

        BinTreeNode<E> child = pivot.getLeftChild();

        if (Env.debug)
            System.out.println("[rotate] RR case: pivot is " + pivot.getData());

        rotateLeft(parent, pivot, child); // RR

        return pivot;
    }
}
