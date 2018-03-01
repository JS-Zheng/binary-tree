package com.jszheng.searchtree.rotation;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.TreeNode;

public class RlRotation extends AbstractRotation {

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
     *       B                 C (Pivot)
     *      / \               / \
     *     /   \             /   \
     *    /     \           /     \
     *   D       E         F       G
     *  / \     / \       / \     / \
     * H   I   J   K     L   M   N   O
     *
     *
     * Result:
     *
     *                        F
     *                       / \
     *                      /   \
     *                     /     \
     *                    /       \
     *                   /         \
     *                  /           \
     *                 /             \
     *                /               \
     *               /                 \
     *              /                   \
     *             /                     \
     *            /                       \
     *           A                         C
     *          / \                       / \
     *         /   \                     /   \
     *        /     \                   /     \
     *       /       \                 /       \
     *      /         \               /         \
     *     B           L             M           G
     *    / \                                   / \
     *   /   \                                 /   \
     *  D     E                               N     O
     * / \   / \
     * H I   J K
     */
    @Override
    <E> TreeNode<E> rotateTree(BinaryTree<E> bt, TreeNode<E> parent) {
        TreeNode<E> middle = parent.getRightChild();
        if (middle == null) return null;

        TreeNode<E> pivot = middle.getLeftChild();
        if (pivot == null) return null;

        if (Env.debug)
            System.out.println("[rotate] RL case: pivot is " + pivot.getData());

        // Note the first argument.
        rotateRight(middle, pivot, pivot.getRightChild()); // LL

        // Note the first argument.
        rotateLeft(parent, pivot, pivot.getLeftChild()); // RR

        return pivot;
    }
}
