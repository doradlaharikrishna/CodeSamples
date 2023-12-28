package org.harikrishna.CustomAnnotationProcessor.CamelCase;

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
public class CamelCaseProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(CamelCase.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD
                            && !enclosedElement.getModifiers().contains(Modifier.STATIC)
                            && !isCamelCase(enclosedElement.getSimpleName().toString())) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "Field '" + enclosedElement.getSimpleName().toString() + "' in class '"
                                        + element.getSimpleName().toString() + "' is not in camelCase", enclosedElement);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(CamelCase.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private boolean isCamelCase(String name) {
        // you can have whatever logic you want to be executed here
        return name.matches("[a-z][a-zA-Z0-9]*");
    }
}

