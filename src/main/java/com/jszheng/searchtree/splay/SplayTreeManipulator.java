package com.jszheng.searchtree.splay;

import com.jszheng.searchtree.BstManipulator;

public class SplayTreeManipulator extends BstManipulator {

    public SplayTreeManipulator(Class dataType) {
        super(dataType);
    }

    @Override
    protected void comment() {
        System.out.println("[註]: 伸展樹 (Splay Tree)");
        System.out.println("提示 -- 進行 Select 操作後，可隨時利用 print 觀察變化喔 :)\n");
    }

    @Override
    protected SplayTree createTree() {
        return new SplayTree(baseBt());
    }
}
