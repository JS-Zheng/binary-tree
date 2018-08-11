package com.jszheng.searchtree.redblack;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.searchtree.BstInsertion;
import com.jszheng.searchtree.rotation.RotationState;

import static com.jszheng.searchtree.redblack.RedBlackTree.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.RED;

/*
 * O(Log n)
 */
abstract class AbsRedBlackInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    void changeColorWithRedNodeCheck(RedBlackTreeNode<E> parent, RedBlackTreeNode<E> lChild, RedBlackTreeNode<E> rChild) {
        RedBlackTree<E> rbt = getBt();

        changeColor(parent, lChild, rChild);

        if (!parent.isRoot()) {
            rbt.setColor(parent, RED);

            if (Env.debug)
                System.out.println("[insert] make parentNode " + parent.getData() + " RED.");

            // Must do this!
            // 檢查 parent 與 grandParent 是否產生連續紅色
            checkContinuousRedNode(parent);
        }
    }

    @Override
    protected RedBlackTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (RedBlackTree<E>) bt;
    }

    // 三點變黑，父點變紅 (if not root)
    void changeColor(RedBlackTreeNode<E> parent, RedBlackTreeNode<E> lChild, RedBlackTreeNode<E> rChild) {
        RedBlackTree<E> rbt = getBt();

        rbt.colorChangeCount++;

        if (Env.debug)
            System.out.printf("[insert] change color -- make node %s & %s BLACK\n"
                    , lChild.getData(), rChild.getData());

        rbt.setColor(lChild, BLACK);
        rbt.setColor(rChild, BLACK);
        rbt.setColor(parent, RED);
    }

    // 檢查 child 與 parent 有無連續 RED，若有則需作 Rotation
    void checkContinuousRedNode(RedBlackTreeNode<E> child) {
        RedBlackTree<E> rbt = getBt();

        RedBlackTreeNode<E> parent = child.getParent();

        if (rbt.colorOf(parent) != RED) return;

        RedBlackTreeNode<E> grandParent = parent != null ? parent.getParent() : null;

        if (grandParent == null) return;

        if (Env.debug)
            System.out.println("[insert] detected continuous red: "
                    + child.getData() + " & " + parent.getData());

        rotateContinuousRedNode(rbt, child, parent, grandParent);
    }

    boolean rotateContinuousRedNode(RedBlackTree<E> rbt, RedBlackTreeNode<E> child, RedBlackTreeNode<E> parent, RedBlackTreeNode<E> grandParent) {
        boolean isDoubleRotate = child.isLeftChild() != parent.isLeftChild();
        RotationState state = rbt.createRotationState(parent, child);
        state.rotate(rbt, grandParent);

        if (!isDoubleRotate) {
            rbt.setColor(parent, BLACK);
            rbt.setColor(grandParent, RED);
        } else {
            rbt.setColor(child, BLACK);
            rbt.setColor(grandParent, RED);
        }

        return isDoubleRotate;
    }

    boolean isNeedColorChange(RedBlackTreeNode<E> lChild, RedBlackTreeNode<E> rChild) {
        RedBlackTree<E> rbt = getBt();
        boolean lColor = rbt.colorOf(lChild);
        boolean rColor = rbt.colorOf(rChild);

        return lColor == RED && rColor == RED;
    }
}
