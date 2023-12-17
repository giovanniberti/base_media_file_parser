package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;
import io.github.giovanniberti.base_media_file_parser.boxes.InvalidBoxException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoxNodeTest {
    @Test
    void boxNodeThrowsIfMFHDHasChildren() {
        BoxType boxType = BoxType.MFHD;

        assertThrows(InvalidBoxException.class, () -> new BoxNode(boxType, 10, List.of(new BoxNode(BoxType.MDAT, 0))));
    }
}
