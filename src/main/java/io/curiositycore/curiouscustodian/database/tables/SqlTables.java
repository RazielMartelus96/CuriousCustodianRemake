package io.curiositycore.curiouscustodian.database.tables;

public enum SqlTables {
    BLOCK_ACTIONS("""
                CREATE TABLE IF NOT EXISTS block_actions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    timestamp TIMESTAMP NOT NULL,
                    action_location_x DOUBLE NOT NULL,
                    action_location_y DOUBLE NOT NULL,
                    action_location_z DOUBLE NOT NULL,
                    action_location_world VARCHAR(255) NOT NULL,
                    action_type VARCHAR(255) NOT NULL,
                    material VARCHAR(255) NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES uuids(uuid_id),
                    INDEX idx_user_id (user_id)
                );"""),
    UUIDS("""
                CREATE TABLE IF NOT EXISTS uuids (
                    uuid_id INT AUTO_INCREMENT PRIMARY KEY,
                    user_uuid CHAR(36) NOT NULL UNIQUE
                );
                """),
    LOG_ACTIONS("""
                CREATE TABLE IF NOT EXISTS log_actions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    timestamp TIMESTAMP NOT NULL,
                    action_location_x DOUBLE NOT NULL,
                    action_location_y DOUBLE NOT NULL,
                    action_location_z DOUBLE NOT NULL,
                    action_location_world VARCHAR(255) NOT NULL,
                    action_type VARCHAR(255) NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES uuids(uuid_id),
                    INDEX idx_user_id (user_id)
                );""");
    private String tableQuery;
    SqlTables(String tableQuery){
        this.tableQuery = tableQuery;
    }
    public String getTableQuery(){
        return this.tableQuery;
    }
}
