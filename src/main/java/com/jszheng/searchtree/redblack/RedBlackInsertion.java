package com.jszheng.searchtree.redblack;

import com.jszheng.Env;
import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BstInsertion;
import com.jszheng.searchtree.rotation.LlRotation;
import com.jszheng.searchtree.rotation.RotationState;
import com.jszheng.searchtree.rotation.RrRotation;

import static com.jszheng.searchtree.redblack.RedBlackTree.Color.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.Color.RED;

/*
 * O(Log n)
 */
class RedBlackInsertion<E extends Comparable<? super E>> extends BstInsertion<E> {

    @Override
    protected RedBlackTree<E> getBt() {
        BinaryTree<E> bt = super.getBt();
        return (RedBlackTree<E>) bt;
    }

    @Override
    protected boolean handleNode(BinTreeNode<E> node) {
        BinTreeNode<E> lChild = node.getLeftChild();
        BinTreeNode<E> rChild = node.getRightChild();

        // 搜尋過程中，若遇兩子點是 RED 則先做 color change
        checkNeedColorChange(node, lChild, rChild);
        return true;
    }

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        RedBlackTree<E> rbt = getBt();

        // 新節點必為 RED
        rbt.putColor(newNode, RED);

        // Second Continuous check.
        checkContinuousRedNode(newNode);
    }

    // 三點變黑，父點變紅 (if not root)
    private void changeColor(BinTreeNode<E> parent, BinTreeNode<E> lChild, BinTreeNode<E> rChild) {
        RedBlackTree<E> rbt = getBt();

        if (Env.debug)
            System.out.printf("[insert] change Color -- make node %s & %s BLACK\n"
                    , lChild.getData(), rChild.getData());

        rbt.putColor(lChild, BLACK);
        rbt.putColor(rChild, BLACK);

        if (!parent.isRoot()) {
            rbt.putColor(parent, RED);

            if (Env.debug)
                System.out.println("[insert] make parentNode " + parent.getData() + " RED.");

            // Must do this!
            // 檢查 parent 與 grandParent 是否產生連續紅色
            checkContinuousRedNode(parent);
        }
    }

    // 檢查 child 與 parent 有無連續 RED，若有則需作 Rotation
    private void checkContinuousRedNode(BinTreeNode<E> child) {
        RedBlackTree<E> rbt = getBt();

        BinTreeNode<E> parent = child.getParent();
        BinTreeNode<E> grandParent = parent != null ? parent.getParent() : null;
        if (grandParent == null || rbt.colorOf(parent) != RED) return;

        if (Env.debug)
            System.out.println("[insert] detected continuous red: "
                    + child.getData() + " & " + parent.getData());

        RotationState state = rbt.getRotationState(parent, child);
        state.rotate(rbt, grandParent);

        if (state instanceof RrRotation || state instanceof LlRotation) {
            rbt.putColor(parent, BLACK);
            rbt.putColor(grandParent, RED);
        } else {
            rbt.putColor(child, BLACK);
            rbt.putColor(grandParent, RED);
        }
    }

    private void checkNeedColorChange(BinTreeNode<E> parent, BinTreeNode<E> lChild, BinTreeNode<E> rChild) {
        RedBlackTree<E> rbt = getBt();
        RedBlackTree.Color lColor = rbt.colorOf(lChild);
        RedBlackTree.Color rColor = rbt.colorOf(rChild);

        if (lColor == RED && rColor == RED)
            changeColor(parent, lChild, rChild);
    }
}
