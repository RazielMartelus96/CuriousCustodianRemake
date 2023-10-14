package io.curiositycore.curiouscustodian.model.actions.block;

import io.curiositycore.curiouscustodian.model.actions.GameAction;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockEvent;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BlockAction implements GameAction{
    protected Material blockType;
    protected Location actionLocation;
    protected LocalDateTime timestamp;
    protected UUID userId;

    protected BlockAction(BlockEvent event, LocalDateTime timestamp){
        Block block = event.getBlock();
        this.blockType = block.getType();
        this.actionLocation = block.getLocation();
        this.timestamp = timestamp;
        ;
    }
    @Override
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public UUID getUserId() {
        return this.userId;
    }

    @Override
    public Location getActionLocation() {
        return this.actionLocation;
    }

    @Override
    public String getInsertSQL() {
        return """
        INSERT INTO block_actions (user_id, timestamp, action_location_x, action_location_y, action_location_z, action_location_world, action_type, material)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{
                this.userId,
                this.timestamp,
                this.actionLocation.getX(),
                this.actionLocation.getY(),
                this.actionLocation.getZ(),
                this.actionLocation.getWorld().getName(),
                getActionType().getTypeId(),
                getMaterialName()
        };
    }

    @Override
    public String getActionTypeString() {
        return GameAction.super.getActionTypeString() + formatMaterialToBlockName(this.blockType) + "block";
    }
    private String getMaterialName(){
        return this.blockType.name().replace("_"," ").toLowerCase();
    }

    private String formatMaterialToBlockName(Material blockType){
        String blockTypeString = blockType.name();
        if(blockTypeString.contains("BLOCK")){
            blockTypeString = blockTypeString.replace("BLOCK","");
        }
        blockTypeString = blockTypeString.toLowerCase().replace("_"," ");
        if(!blockTypeString.endsWith(" ")){
            return blockTypeString + " ";
        }
        return blockTypeString;
    }
}
