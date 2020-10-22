package org.iish.hsn.invoer.thymeleaf;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import javax.persistence.Column;
import java.lang.reflect.Field;

/**
 * Adds specific attributes based on the field bind to this element.
 */
public class FieldAttrProcessor extends AbstractAttributeTagProcessor {
    public FieldAttrProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false,
                "field", true, 0, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        try {
            IEngineConfiguration configuration = context.getConfiguration();
            IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);

            // If we don't have a field and a class, find out what this variable refers to
            if (!attributeValue.contains(".")) {
                String expr = "${" + attributeValue + "}";
                IStandardExpression expression = expressionParser.parseExpression(context, expr);
                attributeValue = (String) expression.execute(context);
            }

            // Separate the attribute value, so we have the name of the field and the class to which it belongs
            int lastSeparator = attributeValue.lastIndexOf('.');
            String expr = "*{" + attributeValue.substring(0, lastSeparator) + "}";
            String field = attributeValue.substring(lastSeparator + 1);

            IStandardExpression expression = expressionParser.parseExpression(context, expr);
            Object objectOfField = expression.execute(context);

            // Now we have the object of this specific field, now inspect the class
            Class<?> classOfField = objectOfField.getClass();
            Field declaredField = classOfField.getDeclaredField(field);
            Class<?> typeOfField = declaredField.getType();

            // If the field is a string, then check the column attribute, is there a 'length' given?
            if (typeOfField.isAssignableFrom(String.class)) {
                Column column = declaredField.getAnnotation(Column.class);
                if ((column != null) && !tag.hasAttribute("maxlength")) {
                    structureHandler.setAttribute("maxlength", Integer.valueOf(column.length()).toString());
                }
            }

            // If the field is an integer, then add the corresponding class
            if (typeOfField.isAssignableFrom(Integer.class) || typeOfField.toString().equals("int")) {
                String attribute = "integer-field";
                if (tag.hasAttribute("class")) {
                    attribute += " " + tag.getAttributeValue("class");
                }
                structureHandler.setAttribute("class", attribute);
            }
        } catch (Exception e) {
            throw new TemplateProcessingException(attributeValue + " is not a valid field", e);
        }
    }
}
