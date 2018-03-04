package com.jszheng.base;

import com.jszheng.Env;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;

import static com.jszheng.base.SkewedState.*;

abstract class AbstractBinaryTree<E> implements BinaryTree<E> {

    protected InsertionAlgo<E> insertionAlgo;
    protected SearchAlgo<E> searchAlgo;

    @Override
    public int count() {
        return count(getRoot());
    }

    @Override
    public int count(BinTreeNode<E> node) {
        if (node == null)
            return 0;

        int nL = count(node.getLeftChild());
        int nR = count(node.getRightChild());
        return nL + nR + 1;
    }

    @Override
    public int height() {
        return height(getRoot());
    }

    @Override
    public int height(BinTreeNode<E> node) {
        if (node == null)
            return 0;

        int hL = height(node.getLeftChild());
        int hR = height(node.getRightChild());
        return Math.max(hL, hR) + 1;
    }

    @Override
    public int leavesCount() {
        return leavesCount(getRoot());
    }

    @Override
    public int leavesCount(BinTreeNode<E> node) {
        if (node == null)
            return 0;

        int nL = leavesCount(node.getLeftChild());
        int nR = leavesCount(node.getRightChild());
        int sum = nL + nR;

        return sum > 0 ? sum : 1;
    }

    @Override
    public int maxCount() {
        int maxLevel = height();
        return BinaryTreeLemma.maxCount(maxLevel);
    }

    @Override
    public BinTreeNode<E> search(E data) {
        if (data == null)
            return null;

        SearchAlgo<E> algo = createSearchAlgo();
        SearchResult<E> result = algo.search(this, data);

        return result != null ? result.getNode() : null;
    }

    @Override
    public SkewedState skewedState() {
        SkewedState leftState = checkTreeSkewed(true);
        if (leftState != null)
            return leftState;
        else
            return checkTreeSkewed(false);
    }

    @Override
    public void swap() {
        swap(getRoot());
    }

    @Override
    public void swap(BinTreeNode<E> node) {
        if (node != null) {
            swap(node.getLeftChild());
            swap(node.getRightChild());

            BinTreeNode<E> tmpLeft = node.getLeftChild();
            BinTreeNode<E> tmpRight = node.getRightChild();
            if (tmpLeft == null && tmpRight == null) return;

            node.setLeftChildWithIndex(tmpRight);
            node.setRightChildWithIndex(tmpLeft);
            node.isRoot();
        }
    }

    @Override
    public void insert(E... data) {
        if (data.length < 1) return;

        // Default Algo
        InsertionAlgo<E> algo = createInsertionAlgo();

        for (E d : data) {
            if (Env.debug)
                System.out.println("[insert] data: " + d);
            algo.insert(this, d);
            if (Env.debug)
                System.out.println();
        }
    }

    protected <T extends Comparable<? super T>> BinTreeNode<T> compareNode(BinTreeNode<T> t1, BinTreeNode<T> t2, boolean findGreater) {
        T t1Data = t1 != null ? t1.getData() : null;
        T t2Data = t2 != null ? t2.getData() : null;

        if (t1Data == null && t2Data == null) return null;
        else if (t1Data == null) return t2;
        else if (t2Data == null) return t1;

        int compareResult = t1Data.compareTo(t2Data);
        boolean t1GreaterThanT2 = compareResult > 0;

        if (findGreater)
            return t1GreaterThanT2 ? t1 : t2;
        else
            return t1GreaterThanT2 ? t2 : t1;
    }

    protected abstract InsertionAlgo<E> createInsertionAlgo();

    protected abstract SearchAlgo<E> createSearchAlgo();

    BinTreeNode<E> copyNodes(BinTreeNode<E> node, boolean deep) {
        if (node == null)
            return null;

        BinTreeNode<E> copyNode;

        if (!deep)
            copyNode = node;
        else {
            copyNode = node.newNode();

            copyNode.setData(node.getData());
            copyNode.setLeftChildWithIndex(copyNodes(node.getLeftChild(), true));
            copyNode.setRightChildWithIndex(copyNodes(node.getRightChild(), true));
        }
        return copyNode;
    }

    private SkewedState checkTreeSkewed(boolean checkLeft) {
        BinTreeNode<E> lastNode = null;
        BinTreeNode<E> root = getRoot();
        BinTreeNode<E> currentNode = root;

        // Check Root.
        if (getRoot() == null)
            return EMPTY;

        // Check Left or Right.
        while (currentNode != null) {

            boolean hasLChild = currentNode.hasLeftChild();
            boolean hasRChild = currentNode.hasRightChild();

            if (hasLChild && hasRChild)
                return NORMAL; // 非歪斜

            if (checkLeft && hasRChild
                    && currentNode != root) // Must check root.
                return NORMAL;

            if (!checkLeft && hasLChild
                    && currentNode != root) // Must check root.
                return NORMAL;

            lastNode = currentNode;
            currentNode = checkLeft ? currentNode.getLeftChild()
                    : currentNode.getRightChild();
        }

        if (lastNode == root)
            return null;
        else
            return checkLeft ? LEFT_SKEWED : RIGHT_SKEWED;
    }
}
