package com.jszheng.heap.leftist;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.base.BtDecorator;
import com.jszheng.base.complete.LinearSearch;
import com.jszheng.heap.MeldableHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;

// shortest(node.getLChild()) >= shortest(node.getRChild()),
// for all nodes are internal node
//
// It's not a Complete Binary Tree.
abstract class LeftistTree<E extends Comparable<? super E>> extends BtDecorator<E>
        implements MeldableHeap<LeftistTree<E>, E, BinTreeNode<E>> {

    private final boolean maxHeap;

    LeftistTree(BinaryTree<E> component) {
        super(component);
        maxHeap = isMaxHeap();
    }

    @Override
    public String getNodeString(BinTreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? data.toString() + "(" + shortest(node) + ")" :
                (getRoot() == node ? "⊙" : "[null]");
    }

    @Override
    public void merge(LeftistTree<E> tree) {
        if (tree == null || tree.isEmpty()) return;
        BinTreeNode<E> otherRoot = tree.getRoot();
        merge(otherRoot);
    }

    // O(log n)
    public void merge(BinTreeNode<E> node) {
        if (node == null) return;
        if (Env.debug) {
            System.out.println("[merge] target: " + getNodeString(node));
        }
        BinTreeNode<E> root = getRoot();
        BinTreeNode<E> otherRoot = node;
        BinTreeNode<E> lastParent = null;
        BinTreeNode<E> parent;

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
            BinTreeNode<E> lChild = lastParent.getLeftChild();
            BinTreeNode<E> rChild = lastParent.getRightChild();
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
            System.out.println("[merge] complete");
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

    // O(log n)
    E deleteExtrema() {
        BinTreeNode<E> root = getRoot();
        if (root == null) return null;
        clearRoot();

        E min = root.getData();
        BinTreeNode<E> lChild = root.getLeftChild();
        BinTreeNode<E> rChild = root.getRightChild();

        if (lChild != null)
            lChild.deleteParentAndCheckItsChild();
        if (rChild != null)
            rChild.deleteParentAndCheckItsChild();

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
        BinTreeNode<E> root = getRoot();
        if (root == null) return null;
        return root.getData();
    }

    // the length of the shortest path from the node to an external node
    private int shortest(BinTreeNode<E> node) {
        if (node == null) return 0;

        int lPath = shortest(node.getLeftChild());
        int rPath = shortest(node.getRightChild());

        int minChildPath = Math.min(lPath, rPath);
        return 1 + minChildPath;
    }
}
