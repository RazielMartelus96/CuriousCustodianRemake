package io.curiositycore.curiouscustodian.model.actions.log;

import io.curiositycore.curiouscustodian.model.actions.GameAction;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class LogAction implements GameAction {
    protected Location actionLocation;
    protected LocalDateTime timestamp;
    protected UUID userId;

    protected LogAction(PlayerEvent event, LocalDateTime timestamp){
        this.actionLocation = event.getPlayer().getLocation();
        this.timestamp = timestamp;
        this.userId = event.getPlayer().getUniqueId();
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
        INSERT INTO log_actions (user_id, timestamp, action_location_x, action_location_y, action_location_z, action_location_world, action_type)
        VALUES (?, ?, ?, ?, ?, ?, ?);
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
                getActionType().getTypeId()
        };
    }



}
