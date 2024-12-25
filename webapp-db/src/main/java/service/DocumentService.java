package service;

import db.dao.proxy.DocumentDao;
import db.entity.Document;
import db.factory.DaoFactory;
import db.vo.DocumentVO;
import db.vo.converter.VOConverter;

public class DocumentService {
    private DocumentDao documentDaoProxy;

    public DocumentService() {
        documentDaoProxy = DaoFactory.createDocumentDao();
    }

    public DocumentVO getDocumentById(Integer id) {
        Document document = documentDaoProxy.findById(id);
        return VOConverter.toDocumentVO(document);
    }
}
