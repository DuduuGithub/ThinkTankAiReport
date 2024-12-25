package service;

import db.dao.proxy.UserDao;
import db.entity.User;
import db.factory.DaoFactory;

public class UserVerifyService {

    /*
     * 用途：用于用户登录时对用户的验证
     * 参数：字符串形式的用户id和密码
     * 返回：如果验证通过，返回True，否则返回False
     */
    public static boolean userVerify(String userIdString,String password){
        
        // 把字符串userId转化为整数形式
        int userId = Integer.parseInt(userIdString);

        UserDao userDao = DaoFactory.createUserDao();
        User queryResult = userDao.findById(userId);

        if(queryResult == null){
            return false;
        }
        else{
            String rightPassword = queryResult.getPassword();
            return password.equals(rightPassword);
        }
    }
}
