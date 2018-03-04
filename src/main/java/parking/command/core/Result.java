package parking.command.core;

/**
 * Contract for output of {@link Command} execution.
 *
 * @author zakyalvan
 */
public interface Result {
    /**
     * Executed command.
     *
     * @return
     */
    Command command();

    /**
     * Flag determine whether execution was success.
     *
     * @return
     */
    boolean success();

    /**
     * Exception thrown, if command execution failed.
     *
     * @return
     */
    Throwable exception();
}