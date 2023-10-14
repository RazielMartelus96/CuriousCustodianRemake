package io.curiositycore.curiouscustodian.model.actions;

import io.curiositycore.curiouscustodian.database.managers.DatabaseManager;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ActionManager {
    private LocalDateTime serverStartTime;
    private static ActionManager instance;
    private Map<LocalDateTime,GameAction> actionCache = new HashMap<>();
    public static ActionManager getInstance(){
        if(instance == null){
            instance = new ActionManager();
        }
        return instance;
    }

    public void registerAction(GameAction actionType){
        this.actionCache.put(actionType.getTimestamp(), actionType);
    }
    public void unregisterAction(LocalDateTime actionTimestamp){
        this.actionCache.remove(actionTimestamp);
    }

    public List<GameAction> getPlayerActions(Predicate<GameAction> lookupFilters, LocalDateTime earliestTimeInRange){
        if(!earliestTimeInRange.isAfter(this.serverStartTime)){
            registerDatabaseActions(lookupFilters, earliestTimeInRange);
        }

       return this.actionCache.values()
                .stream()
                .filter(lookupFilters)
               .sorted(Comparator
                       .comparing(GameAction::getTimestamp))
               .collect(Collectors.toList());
    }

    public void setUpTime(LocalDateTime serverStartTime){
        this.serverStartTime = serverStartTime;
    }

    public synchronized void sendAllBlockActionsToDatabase(){
        Bukkit.getLogger().info("Sending all block actions to database");
        Bukkit.getLogger().info("Action cache size: " + this.actionCache.size());
        this.actionCache.values().forEach(action -> {
            DatabaseManager.getInstance().insertBlockAction(action);

        });
        this.actionCache.clear();
    }

    //TODO set up sql database filtering by converting the predicate to a sql query.
    private void registerDatabaseActions(Predicate<GameAction> lookupFilters, LocalDateTime start){
    }

    public String getActionCacheSize() {
        return String.valueOf(this.actionCache.size());
    }
}


