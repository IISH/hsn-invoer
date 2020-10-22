package org.iish.hsn.invoer.thymeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

public class HSNThymeleafDialect extends AbstractProcessorDialect {
    private final boolean randomToStaticSource;

    public HSNThymeleafDialect(final boolean randomToStaticSource) {
        super("HSN", "hsn", StandardDialect.PROCESSOR_PRECEDENCE);
        this.randomToStaticSource = randomToStaticSource;
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new SrcAttrProcessor(dialectPrefix, randomToStaticSource));
        processors.add(new FieldAttrProcessor(dialectPrefix));
        return processors;
    }
}
