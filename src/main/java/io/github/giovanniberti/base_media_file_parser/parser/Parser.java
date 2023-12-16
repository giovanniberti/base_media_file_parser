package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Parser implements Closeable, AutoCloseable {
    private final BufferedInputStream stream;

    public Parser(InputStream stream) {
        this.stream = new BufferedInputStream(stream);
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    public BoxNode parse() throws IOException {
        return parseNextBox();
    }

    private BoxNode parseNextBox() throws IOException {
        byte[] sizeValue = readNBytes(4);
        int size = ByteBuffer.wrap(sizeValue).getInt();

        byte[] boxMagic = readNBytes(4);
        BoxType type = BoxType.fromBytes(boxMagic);

        stream.skipNBytes(size);

        return new BoxNode(type);
    }

    private byte[] readNBytes(int length) throws IOException {
        byte[] bytes = stream.readNBytes(length);

        if (bytes.length < 4) {
            throw new IOException("Returned less bytes than requested: %d".formatted(length));
        }
        return bytes;
    }
}
