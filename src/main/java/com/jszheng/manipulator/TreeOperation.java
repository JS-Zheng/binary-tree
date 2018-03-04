package com.jszheng.manipulator;

import com.jszheng.base.Tree;

public interface TreeOperation<E extends Tree> {

    void execute(E tree);
}
