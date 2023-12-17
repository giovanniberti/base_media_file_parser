package io.github.giovanniberti.base_media_file_parser.printer;

import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;
import io.github.giovanniberti.base_media_file_parser.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static io.github.giovanniberti.base_media_file_parser.ByteUtils.bytesAsHex;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrettyPrinterTest {
    @Test
    void prettyPrintsTestFileAsSnapshot() throws IOException, NoSuchAlgorithmException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        try (Parser parser = new Parser(is)) {
            List<BoxNode> parsed = parser.parse();

            PrettyPrinter prettyPrinter = new PrettyPrinter();

            String output = prettyPrinter.prettyPrint(parsed);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String sha256sum = bytesAsHex(digest.digest(output.getBytes())).replace(" ", "");

            assertEquals("8e49888cca0e81eaad8b94d751dccdc3f7dc19b1b596ca35a59be8d1b240dbaa", sha256sum);
        }
    }
}
