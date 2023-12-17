package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    void canParseTestFile() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        List<BoxNode> expectedStructure = List.of(
                new BoxNode(
                        BoxType.MOOF,
                        181,
                        List.of(
                                new BoxNode(
                                        BoxType.MFHD,
                                        16
                                ),
                                new BoxNode(
                                        BoxType.TRAF,
                                        157,
                                        List.of(
                                                new BoxNode(
                                                        BoxType.TFHD,
                                                        24
                                                ),
                                                new BoxNode(
                                                        BoxType.TRUN,
                                                        20
                                                ),
                                                new BoxNode(
                                                        BoxType.UUID,
                                                        44
                                                ),
                                                new BoxNode(
                                                        BoxType.UUID,
                                                        61
                                                )
                                        )
                                )
                        )
                ),
                new BoxNode(
                        BoxType.MDAT,
                        17908
                )
        );

        try (Parser parser = new Parser(is)) {
            List<BoxNode> boxNode = parser.parse();
            System.out.println(boxNode);

            assertEquals(boxNode, expectedStructure);
        } catch (ParsingFailedException e) {
            System.out.println(e.parsedTree);
            throw e;
        }
    }
}
