package com.jszheng.heap.leftist;

import com.jszheng.Env;
import com.jszheng.base.LinkedBinaryTree;
import com.jszheng.base.complete.LinearSearch;
import com.jszheng.heap.MeldableHeap;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.search.SearchAlgo;

// shortest(node.getLChild()) >= shortest(node.getRChild()),
// for all nodes are internal node
//
// It's not a Complete Binary Tree.
abstract class LeftistTree<E extends Comparable<? super E>> extends LinkedBinaryTree<E>
        implements MeldableHeap<LeftistTree<E>, E, BinTreeNode<E>> {

    private final boolean maxHeap;

    LeftistTree() {
        maxHeap = isMaxHeap();
    }

    abstract boolean isMaxHeap();

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

    /*
     * O(log n)
     *
     * 1. 比較 node，以 較小(大) 者作為合併樹的根節點
     * 2. 保持合併樹根左子樹不變
     * 3. 以合併樹根之右子樹 與 step 1. 之失敗者 進行合併，並將結果作為合併樹根的右子樹
     * 4. 若於 step 3. 無右子樹可供比較，直接將 失敗者 作為其右子樹
     * 5. 延著最後的新樹根往回驗證 左傾樹性質: shortest(lChild) >= shortest(rChild)，若不成立則左右進行 swap
     */
    public void merge(BinTreeNode<E> node) {
        if (node == null) return;

        BinTreeNode<E> root = getRoot();
        BinTreeNode<E> otherRoot = node;
        BinTreeNode<E> lastParent = null;
        BinTreeNode<E> parent;

        for (int i = 0; root != null; i++) {
            parent = compareNode(root, otherRoot, maxHeap);

            if (parent != root) {
                if (i == 0) {
                    if (Env.debug)
                        System.out.println("[merge] use " + parent.getData() + " as new root of merge-tree");

                    setRoot(parent);
                } else {
                    if (Env.debug)
                        System.out.println("[merge] " + lastParent.getData() + " set " + parent.getData() + " as right child");

                    lastParent.setRightChild(parent);
                }
                otherRoot = root;
            }


            lastParent = parent;
            root = lastParent.getRightChild();
        }

        if (lastParent != null) {
            if (Env.debug)
                System.out.println("[merge] " + lastParent.getData() + " set " + otherRoot.getData() + " as right child");

            lastParent.setRightChild(otherRoot);
        }

        while (lastParent != null) {
            BinTreeNode<E> lChild = lastParent.getLeftChild();
            BinTreeNode<E> rChild = lastParent.getRightChild();
            int lPath = shortest(lChild);
            int rPath = shortest(rChild);
            if (lPath < rPath) {
                if (Env.debug) {
                    System.out.println("[merge] lChild path is smaller than rChild path:");
                    System.out.printf("[merge] %s swap its lChild:%s & rChild:%s\n"
                            , getNodeString(lastParent), getNodeString(lChild), getNodeString(rChild));
                }

                lastParent.setLeftChild(rChild);
                lastParent.setRightChild(lChild);
            }

            lastParent = lastParent.getParent();
        }

        if (Env.debug)
            System.out.println("[merge] complete");
    }

    @Override
    protected SearchAlgo<E> createSearchAlgo() {
        return new LinearSearch<>();
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        return new LeftistTreeInsertion<>();
    }

    // O(log n)
    E deleteExtrema() {
        String deleteTarget = maxHeap ? "max" : "min";
        BinTreeNode<E> root = getRoot();
        if (root == null) return null;
        if (Env.debug)
            System.out.println("[delete " + deleteTarget + "] clear root");

        clearRoot();

        E min = root.getData();
        BinTreeNode<E> lChild = root.getLeftChild();
        BinTreeNode<E> rChild = root.getRightChild();

        if (lChild != null)
            lChild.deleteParent();
        if (rChild != null)
            rChild.deleteParent();

        if (lChild != null) {
            if (Env.debug) {

                System.out.println("[delete " + deleteTarget + "] set original lChild: " + lChild.getData() + " as new root");
                if (rChild != null)
                    System.out.println("[delete " + deleteTarget + "] merge original rChild: " + rChild.getData());
            }
            setRoot(lChild);
            merge(rChild);
        } else if (rChild != null) {
            if (Env.debug) {
                System.out.println("[delete " + deleteTarget + "] set original rChild: " + rChild.getData() + " as new root");
            }
            setRoot(rChild);
        }

        if (Env.debug)
            System.out.println();

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
