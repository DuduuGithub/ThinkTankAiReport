package service;

import db.dao.proxy.DocumentDao;
import db.factory.DaoFactory;
import db.vo.Document;

public class DocumentService {
    private DocumentDao documentDaoProxy;

    public DocumentService() {
        documentDaoProxy = DaoFactory.createDocumentDao();
    }

    public Document getDocumentById(Integer id) {
        return documentDaoProxy.findById(id);
    }
}
