package com.jszheng.searchtree.redblack;

import com.jszheng.node.BinTreeNode;
import com.jszheng.searchtree.BstSearch;

import static com.jszheng.searchtree.redblack.RedBlackTree.BLACK;
import static com.jszheng.searchtree.redblack.RedBlackTree.RED;

/*
 * O(Log n)
 */
class RedBlackTopDownInsertion<E extends Comparable<? super E>> extends AbsRedBlackInsertion<E> {

    @Override
    protected BstSearch<E> createSearchAlgo() {
        return new RbtInsertionSearch();
    }

    @Override
    protected void fixAfterInsertion(BinTreeNode<E> newNode) {
        RedBlackTree<E> rbt = getBt();
        RedBlackTreeNode<E> node = (RedBlackTreeNode<E>) newNode;

        // 新節點必為 RED
        rbt.setColor(node, RED);

        // Second Continuous check.
        checkContinuousRedNode(node);

        rbt.setColor(rbt.getRoot(), BLACK);
    }

    class RbtInsertionSearch extends BstSearch<E> {
        @Override
        protected boolean handleNode(BinTreeNode<E> node) {
            // 搜尋路徑中，若遇兩子點是 RED 則先做 color change
            RedBlackTreeNode<E> target = (RedBlackTreeNode<E>) node;
            RedBlackTreeNode<E> lChild = target.getLeftChild();
            RedBlackTreeNode<E> rChild = target.getRightChild();

            if (isNeedColorChange(lChild, rChild))
                changeColorWithRedNodeCheck(target, lChild, rChild);

            return true;
        }
    }
}
