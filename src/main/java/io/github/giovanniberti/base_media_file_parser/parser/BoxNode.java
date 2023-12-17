package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;
import io.github.giovanniberti.base_media_file_parser.boxes.InvalidBoxException;

import java.util.ArrayList;
import java.util.List;

public record BoxNode(
        BoxType type,
        int size,
        List<BoxNode> children,
        String content
) {
    public BoxNode {
        if (!type.isContainer() && !children.isEmpty()) {
            throw new InvalidBoxException(type);
        }
    }

    BoxNode(BoxType type, int size, List<BoxNode> children) {
        this(type, size, children, null);
    }

    BoxNode(BoxType type, int size) {
        this(type, size, new ArrayList<>(), null);
    }
}
