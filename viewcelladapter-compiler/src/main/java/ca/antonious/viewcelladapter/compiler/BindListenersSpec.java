package ca.antonious.viewcelladapter.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Created by George on 2016-12-27.
 */

public class BindListenersSpec {
    private String simpleClassName;
    private TypeMirror viewCellTypeMirror;
    private TypeMirror viewHolderTypeMirror;
    private PackageElement packageElement;
    private List<BindListenerSpec> bindListenerSpecs = new ArrayList<>();

    public BindListenersSpec(TypeElement classElement) {
        simpleClassName = classElement.getSimpleName().toString();
        packageElement = (PackageElement) classElement.getEnclosingElement();
        viewCellTypeMirror = classElement.asType();
    }

    public JavaFile generateCode() {
        TypeName viewHolder = ClassName.get(viewHolderTypeMirror);
        TypeName viewCell = ClassName.get(viewCellTypeMirror);

        ClassName superClassName = ClassName.get("ca.antonious.viewcelladapter", "ListenerBinder");
        TypeName superClass = ParameterizedTypeName.get(superClassName, viewCell, viewHolder);

        ClassName listenerCollection = ClassName.get("ca.antonious.viewcelladapter", "ListenerCollection");

        MethodSpec.Builder bindListenersMethodSpec = MethodSpec.methodBuilder("bindListeners")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewCell, "viewCell")
                .addParameter(viewHolder, "viewHolder")
                .addParameter(listenerCollection, "listenerCollection");

        for (int i = 0; i < bindListenerSpecs.size(); i++) {
            BindListenerSpec bindListenerSpec = bindListenerSpecs.get(i);
            TypeName listener = ClassName.get(bindListenerSpec.getListenerType());

            bindListenersMethodSpec
                    .addStatement("$T listener$L = listenerCollection.getListener($T.class)", listener, i, listener)
                    .beginControlFlow("if (listener$L != null)", i)
                    .addStatement(String.format("viewCell.%s(%s, %s)", bindListenerSpec.getBindListenerMethodName(), "viewHolder", "listener" + i))
                    .endControlFlow()
                    .build();
        }

        MethodSpec methodSpec = bindListenersMethodSpec.build();

        TypeSpec typeSpec = TypeSpec.classBuilder(simpleClassName + "_ListenerBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(superClass)
                .addMethod(methodSpec)
                .build();

        return JavaFile.builder(getPackageName(), typeSpec).build();
    }

    public void visitMethod(ExecutableElement methodElement) {
        String methodName = methodElement.getSimpleName().toString();

        VariableElement viewHolderVar = methodElement.getParameters().get(0);
        viewHolderTypeMirror = viewHolderVar.asType();

        VariableElement listenerVar = methodElement.getParameters().get(1);
        TypeMirror listenerTypeMirror = listenerVar.asType();

        addBindListenerSpec(new BindListenerSpec(methodName, listenerTypeMirror));
    }

    public BindListenersSpec addBindListenerSpec(BindListenerSpec bindListenerSpec) {
        this.bindListenerSpecs.add(bindListenerSpec);
        return this;
    }

    public String getPackageName() {
        return packageElement.getQualifiedName().toString();
    }

    @Override
    public String toString() {
        return generateCode().toString();
    }
}
