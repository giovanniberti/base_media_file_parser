package io.github.giovanniberti.base_media_file_parser.printer;

import io.github.giovanniberti.base_media_file_parser.boxes.BoxType;
import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;

import java.util.List;

public class PrettyPrinter {
    private StringBuilder stringBuilder;

    String prettyPrint(List<BoxNode> parsed) {
        stringBuilder = new StringBuilder();

        for (BoxNode node : parsed) {
            printBox(node, 0);
        }

        return stringBuilder.toString();
    }

    private void printBox(BoxNode boxNode, int indentationLevel) {
        String indentation = " ".repeat(indentationLevel * 4);
        stringBuilder.append("%sBox ID: %s of size %d\n".formatted(indentation, boxNode.type().name().toLowerCase(), boxNode.size()));

        for (BoxNode child : boxNode.children()) {
            printBox(child, indentationLevel + 1);
        }
    }
}
