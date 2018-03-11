package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

abstract class AbstractRotation implements RotationState {

    @Override
    public <E> void rotate(BinaryTree<E> bt, BinTreeNode<E> parent) {
        BinTreeNode<E> grandParent = parent.getParent();
        boolean orinLeft = parent.isLeftChild();

        BinTreeNode<E> pivot = rotateTree(bt, parent);

        if (pivot == null) return;

        setGrandParent(grandParent, orinLeft, pivot);

        if (grandParent == null)
            bt.setRoot(pivot);
    }

    /*
         * Tri-node restructuring
         *
         * Original:
         *
         *      A (Parent)
         *     / \
         *    /   \
         *   /     \
         *  B       C (Pivot)
         *         / \
         *        D   E
         *
         *
         * Result:
         *
         *       C
         *      / \
         *     /   \
         *    /     \
         *   A       E
         *  / \
         * B   D
         *
         */
    <E> void rotateLeft(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> lChild) {
        if (Env.debug) Env.rotateLeftCount++;
        parent.setRightChild(lChild);
        pivot.setLeftChildWithIndex(parent);
    }

    /*
     * Tri-node restructuring
     *
     * Original:
     *
     *             A (Parent)
     *            / \
     *           /   \
     *          /     \
     * (Pivot) B       C
     *        / \
     *       D   E
     *
     *
     * Result:
     *
     *     B
     *    / \
     *   /   \
     *  /     \
     * D       A
     *        / \
     *       E   C
     *
     */
    <E> void rotateRight(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> rChild) {
        if (Env.debug) Env.rotateRightCount++;
        parent.setLeftChild(rChild);
        pivot.setRightChildWithIndex(parent);
    }

    abstract <E> BinTreeNode<E> rotateTree(BinaryTree<E> bt, BinTreeNode<E> parent);

    private <E> void setGrandParent(BinTreeNode<E> grandParent, boolean orinLeft, BinTreeNode<E> newParent) {
        if (grandParent != null) {
            if (orinLeft)
                grandParent.setLeftChildWithIndex(newParent);
            else
                grandParent.setRightChildWithIndex(newParent);
        } else {
            newParent.deleteParentAndCheckItsChild();
        }
    }
}
