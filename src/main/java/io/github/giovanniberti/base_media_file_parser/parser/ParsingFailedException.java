package io.github.giovanniberti.base_media_file_parser.parser;

public class ParsingFailedException extends RuntimeException {
    public final BoxNode parsedTree;

    public ParsingFailedException(BoxNode parsedTree, Throwable cause) {
        super("Parsing failed. Reason: %s".formatted(cause), cause);

        this.parsedTree = parsedTree;
    }
}
