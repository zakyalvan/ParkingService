package parking.command.impl;

import parking.command.core.CommandTranslationException;
import parking.space.PaintColor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zakyalvan
 */
public class InvalidColorArgumentException extends CommandTranslationException {
    private final String givenColor;

    public InvalidColorArgumentException(String givenColor) {
        super(String.format("Given paint color value '%s' is invalid, valid value are %s", givenColor, Stream.of(PaintColor.values()).sorted().collect(Collectors.toList()).toString()));
        this.givenColor = givenColor;
    }
}
