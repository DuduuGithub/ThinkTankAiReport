package db.vo.converter;

import db.entity.Document;
import db.entity.User;
import db.vo.DocumentVO;
import db.vo.UserVO;

/**
 * VOConverter工具类，用于在Entity和VO对象之间进行转换
 */
public class VOConverter {

    /**
     * 将User实体转换为UserVO
     */
    public static UserVO toUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setPassword(user.getPassword());
        return userVO;
    }

    /**
     * 将UserVO转换为User实体
     */
    public static User toUser(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userVO.getUserId());
        user.setPassword(userVO.getPassword());
        return user;
    }

    /**
     * 将Document实体转换为DocumentVO
     */
    public static DocumentVO toDocumentVO(Document document) {
        if (document == null) {
            return null;
        }
        DocumentVO documentVO = new DocumentVO();
        documentVO.setDocumentId(document.getDocumentId());
        documentVO.setTitle(document.getTitle());
        documentVO.setKeywords(document.getKeywords());
        documentVO.setSubject(document.getSubject());
        documentVO.setContent(document.getContent());
        documentVO.setUserId(document.getUserId());
        documentVO.setPdfFile(document.getPdfFile());
        return documentVO;
    }

    /**
     * 将DocumentVO转换为Document实体
     */
    public static Document toDocument(DocumentVO documentVO) {
        if (documentVO == null) {
            return null;
        }
        Document document = new Document();
        document.setDocumentId(documentVO.getDocumentId());
        document.setTitle(documentVO.getTitle());
        document.setKeywords(documentVO.getKeywords());
        document.setSubject(documentVO.getSubject());
        document.setContent(documentVO.getContent());
        document.setUserId(documentVO.getUserId());
        document.setPdfFile(documentVO.getPdfFile());
        return document;
    }
} 