package io.github.giovanniberti.base_media_file_parser;

public class ByteUtils {
    public static String bytesAsHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b));
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
