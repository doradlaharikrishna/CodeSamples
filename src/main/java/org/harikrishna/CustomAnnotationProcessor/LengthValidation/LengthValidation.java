package org.harikrishna.CustomAnnotationProcessor.LengthValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LengthValidation {

    int min() default 4;

    int max() default 5;
}
