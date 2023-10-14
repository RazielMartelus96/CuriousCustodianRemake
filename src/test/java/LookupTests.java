import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.curiositycore.curiouscustodian.CuriousCustodian;
import io.curiositycore.curiouscustodian.model.actions.ActionManager;
import io.curiositycore.curiouscustodian.model.actions.GameAction;
import io.curiositycore.curiouscustodian.model.lookup.types.BlockLookup;
import io.curiositycore.curiouscustodian.model.actions.type.BlockActionType;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LookupTests {
    private ServerMock serverMock;
    private WorldMock worldMock;
    private BlockMock blockMock;
    @BeforeEach
    public void setUp() throws InterruptedException {
        this.serverMock = MockBukkit.getOrCreateMock();
        this.worldMock = this.serverMock.addSimpleWorld("world");
        this.blockMock = new BlockMock(Material.DIAMOND_BLOCK,this.worldMock.getSpawnLocation());
        MockBukkit.load(CuriousCustodian.class);
        Thread.sleep(1000);
    }

    @Test
    void blockBreakEventTest(){
        this.serverMock.getScheduler().performTicks(40);
        PlayerMock playerMock = new PlayerMock(this.serverMock,"TestPlayer");
        this.serverMock.getPluginManager().callEvent(new org.bukkit.event.block.BlockBreakEvent(this.blockMock,playerMock));
        this.serverMock.getScheduler().performTicks(40);
        BlockLookup.AbstractLookupBuilder builder = new BlockLookup.AbstractLookupBuilder();

        builder.addTimeFilter(java.time.LocalDateTime.now().minusMinutes(1),java.time.LocalDateTime.now());
        builder.addActionTypeFilter(BlockActionType.BLOCK_BREAK);
        builder.addUserIdFilter(playerMock.getUniqueId());

        BlockLookup blockLookup = builder.build();
        String lookupString = blockLookup.performLookup()
                .stream()
                .findFirst()
                .orElseThrow()
                .getActionString();

        ActionManager.getInstance().sendAllBlockActionsToDatabase();
        System.out.println(lookupString);
        Assertions.assertTrue(lookupString.contains("Player0 broke a diamond block at"));
    }
    //TODO joint tests fail as the static instance isnt deloaded between tests
    @Test
    void blockPlaceEventTest() throws InterruptedException {
        PlayerMock playerMock = new PlayerMock(this.serverMock,"TestPlayer");
        BlockMock placedBlock = new BlockMock(Material.DIAMOND_ORE,this.worldMock.getSpawnLocation().add(0,1,0));


        this.serverMock.getPluginManager().callEvent(new BlockPlaceEvent(placedBlock, placedBlock.getState(),this.blockMock,playerMock.getItemInHand(),playerMock,true, EquipmentSlot.OFF_HAND));
        this.serverMock.getScheduler().performTicks(40);
        BlockLookup.AbstractLookupBuilder builder = new BlockLookup.AbstractLookupBuilder();

        builder.addTimeFilter(java.time.LocalDateTime.now().minusMinutes(1),java.time.LocalDateTime.now());
        builder.addActionTypeFilter(BlockActionType.BLOCK_PLACE);
        builder.addUserIdFilter(playerMock.getUniqueId());

        BlockLookup blockLookup = builder.build();
        List<GameAction> lookupActionList = blockLookup.performLookup();
        ActionManager.getInstance().sendAllBlockActionsToDatabase();
    }

    @Test
    public void logoutTest(){
        PlayerMock playerMock = new PlayerMock(this.serverMock,"TestPlayer");
        this.serverMock.getPluginManager().callEvent(new org.bukkit.event.player.PlayerQuitEvent(playerMock,"Bye"));
        this.serverMock.getScheduler().performTicks(40);
        ActionManager.getInstance().sendAllBlockActionsToDatabase();

    }
    @Test
    public void loginTest(){
        PlayerMock playerMock = new PlayerMock(this.serverMock,"TestPlayer");
        this.serverMock.getPluginManager().callEvent(new org.bukkit.event.player.PlayerJoinEvent(playerMock,"Hi"));
        this.serverMock.getScheduler().performTicks(40);
        ActionManager.getInstance().sendAllBlockActionsToDatabase();

    }

    @Test
    public void lookupTest(){
        PlayerMock playerMock = new PlayerMock(this.serverMock,"TestPlayer");
        this.serverMock.getPluginManager().callEvent(new org.bukkit.event.player.PlayerJoinEvent(playerMock,"Hi"));
        this.serverMock.getScheduler().performTicks(40);

        this.serverMock.getPluginManager().callEvent(new org.bukkit.event.block.BlockBreakEvent(this.blockMock,playerMock));
        this.serverMock.getScheduler().performTicks(40);

        playerMock.performCommand("cc lookup block name:"+playerMock.getName()+ " time:1");
        this.serverMock.getScheduler().performTicks(40);

    }

    @AfterEach
    void tearDown() throws InterruptedException {
        this.serverMock.getScheduler().performTicks(100);
        Thread.sleep(1000);
        this.serverMock.getPluginManager().disablePlugin(this.serverMock.getPluginManager().getPlugin("CuriousCustodian"));


        MockBukkit.unmock();
    }

}
