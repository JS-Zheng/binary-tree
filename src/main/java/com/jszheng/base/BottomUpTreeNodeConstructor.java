package com.jszheng.base;

import com.jszheng.node.BinTreeNode;

import java.util.Arrays;
import java.util.List;

/*
 * Recursive Level Order TreeNode Constructor
 *
 * O(n * Log n)
 */
class BottomUpTreeNodeConstructor<E> implements TreeNodeConstructor<E> {

    private final BinTreeNode<E> node;
    private boolean nullable = false;

    public BottomUpTreeNodeConstructor(BinTreeNode<E> node) {
        this.node = node;
    }

    @Override
    public void setDataArr(E[] data, boolean nullable) {
        this.nullable = nullable;
        if (data != null && data.length > 0) {
            if (data[0] == null && !nullable) return;
            List<E> list = Arrays.asList(data);
            setDataArr(list, nullable);
        }
    }

    private List<E> getChildList(List<E> treeData, boolean isLeft) {
        SubtreeListAlgo<E> algo = new SubtreeListAlgo<>(isLeft);
        return algo.execute(treeData);
    }

    private void setDataArr(List<E> list, boolean nullable) {
        if (list != null && list.size() > 0) {
            if (list.get(0) == null && !nullable) return;
            node.setData(list.get(0));
            if (list.size() > 1)
                setSubTree(node, list, true);
            if (list.size() > 2)
                setSubTree(node, list, false);
        }
    }

    private void setSubTree(BinTreeNode<E> node, List<E> items, boolean isLeft) {
        List<E> subItems = getChildList(items, isLeft);

        // Prevent unnecessary instance
        if (subItems != null && subItems.size() > 0) {
            BinTreeNode<E> child = node.newNode();
            new BottomUpTreeNodeConstructor<>(child).setDataArr(subItems, this.nullable);

            if (!nullable && child.getData() == null) return;

            if (isLeft)
                node.setLeftChild(child);
            else
                node.setRightChild(child);
        }
    }
}
