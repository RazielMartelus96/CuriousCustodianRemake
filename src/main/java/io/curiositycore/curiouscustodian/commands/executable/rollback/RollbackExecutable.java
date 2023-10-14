package io.curiositycore.curiouscustodian.commands.executable.rollback;

import io.curiositycore.thecuriositycore.commands.executables.CommandExecutable;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RollbackExecutable extends CommandExecutable {
    @Override
    public void perform(CommandSender commandSender, String[] strings) {

    }

    @Override
    protected String initName() {
        return "rollback";
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
