package com.jszheng.insertion;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.InsertableSearchResult;

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
        InsertableSearchResult<E> result = insertData(data);

        boolean isLeft;
        BinTreeNode<E> targetParentNode;

        if (result == null
                || (targetParentNode = result.getParentNode()) == null)
            return false;

        if (!isAllowDuplicated() && result.getNode() != null) {
            if (Env.debug)
                System.out.println("[failure] key already exist");

            return false;
        }

        isLeft = result.isNextLeft();

        BinTreeNode<E> newNode = targetParentNode.newNode();
        newNode.setData(data);

        if (isLeft) {
            newNode.setLeftChild(targetParentNode.getLeftChild());
            targetParentNode.setLeftChild(newNode);
        } else {
            newNode.setRightChild(targetParentNode.getRightChild());
            targetParentNode.setRightChild(newNode);
        }

        // Some Adjust
        fixAfterInsertion(newNode);

        return true;
    }

    // Find parent of insert target (before insert)
    protected abstract InsertableSearchResult<E> insertData(E data);

    protected boolean isAllowDuplicated() {
        return false;
    }

    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        // default do nothing.
    }

    protected BinaryTree<E> getBt() {
        return bt;
    }

    protected boolean validateBt(E data) {
        return bt != null;
    }
}
