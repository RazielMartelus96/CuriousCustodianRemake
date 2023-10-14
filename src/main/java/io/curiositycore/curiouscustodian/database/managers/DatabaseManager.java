package io.curiositycore.curiouscustodian.database.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.curiositycore.curiouscustodian.database.tables.SqlTables;
import io.curiositycore.curiouscustodian.model.actions.ActionManager;
import io.curiositycore.curiouscustodian.model.actions.GameAction;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;


import java.sql.*;
import java.util.Arrays;
import java.util.UUID;

public class DatabaseManager {
    private static DatabaseManager instance;
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    private HikariDataSource dataSource;

    public void initialize(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://"+url+":3306/"+user);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10); // Adjust as needed
        dataSource = new HikariDataSource(config);
        setupDatabase();
        Bukkit.getScheduler().runTaskTimerAsynchronously(Bukkit.getPluginManager().getPlugin("CuriousCustodian"), () -> {
            ActionManager.getInstance().sendAllBlockActionsToDatabase();
        }, 1, 20 * 60 * 5);
    }

    public void insertBlockAction(GameAction action) {
        String insertSQL = action.getInsertSQL();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            Object[] values = action.getInsertValues();
            for (int i = 0; i < values.length; i++) {
                if(values[i] instanceof UUID uuid){
                    values[i] = getOrInsertUUID(uuid);
                }
                preparedStatement.setObject(i + 1, values[i]);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    private void setupDatabase() {
        createDatabase(SqlTables.UUIDS.getTableQuery());
        Arrays.stream(SqlTables.values())
                .filter(sqlTable -> !sqlTable.equals(SqlTables.UUIDS))
                .forEach(sqlTable ->
                        createDatabase(sqlTable.getTableQuery()));
    }
    private void createDatabase(String sqlQuery){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getOrInsertUUID(UUID userUUID) throws SQLException {
        String selectSQL = "SELECT uuid_id FROM uuids WHERE user_uuid = ?";
        String insertSQL = "INSERT INTO uuids (user_uuid) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            selectStatement.setString(1, userUUID.toString());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("uuid_id");
            }

            insertStatement.setString(1, userUUID.toString());
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve uuid_id.");
            }
        }
    }
}
