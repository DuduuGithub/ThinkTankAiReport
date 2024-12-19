package com.thinktank.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.thinktank.db.dao.proxy.LiteratureDAOProxy;

public class PdfTools {
    public static InputStream getPdfInputStream(int fileId){
        InputStream pdfInputStream = LiteratureDAOProxy.getPdfInputStream(fileId);
        return pdfInputStream;
    }
    public static String encodePdfToBase64(InputStream pdfInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return java.util.Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
}
