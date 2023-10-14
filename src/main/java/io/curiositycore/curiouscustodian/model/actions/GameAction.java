package io.curiositycore.curiouscustodian.model.actions;

import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public interface GameAction {
    LocalDateTime getTimestamp();
    ActionType getActionType();
    UUID getUserId();
    Location getActionLocation();
    String getInsertSQL();
    Object[] getInsertValues();
    default String getActionTypeString() {
        return getActionType().getTypeString();
    }
    default String getActionString(){
        return Bukkit.getOfflinePlayer(getUserId()).getName() + " " +  getActionTypeString() + " at " + formatLocalDateTime() + " at location: " + getLocationString();
    }

    default String getLocationString(){
        return "(" + getActionLocation().getBlockX() + ", " + getActionLocation().getBlockY() +", " + getActionLocation().getBlockZ() + ")";
    }

    default String formatLocalDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getTimestamp().format(formatter);
    }




}
