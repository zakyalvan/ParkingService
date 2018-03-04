package parking.command.core;

/**
 * @author zakyalvan
 */
public class FormatterUnavailableException extends RuntimeException {
    private final Result result;

    public FormatterUnavailableException(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
