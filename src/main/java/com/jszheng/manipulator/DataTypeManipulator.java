package com.jszheng.manipulator;

public class DataTypeManipulator extends Manipulator<Class> {

    private TreeManipulatorFactory factory;

    public DataTypeManipulator(TreeManipulatorFactory factory) {
        this.factory = factory;
    }

    @Override
    protected void addDefaultOperation() {
        addOperation("整數", String.class);
        addOperation("字串", Integer.class);
    }

    @Override
    public boolean handleOperation(int typeId) {
        if (!super.handleOperation(typeId))
            return false;

        Class type;

        if (typeId == 1)
            type = TreeManipulatorFactory.TYPE_INT;
        else if (typeId == 2)
            type = TreeManipulatorFactory.TYPE_String;
        else
            return false;

        factory.setDataType(type);
        factory.executeWithPrompt();

        return true;
    }

    @Override
    protected String getChoosePrompt() {
        return "========== A、請選擇資料型別 ==========\n";
    }
}
