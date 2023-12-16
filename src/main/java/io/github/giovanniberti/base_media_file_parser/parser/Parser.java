package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;
import io.github.giovanniberti.base_media_file_parser.boxes.InvalidBoxTypeException;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Parser implements Closeable, AutoCloseable {
    private final BufferedInputStream stream;

    private BoxNode root;

    public Parser(InputStream stream) {
        this.stream = new BufferedInputStream(stream);
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    public BoxNode parse() throws IOException {
        this.root = parseNextBox();

        if (this.root.type().equals(BoxType.MOOF)) {
            List<BoxNode> children = parseContainerBoxChildren(this.root.size());
            this.root.children().addAll(children);
        }

        return this.root;
    }

    private List<BoxNode> parseContainerBoxChildren(int contentSize) throws IOException {
        int startingOffset = stream.available();
        int endOffset = startingOffset - contentSize;

        List<BoxNode> children = new ArrayList<>();

        while (stream.available() >= endOffset) {
            BoxNode child = parseNextBox();
            children.add(child);
        }

        return children;
    }

    private BoxNode parseNextBox() throws IOException {
        byte[] sizeValue = readNBytes(4);
        int size = ByteBuffer.wrap(sizeValue).getInt();

        byte[] boxMagic = readNBytes(4);

        try {
            BoxType type = BoxType.fromBytes(boxMagic);

            int skipSize = size - 8;
            if (type.isContainer()) {
                skipSize = 0;
            }

            stream.skipNBytes(skipSize);

            return new BoxNode(type, size);
        } catch (InvalidBoxTypeException e) {
            throw new ParsingFailedException(this.root, e);
        }
    }

    private byte[] readNBytes(int length) throws IOException {
        byte[] bytes = stream.readNBytes(length);

        if (bytes.length < 4) {
            throw new IOException("Returned less bytes than requested: %d".formatted(length));
        }
        return bytes;
    }
}
