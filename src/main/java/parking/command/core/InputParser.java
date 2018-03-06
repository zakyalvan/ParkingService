package parking.command.core;

/**
 * Responsible for parsing raw command input, used in default command dispatching mechanism.
 *
 * @author zakyalvan
 */
public interface InputParser {
    /**
     * Parse raw command input into parse {@link Input}.
     *
     * @param input
     * @return
     */
    Input parse(String input);

    /**
     * Static factory method for default {@link InputParser}, hiding implementation details.
     *
     * @return
     */
    public static InputParser defaultParser() {
        return new DefaultInputParser();
    }
}
