package com.jszheng.searchtree.avl;

import com.jszheng.base.BinaryTree;
import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.SelfBalancingBst;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.util.FibonacciNumber;

import java.math.BigInteger;

public class AvlTree<E extends Comparable<? super E>> extends SelfBalancingBst<E, BinaryTree<E>> {

    public AvlTree(BinaryTree<E> component) {
        super(component);
    }

    @Override
    public BinaryTree<E> copy(boolean deep) {
        BinaryTree<E> base = component.copy(deep);
        return new AvlTree<>(base);
    }

    public int getMaxHeightToConstructAvl(int nodeCount) {
        int i = 0;
        while ((FibonacciNumber.getNumber(i + 2).subtract(BigInteger.ONE)).intValue() < nodeCount) {
            i++;
        }
        return i - 1;
    }

    public int getMaxNodeCountToConstructAvl(int height) {
        return (int) Math.pow(2, height) - 1;
    }

    public int getMinHeightToConstructAvl(int nodeCount) {
        return BinaryTreeLemma.getHeightByNodeCount(nodeCount);
    }

    public int getMinNodeCountToConstructAvl(int height) {
        return FibonacciNumber.getNumber(height + 2).subtract(BigInteger.ONE).intValue();
    }

    @Override
    public String getNodeString(BinTreeNode<E> node) {
        E data = node != null ? node.getData() : null;
        int bf = getBalanceFactor(node);
        return data != null ? data.toString() + (bf == 0 ? "" : "(" + bf + ")") : " ";
    }

    public int getBalanceFactor(BinTreeNode<E> node) {
        int hL = height(node.getLeftChild());
        int hR = height(node.getRightChild());

        return hL - hR;
    }

    @Override
    public AvlTree<E> newTree() {
        return new AvlTree<>(component.newTree());
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new AvlInsertion<>();
        return insertionAlgo;
    }

    @Override
    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new AvlDeletion<>();
        return deletionAlgo;
    }

    void handleUnbalancedNode(BinTreeNode<E> unbalancedNode, int bf, boolean isGrandChildLeft) {
        if (unbalancedNode == null)
            throw new RuntimeException("Unbalanced Node cannot be null.");

        RotationState state = null;

        switch (bf) {
            case 2: // L
                if (isGrandChildLeft)
                    state = createLlRotate(); // LL
                else
                    state = createLrRotate(); // LR
                break;
            case -2: // R
                if (!isGrandChildLeft)
                    state = createRrRotate(); // RR
                else
                    state = createRlRotate(); // RL
        }

        assert state != null;
        state.rotate(this, unbalancedNode);
    }

}
