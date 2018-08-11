package com.jszheng.base;

import com.jszheng.util.BFSAlgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Return Left or Right subtree List.
 * Created by zhengzhongsheng on 2017/10/16.
 */
class SubtreeListAlgo<Param> implements BFSAlgo<List<Param>, Integer, List<Param>> {

    private List<Param> list;
    private List<Param> treeArr;
    private int minSize;
    private boolean isLeft;

    SubtreeListAlgo(boolean isLeft) {
        this.isLeft = isLeft;
    }

    @Override
    public boolean init(List<Param> params) {
        this.list = new ArrayList<>();
        this.treeArr = params;
        this.minSize = isLeft ? 1 : 2; // 若有 左(右)子點 則陣列長度至少為 2(3)

        // "items[0] != null" use to prevent null-parent tree
        return treeArr != null && minSize < treeArr.size();
    }

    @Override
    public Integer firstItem(List<Param> params) {
        return minSize;
    }

    @Override
    public boolean onDataPolled(Queue<Integer> queue, Integer parentIndex) {
        list.add(treeArr.get(parentIndex));

        int lChildIndex = BinaryTreeLemma.lChildIndex(parentIndex);
        int rChildIndex = BinaryTreeLemma.rChildIndex(parentIndex);

        // 若有左子點，則加入 parentQueue 成為備選 parent
        if (lChildIndex < treeArr.size())
            queue.offer(lChildIndex);

        // 若有右子點，則加入 parentQueue 成為備選 parent
        if (rChildIndex < treeArr.size())
            queue.offer(rChildIndex);

        return true;
    }

    @Override
    public List<Param> onFinish(List<Param> param) {
        return list;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }
}
