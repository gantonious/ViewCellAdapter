package ca.antonious.viewcelladapter.compiler;

import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
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
    private Types typeUtils;
    private Elements elementUtils;
    private Messager messager;

    private TypeElement abstractViewCellTypeElement;
    private Map<TypeElement, BindListenersSpec.Builder> bindListenersSpecs;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();

        abstractViewCellTypeElement = elementUtils.getTypeElement("ca.antonious.viewcelladapter.viewcells.AbstractViewCell");
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
            TypeElement viewCellType = (TypeElement) element.getEnclosingElement();
            BindListenersSpec.Builder bindListenersSpecBuilder = getOrCreateBindListenersSpecBuilder(viewCellType);

            try {
                ExecutableElement methodElement = (ExecutableElement) element;
                visitMethod(methodElement, bindListenersSpecBuilder);
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
            }
        }
    }

    private BindListenersSpec.Builder getOrCreateBindListenersSpecBuilder(TypeElement viewCellType) {
        if (!bindListenersSpecs.containsKey(viewCellType)) {
            BindListenersSpec.Builder specBuilder = createBindListenersSpecBuilder(viewCellType);
            bindListenersSpecs.put(viewCellType, specBuilder);
        }
        return bindListenersSpecs.get(viewCellType);
    }

    private BindListenersSpec.Builder createBindListenersSpecBuilder(TypeElement viewCellType) {
        DeclaredType declaredViewCell = (DeclaredType) viewCellType.asType();
        DeclaredType declaredAbstractViewCell = getAbstractViewHolderDeclaration(declaredViewCell);

        guardAgainstUsageInNonViewCellClasses(viewCellType, declaredAbstractViewCell);

        TypeElement viewHolderType = getGenericViewHolderType(declaredAbstractViewCell);

        return new BindListenersSpec.Builder(viewCellType, viewHolderType);
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

    private void visitMethod(ExecutableElement methodElement, BindListenersSpec.Builder specBuilder) {
        guardAgainstPrivateMethod(methodElement);
        guardAgainstIncorrectParameterSize(methodElement, specBuilder);
        guardAgainstIllegalViewHolderType(methodElement, specBuilder);

        String methodName = methodElement.getSimpleName().toString();

        VariableElement listenerVariable = methodElement.getParameters().get(1);
        TypeMirror listenerType = listenerVariable.asType();

        specBuilder.addListener(new BindListenerSpec(methodName, listenerType));
    }

    private void guardAgainstPrivateMethod(ExecutableElement methodElement) {
        if (methodElement.getModifiers().contains(Modifier.PRIVATE)) {
            String errorMessage = "Methods with @BindListener should not be private.";

            throw new IllegalStateException(errorMessage);
        }
    }

    private void guardAgainstIncorrectParameterSize(ExecutableElement methodElement, BindListenersSpec.Builder specBuilder) {
        int parametersSize = methodElement.getParameters().size();

        if (parametersSize != 2) {
            String viewHolderName = specBuilder.getViewHolderElement().getQualifiedName().toString();
            String errorTemplate = "Expected two arguments of type %s, java.lang.Object for method annotated with @BindListener, but found %d argument(s).";
            String errorMessage = String.format(Locale.getDefault(), errorTemplate, viewHolderName, parametersSize);

            throw new IllegalStateException(errorMessage);
        }
    }

    private void guardAgainstIllegalViewHolderType(ExecutableElement methodElement, BindListenersSpec.Builder specBuilder) {
        TypeMirror viewHolderType = methodElement.getParameters().get(0).asType();

        if (!typeUtils.isSameType(viewHolderType, specBuilder.getViewHolderElement().asType())) {
            String actualViewHolderName = ((TypeElement) typeUtils.asElement(viewHolderType)).getQualifiedName().toString();
            String expectedViewHolderName = specBuilder.getViewHolderElement().getQualifiedName().toString();
            String errorTemplate = "Expected first argument for method annotated with @BindListener to be of type %s, but found %s.";
            String errorMessage = String.format(Locale.getDefault(), errorTemplate, expectedViewHolderName, actualViewHolderName);

            throw new IllegalStateException(errorMessage);
        }
    }

    private TypeElement getGenericViewHolderType(DeclaredType declaredAbstractViewCell) {
        List<? extends TypeMirror> typeParameters = declaredAbstractViewCell.getTypeArguments();
        return (TypeElement) typeUtils.asElement(typeParameters.get(0));
    }

    private DeclaredType getAbstractViewHolderDeclaration(DeclaredType declaredViewCell) {
        while (true) {
            if (typeUtils.directSupertypes(declaredViewCell).size() == 0) {
                return null;
            }

            TypeMirror superClass = typeUtils.directSupertypes(declaredViewCell).get(0);
            declaredViewCell = (DeclaredType) superClass;

            if (typeUtils.isSameType(abstractViewCellTypeElement.asType(), declaredViewCell.asElement().asType())) {
                return declaredViewCell;
            }
        }
    }
}
