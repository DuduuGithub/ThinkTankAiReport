package db.factory;

import db.dao.proxy.DocumentDao;
import db.dao.proxy.UserDao;
import db.dao.proxy.impl.DocumentDaoImpl;
import db.dao.proxy.impl.UserDaoImpl;

public class DaoFactory {
    public static DocumentDao createDocumentDao() {
        return new DocumentDaoImpl();
    }
    
    public static UserDao createUserDao() {
        return new UserDaoImpl();
    }
} 