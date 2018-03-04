package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

public class LlRotation extends AbstractRotation {

    @Override
    <E> BinTreeNode<E> rotateTree(BinaryTree<E> bt, BinTreeNode<E> parent) {
        BinTreeNode<E> pivot = parent.getLeftChild();
        if (pivot == null) return null;

        BinTreeNode<E> child = pivot.getRightChild();

        if (Env.debug)
            System.out.println("[rotate] LL case: pivot is " + pivot.getData());

        rotateRight(parent, pivot, child); // LL

        return pivot;
    }
}
