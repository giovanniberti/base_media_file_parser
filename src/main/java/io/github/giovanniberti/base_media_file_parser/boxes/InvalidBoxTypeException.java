package io.github.giovanniberti.base_media_file_parser.boxes;

import java.util.Arrays;

public class InvalidBoxTypeException extends RuntimeException {

    public InvalidBoxTypeException(byte[] rawValue) {
        super("Expected box type, got %s (%s)".formatted(bytesAsHex(rawValue), new String(rawValue)));
    }

    private static String bytesAsHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
    }
}
