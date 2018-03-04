package parking.command.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author zakyalvan
 */
public class CommandDispatcher {
    private DelegatingCommandTranslator commandTranslators;
    private DelegatingResultFormatter resultFormats;

    public CommandDispatcher(Consumer<List<SmartCommandTranslator>> translatorsCustomizer, Consumer<List<SmartResultFormatter>> formatsCustomizer) {
        if(translatorsCustomizer != null) {
            List<SmartCommandTranslator> delegateTranslators = new ArrayList<>();
            translatorsCustomizer.accept(delegateTranslators);
            this.commandTranslators = new DelegatingCommandTranslator(delegateTranslators);
        }
        if(formatsCustomizer != null) {
            List<SmartResultFormatter> delegateFormats = new ArrayList<>();
            formatsCustomizer.accept(delegateFormats);
            this.resultFormats = new DelegatingResultFormatter(delegateFormats);
        }
    }

    public String dispatch(String input) {
        try {
            Command command = commandTranslators.translate(input);
            Result result = command.execute();
            return resultFormats.format(result);
        }
        catch(TranslatorUnavailableException e) {
            return "No command translator : ".concat(e.commandInput());
        }
        catch(CommandTranslationException e) {
            return "Command translation error : ".concat(e.getMessage());
        }
        catch(Exception e) {
            e.printStackTrace();
            return "Unhandled error, see stack traces above";
        }
    }
}