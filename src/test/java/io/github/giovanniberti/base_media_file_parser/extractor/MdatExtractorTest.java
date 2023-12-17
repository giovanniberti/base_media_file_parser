package io.github.giovanniberti.base_media_file_parser.extractor;

import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;
import io.github.giovanniberti.base_media_file_parser.parser.Parser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MdatExtractorTest {
    @Test
    void canExtractImagesFromSnapshot() throws IOException, DocumentException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        try (Parser parser = new Parser(is)) {
            List<BoxNode> parsed = parser.parse();

            Map<String, byte[]> images = MdatExtractor.extractMdatImages(parsed);
            Set<String> expectedIds = Set.of("image001", "image002", "image003");

            assertEquals(expectedIds, images.keySet());

            // Here we could use a hash function like md5 to check consistency of the extracted images
            // Left as an exercise for the reader :)
        }
    }
}
