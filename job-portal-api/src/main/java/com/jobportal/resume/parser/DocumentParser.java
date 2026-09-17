package com.jobportal.resume.parser;

import java.io.IOException;
import java.io.InputStream;

public interface DocumentParser {

    /**
     * Parses the given input stream and extracts all plain text content.
     */
    String parse(InputStream inputStream) throws IOException;

    /**
     * Returns true if this parser supports the given MIME content type or filename.
     */
    boolean supports(String contentType, String fileName);
}
