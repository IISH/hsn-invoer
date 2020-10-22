package org.iish.hsn.invoer.thymeleaf;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.requestdata.RequestDataValueProcessorUtils;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Random;

/**
 * Adds a random value to the source URL attribute to prevent caching.
 */
public class SrcAttrProcessor extends AbstractAttributeTagProcessor {
    private static final Random RANDOM = new Random();

    private final boolean randomToStaticSource;

    public SrcAttrProcessor(final String dialectPrefix, final boolean randomToStaticSource) {
        super(TemplateMode.HTML, dialectPrefix, null, false,
                "src", true, 0, true);
        this.randomToStaticSource = randomToStaticSource;
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        IEngineConfiguration configuration = context.getConfiguration();
        IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        IStandardExpression expression = expressionParser.parseExpression(context, attributeValue);

        String url = (String) expression.execute(context);
        url = RequestDataValueProcessorUtils.processUrl(context, url);

        if (randomToStaticSource) {
            url += "?r=" + Math.abs(RANDOM.nextInt());
        }
        structureHandler.setAttribute("src", url);
    }
}
