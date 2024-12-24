package controller;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import service.UserVerifyService;

@Controller
@SessionAttributes("userId")  // 将 userId 存储在会话属性中
public class LoginController {

    @Autowired
    private UserVerifyService userVerifyService;  // 自动注入验证服务

    /**
     * 用途：处理用户登录请求
     * 参数：请求中需要有用户的 userId 和 password
     */
    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        // 调用用户验证服务
        if (userVerifyService.userVerify(userId, password)) {
            // 认证成功，设置会话属性
            model.addAttribute("userId", userId);  // 设置会话属性（Spring 会自动存储到 session 中）

            // 设置会话过期时间（例如 30 分钟）
            // 在 Spring 中，session 过期时间可以通过配置 session 处理
            // session.setMaxInactiveInterval(30 * 60); // 这行在 Spring 中通常在配置中设置

            // 重定向到首页
            return "redirect:/index";  // Spring 路由方式
        } else {
            // 认证失败，重定向回登录页面并添加错误参数
            redirectAttributes.addAttribute("error", 1);
            return "redirect:/login";  // 返回到登录页面
        }
    }

    /**
     * 用途：处理登录页面请求
     */
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) Integer error, Model model) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误，请重试！");
        }
        return "login";  // 返回登录页面
    }

    /**
     * 用途：处理首页请求
     */
    @GetMapping("/index")
    public String showHomePage(Model model) {
        // 可以在此处获取用户信息等
        return "index";  // 返回首页页面
    }

}
