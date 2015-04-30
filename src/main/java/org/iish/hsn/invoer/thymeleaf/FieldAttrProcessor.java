package org.iish.hsn.invoer.thymeleaf;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Adds specific attributes based on the field bind to this element.
 */
public class FieldAttrProcessor extends AbstractAttrProcessor {
    public FieldAttrProcessor() {
        super("field");
    }

    @Override
    protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);

        try {
            // First translate the attribute value, if necessary
            Map argsExprEvalRoot = (Map) arguments.getExpressionEvaluationRoot();
            if (argsExprEvalRoot.containsKey(attributeValue)) {
                attributeValue = argsExprEvalRoot.get(attributeValue).toString();
            }

            // Separate the attribute value, so we have the name of the field and the class to which it belongs
            int lastSeperator = attributeValue.lastIndexOf('.');
            String expr = "*{" + attributeValue.substring(0, lastSeperator) + "}";
            String field = attributeValue.substring(lastSeperator + 1);

            Configuration configuration = arguments.getConfiguration();
            IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);

            IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, expr);
            Object objectOfField = expression.execute(configuration, arguments);

            // Now we have the object of this specific field, now inspect the class
            Class classOfField = objectOfField.getClass();
            Field declaredField = classOfField.getDeclaredField(field);
            Class<?> typeOfField = declaredField.getType();

            // If the field is a string, then check the column attribute, is there a 'length' given?
            if (typeOfField.isAssignableFrom(String.class)) {
                Column column = declaredField.getAnnotation(Column.class);
                if (column != null) {
                    element.setAttribute("maxlength", Integer.valueOf(column.length()).toString());
                }
            }

            // If the field is an integer, then add the corresponding class
            if (typeOfField.isAssignableFrom(Integer.class) || typeOfField.toString().equals("int")) {
                String attribute = "integer-field";
                if (element.hasAttribute("class")) {
                    attribute += " " + element.getAttributeValue("class");
                }
                element.setAttribute("class", attribute);
            }

            // Now delete this attribute
            element.removeAttribute("hsn:field");

            return ProcessorResult.OK;
        }
        catch (Exception e) {
            throw new TemplateProcessingException(attributeValue + " is not a valid field", e);
        }
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}
