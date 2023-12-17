package io.github.giovanniberti.base_media_file_parser.extractor;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;
import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MdatExtractor {
    private MdatExtractor() {}

    public static Map<String, byte[]> extractMdatImages(List<BoxNode> parsed) throws DocumentException {
        Map<String, byte[]> images = new HashMap<>();

        for (BoxNode node : parsed) {
            if (node.type().isContainer()) {
                images.putAll(extractMdatImages(node.children()));
            } else if (node.type().equals(BoxType.MDAT)) {
                if (node.content() != null) {
                    images.putAll(parseMdatData(node.content()));
                }
            }
        }

        return images;
    }

    private static Map<String, byte[]> parseMdatData(String content) throws DocumentException {
        InputStream stream = new ByteArrayInputStream(content.getBytes());

        SAXReader reader = new SAXReader();
        Document document = reader.read(stream);

        Map<String, String> uris = Map.of(
                "ttml", "http://www.w3.org/ns/ttml",
                "tts", "http://www.w3.org/ns/ttml#styling",
                "smpte", "http://www.smpte-ra.org/schemas/2052-1/2010/smpte-tt"
        );

        XPath xpath = document.createXPath("/tt/ttml:head/ttml:metadata/smpte:image");
        xpath.setNamespaceURIs(uris);

        List<Node> imageNodes = xpath.selectNodes(document);

        Map<String, byte[]> images = new HashMap<>();
        for (Node node : imageNodes) {
            Element e = ((Element) node);
            String id = e.attribute("id").getValue();
            String encodedContent = e.getStringValue().replace("\n","");

            byte[] decodedBytes = Base64.getDecoder().decode(encodedContent);

            images.put(id, decodedBytes);
        }

        return images;
    }
}
