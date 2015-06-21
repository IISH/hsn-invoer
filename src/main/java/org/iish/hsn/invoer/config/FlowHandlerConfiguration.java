package org.iish.hsn.invoer.config;

import org.iish.hsn.invoer.flow.ExceptionHandlingFlowHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowHandlerConfiguration {

    /**
     * Spring Web Flow looks at the name of the flow handler to match the name of the flow.
     * As we want to use the same flow handler for all flows, register the bean with all flow names as aliases.
     *
     * @return An exception handling flow handler.
     */
    @Bean(name = {
            "geboorte/invoer", "geboorte/correctie", "geboorte/verwijderen",
            "overlijden/invoer", "overlijden/correctie", "overlijden/verwijderen",
            "huwelijk/invoer", "huwelijk/correctie", "huwelijk/verwijderen",
            "persoonskaart/invoer", "persoonskaart/correctie", "persoonskaart/verwijderen",
            "bevolkingsregister/invoer/per-regel", "bevolkingsregister/invoer/alle-regels",
            "bevolkingsregister/correctie/adressen", "bevolkingsregister/correctie/bijzonderheden",
            "bevolkingsregister/correctie/identificatie", "bevolkingsregister/correctie/personen",
            "bevolkingsregister/correctie/samenstelling", "bevolkingsregister/verwijderen",
    })
    public ExceptionHandlingFlowHandler flowHandler() {
        return new ExceptionHandlingFlowHandler();
    }
}
