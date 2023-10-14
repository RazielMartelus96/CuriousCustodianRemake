package io.curiositycore.curiouscustodian.model.actions.type;

public enum LogActionType implements ActionType{

    LOGOUT("logout", 0),

    LOGIN("login",1);

    private String typeString;
    private int typeId;

    LogActionType(String typeString, int typeId){
        this.typeString = typeString;
        this.typeId = typeId;
    }

    @Override
    public String getTypeString() {
        return this.typeString;
    }

    @Override
    public int getTypeId() {
        return this.typeId;
    }
}
