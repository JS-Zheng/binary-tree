package com.jszheng.searchtree.redblack;

import com.jszheng.node.BinTreeNode;

import static com.jszheng.searchtree.redblack.RedBlackTree.Color.RED;

/*
 * O(Log n)
 */
class RedBlackTopDownInsertion<E extends Comparable<? super E>> extends AbsRedBlackInsertion<E> {

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
}
