package com.jszheng.searchtree.treap;

import com.jszheng.node.TreeNode;
import com.jszheng.searchtree.BinarySearchTree;
import com.jszheng.searchtree.BstDeletion;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

public class TreapDeletion<E extends Comparable<? super E>> extends BstDeletion<E> {

    @Override
    protected Treap<E> getBt() {
        BinarySearchTree<E> bst = super.getBt();
        return (Treap<E>) bst;
    }

    // Degree 2
    @Override
    protected void deleteNodeByExtremaChild(BinarySearchTree<E> bst, TreeNode<E> targetNode, boolean replaceByLMax) {
        Treap<E> treap = getBt();
        boolean isMinHeapImpl = treap.isMinHeapImplement();

        while (targetNode.degree() == 2) {
            TreeNode<E> lChild = targetNode.getLeftChild();
            TreeNode<E> rChild = targetNode.getRightChild();
            int lChildPriority = treap.priorityOf(lChild);
            int rChildPriority = treap.priorityOf(rChild);

            TreeNode<E> extremaPriorityChild = isMinHeapImpl && lChildPriority < rChildPriority ? lChild : rChild;

            RotationState state = extremaPriorityChild == lChild ? new LlRotation() : new RrRotation();
            state.rotate(treap, targetNode);
        }

        delete(bst, targetNode);
    }

    @Override
    protected void fixAfterDeletion(TreeNode<E> parent, TreeNode<E> sibling, TreeNode<E> targetNode, boolean isTargetLeft, int degree) {
        getBt().removePriority(targetNode);
    }
}
