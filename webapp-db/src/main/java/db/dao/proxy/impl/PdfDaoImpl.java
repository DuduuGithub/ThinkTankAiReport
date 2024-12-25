package db.dao.proxy.impl;

import db.dao.proxy.PdfDao;
import db.exception.DatabaseException;
import db.util.DBUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PdfDaoImpl类实现了PdfDao接口中定义的PDF文件相关数据库操作
 */
public class PdfDaoImpl implements PdfDao {

    @Override
    public void savePdf(Integer documentId, InputStream pdfInputStream) {
        String sql = "UPDATE document SET pdf_file = ? WHERE document_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBinaryStream(1, pdfInputStream);
            pstmt.setInt(2, documentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("保存PDF文件失败", e);
        }
    }

    @Override
    public InputStream getPdfInputStream(Integer documentId) {
        String sql = "SELECT pdf_file FROM document WHERE document_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBinaryStream("pdf_file");
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("获取PDF文件失败", e);
        }
    }
} 