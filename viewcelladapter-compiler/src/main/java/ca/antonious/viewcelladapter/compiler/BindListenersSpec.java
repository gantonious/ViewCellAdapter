package ca.antonious.viewcelladapter.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by George on 2016-12-27.
 */

public class BindListenersSpec {
    private String viewCellClassName;
    private PackageElement packageElement;

    private TypeName viewCellTypeName;
    private TypeName viewHolderTypeName;
    private TypeName listenerCollectionTypeName;
    private TypeName bindListenersInterfaceName;

    private List<BindListenerSpec> bindListenerSpecs;

    private final static String LISTENER_BINDER_SUFFIX = "_ListenerBinder";
    private final static String BIND_LISTENERS_METHOD_NAME = "bindListeners";
    private final static String VIEW_CELL_VARIABLE_NAME = "viewCell";
    private final static String VIEW_HOLDER_VARIABLE_NAME = "viewHolder";
    private final static String LISTENER_COLLECTION_VARIABLE_NAME = "listenerCollection";

    private BindListenersSpec(TypeElement viewCellElement,
                              TypeMirror viewHolderType,
                              List<? extends BindListenerSpec> bindListenerSpecs) {

        this.bindListenerSpecs = new ArrayList<>();
        this.bindListenerSpecs.addAll(bindListenerSpecs);

        this.viewCellClassName = viewCellElement.getSimpleName().toString();
        this.packageElement = (PackageElement) viewCellElement.getEnclosingElement();

        this.viewCellTypeName = ClassName.get(viewCellElement.asType());
        this.viewHolderTypeName = ClassName.get(viewHolderType);
        this.listenerCollectionTypeName = ClassName.get("ca.antonious.viewcelladapter.viewcells.eventhandling", "ListenerCollection");

        ClassName listenerBinderClassName = ClassName.get("ca.antonious.viewcelladapter.viewcells.eventhandling", "ListenerBinder");
        this.bindListenersInterfaceName = ParameterizedTypeName.get(listenerBinderClassName, viewCellTypeName, this.viewHolderTypeName);
    }

    public JavaFile buildJavaFile() {
        return JavaFile.builder(getPackageName(), buildType())
                       .addFileComment("Generated code by ViewCellAdapter. Do not modify!")
                       .build();
    }

    private TypeSpec buildType() {
        return TypeSpec.classBuilder(viewCellClassName + LISTENER_BINDER_SUFFIX)
                       .addModifiers(Modifier.PUBLIC)
                       .addSuperinterface(bindListenersInterfaceName)
                       .addMethod(buildBindListenersMethod())
                       .build();
    }

    private MethodSpec buildBindListenersMethod() {
        MethodSpec.Builder methodSignatureBuilder = buildBindListenersMethodSignature();
        return buildBindListenersMethodBody(methodSignatureBuilder).build();
    }

    private MethodSpec.Builder buildBindListenersMethodSignature() {
        return MethodSpec.methodBuilder(BIND_LISTENERS_METHOD_NAME)
                         .addAnnotation(Override.class)
                         .addModifiers(Modifier.PUBLIC)
                         .addParameter(viewCellTypeName, VIEW_CELL_VARIABLE_NAME)
                         .addParameter(viewHolderTypeName, VIEW_HOLDER_VARIABLE_NAME)
                         .addParameter(listenerCollectionTypeName, LISTENER_COLLECTION_VARIABLE_NAME);
    }

    private MethodSpec.Builder buildBindListenersMethodBody(MethodSpec.Builder methodBuilder) {
        for (int i = 0; i < bindListenerSpecs.size(); i++) {
            BindListenerSpec bindListenerSpec = bindListenerSpecs.get(i);
            methodBuilder = buildBindListenerLines(methodBuilder, bindListenerSpec, i);
        }

        return methodBuilder;
    }

    private MethodSpec.Builder buildBindListenerLines(MethodSpec.Builder methodBuilder, BindListenerSpec bindListenerSpec, int index) {
        if (bindListenerSpec.shouldBindIfNull()) {
            return buildBindListenerWithoutNullCheck(methodBuilder, bindListenerSpec, index);
        }
        return buildBindListenerWithNullCheck(methodBuilder, bindListenerSpec, index);
    }

    private MethodSpec.Builder buildBindListenerWithNullCheck(MethodSpec.Builder methodBuilder, BindListenerSpec bindListenerSpec, int index) {
        TypeName listener = ClassName.get(bindListenerSpec.getListenerType());

        return methodBuilder
            .addCode("\n")
            .addStatement("$T listener$L = $N.getListener($T.class)", listener, index, LISTENER_COLLECTION_VARIABLE_NAME, listener)
            .beginControlFlow("if (listener$L != null)", index)
            .addStatement("$N.$N($N, $N)", VIEW_CELL_VARIABLE_NAME, bindListenerSpec.getBindListenerMethodName(), VIEW_HOLDER_VARIABLE_NAME, "listener" + index)
            .endControlFlow();
    }

    private MethodSpec.Builder buildBindListenerWithoutNullCheck(MethodSpec.Builder methodBuilder, BindListenerSpec bindListenerSpec, int index) {
        TypeName listener = ClassName.get(bindListenerSpec.getListenerType());

        return methodBuilder
            .addCode("\n")
            .addStatement("$T listener$L = $N.getListener($T.class)", listener, index, LISTENER_COLLECTION_VARIABLE_NAME, listener)
            .addStatement("$N.$N($N, $N)", VIEW_CELL_VARIABLE_NAME, bindListenerSpec.getBindListenerMethodName(), VIEW_HOLDER_VARIABLE_NAME, "listener" + index);
    }

    private String getPackageName() {
        return packageElement.getQualifiedName().toString();
    }

    @Override
    public String toString() {
        return buildJavaFile().toString();
    }

    public static class Builder {
        private TypeElement classElement;
        private TypeElement viewHolderElement;

        private List<BindListenerSpec> bindListenerSpecs;

        public Builder(TypeElement classElement, TypeElement viewCellElement) {
            this.classElement = classElement;
            this.viewHolderElement = viewCellElement;
            bindListenerSpecs = new ArrayList<>();
        }

        public TypeElement getViewHolderElement() {
            return viewHolderElement;
        }

        public BindListenersSpec build() {
            return new BindListenersSpec(classElement, viewHolderElement.asType(), bindListenerSpecs);
        }

        public Builder addListener(BindListenerSpec bindListenerSpec) {
            bindListenerSpecs.add(bindListenerSpec);
            return this;
        }
    }
}
