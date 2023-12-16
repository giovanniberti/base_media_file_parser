package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;

import java.util.ArrayList;
import java.util.List;

public record BoxNode(
        BoxType type,
        int size,
        List<BoxNode> children
) {
    BoxNode(BoxType type, int size) {
        this(type, size, new ArrayList<>());
    }
}
