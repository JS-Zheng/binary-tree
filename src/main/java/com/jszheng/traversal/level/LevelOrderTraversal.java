package com.jszheng.traversal.level;

import com.jszheng.base.BinaryTree;
import com.jszheng.node.LinkedTreeNode;
import com.jszheng.node.TreeNode;
import com.jszheng.traversal.AbstractIterativeTraversal;
import com.jszheng.util.BFSAlgo;

import java.util.*;

import static com.jszheng.base.BinaryTreeLemma.lChildIndex;
import static com.jszheng.base.BinaryTreeLemma.rChildIndex;

public class LevelOrderTraversal<E> extends AbstractIterativeTraversal<E>
        implements ILevelOrderTraversal<E>, BFSAlgo<BinaryTree<E>, TreeNode<E>, List<TreeNode<E>>> {

    private List<TreeNode<E>> dataList = new ArrayList<>();
    private Map<TreeNode<E>, Integer> indexMap = new HashMap<>();
    private Map<TreeNode<E>, Integer> levelMap = new HashMap<>();
    private Map<TreeNode<E>, Boolean> pseudoNode = new HashMap<>();
    private int maxCount;
    private boolean fullBtMode = false;

    @Override
    public boolean execute(Queue<TreeNode<E>> queue, TreeNode<E> node, List<TreeNode<E>> dataList) {
        if (node == null)
            return false;

        dataList.add(node);
        if (!getNodeHandler().handle(node))
            return false;

        int index = indexMap.get(node);
        int level = levelMap.get(node);

        TreeNode<E> lChild = node.getLeftChild();
        TreeNode<E> rChild = node.getRightChild();

        offerChild(queue, lChild, true, index, level);
        offerChild(queue, rChild, false, index, level);

        return true;
    }

    @Override
    public TreeNode<E> firstItem(BinaryTree<E> bt) {
        TreeNode<E> root = bt.getRoot();

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
    public boolean onDataPolled(Queue<TreeNode<E>> queue, TreeNode<E> node) {
        return execute(queue, node, dataList);
    }

    @Override
    public List<TreeNode<E>> onFinish(BinaryTree<E> bt) {
        getNodeHandler().afterTraversed(bt);
        return dataList;
    }

    @Override
    public List<TreeNode<E>> traverse(BinaryTree<E> bt) {
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

    protected int getIndex(TreeNode<E> node) {
        return indexMap.get(node);
    }

    protected int getLevel(TreeNode<E> node) {
        return levelMap.get(node);
    }

    protected boolean isPseudoNode(TreeNode<E> node) {
        return pseudoNode.getOrDefault(node, false);
    }

    private void offerChild(Queue<TreeNode<E>> queue, TreeNode<E> child, boolean isLeft, int parentIndex, int level) {
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
