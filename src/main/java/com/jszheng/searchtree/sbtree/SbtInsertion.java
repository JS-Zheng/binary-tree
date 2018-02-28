package com.jszheng.searchtree.sbtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstInsertion;

/*
 * O(Log n)
 */
class SbtInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    @Override
    protected SizeBalancedTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (SizeBalancedTree<E>) bt;
    }

    @Override
    protected void fixAfterInsertion(TreeNode<E> newNode) {
        SizeBalancedTree<E> sbt = getBt();
        TreeNode<E> parent = newNode.getParent();

        while (parent != null) {
            int compareResult = newNode.getData().compareTo(parent.getData());
            boolean isNewNodeInLeftSubTree = compareResult < 0;
            sbt.maintain(parent, isNewNodeInLeftSubTree);
            parent = parent.getParent();
        }
    }
}
