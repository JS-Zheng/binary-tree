package com.jszheng.searchtree;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.rotation.*;

public abstract class SelfBalancingBst<E extends Comparable<? super E>, Base extends BinaryTree<E>> extends AbstractBst<E, Base> {

    protected SelfBalancingBst(Base component) {
        super(component);
    }

    public RotationState getRotationState(BinTreeNode<E> pivot, BinTreeNode<E> child) {
        RotationState state;

        boolean isPivotLeft = pivot.isLeftChild();
        boolean isChildLeft = child.isLeftChild();

        if (!isPivotLeft && !isChildLeft)
            state = new RrRotation();
        else if (isPivotLeft && isChildLeft)
            state = new LlRotation();
        else if (!isPivotLeft)
            state = new RlRotation();
        else
            state = new LrRotation();

        return state;
    }
}
