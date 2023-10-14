package io.curiositycore.curiouscustodian.commands.executable.lookup.sub;

import io.curiositycore.curiouscustodian.model.actions.GameAction;
import io.curiositycore.curiouscustodian.model.lookup.ActionLookup;
import io.curiositycore.curiouscustodian.model.lookup.util.LookupFactory;
import io.curiositycore.thecuriositycore.commands.executables.CommandExecutable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LogLookupExecutable extends CommandExecutable {

    @Override
    public void perform(CommandSender commandSender, String[] args) {
        ActionLookup lookup = LookupFactory.createLogLookupBuilder(args, commandSender);
        List<GameAction> gameActions = lookup.performLookup();
        gameActions.forEach(gameAction -> Bukkit.getLogger().info(gameAction.getActionString()));
    }

    @Override
    protected String initName() {
        return "session";
    }

    @Override
    protected String initDescription() {
        return null;
    }

    @Override
    protected String initSyntax() {
        return null;
    }

    @Override
    protected List<String> determineTabCompletes(String[] strings) {
        return null;
    }
}
