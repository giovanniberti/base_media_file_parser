package io.github.giovanniberti.base_media_file_parser.printer;

import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;
import io.github.giovanniberti.base_media_file_parser.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PrettyPrinterTest {
    @Test
    void prettyPrintsTestFileAsSnapshot() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        try (Parser parser = new Parser(is)) {
            List<BoxNode> parsed = parser.parse();

            PrettyPrinter prettyPrinter = new PrettyPrinter();

            String output = prettyPrinter.prettyPrint(parsed);
            System.out.println(output);
        }
    }
}
