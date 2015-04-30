package org.iish.hsn.invoer.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class HSNThymeleafDialect extends AbstractDialect {
    public HSNThymeleafDialect() {
        super();
    }

    @Override
    public String getPrefix() {
        return "hsn";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new FieldAttrProcessor());
        return processors;
    }
}
