package com.jszheng.node;

import com.jszheng.util.BFSAlgo;

/*
 * Recursive Level Order TreeNode Constructor
 *
 * O(n * Log n)
 */
public class BottomUpTreeNodeConstructor<E> implements TreeNodeConstructor<E> {

    private final TreeNode<E> node;

    public BottomUpTreeNodeConstructor(TreeNode<E> node) {
        this.node = node;
    }

    @Override
    public void setDataByArr(E[] data) {
        if (data != null && data.length > 0 && data[0] != null) {
            node.setData(data[0]);
            if (data.length > 1)
                setSubTreeByArr(node, data, true);
            if (data.length > 2)
                setSubTreeByArr(node, data, false);
        }
    }

    private E[] getChildArray(E[] treeArr, boolean isLeft) {
        BFSAlgo<E[], Integer, E[]> algo = new SubTreeArrayAlgo<>(isLeft);
        return algo.execute(treeArr);
    }

    private void setSubTreeByArr(TreeNode<E> node, E[] items, boolean isLeft) {
        E[] subItems = getChildArray(items, isLeft);

        // Prevent unnecessary instance
        if (subItems != null && subItems.length > 0 && items[0] != null) {
            TreeNode<E> child = node.newNode();
            child.setDataByArr(subItems);

            if (isLeft)
                node.setLeftChildWithIndex(child);
            else
                node.setRightChildWithIndex(child);
        }
    }
}
