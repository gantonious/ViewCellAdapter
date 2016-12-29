package ca.antonious.viewcelladapter.compiler;

import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import ca.antonious.viewcelladapter.annotations.BindListener;

@AutoService(Processor.class)
public class BindListenerProcessor extends BaseProcessor {
    private Types typesUtil;
    private Elements elementsUtils;

    private TypeElement abstractViewCellTypeElement;
    private Map<TypeElement, BindListenersSpec.Builder> bindListenersSpecs;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        typesUtil = processingEnv.getTypeUtils();
        elementsUtils = processingEnv.getElementUtils();

        abstractViewCellTypeElement = elementsUtils.getTypeElement("ca.antonious.viewcelladapter.viewcells.AbstractViewCell");
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
            DeclaredType declaredAbstractViewCell = getAbstractViewHolderDeclaration((DeclaredType) typeElement.asType());
            guardAgainstUsageInNonViewCellClasses(typeElement, declaredAbstractViewCell);

            TypeElement getViewHolderTypeMirror = getViewHolderTypeMirror(declaredAbstractViewCell);

            BindListenersSpec.Builder specBuilder = new BindListenersSpec.Builder(typeElement, getViewHolderTypeMirror);
            bindListenersSpecs.put(typeElement, specBuilder);
        }
        return bindListenersSpecs.get(typeElement);
    }

    private void guardAgainstUsageInNonViewCellClasses(TypeElement typeElement, DeclaredType declaredAbstractViewCell) {
        if (declaredAbstractViewCell == null) {
            String badClassName = typeElement.getQualifiedName().toString();
            String abstractViewCellName = abstractViewCellTypeElement.getSimpleName().toString();
            String errorTemplate = "%s with method annotated with @BindListener is not a subclass of %s.";
            String errorMessage = String.format(Locale.getDefault(), errorTemplate, badClassName, abstractViewCellName);

            throw new IllegalStateException(errorMessage);
        }
    }

    private void visitMethod(ExecutableElement methodElement, BindListenersSpec.Builder builder) {
        guardAgainstIncorrectParameterSize(methodElement, builder);
        guardAgainstIllegalViewHolderType(methodElement, builder);

        String methodName = methodElement.getSimpleName().toString();

        VariableElement listenerVar = methodElement.getParameters().get(1);
        TypeMirror listenerTypeMirror = listenerVar.asType();

        builder.addListener(new BindListenerSpec(methodName, listenerTypeMirror));
    }

    private void guardAgainstIncorrectParameterSize(ExecutableElement methodElement, BindListenersSpec.Builder builder) {
        int parametersSize = methodElement.getParameters().size();

        if (parametersSize != 2) {
            String viewHolderName = builder.getViewHolderElement().getQualifiedName().toString();
            String errorTemplate = "Expected two arguments of type %s, java.lang.Object for method annotated with @BindListener, but found %d argument(s).";
            String errorMessage = String.format(Locale.getDefault(), errorTemplate, viewHolderName, parametersSize);

            throw new IllegalStateException(errorMessage);
        }
    }

    private void guardAgainstIllegalViewHolderType(ExecutableElement methodElement, BindListenersSpec.Builder builder) {
        TypeMirror viewHolderType = methodElement.getParameters().get(0).asType();

        if (!typesUtil.isSameType(viewHolderType, builder.getViewHolderElement().asType())) {
            String actualViewHolderName = ((TypeElement) typesUtil.asElement(viewHolderType)).getQualifiedName().toString();
            String expectedViewHolderName = builder.getViewHolderElement().getQualifiedName().toString();
            String errorTemplate = "Expected first argument for method annotated with @BindListener to be of type %s, but found %s.";
            String errorMessage = String.format(Locale.getDefault(), errorTemplate, expectedViewHolderName, actualViewHolderName);

            throw new IllegalStateException(errorMessage);
        }
    }

    private TypeElement getViewHolderTypeMirror(DeclaredType declaredAbstractViewCell) {
        List<? extends TypeMirror> typeParameters = declaredAbstractViewCell.getTypeArguments();
        System.out.println(typeParameters.get(0));
        return (TypeElement) typesUtil.asElement(typeParameters.get(0));
    }

    private DeclaredType getAbstractViewHolderDeclaration(DeclaredType baseType) {
        while (true) {
            if (typesUtil.directSupertypes(baseType).size() == 0) {
                return null;
            }

            TypeMirror superClass = typesUtil.directSupertypes(baseType).get(0);
            baseType = (DeclaredType) superClass;

            if (typesUtil.isSameType(abstractViewCellTypeElement.asType(), baseType.asElement().asType())) {
                return baseType;
            }
        }
    }
}
