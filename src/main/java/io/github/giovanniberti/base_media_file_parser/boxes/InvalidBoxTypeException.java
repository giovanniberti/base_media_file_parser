package io.github.giovanniberti.base_media_file_parser.boxes;

public class InvalidBoxTypeException extends RuntimeException {

    public InvalidBoxTypeException(byte[] rawValue) {
        super("Expected known box type, got [%s] (%s)".formatted(bytesAsHex(rawValue), new String(rawValue)));
    }

    private static String bytesAsHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b));
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
