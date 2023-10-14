package io.curiositycore.curiouscustodian.model.lookup.util;

import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import io.curiositycore.curiouscustodian.model.actions.type.BlockActionType;
import io.curiositycore.curiouscustodian.model.actions.type.LogActionType;
import io.curiositycore.curiouscustodian.model.lookup.LookupParams;
import io.curiositycore.curiouscustodian.model.lookup.types.BlockLookup;
import io.curiositycore.curiouscustodian.model.lookup.types.LogLookup;
import org.bukkit.command.CommandSender;


import java.util.Arrays;

public class LookupFactory {

    public static BlockLookup createBlockLookupBuilder(String[] args, CommandSender sender) {
        BlockLookup.AbstractLookupBuilder builder = new BlockLookup.AbstractLookupBuilder();
        addArgsToLookupBuilder(builder, args, sender, BlockActionType.class);
        return builder.build();
    }

    public static LogLookup createLogLookupBuilder(String[] args, CommandSender sender) {
        LogLookup.LogLookupBuilder builder = new LogLookup.LogLookupBuilder();
        addArgsToLookupBuilder(builder, args, sender, LogActionType.class);
        return builder.build();
    }
    private static <T extends ActionType> LookupBuilder<?> addActionTypeToLookupBuilder(LookupBuilder<?> builder, String argValue, Class<T> actionTypeClass) {
        ActionType paramActionType = Arrays.stream(actionTypeClass.getEnumConstants()).filter(actionType -> actionType.getTypeString().equals(argValue)).findFirst().orElseThrow();
        builder.addActionTypeFilter(paramActionType);
        return builder;
    }
    private static<T extends ActionType> LookupBuilder<?> addArgsToLookupBuilder(LookupBuilder<?> builder, String[] args, CommandSender sender, Class<T> actionTypeClass) {
        for (String arg : args) {
            String[] argParts = arg.split(":");
            if (argParts.length != 2) {
                throw new IllegalArgumentException("Invalid argument: " + arg);
            }
            String argName = argParts[0];
            String argValue = argParts[1];
            if(argName.equals("actionType")){
                builder = addActionTypeToLookupBuilder(builder, argValue, actionTypeClass);
                continue;
            }
            if (!isValidParam(argName)) {
                throw new IllegalArgumentException("Invalid argument: " + argName);
            }
            builder = addArgToLookupBuilder(builder, argName, argValue, sender);

        }
        return builder;
    }

    private static LookupBuilder<?> addArgToLookupBuilder(LookupBuilder<?> builder, String argName, String argValue, CommandSender sender) {
        LookupParams lookupParam = Arrays.stream(LookupParams.values())
                .filter(lookupParams -> lookupParams.getArgString().equals(argName)).findFirst().orElseThrow();
        return lookupParam.addArgToLookupBuilder(builder, argValue, sender);
    }

    private static boolean isValidParam(String argName) {
        return Arrays.stream(LookupParams.values()).anyMatch(lookupParams -> lookupParams.getArgString().equals(argName));
    }
}
