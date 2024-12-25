package db.vo;

import java.io.InputStream;

/**
 * DocumentVO类用于在展示层和业务层之间传递文档数据
 */
public class DocumentVO {
    private Integer documentId;  // 文档ID
    private String title;       // 文档标题
    private String keywords;    // 关键词
    private String subject;     // 主题
    private String content;     // 内容
    private Integer userId;     // 用户ID
    private InputStream pdfFile; // PDF文件

    // 默认构造函数
    public DocumentVO() {
    }

    // 带参数的构造函数
    public DocumentVO(String title, String keywords, String subject, String content, Integer userId) {
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
        return "DocumentVO{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + (content != null ? content.substring(0, Math.min(content.length(), 50)) + "..." : "null") + '\'' +
                ", userId=" + userId +
                '}';
    }
} 