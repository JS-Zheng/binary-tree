package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

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
    <E> TreeNode<E> rotateTree(BinaryTree<E> bt, TreeNode<E> parent) {
        TreeNode<E> middle = parent.getLeftChild();
        if (middle == null) return null;

        TreeNode<E> pivot = middle.getRightChild();
        if (pivot == null) return null;

        if (Env.debug)
            System.out.println("LR Case: Pivot is " + pivot.getData() + ".\n");

        // Note the first argument.
        rotateLeft(middle, pivot, pivot.getLeftChild()); // RR

        // Note the first argument.
        rotateRight(parent, pivot, pivot.getRightChild()); // LL

        return pivot;
    }
}
