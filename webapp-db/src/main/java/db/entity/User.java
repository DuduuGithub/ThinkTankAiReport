package db.entity;

/**
 * User实体类，映射数据库中的user表
 */
public class User {
    private Integer userId;    // 用户ID，自增主键
    private String password;   // 用户密码

    // 默认构造函数
    public User() {
    }

    // 带参数的构造函数
    public User(String password) {
        this.password = password;
    }

    // Getter和Setter方法
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                '}';
    }
} 