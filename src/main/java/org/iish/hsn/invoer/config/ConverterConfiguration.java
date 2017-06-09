package org.iish.hsn.invoer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Bean
    public Converter<Integer, String> integerToStringConverter() {
        return new Converter<Integer, String>() {
            public String convert(Integer source) {
                if ((source == null) || (source == 0)) {
                    return "";
                }
                return source.toString();
            }
        };
    }

    @Bean
    public Converter<String, String> stringTrimmerConverter() {
        return new Converter<String, String>() {
            public String convert(String source) {
                if (source != null) {
                    return source.trim();
                }
                return "";
            }
        };
    }

    @Bean
    public Converter<String, Path> stringToPathConverter() {
        return new Converter<String, Path>() {
            public Path convert(String source) {
                return Paths.get(source);
            }
        };
    }
}
