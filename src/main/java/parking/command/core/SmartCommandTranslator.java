package parking.command.core;

import parking.space.Space;

/**
 * @author zakyalvan
 */
public interface SmartCommandTranslator extends CommandTranslator {
    boolean supports(String input);
}
