package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

public class RrRotation extends AbstractRotation {

    @Override
    <E> TreeNode<E> rotateTree(BinaryTree<E> bt, TreeNode<E> parent) {
        TreeNode<E> pivot = parent.getRightChild();
        if (pivot == null) return null;

        TreeNode<E> child = pivot.getLeftChild();

        if (Env.debug)
            System.out.println("[rotate] RR case: pivot is " + pivot.getData());

        rotateLeft(parent, pivot, child); // RR

        return pivot;
    }
}
