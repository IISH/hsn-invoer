package org.iish.hsn.invoer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class ConverterConfiguration {
    @Bean
    public Converter<String, Integer> stringToIntegerConverter() {
        return new Converter<String, Integer>() {
            public Integer convert(String source) {
                try {
                    return Integer.valueOf(source);
                }
                catch (NumberFormatException nfe) {
                    return 0;
                }
            }
        };
    }
}
