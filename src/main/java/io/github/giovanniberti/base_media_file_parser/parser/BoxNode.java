package io.github.giovanniberti.base_media_file_parser.parser;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;

import java.util.ArrayList;
import java.util.List;

public record BoxNode(
        BoxType type,
        List<BoxNode> children
) {
    BoxNode(BoxType type) {
        this(type, new ArrayList<>());
    }
}
