package io.curiositycore.curiouscustodian.model.lookup;

import io.curiositycore.curiouscustodian.model.lookup.util.LookupBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum LookupParams {
    NAME("name") {
        @Override
        public LookupBuilder<?> addArgToLookupBuilder(LookupBuilder<?> builder, String argValue, CommandSender sender) {
            builder.addUserIdFilter(Bukkit.getOfflinePlayer(argValue).getUniqueId());
            return builder;
        }

        @Override
        public List<String> getTabCompletes() {
            return Collections.emptyList();
        }
    },

    ACTIVATION_RADIUS("activation_radius") {
        @Override
        public LookupBuilder<?> addArgToLookupBuilder(LookupBuilder<?> builder, String argValue, CommandSender sender) {
            if(!(sender instanceof Player player)){
                throw new IllegalArgumentException("Sender must be a player");
            }
            builder.addLookupRadius(player.getLocation(), Double.parseDouble(argValue));
            return builder;
        }

        @Override
        public List<String> getTabCompletes() {
            List<String> argsList = new ArrayList<>();
            for(int index = 0 ; index <=9 ; index++){
                argsList.add(this.getArgString() + ":" + (index+1));
            }
            return argsList;
        }
    },
    TIME_FILTER("time") {
        @Override
        public LookupBuilder<?> addArgToLookupBuilder(LookupBuilder<?> builder, String argValue, CommandSender sender) {
            if(!argValue.matches("\\d+")){
                throw new IllegalArgumentException("Time filter must be a number");
            }
            builder.addTimeFilter(getLocalDateTimeFromTimeFilter(Long.parseLong(argValue)), LocalDateTime.now());
            return builder;
        }

        @Override
        public List<String> getTabCompletes() {
            List<String> argsList = new ArrayList<>();
            for(int index = 0 ; index <=9 ; index++){
                argsList.add(this.getArgString() + ":" + (index+1));
            }
            return argsList;
        }
    };
    @Getter
    private final String argString;
    LookupParams(String argString){
        this.argString = argString;
    }

    public static boolean containsEnoughParams(String[] args){
        return args.length >= values().length-1;
    }
    public abstract LookupBuilder<?> addArgToLookupBuilder(LookupBuilder<?> builder, String argValue, CommandSender sender);
    public abstract List<String> getTabCompletes();
    private static LocalDateTime getLocalDateTimeFromTimeFilter(long timeFilterInDays){
        return LocalDateTime.now().minusDays(timeFilterInDays);
    }


}
