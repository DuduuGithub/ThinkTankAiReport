package db.dao.proxy;

import db.entity.User;
import java.util.List;

/**
 * UserDao接口定义了用户相关的数据库操作方法
 */
public interface UserDao {
    /**
     * 插入新用户数据
     * @param user 用户对象
     */
    void insert(User user);

    /**
     * 更新用户数据（如更新密码）
     * @param user 用户对象
     */
    void update(User user);

    /**
     * 根据用户ID删除用户
     * @param userId 用户ID
     */
    void delete(String userId);

    /**
     * 根据用户ID查询用户数据
     * @param userId 用户ID
     * @return 对应的用户对象，如果不存在则返回null
     */
    User findById(Integer userId);

    /**
     * 查询所有用户数据
     * @return 用户对象列表
     */
    List<User> findAll();
}
