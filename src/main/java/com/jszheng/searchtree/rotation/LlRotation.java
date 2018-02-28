package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

public class LlRotation extends AbstractRotation {

    @Override
    <E> TreeNode<E> rotateTree(BinaryTree<E> bt, TreeNode<E> parent) {
        TreeNode<E> pivot = parent.getLeftChild();
        if (pivot == null) return null;

        TreeNode<E> child = pivot.getRightChild();

        if (Env.debug)
            System.out.println("LL Case: Pivot is " + pivot.getData() + ".\n");

        rotateRight(parent, pivot, child); // LL

        return pivot;
    }
}
