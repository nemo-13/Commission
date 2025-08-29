package DBService;

import java.sql.*;
import User.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private Connection connection;
    private int currentSessionId;

    public DatabaseService() {
        initializeDatabase();
        startNewSession();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            
            Statement pragmaStmt = connection.createStatement();
            pragmaStmt.execute("PRAGMA foreign_keys = ON");
            
            String createSessionsTable = """
                CREATE TABLE IF NOT EXISTS sessions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    start_time TIMESTAMP NOT NULL,
                    description VARCHAR(255) NOT NULL
                )
            """;
            
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id VARCHAR(36) PRIMARY KEY,
                    session_id INTEGER NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    user_type VARCHAR(10) NOT NULL,
                    data_source VARCHAR(10) NOT NULL,
                    FOREIGN KEY (session_id) REFERENCES sessions (id) ON DELETE CASCADE
                )
            """;
            
            Statement stmt = connection.createStatement();
            stmt.execute(createSessionsTable);
            stmt.execute(createUsersTable);
            
        } catch (SQLException e) {
            System.err.println("Ошибка инициализации базы данных: " + e.getMessage());
        }
    }

    private void startNewSession() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String startTime = LocalDateTime.now().format(formatter);
            String description = "Сеанс от " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            String sql = "INSERT INTO sessions (start_time, description) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, startTime);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                currentSessionId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка создания сеанса: " + e.getMessage());
        }
    }

    public void saveUser(User user, String dataSource) {
        try {
            String sql = """
                INSERT INTO users (id, session_id, name, email, user_type, data_source) 
                VALUES (?, ?, ?, ?, ?, ?)
            """;
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getId().toString());
            pstmt.setInt(2, currentSessionId);
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserType().toString());
            pstmt.setString(6, dataSource);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка сохранения пользователя: " + e.getMessage());
        }
    }

    public void clearDatabase() {
        try {
            String deleteUsers = "DELETE FROM users";
            String deleteSessions = "DELETE FROM sessions";
            
            Statement stmt = connection.createStatement();
            stmt.execute(deleteUsers);
            stmt.execute(deleteSessions);
            
            startNewSession();
            
        } catch (SQLException e) {
            System.err.println("Ошибка очистки базы данных: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия соединения: " + e.getMessage());
        }
    }
    
    public int getCurrentSessionId() {
        return currentSessionId;
    }
}