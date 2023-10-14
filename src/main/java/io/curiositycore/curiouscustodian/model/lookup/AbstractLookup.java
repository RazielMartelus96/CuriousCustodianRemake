package io.curiositycore.curiouscustodian.model.lookup;

import io.curiositycore.curiouscustodian.model.actions.ActionManager;
import io.curiositycore.curiouscustodian.model.actions.GameAction;
import io.curiositycore.curiouscustodian.model.actions.type.ActionType;
import io.curiositycore.curiouscustodian.model.lookup.util.LookupBuilder;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class AbstractLookup implements ActionLookup {
    protected UUID idFilter;
    protected ActionType actionTypeFilter;
    protected LocalDateTime[] timeFilter;
    protected Location radiusCenter;
    protected double radius;
    protected AbstractLookup(){
    }
    @Override
    public List<GameAction> performLookup() {
        Predicate<GameAction> lookupFilters = gameAction -> gameAction.getUserId().equals(this.idFilter)
                && gameAction.getTimestamp().isAfter(this.timeFilter[0])
                && gameAction.getTimestamp().isBefore(this.timeFilter[1]);
        if(this.actionTypeFilter != null){
            lookupFilters = lookupFilters.and(gameAction -> gameAction.getActionType().equals(this.actionTypeFilter));
        }
        if(this.radiusCenter != null){
            lookupFilters = lookupFilters.and(gameAction -> gameAction.getActionLocation().distance(this.radiusCenter) <= this.radius);
        }
        return ActionManager.getInstance().getPlayerActions(lookupFilters,timeFilter[0]);
    }

    public abstract static class AbstractLookupBuilder<T extends AbstractLookup> implements LookupBuilder<T> {
        private UUID idFilter;
        private ActionType actionTypeFilter;
        private LocalDateTime[] timeFilter;
        private Location radiusCenter;
        private double radius;
        @Override
        public T build() {
            if(this.idFilter == null || this.timeFilter == null){
                throw new IllegalArgumentException("Must have a user id and time filter");
            }
            T lookup = createLookup();
            lookup.idFilter = this.idFilter;
            lookup.actionTypeFilter = this.actionTypeFilter;
            lookup.timeFilter = this.timeFilter;
            lookup.radiusCenter = this.radiusCenter;
            lookup.radius = this.radius;
            return lookup;
        }
        protected abstract T createLookup();

        @Override
        public void addUserIdFilter(UUID uuid) {
            this.idFilter = uuid;
        }

        @Override
        public void addActionTypeFilter(ActionType actionType) {
            this.actionTypeFilter = actionType;
        }

        @Override
        public void addLookupRadius(Location radiusCenter, double radius) {
            this.radiusCenter = radiusCenter;
            this.radius = radius;
        }

        @Override
        public void addTimeFilter(LocalDateTime earliestTimeInRange, LocalDateTime currentLookupTime) {
            this.timeFilter = new LocalDateTime[]{earliestTimeInRange,currentLookupTime};
        }

    }
}
