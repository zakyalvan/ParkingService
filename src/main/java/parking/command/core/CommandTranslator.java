package parking.command.core;

/**
 *
 * @author zakyalvan
 */
public interface CommandTranslator {
    /**
     * Translate raw command input into {@code Command} object.
     *
     * @param input
     * @return
     * @throws CommandTranslationException
     */
    Command translate(String input) throws CommandTranslationException;
}
