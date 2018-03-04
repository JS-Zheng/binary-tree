package com.jszheng.node;

import com.jszheng.base.BinaryTreeLemma;
import com.jszheng.util.BFSAlgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Return Left or Right subTree Array.
 * <p>
 * Created by zhengzhongsheng on 2017/10/16.
 */
class SubTreeArrayAlgo<Param> implements BFSAlgo<Param[], Integer, Param[]> {

    private List<Param> list = new ArrayList<>();
    private Param[] treeArr;
    private int minSize;
    private boolean isLeft;

    SubTreeArrayAlgo(boolean isLeft) {
        this.isLeft = isLeft;
    }

    @Override
    public Integer firstItem(Param[] params) {
        return minSize;
    }

    @Override
    public boolean init(Param[] params) {
        this.treeArr = params;
        this.minSize = isLeft ? 1 : 2; // 若有 左(右)子點 則陣列長度至少為 2(3)

        // "items[0] != null" use to prevent null-parent tree
        return !(treeArr == null || treeArr[0] == null)
                && minSize < treeArr.length;
    }

    @Override
    public boolean onDataPolled(Queue<Integer> queue, Integer parentIndex) {
        list.add(treeArr[parentIndex]);

        int lChildIndex = BinaryTreeLemma.lChildIndex(parentIndex);
        int rChildIndex = BinaryTreeLemma.rChildIndex(parentIndex);

        // 若有左子點，則加入 parentQueue 成為備選 parent
        if (lChildIndex < treeArr.length) {
            queue.offer(lChildIndex);
        }

        // 若有右子點，則加入 parentQueue 成為備選 parent
        if (rChildIndex < treeArr.length) {
            queue.offer(rChildIndex);
        }

        return true;
    }


    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Param[] onFinish(Param[] param) {
        return (Param[]) list.toArray();
    }
}
