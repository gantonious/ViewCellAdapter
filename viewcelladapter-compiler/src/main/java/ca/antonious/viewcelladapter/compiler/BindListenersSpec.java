package ca.antonious.viewcelladapter.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Created by George on 2016-12-27.
 */

public class BindListenersSpec {
    private String classImport;
    private String simpleClassName;

    private TypeMirror viewCellTypeMirror;
    private TypeMirror viewHolderTypeMirror;
    private TypeMirror listenerTypeMirror;

    private String methodName;

    private String viewHolderImport;
    private String viewHolderSimpleClassName;

    private String listenerImport;
    private String listenerSimpleClassName;

    public BindListenersSpec(Types typeUtils, Element methodElement) {
        TypeElement enclosingClass = (TypeElement) methodElement.getEnclosingElement();
        classImport = enclosingClass.getQualifiedName().toString();
        simpleClassName = enclosingClass.getSimpleName().toString();
        viewCellTypeMirror = enclosingClass.asType();

        ExecutableElement methodType = (ExecutableElement) methodElement;
        methodName = methodType.getSimpleName().toString();

        VariableElement viewHolderVar = methodType.getParameters().get(0);
        viewHolderTypeMirror = viewHolderVar.asType();

        TypeElement viewHolderType = (TypeElement) typeUtils.asElement(viewHolderTypeMirror);
        viewHolderImport = viewHolderType.getQualifiedName().toString();
        viewHolderSimpleClassName = viewHolderType.getSimpleName().toString();

        VariableElement listenerVar = methodType.getParameters().get(1);
        listenerTypeMirror = listenerVar.asType();

        TypeElement listenerType = (TypeElement) typeUtils.asElement(listenerTypeMirror);
        listenerImport = listenerType.getQualifiedName().toString();
        listenerSimpleClassName = listenerType.getSimpleName().toString();
    }

    public JavaFile generateCode() {
        TypeName viewHolder = ClassName.get(viewHolderTypeMirror);
        TypeName viewCell = ClassName.get(viewCellTypeMirror);
        TypeName listener = ClassName.get(listenerTypeMirror);
        ClassName superClassName = ClassName.get("ca.antonious.viewcelladapter", "ListenerBinder");
        TypeName superClass = ParameterizedTypeName.get(superClassName, viewCell, viewHolder);

        ClassName listenerCollection = ClassName.get("ca.antonious.viewcelladapter", "ListenerCollection");

        MethodSpec.Builder bindListenersMethodSpec = MethodSpec.methodBuilder("bindListeners")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(viewCell, "viewCell")
                .addParameter(viewHolder, "viewHolder")
                .addParameter(listenerCollection, "listenerCollection");

        MethodSpec methodSpec = bindListenersMethodSpec
                .addStatement("$T listener1 = listenerCollection.getListener($T.class)", listener, listener)
                .beginControlFlow("if (listener1 != null)")
                .addStatement(String.format("viewCell.%s(%s, %s)", methodName, "viewHolder", "listener1"))
                .endControlFlow()
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(simpleClassName + "_ListenerBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(superClass)
                .addMethod(methodSpec)
                .build();

        return JavaFile.builder("ca.antonious.sample.viewcells", typeSpec).build();
    }

    private MethodSpec generateBindListenersMethod() {
        return null;
    }

    @Override
    public String toString() {
        return generateCode().toString();
    }
}
