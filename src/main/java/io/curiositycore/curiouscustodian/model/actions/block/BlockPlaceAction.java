package io.curiositycore.curiouscustodian.model.actions.block;

import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import io.curiositycore.curiouscustodian.model.actions.type.BlockActionType;
import org.bukkit.event.block.BlockPlaceEvent;

import java.time.LocalDateTime;

public class BlockPlaceAction extends BlockAction{
    public BlockPlaceAction(BlockPlaceEvent event, LocalDateTime timestamp) {
        super(event, timestamp);
        this.userId = event.getPlayer().getUniqueId();
    }

    @Override
    public ActionType getActionType() {
        return BlockActionType.BLOCK_PLACE;
    }
}
