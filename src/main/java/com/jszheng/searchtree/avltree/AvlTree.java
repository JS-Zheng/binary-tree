package com.jszheng.searchtree.avltree;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.SelfBalancingBst;
import com.jszheng.searchtree.rotation.*;
import com.jszheng.util.FibonacciNumber;

import java.math.BigInteger;

public class AvlTree<E extends Comparable<? super E>> extends SelfBalancingBst<E> {

    public AvlTree(BinaryTree<E> component) {
        super(component);
    }

    public int getBalanceFactor(TreeNode<E> node) {
        int hL = height(node.getLeftChild());
        int hR = height(node.getRightChild());

        return hL - hR;
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
    public String getNodeString(TreeNode<E> node) {
        E data = node != null ? node.getData() : null;
        int bf = getBalanceFactor(node);
        return data != null ? data.toString() + (bf == 0 ? "" : "(" + bf + ")") : " ";
    }

    @Override
    public AvlTree<E> newTree() {
        return new AvlTree<>(component.newTree());
    }

    @Override
    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new AvlDeletion<>();
        return deletionAlgo;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new AvlInsertion<>();
        return insertionAlgo;
    }

    TreeNode<E> getHigherChild(TreeNode<E> parent) {
        TreeNode<E> lChild = parent.getLeftChild();
        TreeNode<E> rChild = parent.getRightChild();

        if (lChild == null && rChild == null) {
            return null;
        } else if (lChild == null) {
            return rChild;
        } else if (rChild == null) {
            return lChild;
        }

        int lChildHeight = height(lChild);
        int rChildHeight = height(rChild);

        if (lChildHeight > rChildHeight)
            return lChild;
        else if (lChildHeight < rChildHeight)
            return rChild;
        else
            // 若雙子高度相同，取與 parent 方向相同者
            return parent.isLeftChild() ? lChild : rChild;

    }

    void handleUnbalancedNode(TreeNode<E> unbalancedNode, int bf, boolean isGrandChildLeft) {
        if (unbalancedNode == null)
            throw new RuntimeException("Unbalanced Node cannot be null.");

        // Unbalanced
        if (Env.debug) {
            System.out.println();
            System.out.println(unbalancedNode.getData() + " UNBALANCED!!" + "  BF: " + bf);
        }

        RotationState state = null;

        switch (bf) {
            case 2: // L
                if (isGrandChildLeft)
                    state = new LlRotation();
                else
                    state = new LrRotation();
                break;

            case -2: // R
                if (isGrandChildLeft)
                    state = new RlRotation();
                else
                    state = new RrRotation();
                break;
        }

        assert state != null;
        state.rotate(this, unbalancedNode);
    }
}
