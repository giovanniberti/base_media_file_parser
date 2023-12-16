package io.github.giovanniberti.base_media_file_parser.parser;

import java.util.List;

public class ParsingFailedException extends RuntimeException {
    public final List<BoxNode> parsedTree;

    public ParsingFailedException(List<BoxNode> parsedTree, Throwable cause) {
        super("Parsing failed. Reason: %s".formatted(cause), cause);

        this.parsedTree = parsedTree;
    }
}
