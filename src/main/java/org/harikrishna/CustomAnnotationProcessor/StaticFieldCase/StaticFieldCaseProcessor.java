package org.harikrishna.CustomAnnotationProcessor.StaticFieldCase;

import com.google.auto.service.AutoService;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
public class StaticFieldCaseProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(StaticFieldCase.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD
                            && enclosedElement.getModifiers().contains(Modifier.STATIC)
                            && !isStaticFieldCase(enclosedElement.getSimpleName().toString())) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "Field '" + enclosedElement.getSimpleName().toString() + "' in class '"
                                        + element.getSimpleName().toString() + "' is not in SCREAMING_SNAKE_CASE", enclosedElement);
                    }
                }
            }
        }
        return false;
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(StaticFieldCase.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private boolean isStaticFieldCase(String name) {
        // you can have whatever logic you want to be executed here
        return name.matches("[A-Z0-9_]+");
    }
}
