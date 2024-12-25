package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.vo.Document;
import service.PdfTools;

/*
 * 用途：用户通过表单传入一个pdf文件，通过调用大模型获得这个pdf的各个字段，并把这个报告存入数据库
 * 参数：request里面需要有pdf文件
 */
public class AddReportWithPdfController {
    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应类型为 JSON 格式

        String url = "C:\\Users\\a1372\\Desktop\\example.txt";
        String content = "Hello, this is a test content!";

        try {
            FileWriter writer = new FileWriter(url);
            writer.write(content);
            writer.close();
            System.out.println("File has been written successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Part filePart = request.getPart("file");
        if (filePart != null) {
            // 获取原始文件名
            String fileName = filePart.getSubmittedFileName();
            // 获取文件输入流
            InputStream pdfInputStream = filePart.getInputStream();

            // 获取
            Document document = new Document();

            // 返回响应
            // 构造JSON响应
            String jsonResponse = String.format(
                "{\"success\": true, \"message\": \"文件上传成功\", \"fileName\": \"%s\"}",
                fileName
            );
            response.getWriter().println(jsonResponse);


            url = "C:\\Users\\a1372\\Desktop\\example2.txt";
            content = "Hello, this is a test content!";
            content = PdfTools.getPdfContent(pdfInputStream);

            try {
                FileWriter writer = new FileWriter(url);
                writer.write(content);
                writer.close();
                System.out.println("File has been written successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            // 未选择文件的情况
            response.getWriter().println(
                "{\"success\": false, \"message\": \"请选择要上传的文件\"}"
            );

            url = "C:\\Users\\a1372\\Desktop\\example3.txt";
            content = "Hello, this is a test content!";

            try {
                FileWriter writer = new FileWriter(url);
                writer.write(content);
                writer.close();
                System.out.println("File has been written successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
