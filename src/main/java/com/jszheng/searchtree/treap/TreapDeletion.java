package com.jszheng.searchtree.treap;

import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

class TreapDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected Treap<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (Treap<E>) bst;
    }

    // Degree 2
    @Override
    protected void deleteNodeByExtremaChild(BinarySearchTree<E> bst, BinTreeNode<E> targetNode, boolean replaceByLMax) {
        Treap<E> treap = getBt();
        boolean isMaxHeapImpl = treap.isMaxHeap();

        while (targetNode.degree() == 2) {
            BinTreeNode<E> lChild = targetNode.getLeftChild();
            BinTreeNode<E> rChild = targetNode.getRightChild();
            int lChildPriority = treap.priorityOf(lChild);
            int rChildPriority = treap.priorityOf(rChild);

            BinTreeNode<E> extremaPriorityChild = !isMaxHeapImpl && lChildPriority < rChildPriority ? lChild : rChild;

            RotationState state = extremaPriorityChild == lChild ? new LlRotation() : new RrRotation();
            state.rotate(treap, targetNode);
        }

        delete(bst, targetNode);
    }

    @Override
    protected void fixAfterDeletion(BinTreeNode<E> parent, BinTreeNode<E> sibling, BinTreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        getBt().removePriority(targetNode);
    }
}
