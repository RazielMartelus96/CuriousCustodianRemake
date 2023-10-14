package io.curiositycore.curiouscustodian;

import io.curiositycore.curiouscustodian.commands.CuriousCustodianCommandManager;
import io.curiositycore.curiouscustodian.database.managers.DatabaseManager;
import io.curiositycore.curiouscustodian.model.actions.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;

public class CuriousCustodian extends JavaPlugin {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        initializeDatabase();
        DatabaseManager.getInstance().initialize(dbUrl, dbUser, dbPassword);
        this.getCommand("curiouscustodian").setExecutor(new CuriousCustodianCommandManager());
        ActionManager.getInstance().setUpTime(LocalDateTime.now());
        Bukkit.getPluginManager().registerEvents(new io.curiositycore.curiouscustodian.listeners.ActionListener(), this);
    }

    @Override
    public void onDisable() {

    }

    private void initializeDatabase(){
        this.dbUrl = getConfig().getString("database.host");
        this.dbUser = getConfig().getString("database.username");
        this.dbPassword = getConfig().getString("database.password");
    }
}
