package com.jszheng.base;

public interface BinaryTreeLemma {

    static int lChildIndex(int index) {
        return (index << 1) + 1;
    }

    static int rChildIndex(int index) {
        return (index << 1) + 2;
    }

    static int parentIndex(int index) {
        return Math.floorDiv((index - 1), 2);
    }

    static boolean isDescendantIndex(int ancestorIndex, int index) {
        if (ancestorIndex < 0 || index < 0)
            return false;

        if (ancestorIndex > index)
            return false;

        if (ancestorIndex == 0)
            return true;

        for (; index > -1; index = parentIndex(index))
            if (ancestorIndex == index) return true;

        return false;
    }

    // i 從 1 開始
    // 第 i level 最大 Node 數: 2^(i-1)
    static int getMaxCountByLevel(int level) {
        if (level < 1) return 0;
        return (int) Math.pow(2, level - 1);
    }

    static int getHeightByIndex(int index) {
        return getHeightByNodeCount(index + 1);
    }

    static int getHeightByNodeCount(int nodeCount) {
        // Log2(x) = ln(x)/ln(2)
        return (int) Math.ceil(Math.log(nodeCount + 1) / Math.log(2));
    }

    // i 從 1 開始
    // 高度為 i 的樹 之 最大總 Node 數: 2^i -1
    static int maxCount(int height) {
        return (int) Math.pow(2, height) - 1;
    }
}
