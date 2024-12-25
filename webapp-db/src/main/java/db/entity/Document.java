package db.entity;

import java.io.InputStream;

/**
 * Document实体类，映射数据库中的document表
 */
public class Document {
    private Integer documentId;  // 文档ID，自增主键
    private String title;       // 文档标题
    private String keywords;    // 关键词
    private String subject;     // 主题
    private String content;     // 内容
    private Integer userId;     // 用户ID，外键
    private InputStream pdfFile; // PDF文件

    // 默认构造函数
    public Document() {
    }

    // 带参数的构造函数
    public Document(String title, String keywords, String subject, String content, Integer userId) {
        this.title = title;
        this.keywords = keywords;
        this.subject = subject;
        this.content = content;
        this.userId = userId;
    }

    // Getter和Setter方法
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public InputStream getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(InputStream pdfFile) {
        this.pdfFile = pdfFile;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", hasPdf=" + (pdfFile != null) +
                '}';
    }
} 