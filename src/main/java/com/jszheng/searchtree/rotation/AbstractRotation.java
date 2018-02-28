package com.jszheng.searchtree.rotation;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

abstract class AbstractRotation implements RotationState {

    @Override
    public <E> void rotate(BinaryTree<E> bt, TreeNode<E> parent) {
        TreeNode<E> grandParent = parent.getParent();
        boolean orinLeft = parent.isLeftChild();

        TreeNode<E> pivot = rotateTree(bt, parent);

        if (pivot == null) return;

        setGrandParent(grandParent, orinLeft, pivot);

        if (grandParent == null)
            bt.setRoot(pivot);
    }

    private <E> void setGrandParent(TreeNode<E> grandParent, boolean orinLeft, TreeNode<E> newParent) {
        if (grandParent != null) {
            if (orinLeft)
                grandParent.setLeftChildWithIndex(newParent);
            else
                grandParent.setRightChildWithIndex(newParent);
        } else {
            newParent.deleteParent();
        }
    }

    abstract <E> TreeNode<E> rotateTree(BinaryTree<E> bt, TreeNode<E> parent);

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
    <E> void rotateLeft(TreeNode<E> parent, TreeNode<E> pivot, TreeNode<E> lChild) {
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
    <E> void rotateRight(TreeNode<E> parent, TreeNode<E> pivot, TreeNode<E> rChild) {
        parent.setLeftChild(rChild);
        pivot.setRightChildWithIndex(parent);
    }
}
