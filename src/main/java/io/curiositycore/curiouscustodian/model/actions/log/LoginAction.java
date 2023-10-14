package io.curiositycore.curiouscustodian.model.actions.log;

import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import io.curiositycore.curiouscustodian.model.actions.type.LogActionType;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

public class LoginAction extends LogAction{
    public LoginAction(PlayerJoinEvent event, LocalDateTime timestamp) {
        super(event, timestamp);
    }

    @Override
    public ActionType getActionType() {
        return LogActionType.LOGIN;
    }
}
