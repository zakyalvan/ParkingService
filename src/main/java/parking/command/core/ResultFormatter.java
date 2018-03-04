package parking.command.core;

/**
 * Format result of command execution into readable string representation.
 *
 * @author zakyalvan
 */
public interface ResultFormatter {
    /**
     * Format result.
     */
    String format(Result result);
}
