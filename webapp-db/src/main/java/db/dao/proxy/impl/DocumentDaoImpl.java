package db.dao.proxy.impl;

import db.dao.proxy.DocumentDao;
import db.exception.DatabaseException;
import db.util.DBUtil;
import db.entity.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DocumentDaoImpl类实现了DocumentDao接口中定义的文档相关数据库操作
 */
public class DocumentDaoImpl implements DocumentDao {

    @Override
    public void insert(Document document) {
        String sql = "INSERT INTO document (title, keywords, subject, content, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getKeywords());
            pstmt.setString(3, document.getSubject());
            pstmt.setString(4, document.getContent());
            pstmt.setInt(5, document.getUserId());
            pstmt.executeUpdate();
            
            // 获取生成的ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    document.setDocumentId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("插入文档数据失败", e);
        }
    }

    @Override
    public void update(Document document) {
        String sql = "UPDATE document SET title = ?, keywords = ?, subject = ?, content = ?, user_id = ? WHERE document_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getKeywords());
            pstmt.setString(3, document.getSubject());
            pstmt.setString(4, document.getContent());
            pstmt.setInt(5, document.getUserId());
            pstmt.setInt(6, document.getDocumentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("更新文档数据失败", e);
        }
    }

    @Override
    public void delete(String documentId) {
        String sql = "DELETE FROM document WHERE document_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(documentId));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("删除文档数据失败", e);
        } catch (NumberFormatException e) {
            throw new DatabaseException("文档ID格式不正确", e);
        }
    }

    @Override
    public Document findById(Integer documentId) {
        String sql = "SELECT * FROM document WHERE document_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractDocumentFromResultSet(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("查询文档数据失败", e);
        }
    }

    @Override
    public List<Document> findByUserId(String userId) {
        String sql = "SELECT * FROM document WHERE user_id = ?";
        List<Document> documents = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(userId));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(extractDocumentFromResultSet(rs));
                }
            }
            return documents;
        } catch (SQLException e) {
            throw new DatabaseException("查询用户文档数据失败", e);
        } catch (NumberFormatException e) {
            throw new DatabaseException("用户ID格式不正确", e);
        }
    }

    @Override
    public List<Document> findByKeywords(String keywords) {
        String sql = "SELECT * FROM document WHERE keywords LIKE ?";
        List<Document> documents = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keywords + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(extractDocumentFromResultSet(rs));
                }
            }
            return documents;
        } catch (SQLException e) {
            throw new DatabaseException("根据关键词查询文档失败", e);
        }
    }

    @Override
    public List<Document> findAll() {
        String sql = "SELECT * FROM document";
        List<Document> documents = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
            return documents;
        } catch (SQLException e) {
            throw new DatabaseException("查询所有文档数据失败", e);
        }
    }

    @Override
    public int batchInsert(List<Document> documents) {
        String sql = "INSERT INTO document (title, keywords, subject, content, user_id) VALUES (?, ?, ?, ?, ?)";
        int successCount = 0;
        
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (Document document : documents) {
                    pstmt.setString(1, document.getTitle());
                    pstmt.setString(2, document.getKeywords());
                    pstmt.setString(3, document.getSubject());
                    pstmt.setString(4, document.getContent());
                    pstmt.setInt(5, document.getUserId());
                    pstmt.addBatch();
                    successCount++;
                    
                    if (successCount % 1000 == 0) {
                        pstmt.executeBatch();
                    }
                }
                pstmt.executeBatch();
                
                // 获取生成的ID
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    int i = 0;
                    while (rs.next() && i < documents.size()) {
                        documents.get(i).setDocumentId(rs.getInt(1));
                        i++;
                    }
                }
                
                conn.commit();
                return successCount;
            } catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("批量插入文档数据失败", e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("数据库连接失败", e);
        }
    }

    @Override
    public List<Document> batchExport(List<String> documentIds) {
        if (documentIds == null || documentIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        String placeholders = String.join(",", Collections.nCopies(documentIds.size(), "?"));
        String sql = "SELECT * FROM document WHERE document_id IN (" + placeholders + ")";
        
        List<Document> documents = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < documentIds.size(); i++) {
                pstmt.setInt(i + 1, Integer.parseInt(documentIds.get(i)));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(extractDocumentFromResultSet(rs));
                }
            }
            return documents;
        } catch (SQLException e) {
            throw new DatabaseException("批量导出文档数据失败", e);
        } catch (NumberFormatException e) {
            throw new DatabaseException("文档ID格式不正确", e);
        }
    }

    /**
     * 从ResultSet中提取文档数据
     */
    private Document extractDocumentFromResultSet(ResultSet rs) throws SQLException {
        Document document = new Document();
        document.setDocumentId(rs.getInt("document_id"));
        document.setTitle(rs.getString("title"));
        document.setKeywords(rs.getString("keywords"));
        document.setSubject(rs.getString("subject"));
        document.setContent(rs.getString("content"));
        document.setUserId(rs.getInt("user_id"));
        return document;
    }
}
