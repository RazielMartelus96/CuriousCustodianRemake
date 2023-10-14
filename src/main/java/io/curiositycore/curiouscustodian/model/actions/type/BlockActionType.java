package io.curiositycore.curiouscustodian.model.actions.type;

public enum BlockActionType implements ActionType {
    BLOCK_BREAK("broke a ",0),
    BLOCK_PLACE("placed a ",1),
    BLOCK_INTERACT("interacted with a ",2);

    private String typeString;
    private int typeId;
    BlockActionType(String typeString, int typeId){
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
