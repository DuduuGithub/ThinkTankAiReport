package db.dao.proxy;

import db.entity.Document;
import java.util.List;

/**
 * DocumentDao接口定义了文档相关的数据库操作方法
 */
public interface DocumentDao {

    /**
     * 插入一篇新论文
     * @param paper 论文对象
     */
    void insert(Document document);

    /**
     * 更新一篇论文的数据
     * @param paper 论文对象
     */
    void update(Document document);

    /**
     * 根据论文ID删除一篇论文
     * @param paperId 论文ID
     */
    void delete(String documentId);

    /**
     * 根据ID查询文档
     * @param documentId 文档ID
     * @return 文档对象
     */
    Document findById(Integer documentId);

    /**
     * 根据用户ID查找该用户下的所有论文
     * @param userId 用户ID
     * @return 论文列表
     */
    List<Document> findByUserId(String userId);

    /**
     * 根据关键词查询论文
     * @param keywords 关键词
     * @return 匹配关键词的论文列表
     */
    List<Document> findByKeywords(String keywords);

    /**
     * 查询所有论文数据
     * @return 所有论文列表
     */
    List<Document> findAll();

    /**
     * 批量插入多篇论文
     * @param documents 论文列表
     * @return 成功插入的论文数目
     */
    int batchInsert(List<Document> documents);

    /**
     * 批量导出指定ID列表的论文
     * @param documentIds 论文ID列表
     * @return 对应ID的论文列表
     */
    List<Document> batchExport(List<String> documentIds);
}

