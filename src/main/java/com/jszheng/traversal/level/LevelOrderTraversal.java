package com.jszheng.traversal.level;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.BinTreeNode;
import com.jszheng.node.LinkedTreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;
import com.jszheng.util.BFSAlgo;

import java.util.*;

import static com.jszheng.base.BinaryTreeLemma.lChildIndex;
import static com.jszheng.base.BinaryTreeLemma.rChildIndex;

public class LevelOrderTraversal<E> extends AbstractIterativeTraversal<E>
        implements ILevelOrderTraversal<E>, BFSAlgo<BinaryTree<E>, BinTreeNode<E>, List<BinTreeNode<E>>> {

    private List<BinTreeNode<E>> dataList = new ArrayList<>();
    private Map<BinTreeNode<E>, Integer> indexMap = new HashMap<>();
    private Map<BinTreeNode<E>, Integer> levelMap = new HashMap<>();
    private Map<BinTreeNode<E>, Boolean> pseudoNode = new HashMap<>();
    private int maxCount;
    private boolean fullBtMode = false;

    @Override
    public boolean execute(Queue<BinTreeNode<E>> queue, BinTreeNode<E> node, List<BinTreeNode<E>> dataList) {
        if (node == null)
            return false;

        dataList.add(node);
        if (!getNodeHandler().handle(node))
            return false;

        int index = indexMap.get(node);
        int level = levelMap.get(node);

        BinTreeNode<E> lChild = node.getLeftChild();
        BinTreeNode<E> rChild = node.getRightChild();

        offerChild(queue, lChild, true, index, level);
        offerChild(queue, rChild, false, index, level);

        return true;
    }

    @Override
    public BinTreeNode<E> firstItem(BinaryTree<E> bt) {
        BinTreeNode<E> root = bt.getRoot();

        indexMap.put(root, 0);
        levelMap.put(root, 1);
        return root;
    }

    @Override
    public boolean init(BinaryTree<E> bt) {
        if (bt != null) {
            this.maxCount = bt.maxCount();
            return true;
        }

        return false;
    }

    @Override
    public boolean onDataPolled(Queue<BinTreeNode<E>> queue, BinTreeNode<E> node) {
        return execute(queue, node, dataList);
    }

    @Override
    public List<BinTreeNode<E>> onFinish(BinaryTree<E> bt) {
        getNodeHandler().afterTraversed(bt);
        return dataList;
    }

    @Override
    public List<BinTreeNode<E>> traverse(BinaryTree<E> bt) {
        return execute(bt);
    }

    @Override
    public boolean isFullBtMode() {
        return fullBtMode;
    }

    @Override
    public void setFullBtMode(boolean isFullMode) {
        this.fullBtMode = isFullMode;
    }

    protected int getIndex(BinTreeNode<E> node) {
        return indexMap.get(node);
    }

    protected int getLevel(BinTreeNode<E> node) {
        return levelMap.get(node);
    }

    protected boolean isPseudoNode(BinTreeNode<E> node) {
        return pseudoNode.getOrDefault(node, false);
    }

    private void offerChild(Queue<BinTreeNode<E>> queue, BinTreeNode<E> child, boolean isLeft, int parentIndex, int level) {
        int maxChildIndex = isLeft ? lChildIndex(dataList.size() - 1) : rChildIndex(dataList.size() - 1);

        if (child == null && fullBtMode &&
                maxChildIndex < this.maxCount) {
            child = new LinkedTreeNode<>(); // mock null Node.
            pseudoNode.put(child, true);
        }

        if (child != null) {
            int childIndex = isLeft ? lChildIndex(parentIndex) : rChildIndex(parentIndex);
            queue.offer(child);
            indexMap.put(child, childIndex);
            levelMap.put(child, level + 1);
        }
    }
}
