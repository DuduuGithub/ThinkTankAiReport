package controller;

import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserVerifyService;

public class LoginController {
    /*
     * 用途：处理用户登录请求
     * 参数：请求中需要有用户的userId和password
     */
    public static void processRequest(HttpServletRequest request,HttpServletResponse response) throws Exception{
        // 获取用户名和密码
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\a1372\\Desktop\\log.txt")) {
            fileWriter.write(userId + '\n' + password);
            System.out.println("文件写入成功！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件写入失败！");
        }

        if (UserVerifyService.userVerify(userId, password)) {
            // 认证成功，设置会话属性
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            // 设置会话过期时间（例如 30 分钟）
            session.setMaxInactiveInterval(30 * 60);
            // 重定向到首页
            response.sendRedirect("/abc/index.html");
        } else {
            // 认证失败，重定向回登录页面并添加错误参数
            //response.getWriter().println(userId + '\n' + password);
            response.sendRedirect("/abc/login.html?error=1");
        }
    }
}
