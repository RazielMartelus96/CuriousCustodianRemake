package io.curiositycore.curiouscustodian.commands.executable.lookup;

import io.curiositycore.curiouscustodian.commands.executable.lookup.sub.BlockLookupExecutable;
import io.curiositycore.curiouscustodian.commands.executable.lookup.sub.LogLookupExecutable;
import io.curiositycore.thecuriositycore.commands.executables.CommandExecutable;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class LookupExecutable extends CommandExecutable {
    public LookupExecutable() {
        addSubCommand(new LogLookupExecutable());
        addSubCommand(new BlockLookupExecutable());
    }
    @Override
    public void perform(CommandSender commandSender, String[] strings) {
    }

    @Override
    protected String initName() {
        return "lookup";
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
    protected List<String> determineTabCompletes(String[] args) {
        return this.subCommands.values().stream().map(CommandExecutable::getName).toList();
    }

}
