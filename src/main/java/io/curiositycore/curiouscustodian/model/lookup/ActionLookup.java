package io.curiositycore.curiouscustodian.model.lookup;

import io.curiositycore.curiouscustodian.model.actions.GameAction;

import java.util.List;

public interface ActionLookup {
    List<GameAction> performLookup();

}
