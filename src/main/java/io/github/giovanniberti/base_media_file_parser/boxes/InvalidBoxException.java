package io.github.giovanniberti.base_media_file_parser.boxes;

public class InvalidBoxException extends RuntimeException {
    public InvalidBoxException(BoxType type) {
        super("Tried to instantiate non-container BoxNode with type %s with children!".formatted(type));
    }
}
