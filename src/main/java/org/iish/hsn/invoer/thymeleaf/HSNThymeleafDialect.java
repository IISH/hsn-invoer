package org.iish.hsn.invoer.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class HSNThymeleafDialect extends AbstractDialect {
    private final boolean randomToStaticSource;

    public HSNThymeleafDialect(final boolean randomToStaticSource) {
        this.randomToStaticSource = randomToStaticSource;
    }

    @Override
    public String getPrefix() {
        return "hsn";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new SrcAttrProcessor(randomToStaticSource));
        processors.add(new FieldAttrProcessor());
        return processors;
    }
}
