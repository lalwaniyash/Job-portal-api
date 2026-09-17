package com.jobportal.resume.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentParserFactory {

    private final List<DocumentParser> parsers;

    public DocumentParser getParser(String contentType, String fileName) {
        return parsers.stream()
                .filter(parser -> parser.supports(contentType, fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported file format: " + contentType + " (" + fileName + "). Only PDF and DOCX files are allowed."));
    }
}
