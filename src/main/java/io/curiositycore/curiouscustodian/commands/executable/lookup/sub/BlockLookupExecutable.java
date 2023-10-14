package io.curiositycore.curiouscustodian.commands.executable.lookup.sub;

import io.curiositycore.curiouscustodian.model.actions.GameAction;
import io.curiositycore.curiouscustodian.model.lookup.ActionLookup;
import io.curiositycore.curiouscustodian.model.lookup.util.LookupFactory;
import io.curiositycore.thecuriositycore.commands.executables.CommandExecutable;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class BlockLookupExecutable extends CommandExecutable {

    @Override
    public void perform(CommandSender commandSender, String[] args) {
        String[] paramArray = Arrays.copyOfRange(args, 1, args.length);

        ActionLookup lookup = LookupFactory.createBlockLookupBuilder(paramArray, commandSender);
        List<GameAction> gameActions = lookup.performLookup();
        gameActions.forEach(gameAction -> Bukkit.getLogger().info(gameAction.getActionString()));
    }

    @Override
    protected String initName() {
        return "block";
    }

    @Override
    protected String initDescription() {
        return "Lookup block actions";
    }

    @Override
    protected String initSyntax() {
        return "/lookup block <params>";
    }

    @Override
    protected List<String> determineTabCompletes(String[] strings) {
        return Collections.emptyList();
    }
}
