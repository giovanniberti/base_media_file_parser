package io.github.giovanniberti.base_media_file_parser.boxes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoxTypeTest {
    @Test
    void canParseValidBoxType() {
        byte[] magic = new byte[]{0x6d, 0x6f, 0x6f, 0x66};

        assertDoesNotThrow(() -> {
            BoxType.fromBytes(magic);
        });
    }

    @Test
    void throwsOnInvalidBoxType() {
        byte[] magic = new byte[]{0x00, 0x00, 0x00, 0x00};

        assertThrows(InvalidBoxTypeException.class, () -> BoxType.fromBytes(magic));
    }

    @Test
    void throwsOnInvalidBoxTypeLength() {
        byte[] magic = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00};

        assertThrows(InvalidBoxTypeException.class, () -> BoxType.fromBytes(magic));
    }
}
