package parking.command.core;

import java.util.Collections;
import java.util.List;

/**
 * A {@link CommandTranslator} which delegate to other {@link CommandTranslator}.
 *
 * @author zakyalvan
 */
public class DelegatingCommandTranslator implements CommandTranslator {
    private final List<SmartCommandTranslator> delegateTranslators;

    public DelegatingCommandTranslator(List<SmartCommandTranslator> delegateTranslators) {
        if(delegateTranslators == null) {
            throw new IllegalArgumentException();
        }
        this.delegateTranslators = Collections.unmodifiableList(delegateTranslators);
    }

    @Override
    public Command translate(Input input) {
        if(input == null) {
            throw new BlankInputException();
        }

        CommandTranslator applicableTranslator = null;
        for(SmartCommandTranslator delegateTranslator : delegateTranslators) {
            if(delegateTranslator.supports(input)) {
                applicableTranslator = delegateTranslator;
                break;
            }
        }

        if(applicableTranslator == null) {
            throw new TranslatorNotFoundException(input);
        }

        return applicableTranslator.translate(input);
    }
}