package com.thinktank.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClsTokenGetterService {

    static public String getClsToken(String article, String outputFileName) {
        StringBuilder output = new StringBuilder();
        File tempFile = null;

        try {
            // 创建临时文件
            tempFile = File.createTempFile("article", ".txt");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(article); // 将文章内容写入临时文件
            }

            // 构建ProcessBuilder，传递临时文件路径
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "src/main/clsToken/getClsToken.py", tempFile.getAbsolutePath(), outputFileName);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor(); // 等待进程完成
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 删除临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }

        return output.toString(); // 返回Python脚本的输出
    }
    public static void main(String[] args) {
        ClsTokenGetterService.getClsToken("我是一个好人", "output.txt");
    }
}
