package io.curiositycore.curiouscustodian.model.lookup.util;

import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import io.curiositycore.curiouscustodian.model.lookup.ActionLookup;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LookupBuilder<T extends ActionLookup> {

    ActionLookup build();
    void addUserIdFilter(UUID uuid);
    void addActionTypeFilter(ActionType actionType);
    void addLookupRadius(Location radiusCenter, double radius);
    void addTimeFilter(LocalDateTime earliestTimeInRange, LocalDateTime currentLookupTime);
}

