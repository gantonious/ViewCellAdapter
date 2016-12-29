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

import ca.antonious.viewcelladapter.annotations.BindListener;

@AutoService(Processor.class)
public class BindListenerProcessor extends BaseProcessor {
    private Map<TypeElement, BindListenersSpec> bindListenersSpecs;

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

    /*  Steps:
        1) Get all classes with @BindListener
        2) Ensure each class extends ViewCell
        3) Get all methods with @BindListener
        4) Check the parameters of each method
            - Check that there are only two parameters
            - Check parameter one matches the generic TViewHolder parameter
            - Check parameter two matches the specified listener class being bound to
        5) Build a spec defining all the methods that need to called
            - Get the appropriate listener from the ListenerCollection
            - Check if the listener is null
            - If not bind the listener to the view cell
        6) Generate the BindListeners implementation based on the spec

        set - annotations processed
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element: roundEnvironment.getElementsAnnotatedWith(BindListener.class)) {
            TypeElement classType = (TypeElement) element.getEnclosingElement();
            BindListenersSpec bindListenersSpec = getOrCreateBindListenersSpec(classType);
            bindListenersSpec.visitMethod((ExecutableElement) element);
        }

        for (Map.Entry<TypeElement, BindListenersSpec> entry: bindListenersSpecs.entrySet()) {
            try {
                entry.getValue().generateCode().writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private BindListenersSpec getOrCreateBindListenersSpec(TypeElement typeElement) {
        if (!bindListenersSpecs.containsKey(typeElement)) {
            BindListenersSpec spec = new BindListenersSpec(typeElement);
            bindListenersSpecs.put(typeElement, spec);
        }
        return bindListenersSpecs.get(typeElement);
    }

    private void checkAnnotation() {

    }
}
