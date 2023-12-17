package io.github.giovanniberti.base_media_file_parser.boxes;

import static io.github.giovanniberti.base_media_file_parser.ByteUtils.bytesAsHex;

public class InvalidBoxTypeException extends RuntimeException {

    public InvalidBoxTypeException(byte[] rawValue) {
        super("Expected known box type, got [%s] (%s)".formatted(bytesAsHex(rawValue), new String(rawValue)));
    }
}
