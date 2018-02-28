package com.jszheng.base;

import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;
import com.jszheng.search.SearchAlgo;
import com.jszheng.search.SearchResult;

import static com.jszheng.base.SkewedState.*;


abstract class AbstractBinaryTree<E> implements BinaryTree<E> {

    @Override
    public int count() {
        return count(getRoot());
    }

    @Override
    public int count(TreeNode<E> node) {
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
    public int height(TreeNode<E> node) {
        if (node == null)
            return 0;

        int hL = height(node.getLeftChild());
        int hR = height(node.getRightChild());
        return Math.max(hL, hR) + 1;
    }

    @Override
    public void insert(E... data) {
        if (data.length < 1) return;

        // Default Algo
        InsertionAlgo<E> algo = createInsertionAlgo();

        for (E d : data)
            algo.insert(this, d);
    }

    @Override
    public int leavesCount() {
        return leavesCount(getRoot());
    }

    @Override
    public int leavesCount(TreeNode<E> node) {
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
    public TreeNode<E> search(E data) {
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
    public void swap(TreeNode<E> node) {
        if (node != null) {
            swap(node.getLeftChild());
            swap(node.getRightChild());

            TreeNode<E> tmpLeft = node.getLeftChild();
            TreeNode<E> tmpRight = node.getRightChild();
            if (tmpLeft == null && tmpRight == null) return;

            node.setLeftChildWithIndex(tmpRight);
            node.setRightChildWithIndex(tmpLeft);
            node.isRoot();
        }
    }

    protected abstract InsertionAlgo<E> createInsertionAlgo();

    protected abstract SearchAlgo<E> createSearchAlgo();

    private SkewedState checkTreeSkewed(boolean checkLeft) {
        TreeNode<E> lastNode = null;
        TreeNode<E> root = getRoot();
        TreeNode<E> currentNode = root;

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

    TreeNode<E> copyNodes(TreeNode<E> node, boolean deep) {
        if (node == null)
            return null;

        TreeNode<E> copyNode;

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
}
