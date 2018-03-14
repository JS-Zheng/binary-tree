package com.jszheng.searchtree.redblack;

import com.jszheng.node.BinTreeNode;

import static com.jszheng.searchtree.redblack.RedBlackTree.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.RED;

/*
 * O(Log n)
 */
class RedBlackBottomUpInsertion<E extends Comparable<? super E>> extends AbsRedBlackInsertion<E> {

    // Lite version of CLRS
    protected void fixAfterInsertion(BinTreeNode<E> node) {
        RedBlackTree<E> rbt = getBt();
        RedBlackTreeNode<E> target = (RedBlackTreeNode<E>) node;

        rbt.setColor(target, RED);

        RedBlackTreeNode<E> parent;

        while (target != null && (parent = target.getParent()) != null && rbt.colorOf(parent) == RED) {
            RedBlackTreeNode<E> uncle = parent.getSibling();
            RedBlackTreeNode<E> grandParent = parent.getParent();

            if (rbt.colorOf(uncle) == RED) {
                changeColor(grandParent, parent, uncle);
                target = grandParent;
                continue;
            }

            if (rotateContinuousRedNode(rbt, target, parent, grandParent))
                target = parent; // set target to its parent, if DoubleRotation
        }

        rbt.setColor(rbt.getRoot(), BLACK);
    }
}
