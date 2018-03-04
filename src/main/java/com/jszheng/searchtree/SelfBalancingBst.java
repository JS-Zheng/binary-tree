package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.rotation.*;

public class SelfBalancingBst<E extends Comparable<? super E>> extends BinarySearchTree<E> {

    protected SelfBalancingBst(BinaryTree<E> component) {
        super(component);
    }

    public RotationState getRotationState(BinTreeNode<E> pivot, BinTreeNode<E> child) {
        RotationState state;

        boolean isPivotLeft = pivot.isLeftChild();
        boolean isChildLeft = child.isLeftChild();

        if (!isPivotLeft && !isChildLeft)
            state = createRrRotation();
        else if (isPivotLeft && isChildLeft)
            state = createLlRotation();
        else if (!isPivotLeft)
            state = createRlRotation();
        else state = createLrRotation();

        return state;
    }

    private RotationState createLlRotation() {
        return new LlRotation();
    }

    private RotationState createLrRotation() {
        return new LrRotation();
    }

    private RotationState createRlRotation() {
        return new RlRotation();
    }

    private RotationState createRrRotation() {
        return new RrRotation();
    }
}
