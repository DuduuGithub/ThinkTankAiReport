package db.vo;

/**
 * UserVO类用于在展示层和业务层之间传递用户数据
 */
public class UserVO {
    private Integer userId;    // 用户ID
    private String password;   // 用户密码

    // 默认构造函数
    public UserVO() {
    }

    // 带参数的构造函数
    public UserVO(Integer userId, String password) {
        this.userId = userId;
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
        return "UserVO{" +
                "userId=" + userId +
                ", password='***'" +  // 出于安全考虑，不显示实际密码
                '}';
    }
} 