package io.github.giovanniberti.base_media_file_parser.parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class ParserTest {
    @Test
    void canParseTestFile() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        try (Parser parser = new Parser(is)) {
            BoxNode boxNode = parser.parse();
            System.out.println(boxNode);
        }
    }
}