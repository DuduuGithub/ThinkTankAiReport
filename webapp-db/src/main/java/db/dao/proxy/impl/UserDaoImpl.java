package db.dao.proxy.impl;

import db.dao.proxy.UserDao;
import db.exception.DatabaseException;
import db.util.DBUtil;
import db.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDaoImpl类实现了UserDao接口中定义的用户相关数据库操作。
 */
public class UserDaoImpl implements UserDao {

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO user (password) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getPassword());
            pstmt.executeUpdate();
            
            // 获取生成的ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("插入用户数据失败", e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE user SET password = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setInt(2, user.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("更新用户数据失败", e);
        }
    }

    @Override
    public void delete(String userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(userId));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("删除用户数据失败", e);
        } catch (NumberFormatException e) {
            throw new DatabaseException("用户ID格式不正确", e);
        }
    }

    @Override
    public User findById(Integer userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("查询用户数据失败", e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException("查询所有用户数据失败", e);
        }
    }

    /**
     * 从ResultSet中提取用户数据
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
