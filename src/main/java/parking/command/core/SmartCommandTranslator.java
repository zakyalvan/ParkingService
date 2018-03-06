package parking.command.core;

import parking.space.Space;

/**
 * @author zakyalvan
 */
public interface SmartCommandTranslator extends CommandTranslator {
    /**
     * Check whether this {@link Command} can be translated (or created)
     * based on given {@link Input}.
     *
     * @param input
     * @return
     */
    boolean supports(Input input);
}
