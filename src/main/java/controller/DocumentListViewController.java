package controller;

import db.vo.Document;
import service.DocumentService;
import view.ThymeleafViewResolver;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DocumentListViewController {

    private DocumentService documentService;
    private ThymeleafViewResolver viewResolver;

    public DocumentListViewController(DocumentService documentService) {
        this.documentService = documentService;
        this.viewResolver = new ThymeleafViewResolver();  // 使用你自己的 Thymeleaf 视图解析器
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = (String) request.getSession().getAttribute("userId");

        String title = request.getParameter("title");
        String keywords = request.getParameter("keywords");
        String subject = request.getParameter("subject");

        List<Document> documentList = documentService.searchDocuments(userId, title, keywords, subject);

        Context context = new Context();
        context.setVariable("documentList", documentList);
        context.setVariable("titleFilter", title);
        context.setVariable("keywordsFilter", keywords);
        context.setVariable("subjectFilter", subject);

        // 渲染模板并输出到响应流
        String renderedHtml = viewResolver.render("DocumentListView", context);
        response.setContentType("text/html");
        response.getWriter().write(renderedHtml);
    }
}
