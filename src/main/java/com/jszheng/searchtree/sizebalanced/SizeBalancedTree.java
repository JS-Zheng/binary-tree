package com.jszheng.searchtree.sizebalanced;

import com.jszheng.Env;
import com.jszheng.insertion.InsertionAlgo;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.SelfBalancingBst;
import com.jszheng.searchtree.rotation.RotateListener;

public class SizeBalancedTree<E extends Comparable<? super E>> extends SelfBalancingBst<E> implements RotateListener<E> {


    @Override
    public String getNodeString(BinTreeNode<E> node) {
        Object data = node != null ? node.getData() : null;
        return data != null ? node.getData() + "(" + sizeOf((SbTreeNode<E>) node) + ")" :
                (getRoot() == node ? "⊙" : " ");
    }

    private int sizeOf(SbTreeNode<E> node) {
//        return size(node);
        return node != null ? node.size : 0;
    }

    @Override
    public SizeBalancedTree<E> newTree() {
        return new SizeBalancedTree<>();
    }

    @Override
    public SbTreeNode<E> newNode() {
        return new SbTreeNode<>();
    }

    @Override
    public void onRotateLeft(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> lChild) {
        onRotate(parent, pivot, lChild);
    }

    @Override
    public void onRotateRight(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> rChild) {
        onRotate(parent, pivot, rChild);
    }

    private void onRotate(BinTreeNode<E> parent, BinTreeNode<E> pivot, BinTreeNode<E> child) {
        SbTreeNode<E> sbParent = (SbTreeNode<E>) parent;
        SbTreeNode<E> sbPivot = (SbTreeNode<E>) pivot;

        int sizeOfParent = sbParent.size;
        int sizeOfPivot = sbPivot.size;
        int sizeOfChild = child != null ? ((SbTreeNode<E>) child).size : 0;

        sbPivot.size = sizeOfParent;
        sbParent.size = sizeOfParent - sizeOfPivot + sizeOfChild;
    }

    public BinTreeNode<E> selectKth(int k) {
        SbTreeNode<E> target = (SbTreeNode<E>) getRoot();

        while (target != null) {
            SbTreeNode<E> lChild = target.getLeftChild();
            int lChildSize = sizeOf(lChild);

            int rankOfTarget = lChildSize + 1;

            if (k == rankOfTarget) return target;
            else if (k < rankOfTarget) target = target.getLeftChild();
            else {
                k = k - rankOfTarget;
                target = target.getRightChild();
            }
        }

        return null;
    }

    @Override
    protected InsertionAlgo<E> createInsertionAlgo() {
        if (insertionAlgo == null)
            insertionAlgo = new SbtInsertion<>();
        return insertionAlgo;
    }

    @Override
    protected BstDeletion<E> createDeletionAlgo() {
        if (deletionAlgo == null)
            deletionAlgo = new SbtDeletion<>();
        return deletionAlgo;
    }

    void maintain(SbTreeNode<E> node, boolean checkLGrandSon) {
        if (node == null) return;
        SbTreeNode<E> lChild = node.getLeftChild();
        SbTreeNode<E> rChild = node.getRightChild();
        int lChildSize = sizeOf(lChild);
        int rChildSize = sizeOf(rChild);

        boolean basicCondition = (checkLGrandSon && lChild != null) || (!checkLGrandSon && rChild != null);

        if (!basicCondition) return;

        if (checkLGrandSon) {
            if (rChildSize < sizeOf(lChild.getLeftChild())) {
                // case 1: s[right[t]] < s[left[left[t]]
                if (Env.debug) System.out.println("case 1 -- target: " + node.getData());
                createLlRotate().rotate(this, node);
            } else if (rChildSize < sizeOf(lChild.getRightChild())) {
                // case 2: s[right[t]] < s[right[left[t]]
                if (Env.debug) System.out.println("case 2 -- target: " + node.getData());

                createLrRotate().rotate(this, node);
            } else
                return;
        } else {
            if (lChildSize < sizeOf(rChild.getRightChild())) {
                // case 3: s[left[t]] < s[right[right[t]]
                if (Env.debug) System.out.println("case 3 -- target: " + node.getData());
                createRrRotate().rotate(this, node);
            } else if (lChildSize < sizeOf(rChild.getLeftChild())) {
                // case 4: s[left[t]] < s[left[right[t]]
                if (Env.debug) System.out.println("case 4 -- target: " + node.getData());

                createRlRotate().rotate(this, node);
            } else
                return;
        }

        maintain(node.getLeftChild(), true);   //maintain the left subtree
        maintain(node.getRightChild(), false);   //maintain the right subtree
        maintain(node, true);      //maintain the whole tree
        maintain(node, false);       //maintain the whole tree
    }
}
