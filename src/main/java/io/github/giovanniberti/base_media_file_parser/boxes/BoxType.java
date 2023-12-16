package io.github.giovanniberti.base_media_file_parser.boxes;

import java.util.Arrays;

public enum BoxType {
    MOOF(new byte[]{0x6d, 0x6f, 0x6f, 0x66});

    private final byte[] magic;

    BoxType(byte[] magic) {
        this.magic = magic;
    }

    public static BoxType fromBytes(byte[] rawType) {
        if (rawType.length != 4) {
            throw new InvalidBoxTypeException(rawType);
        }

        for (BoxType boxType : BoxType.values()) {
            if (Arrays.equals(rawType, boxType.magic)) {
                return boxType;
            }
        }

        throw new InvalidBoxTypeException(rawType);
    }

    public boolean isContainer() {
        return this.equals(MOOF);
    }
}
