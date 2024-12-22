package controller;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.vo.Document;
import service.PdfMetaDataService;

/*
 * 用途：用户通过表单传入一个pdf文件，通过调用大模型获得这个pdf的各个字段，并把这个报告存入数据库
 * 参数：request里面需要有pdf文件
 */
public class AddReportWithPdfController {
    @SuppressWarnings({ "rawtypes", "unused" })
    public static void addTextWithPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Part filePart = request.getPart("pdfFile");
        if (filePart != null) {
            // 获取原始文件名
            String fileName = filePart.getSubmittedFileName();
            // 获取文件输入流
            InputStream pdfInputStream = filePart.getInputStream();

            // 获取pdf的各个字段
            Map metaDataMap = PdfMetaDataService.getPdfMetaData(pdfInputStream);
            // ----------------------------------------------------------------------------需要加上对应的用户id，这个等到会话写完再说

            Document document = new Document();

            // 返回响应
            response.getWriter().println("文件上传成功: " + fileName);
        } else {
            response.getWriter().println("未选择文件");
        }
    }
}
