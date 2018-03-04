package parking.command.core;

/**
 * @author zakyalvan
 */
public interface SmartResultFormatter extends ResultFormatter {
    boolean supports(Result result);
}
