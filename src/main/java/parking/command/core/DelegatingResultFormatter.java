package parking.command.core;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author zakyalvan
 */
public class DelegatingResultFormatter implements ResultFormatter {
    private final List<SmartResultFormatter> delegateFormats;

    public DelegatingResultFormatter(List<SmartResultFormatter> delegateFormats) {
        if(delegateFormats == null) {
            throw new IllegalArgumentException();
        }
        this.delegateFormats = Collections.unmodifiableList(delegateFormats);
    }

    @Override
    public String format(Result result) {
        if(result == null) {
            throw new NullResultException();
        }

        ResultFormatter applicableFormat = null;

        for (SmartResultFormatter delegateFormat : delegateFormats) {
            if(delegateFormat.supports(result)) {
                applicableFormat = delegateFormat;
                break;
            }
        }

        if(applicableFormat == null) {
            throw new FormatterUnavailableException(result);
        }

        return applicableFormat.format(result);
    }
}