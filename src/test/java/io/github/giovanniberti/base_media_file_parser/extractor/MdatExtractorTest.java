package io.github.giovanniberti.base_media_file_parser.extractor;

import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;
import io.github.giovanniberti.base_media_file_parser.parser.Parser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.giovanniberti.base_media_file_parser.ByteUtils.bytesAsHex;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MdatExtractorTest {
    @Test
    void canExtractImagesFromSnapshot() throws IOException, DocumentException, NoSuchAlgorithmException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        try (Parser parser = new Parser(is)) {
            List<BoxNode> parsed = parser.parse();

            Map<String, byte[]> images = MdatExtractor.extractMdatImages(parsed);
            Set<String> expectedIds = Set.of("image001", "image002", "image003");

            assertEquals(expectedIds, images.keySet());

            Map<String, String> imageHashes = Map.of(
                    "image001", "485eec5ba75f0f86e391722914ae367250071014f3d5cdcefd78ff972ffe74bc",
                    "image002", "79084c7f2a7c4c62ab0d45b38fd38f356ede6dafd1c90c1189c7eb11c80529af",
                    "image003", "79084c7f2a7c4c62ab0d45b38fd38f356ede6dafd1c90c1189c7eb11c80529af"
            );

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            for (Map.Entry<String, byte[]> image : images.entrySet()) {
                digest.reset();
                String sha256sum = bytesAsHex(digest.digest(image.getValue())).replace(" ", "");

                assertEquals(imageHashes.get(image.getKey()), sha256sum);
            }
        }
    }
}
