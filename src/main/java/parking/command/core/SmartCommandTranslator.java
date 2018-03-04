package parking.command.core;

/**
 * @author zakyalvan
 */
public interface SmartCommandTranslator extends CommandTranslator {
    boolean supports(String input);
}
