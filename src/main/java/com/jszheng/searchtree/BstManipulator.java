package com.jszheng.searchtree;

public final class BstManipulator extends AbstractBstManipulator<BinarySearchTree> {

    public BstManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 二元搜尋樹 (Binary Search Tree, BST)");
        System.out.println("a. 此實作採集合之定義 -- 不允許插入相同鍵之資料");
        System.out.println("b. 移除分支度為 2 之節點，預設採用中序前驅節點取代");
        System.out.println();
    }

    @Override
    protected BinarySearchTree createTree() {
        return new BinarySearchTree<>(baseBt());
    }
}
