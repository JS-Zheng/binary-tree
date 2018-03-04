package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;

public class LrRotation extends AbstractRotation {

    /*
     * Original:
     *                A (Parent)
     *               / \
     *              /   \
     *             /     \
     *            /       \
     *           /         \
     *          /           \
     *         /             \
     *        /               \
     *       B (Pivot)         C
     *      / \               / \
     *     /   \             /   \
     *    /     \           /     \
     *   D       E         F       G
     *  / \     / \       / \     / \
     * H   I   J   K     L   M   N   O
     *
     *
     * Result:
     *                       E
     *                      / \
     *                     /   \
     *                    /     \
     *                   /       \
     *                  /         \
     *                 /           \
     *                /             \
     *               /               \
     *              /                 \
     *             /                   \
     *            /                     \
     *           /                       \
     *          B                         A
     *         / \                       / \
     *        /   \                     /   \
     *       /     \                   /     \
     *      /       \                 /       \
     *     /         \               /         \
     *    D           J             K           C
     *   / \                                   / \
     *  /   \                                 /   \
     * H     I                               F     G
     *                                      / \   / \
     *                                      L M   N O
     *
     */
    @Override
    <E> BinTreeNode<E> rotateTree(BinaryTree<E> bt, BinTreeNode<E> parent) {
        BinTreeNode<E> middle = parent.getLeftChild();
        if (middle == null) return null;

        BinTreeNode<E> pivot = middle.getRightChild();
        if (pivot == null) return null;

        if (Env.debug)
            System.out.println("[rotate] LR case: pivot is " + pivot.getData());

        // Note the first argument.
        rotateLeft(middle, pivot, pivot.getLeftChild()); // RR

        // Note the first argument.
        rotateRight(parent, pivot, pivot.getRightChild()); // LL

        return pivot;
    }
}
