package ca.antonious.viewcelladapter.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by George on 2016-12-27.
 */

public class BindListenersSpec {
    private String simpleClassName;
    private PackageElement packageElement;

    private TypeName viewCellType;
    private TypeName viewHolderType;
    private TypeName listenerCollectionType;
    private TypeName bindListenersInterface;

    private List<BindListenerSpec> bindListenerSpecs;

    private BindListenersSpec(TypeElement classElement,
                             TypeMirror viewHolderTypeMirror,
                             List<? extends BindListenerSpec> bindListenerSpecs) {

        this.bindListenerSpecs = new ArrayList<>();
        this.bindListenerSpecs.addAll(bindListenerSpecs);

        simpleClassName = classElement.getSimpleName().toString();
        packageElement = (PackageElement) classElement.getEnclosingElement();

        viewCellType = ClassName.get(classElement.asType());
        viewHolderType = ClassName.get(viewHolderTypeMirror);
        listenerCollectionType = ClassName.get("ca.antonious.viewcelladapter", "ListenerCollection");

        ClassName listenerBinderClassName = ClassName.get("ca.antonious.viewcelladapter", "ListenerBinder");
        bindListenersInterface = ParameterizedTypeName.get(listenerBinderClassName, viewCellType, viewHolderType);
    }

    public JavaFile buildJavaFile() {
        return JavaFile.builder(getPackageName(), buildType())
                       .addFileComment("Generated code by ViewCellAdapter. Do not modify!")
                       .build();
    }

    private TypeSpec buildType() {
        return TypeSpec.classBuilder(simpleClassName + "_ListenerBinder")
                       .addModifiers(Modifier.PUBLIC)
                       .addSuperinterface(bindListenersInterface)
                       .addMethod(buildBindListenersMethod())
                       .build();
    }

    private MethodSpec buildBindListenersMethod() {
        MethodSpec.Builder methodSignatureBuilder = buildBindListenersMethodSignature();
        return buildBindListenersMethodBody(methodSignatureBuilder).build();
    }

    private MethodSpec.Builder buildBindListenersMethodSignature() {
        return MethodSpec.methodBuilder("bindListeners")
                         .addAnnotation(Override.class)
                         .addModifiers(Modifier.PUBLIC)
                         .addParameter(viewCellType, "viewCell")
                         .addParameter(viewHolderType, "viewHolder")
                         .addParameter(listenerCollectionType, "listenerCollection");
    }

    private MethodSpec.Builder buildBindListenersMethodBody(MethodSpec.Builder methodBuilder) {
        for (int i = 0; i < bindListenerSpecs.size(); i++) {
            BindListenerSpec bindListenerSpec = bindListenerSpecs.get(i);
            TypeName listener = ClassName.get(bindListenerSpec.getListenerType());

            methodBuilder = methodBuilder
                    .addStatement("$T listener$L = listenerCollection.getListener($T.class)", listener, i, listener)
                    .beginControlFlow("if (listener$L != null)", i)
                    .addStatement(String.format("viewCell.%s(%s, %s)", bindListenerSpec.getBindListenerMethodName(), "viewHolder", "listener" + i))
                    .endControlFlow();
        }

        return methodBuilder;
    }

    public String getPackageName() {
        return packageElement.getQualifiedName().toString();
    }

    @Override
    public String toString() {
        return buildJavaFile().toString();
    }

    public static class Builder {
        private TypeElement classElement;
        private TypeMirror viewHolderTypeMirror;
        private List<BindListenerSpec> bindListenerSpecs;

        public Builder(TypeElement classElement) {
            this.classElement = classElement;
            bindListenerSpecs = new ArrayList<>();
        }

        public BindListenersSpec build() {
            return new BindListenersSpec(classElement, viewHolderTypeMirror, bindListenerSpecs);
        }

        public Builder visitMethod(ExecutableElement methodElement) {
            if (methodElement.getParameters().size() != 2) {
                throw new IllegalArgumentException("Expected two arguements for method annotated with @BindListener");
            }

            String methodName = methodElement.getSimpleName().toString();

            VariableElement viewHolderVar = methodElement.getParameters().get(0);
            viewHolderTypeMirror = viewHolderVar.asType();

            VariableElement listenerVar = methodElement.getParameters().get(1);
            TypeMirror listenerTypeMirror = listenerVar.asType();

            bindListenerSpecs.add(new BindListenerSpec(methodName, listenerTypeMirror));
            return this;
        }
    }
}
