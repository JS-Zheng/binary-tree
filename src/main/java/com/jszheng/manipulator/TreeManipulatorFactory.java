package com.jszheng.manipulator;

import static com.jszheng.util.ReflectionUtil.newInstanceByClzWithParam;

public class TreeManipulatorFactory extends Manipulator<Class> {

    static final Class TYPE_INT = Integer.class;
    static final Class TYPE_String = String.class;
    private Class dataType;

    public TreeManipulatorFactory() {
    }

    @Override
    public boolean handleOperation(int operationId) {
        if (!super.handleOperation(operationId))
            return false;

        TreeManipulator manipulator = getManipulator(operationId);
        if (manipulator == null) return false;
        manipulator.executeWithPrompt();

        return true;
    }

    @Override
    protected String getChoosePrompt() {
        return "========== B、請選擇二元樹 ==========\n";
    }

    private TreeManipulator getManipulator(int id) {
        if (id > operations.size()) return null;

        try {
            Class manipulatorClz = operations.get(id - 1);
            Class[] types = {Class.class};
            Object[] params = {this.dataType};
            return (TreeManipulator) newInstanceByClzWithParam(manipulatorClz, types, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void register(String name, Class manipulatorClz) {
        operationNames.add(name);
        operations.add(manipulatorClz);
    }

    public void setDataType(Class dataType) {
        this.dataType = dataType;
    }
}
