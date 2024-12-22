package service;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.gson.Gson;

public class PdfMetaDataService {
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception {
        String filePath = "D:/郭如璇的文件/200大二上/马原/马原论文/AI能否产生真正的意识_—...个马克思主义哲学的分析视角_王亚萍.pdf";
        File file = new File(filePath);
        PDDocument document = PDDocument.load(file);

        // 使用PDFTextStripper提取文本
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);

        Map map = getMetaDataMap(text);
        System.out.println(map.get("title"));
        System.out.println(map.get("keywords"));
        System.out.println(map.get("subject"));
    }

    /*
     * 用途：获得pdf输入流的元数据
     * 参数：pdf的输入流
     * 返回：
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map getPdfMetaData(InputStream pdfInputStream) throws Exception {
        String pdfContent = PdfTools.getPdfContent(pdfInputStream);

        // 调用大模型获得结果
        String result = BigModelNew.askQuestion(pdfContent + "。" + "请你给出以上文章的标题（title），关键词（文章自带的关键词,keywords），主题词（根据文章内容生成的主题词，subject），返回的形式为一个形如{\"title\":\"value1\", \"keywords\":\"value2\", \"subject\":\"value3\"}的json字符串");

        // 截取result中的json字符串
        String jsonString = result = result.substring(result.indexOf('{'), result.lastIndexOf('}') + 1);

        // 创建Gson对象，用于把json字符串转换为Map
        Gson gson = new Gson();

        // 将json字符串转换为Map
        Map<String, Object> map = gson.fromJson(jsonString, Map.class);

        map.put("content", pdfContent);
        map.put("pdfFile",pdfInputStream);

        System.out.println(map);

        return map;
    }

    /*
     * 用途：用途本类的getPdfMetaData方法获取元数据
     * 参数：字符串——pdf的内容、
     * 返回：Map，包含pdf的
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected static Map getMetaDataMap(String pdfContent) throws Exception {

        // 调用大模型获得结果
        String result = BigModelNew.askQuestion(pdfContent + "。" + "请你给出以上文章的标题（title），关键词（文章自带的关键词,keywords），主题词（根据文章内容生成的主题词，subject），返回的形式为一个形如{\"title\":\"value1\", \"keywords\":\"value2\", \"subject\":\"value3\"}的json字符串");

        // 获得结果中的json字符串
        String jsonString = result = result.substring(result.indexOf('{'), result.lastIndexOf('}') + 1);

        // 创建Gson对象，用于把json字符串转换为Map
        Gson gson = new Gson();

        // 将json字符串转换为Map
        Map<String, Object> map = gson.fromJson(jsonString, Map.class);
        return map;
    }
}
