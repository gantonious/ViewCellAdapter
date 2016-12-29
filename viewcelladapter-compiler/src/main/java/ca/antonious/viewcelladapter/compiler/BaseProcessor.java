package ca.antonious.viewcelladapter.compiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by George on 2016-12-27.
 */

public abstract class BaseProcessor extends AbstractProcessor {
    protected Types typeUtils;
    protected Elements elementUtils;
    protected Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
    }

    public boolean hasSupertype(DeclaredType declaredType) {
        return !typeUtils.directSupertypes(declaredType).isEmpty();
    }

    public DeclaredType getSupertypeOf(DeclaredType declaredType) {
        TypeMirror superClass = typeUtils.directSupertypes(declaredType).get(0);
        return (DeclaredType) superClass;
    }
}
