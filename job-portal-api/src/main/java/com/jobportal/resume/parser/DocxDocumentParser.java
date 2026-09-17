package com.jobportal.resume.parser;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DocxDocumentParser implements DocumentParser {

    @Override
    public String parse(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            String text = extractor.getText();
            return text != null ? text.trim() : "";
        }
    }

    @Override
    public boolean supports(String contentType, String fileName) {
        return (contentType != null && (contentType.toLowerCase().contains("word")
                || contentType.toLowerCase().contains("officedocument")
                || contentType.toLowerCase().contains("msword")))
                || (fileName != null && (fileName.toLowerCase().endsWith(".docx") || fileName.toLowerCase().endsWith(".doc")));
    }
}
