package com.jszheng.base;

@FunctionalInterface
interface TreeNodeConstructor<E> {

    void setDataArr(E[] data, boolean nullable);
}
