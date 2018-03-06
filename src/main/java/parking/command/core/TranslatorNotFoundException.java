package parking.command.core;

/**
 * @author zakyalvan
 */
public class TranslatorNotFoundException extends CommandTranslationException {
    private final Input commandInput;

    public TranslatorNotFoundException(Input commandInput) {
        super(String.format("No command translator for raw command input %s", commandInput));
        this.commandInput = commandInput;
    }

    public Input commandInput() {
        return commandInput;
    }
}
