import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AddReportWithPdfController;
import controller.LoginController;

@WebServlet("/app/*")
@MultipartConfig
public class RequestDispatchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            dispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            dispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 获取请求路径
        String path = request.getRequestURI();

        // 取出请求的方法字符串
        int index = path.lastIndexOf("/");
        path = path.substring(index);

        switch (path) {
            case "/login":
                LoginController.processRequest(request, response);
                break;
            case "/loadPdf":
                AddReportWithPdfController.processRequest(request,response);
                break;
            default:
                response.getWriter().println(path);
                break;
        }
    }
}
