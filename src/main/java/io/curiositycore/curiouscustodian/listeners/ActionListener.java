package io.curiositycore.curiouscustodian.listeners;

import io.curiositycore.curiouscustodian.model.actions.ActionManager;
import io.curiositycore.curiouscustodian.model.actions.block.BlockBreakAction;
import io.curiositycore.curiouscustodian.model.actions.GameAction;

import io.curiositycore.curiouscustodian.model.actions.block.BlockPlaceAction;
import io.curiositycore.curiouscustodian.model.actions.log.LoginAction;
import io.curiositycore.curiouscustodian.model.actions.log.LogoutAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;

public class ActionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        registerEvent(new BlockBreakAction(event, LocalDateTime.now()));
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        registerEvent(new BlockPlaceAction(event, LocalDateTime.now()));
    }
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event){
        registerEvent(new LogoutAction(event, LocalDateTime.now()));
    }
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event){
        registerEvent(new LoginAction(event, LocalDateTime.now()));
    }

    private void registerEvent(GameAction action){
        ActionManager.getInstance().registerAction(action);
    }
}
