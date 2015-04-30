package org.iish.hsn.invoer.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A simple annotation for controller methods that should not check whether the user entered valid input metadata.
 * This is the case for controller methods that handle user input on the input metadata.
 * If this annotation is not used, a redirect loop can occur.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NoInputMetadataCheck {
}
