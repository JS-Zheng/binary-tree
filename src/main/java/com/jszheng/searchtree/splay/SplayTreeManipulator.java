package com.jszheng.searchtree.splay;

import com.jszheng.searchtree.AbstractBstManipulator;

public final class SplayTreeManipulator extends AbstractBstManipulator<SplayTree> {

    public SplayTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 伸展樹 (Splay Tree)");
        System.out.println("a. 為一種 self-balancing 二元搜尋樹");
        System.out.println("b. 儘管如此，伸展樹仍可能呈線性結構");
        System.out.println("c. 藉由將常尋訪的節點，旋轉至根節點附近，進而提高效能");
        System.out.println("d. 進行 Select 操作後，可隨時利用 print 觀察變化喔 :)");
        System.out.println("e. 使用分攤 (amortized) 方式，使插入、尋找 與刪除時間達 O(log n)");
        System.out.println();
    }

    @Override
    protected SplayTree createTree() {
        return new SplayTree(baseBt());
    }
}
