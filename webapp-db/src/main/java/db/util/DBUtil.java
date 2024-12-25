package db.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库工具类，提供数据库连接池和资源关闭功能
 */
public class DBUtil {
    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(DBConfig.getDriverClass());
            config.setJdbcUrl(DBConfig.getUrl());
            config.setUsername(DBConfig.getUsername());
            config.setPassword(DBConfig.getPassword());
            
            // 连接池配置
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(300000);
            config.setConnectionTimeout(20000);
            
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new DatabaseException("初始化数据库连接池失败", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseException("获取数据库连接失败", e);
        }
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DatabaseException("关闭数据库连接失败", e);
            }
        }
    }

    /**
     * 关闭PreparedStatement
     */
    public static void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new DatabaseException("关闭PreparedStatement失败", e);
            }
        }
    }

    /**
     * 关闭ResultSet
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DatabaseException("关闭ResultSet失败", e);
            }
        }
    }

    /**
     * 关闭所有数据库资源
     */
    public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        closeResultSet(rs);
        closePreparedStatement(pstmt);
        closeConnection(conn);
    }
}
