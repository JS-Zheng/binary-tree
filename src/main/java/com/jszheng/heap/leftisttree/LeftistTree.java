package com.jszheng.heap.leftisttree;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.base.completebt.LinearSearch;
import com.jszheng.heap.MeldableHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.TreeNode;
import com.jszheng.search.SearchAlgo;

// shortest(node.getLChild()) >= shortest(node.getRChild()),
// for all nodes are internal node
//
// It's not a Complete Binary Tree.
abstract class LeftistTree<E extends Comparable<? super E>> extends BtDecorator<E> implements MeldableHeap<E> {

    private final boolean maxHeap;

    LeftistTree(BinaryTree<E> component) {
        super(component);
        maxHeap = isMaxHeap();
    }

    @Override
    public String getNodeString(TreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() + "(" + shortest(node) + ")" :
                (getRoot() == node ? "⊙" : "[null]");
    }

    public void merge(LeftistTree<E> tree) {
        if (tree == null || tree.isEmpty()) return;
        TreeNode<E> otherRoot = tree.getRoot();
        merge(otherRoot);
    }

    // O(log n)
    @Override
    public void merge(TreeNode<E> node) {
        if (node == null) return;
        if (Env.debug) {
            System.out.println("[merge] target: " + getNodeString(node));
        }
        TreeNode<E> root = getRoot();
        TreeNode<E> otherRoot = node;
        TreeNode<E> lastParent = null;
        TreeNode<E> parent;

        for (int i = 0; root != null; i++) {
            parent = compareNode(root, otherRoot, maxHeap);

            if (parent != root) {
                if (i == 0) {
                    if (Env.debug) System.out.println("[merge] set new root: " + parent.getData());
                    setRoot(parent);
                } else
                    lastParent.setRightChildWithIndex(parent);

                otherRoot = root;
            }

            lastParent = parent;
            root = lastParent.getRightChild();
        }

        if (lastParent != null)
            lastParent.setRightChildWithIndex(otherRoot);

        while (lastParent != null) {
            TreeNode<E> lChild = lastParent.getLeftChild();
            TreeNode<E> rChild = lastParent.getRightChild();
            int lPath = shortest(lChild);
            int rPath = shortest(rChild);
            if (lPath < rPath) {
                if (Env.debug) {
                    System.out.printf("[merge] %s swap its lChild:%s & rChild:%s\n"
                            , getNodeString(lastParent), getNodeString(lChild), getNodeString(rChild));
                }
                lastParent.setLeftChildWithIndex(rChild);
                lastParent.setRightChildWithIndex(lChild);
            }

            lastParent = lastParent.getParent();
        }

        if (Env.debug)
            System.out.println("[merge] complete\n");
    }

    abstract boolean isMaxHeap();

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new LeftistTreeInsertion<>();
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }

    // the length of the shortest path from the node to an external node
    private int shortest(TreeNode<E> node) {
        if (node == null) return 0;

        int lPath = shortest(node.getLeftChild());
        int rPath = shortest(node.getRightChild());

        int minChildPath = Math.min(lPath, rPath);
        return 1 + minChildPath;
    }

    // O(log n)
    E deleteExtrema() {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        clearRoot();

        E min = root.getData();
        TreeNode<E> lChild = root.getLeftChild();
        TreeNode<E> rChild = root.getRightChild();

        if (lChild != null)
            lChild.deleteParent();
        if (rChild != null)
            rChild.deleteParent();

        if (lChild != null) {
            setRoot(lChild);
            merge(rChild);
        } else if (rChild != null) {
            setRoot(rChild);
        }

        return min;
    }

    // O(1)
    E searchExtrema() {
        TreeNode<E> root = getRoot();
        if (root == null) return null;
        return root.getData();
    }
}
