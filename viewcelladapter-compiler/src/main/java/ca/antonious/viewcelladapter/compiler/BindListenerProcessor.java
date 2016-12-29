package ca.antonious.viewcelladapter.compiler;

import com.google.auto.service.AutoService;

import java.lang.annotation.ElementType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import ca.antonious.viewcelladapter.annotations.BindListener;

@AutoService(Processor.class)
public class BindListenerProcessor extends BaseProcessor {
    private Types typesUtil;
    private Elements elementsUtils;

    private Map<TypeElement, BindListenersSpec.Builder> bindListenersSpecs;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        typesUtil = processingEnv.getTypeUtils();
        elementsUtils = processingEnv.getElementUtils();

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
                visitMethod((ExecutableElement) element, bindListenersSpec);
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
            }
        }
    }

    private BindListenersSpec.Builder getOrCreateBindListenersSpecBuilder(TypeElement typeElement) {
        if (!bindListenersSpecs.containsKey(typeElement)) {
            DeclaredType declaredAbstractViewCell = getAbstractViewHolderTypeV2((DeclaredType) typeElement.asType());
            TypeElement getViewHolderTypeMirror = getViewHolderTypeMirrorV2(declaredAbstractViewCell);

            System.out.println(getViewHolderTypeMirror.toString());

            BindListenersSpec.Builder specBuilder = new BindListenersSpec.Builder(typeElement, getViewHolderTypeMirror);
            bindListenersSpecs.put(typeElement, specBuilder);
        }
        return bindListenersSpecs.get(typeElement);
    }

    private void visitMethod(ExecutableElement methodElement, BindListenersSpec.Builder builder) {
        if (methodElement.getParameters().size() != 2) {
            throw new IllegalArgumentException("Expected two arguments of type ViewCell, Object for method annotated with @BindListener");
        }

        String methodName = methodElement.getSimpleName().toString();

        VariableElement listenerVar = methodElement.getParameters().get(1);
        TypeMirror listenerTypeMirror = listenerVar.asType();

        builder.addListener(new BindListenerSpec(methodName, listenerTypeMirror));
    }

    private TypeElement getViewHolderTypeMirrorV2(DeclaredType declaredAbstractViewCell) {
        List<? extends TypeMirror> typeParameters = declaredAbstractViewCell.getTypeArguments();
        System.out.println(typeParameters.get(0));
        return (TypeElement) typesUtil.asElement(typeParameters.get(0));
    }

    private DeclaredType getAbstractViewHolderTypeV2(DeclaredType baseType) {
        TypeMirror abstractViewCellType = elementsUtils.getTypeElement("ca.antonious.viewcelladapter.viewcells.AbstractViewCell").asType();

        while (true) {
            if (typesUtil.directSupertypes(baseType).size() == 0) {
                return null;
            }

            System.out.println(typesUtil.directSupertypes(baseType));
            TypeMirror superClass = typesUtil.directSupertypes(baseType).get(0);

            baseType = (DeclaredType) superClass;

            if (typesUtil.isSameType(abstractViewCellType, baseType.asElement().asType())) {
                return baseType;
            }
        }
    }
}
