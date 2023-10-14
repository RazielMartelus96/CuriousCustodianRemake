package io.curiositycore.curiouscustodian.commands;

import io.curiositycore.curiouscustodian.commands.executable.lookup.LookupExecutable;
import io.curiositycore.curiouscustodian.commands.executable.rollback.RollbackExecutable;
import io.curiositycore.thecuriositycore.commands.managers.CommandManager;

public class CuriousCustodianCommandManager extends CommandManager {
    public CuriousCustodianCommandManager() {
        addExecuteable(new LookupExecutable());
        addExecuteable(new RollbackExecutable());
    }
}
