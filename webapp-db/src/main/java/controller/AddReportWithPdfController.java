package controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.vo.Document;

/*
 * 用途：用户通过表单传入一个pdf文件，通过调用大模型获得这个pdf的各个字段，并把这个报告存入数据库
 * 参数：request里面需要有pdf文件
 */
public class AddReportWithPdfController {
    @SuppressWarnings({ "unused" })
    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应类型为 JSON 格式
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Part filePart = request.getPart("pdfFile");
        if (filePart != null) {
            // 获取原始文件名
            String fileName = filePart.getSubmittedFileName();
            // 获取文件输入流
            InputStream pdfInputStream = filePart.getInputStream();

            // 获取
            Document document = new Document();

            // 返回响应
            response.getWriter().println("文件上传成功: " + fileName);
        } else {
            response.getWriter().println("未选择文件");
        }
    }
}
