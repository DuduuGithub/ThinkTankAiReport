package db.factory;

import db.dao.proxy.DocumentDao;
import db.dao.proxy.PdfDao;
import db.dao.proxy.UserDao;
import db.dao.proxy.impl.DocumentDaoImpl;
import db.dao.proxy.impl.PdfDaoImpl;
import db.dao.proxy.impl.UserDaoImpl;
import db.exception.DatabaseException;
import java.lang.reflect.Proxy;

/**
 * DaoFactory类用于创建DAO实例，使用动态代理模式
 */
public class DaoFactory {
    
    /**
     * 创建DocumentDao的代理实例
     */
    public static DocumentDao createDocumentDao() {
        DocumentDaoImpl target = new DocumentDaoImpl();
        return (DocumentDao) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class[]{DocumentDao.class},
            (proxy, method, args) -> {
                try {
                    return method.invoke(target, args);
                } catch (Exception e) {
                    throw new DatabaseException("DocumentDao操作失败: " + e.getMessage(), e);
                }
            }
        );
    }
    
    /**
     * 创建UserDao的代理实例
     */
    public static UserDao createUserDao() {
        UserDaoImpl target = new UserDaoImpl();
        return (UserDao) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class[]{UserDao.class},
            (proxy, method, args) -> {
                try {
                    return method.invoke(target, args);
                } catch (Exception e) {
                    throw new DatabaseException("UserDao操作失败: " + e.getMessage(), e);
                }
            }
        );
    }

    /**
     * 创建PdfDao的代理实例
     */
    public static PdfDao createPdfDao() {
        PdfDaoImpl target = new PdfDaoImpl();
        return (PdfDao) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class[]{PdfDao.class},
            (proxy, method, args) -> {
                try {
                    return method.invoke(target, args);
                } catch (Exception e) {
                    throw new DatabaseException("PdfDao操作失败: " + e.getMessage(), e);
                }
            }
        );
    }
} 