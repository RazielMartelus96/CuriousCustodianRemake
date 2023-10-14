package io.curiositycore.curiouscustodian.model.actions.log;

import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import io.curiositycore.curiouscustodian.model.actions.type.LogActionType;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;


public class LogoutAction extends LogAction{
    public LogoutAction(PlayerQuitEvent event, LocalDateTime now) {
        super(event, now);
    }

    @Override
    public ActionType getActionType() {
        return LogActionType.LOGOUT;
    }
}
