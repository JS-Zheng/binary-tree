package com.jszheng.insertion;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

public abstract class AbstractInsertionAlgo<E> implements InsertionAlgo<E> {

    private BinaryTree<E> bt;

    @Override
    public boolean insert(BinaryTree<E> bt, E data) {
        if (bt == null) return false;

        this.bt = bt;

        if (bt.isEmpty()) {
            bt.setRoot(data);
            return true;
        }

        // main hook
        InsertionLocator<E> result = insertData(data);

        boolean isLeft;
        TreeNode<E> targetParentNode;

        if (result == null || (targetParentNode = result.parentNode) == null)
            return false;

        isLeft = result.isLeft;

        TreeNode<E> newNode = targetParentNode.newNode();
        newNode.setData(data);

        if (isLeft) {
            newNode.setLeftChild(targetParentNode.getLeftChild());
            targetParentNode.setLeftChildWithIndex(newNode);
        } else {
            newNode.setRightChild(targetParentNode.getRightChild());
            targetParentNode.setRightChildWithIndex(newNode);
        }

        // Some Adjust
        fixAfterInsertion(newNode);

        return true;
    }

    protected BinaryTree<E> getBt() {
        return bt;
    }

    protected void fixAfterInsertion(TreeNode<E> newNode) {
        // default do nothing.
    }

    // Find parent of insert target (before insert)
    protected abstract InsertionLocator<E> insertData(E data);

    protected boolean validateBt(E data) {
        return bt != null;
    }
}
