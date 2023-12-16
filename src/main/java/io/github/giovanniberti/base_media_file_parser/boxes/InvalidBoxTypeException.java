package io.github.giovanniberti.base_media_file_parser.boxes;

public class InvalidBoxTypeException extends RuntimeException {

    public InvalidBoxTypeException(byte[] rawValue) {
        super("Expected box type, got %s".formatted(new String(rawValue)));
    }
}
