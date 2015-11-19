package org.iish.hsn.invoer.thymeleaf;

import org.apache.commons.lang.RandomStringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.spring4.requestdata.RequestDataValueProcessorUtils;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

/**
 * Adds a random value to the source URL attribute to prevent caching.
 */
public class SrcAttrProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {
    private final boolean randomToStaticSource;

    public SrcAttrProcessor(final boolean randomToStaticSource) {
        super("src");
        this.randomToStaticSource = randomToStaticSource;
    }

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String attributeName) {
        return "src";
    }

    @Override
    protected String getTargetAttributeValue(final Arguments arguments, final Element element,
                                             final String attributeName) {
        final String attributeValue = super.getTargetAttributeValue(arguments, element, attributeName);
        String url = RequestDataValueProcessorUtils.processUrl(arguments.getConfiguration(), arguments, attributeValue);

        if (randomToStaticSource)
            return url + "?r=" + RandomStringUtils.randomAlphanumeric(10);
        return url;
    }

    @Override
    protected ModificationType getModificationType(final Arguments arguments, final Element element,
                                                   final String attributeName, final String newAttributeName) {
        return ModificationType.SUBSTITUTION;
    }

    @Override
    protected boolean removeAttributeIfEmpty(final Arguments arguments, final Element element,
                                             final String attributeName, final String newAttributeName) {
        return false;
    }
}
