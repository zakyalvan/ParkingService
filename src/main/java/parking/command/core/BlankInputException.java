package parking.command.core;

/**
 * @author zakyalvan
 */
public class BlankInputException extends CommandTranslationException {
    public BlankInputException() {
        super("Blank raw input given for command translator");
    }
}
