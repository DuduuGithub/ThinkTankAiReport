import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;

@WebServlet("/app/*")
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
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            path = "/home"; // 默认路径
        }

        switch (path) {
            case "/login":
                LoginController.login(request, response);
                break;
            // case "/logout":
            //     LoginController.handleLogout(request, response);
            //     break;
            // case "/home":
            //     HomeController.showHome(request, response);
            //     break;
            // 处理其他路径
            default:
                response.getWriter().println(path);
                break;
        }
    }
}
