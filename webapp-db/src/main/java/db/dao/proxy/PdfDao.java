package db.dao.proxy;

import java.io.InputStream;

/**
 * PdfDao接口定义了PDF文件相关的数据库操作
 */
public interface PdfDao {
    /**
     * 保存PDF文件
     * @param documentId 文档ID
     * @param pdfInputStream PDF文件输入流
     */
    void savePdf(Integer documentId, InputStream pdfInputStream);

    /**
     * 获取PDF文件输入流
     * @param documentId 文档ID
     * @return PDF文件输入流
     */
    InputStream getPdfInputStream(Integer documentId);
} 