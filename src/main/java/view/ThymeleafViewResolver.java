package view;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafViewResolver {
    
    private TemplateEngine templateEngine;

    public ThymeleafViewResolver() {
        // 配置模板解析器
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("src/main/WEB-INF/templates/");  // 模板文件所在的目录
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");

        // 创建模板引擎
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public String render(String templateName, Context context) {
        // 渲染模板并返回结果
        return templateEngine.process(templateName, context);
    }
}
