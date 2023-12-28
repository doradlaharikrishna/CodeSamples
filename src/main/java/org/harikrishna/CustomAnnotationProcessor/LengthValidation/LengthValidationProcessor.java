package org.harikrishna.CustomAnnotationProcessor.LengthValidation;

import com.google.auto.service.AutoService;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
public class LengthValidationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(LengthValidation.class)) {
            if (element.getKind() == ElementKind.CLASS) {

                LengthValidation lengthValidation = element.getAnnotation(LengthValidation.class);
                if (lengthValidation == null) {
                    return false;
                }

                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD
                            && !isLengthValid(enclosedElement.getSimpleName().toString(), lengthValidation)) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "Field '" + enclosedElement.getSimpleName().toString() + "' in class '"
                                        + element.getSimpleName().toString() + "' failed in length validation", enclosedElement);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(LengthValidation.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private boolean isLengthValid(String name, LengthValidation lengthValidation) {
        return name.length() >= lengthValidation.min() && name.length() <= lengthValidation.max();
    }
}
