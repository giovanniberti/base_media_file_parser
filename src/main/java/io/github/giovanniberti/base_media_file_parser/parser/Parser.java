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

    private List<BoxNode> rootNodes;

    public Parser(InputStream stream) {
        this.stream = new BufferedInputStream(stream);
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    public List<BoxNode> parse() throws IOException {
        this.rootNodes = new ArrayList<>();

        while (stream.available() > 0) {
            BoxNode node = parseNextBox();
            this.rootNodes.add(node);
        }

        return this.rootNodes;
    }

    private List<BoxNode> parseContainerBoxChildren(int contentSize) throws IOException {
        int startingOffset = stream.available();
        int endOffset = startingOffset - contentSize;

        List<BoxNode> children = new ArrayList<>();

        while (stream.available() > endOffset) {
            BoxNode child = parseNextBox();
            children.add(child);
        }

        return children;
    }

    private BoxNode parseNextBox() throws IOException {
        final byte[] sizeValue = readNBytes(4);
        final int size = ByteBuffer.wrap(sizeValue).getInt();

        final byte[] boxMagic = readNBytes(4);

        try {
            final BoxType type = BoxType.fromBytes(boxMagic);

            final int contentSize = size - 8;
            int skipSize = contentSize;
            if (type.isContainer()) {
                skipSize = 0;
            }

            String content = null;

            if (type.equals(BoxType.MDAT)) {
                content = new String(readNBytes(contentSize));
            } else {
                stream.skipNBytes(skipSize);
            }

            List<BoxNode> children = List.of();
            if (type.isContainer()) {
                children = parseContainerBoxChildren(contentSize);
            }

            return new BoxNode(type, size, children, content);
        } catch (InvalidBoxTypeException e) {
            throw new ParsingFailedException(this.rootNodes, e);
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
