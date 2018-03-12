package com.jszheng.searchtree.redblack;

import com.jszheng.Env;
import com.jszheng.node.BinTreeNode;

import static com.jszheng.searchtree.redblack.RedBlackTree.Color.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.Color.RED;

/*
 * O(Log n)
 */
class RedBlackBottomUpInsertion<E extends Comparable<? super E>> extends AbsRedBlackInsertion<E> {

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> target) {
        RedBlackTree<E> rbt = getBt();

        // 新節點必為 RED
        rbt.putColor(target, RED);

        BinTreeNode<E> parent = target.getParent();
        if (parent == null) {
            rbt.putColor(target, BLACK);
            return;
        }

        while (parent != null && rbt.colorOf(parent) == RED) {
            BinTreeNode<E> uncle = parent.getParent() != null ? parent.getSibling() : null;
            RedBlackTree.Color colorOfUncle = rbt.colorOf(uncle);

            // Case 1: z's uncle y is red
            if (colorOfUncle == RED) {
                BinTreeNode<E> grandParent = parent.getParent();
                changeColor(grandParent, parent, uncle);

                if (Env.debug) {
                    System.out.println("[insert] move target pointer to grandParent: " +
                            rbt.getNodeString(grandParent));
                }

                target = grandParent; // z = z.p.p
                parent = target.getParent();

            } else {
                BinTreeNode<E> nextTarget = parent;

                if (Env.debug) {
                    System.out.println("[insert] move target pointer to parent: " +
                            rbt.getNodeString(nextTarget));
                }

                checkContinuousRedNode(target);

                target = nextTarget;
                parent = target.getParent();
            }
        }

        BinTreeNode<E> root = rbt.getRoot();
        rbt.putColor(root, BLACK);
    }
}
