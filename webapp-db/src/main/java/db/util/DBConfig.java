package db.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库配置类，用于管理数据库连接信息
 */
public class DBConfig {
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("无法找到database.properties文件");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("加载数据库配置文件失败", e);
        }
    }
    
    public static String getUrl() {
        return properties.getProperty("db.url");
    }
    
    public static String getUsername() {
        return properties.getProperty("db.username");
    }
    
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
    
    public static String getDriverClass() {
        return properties.getProperty("db.driverClass");
    }
} 