package parking.command.core;

/**
 * @author zakyalvan
 */
public class TranslatorUnavailableException extends RuntimeException {
    private final String commandInput;

    public TranslatorUnavailableException(String commandInput) {
        this.commandInput = commandInput;
    }

    public String commandInput() {
        return commandInput;
    }
}
