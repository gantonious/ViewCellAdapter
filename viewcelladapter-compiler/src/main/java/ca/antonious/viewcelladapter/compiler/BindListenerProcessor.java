package ca.antonious.viewcelladapter.compiler;

import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import ca.antonious.viewcelladapter.annotations.BindListener;

@AutoService(Processor.class)
public class BindListenerProcessor extends BaseProcessor {
    private Map<TypeElement, BindListenersSpec.Builder> bindListenersSpecs;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        bindListenersSpecs = new HashMap<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindListener.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        findAndParseBindListeners(roundEnvironment);
        generateSourceFiles(bindListenersSpecs);

        return true;
    }

    private void generateSourceFiles(Map<TypeElement, BindListenersSpec.Builder> bindListenersSpecBuilders) {
        for (Map.Entry<TypeElement, BindListenersSpec.Builder> entry: bindListenersSpecBuilders.entrySet()) {
            try {
                entry.getValue().build().buildJavaFile().writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void findAndParseBindListeners(RoundEnvironment roundEnvironment) {
        for (Element element: roundEnvironment.getElementsAnnotatedWith(BindListener.class)) {
            TypeElement classType = (TypeElement) element.getEnclosingElement();
            BindListenersSpec.Builder bindListenersSpec = getOrCreateBindListenersSpecBuilder(classType);

            try {
                bindListenersSpec.visitMethod((ExecutableElement) element);
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
            }
        }
    }

    private BindListenersSpec.Builder getOrCreateBindListenersSpecBuilder(TypeElement typeElement) {
        if (!bindListenersSpecs.containsKey(typeElement)) {
            BindListenersSpec.Builder specBuilder = new BindListenersSpec.Builder(typeElement);
            bindListenersSpecs.put(typeElement, specBuilder);
        }
        return bindListenersSpecs.get(typeElement);
    }
}
