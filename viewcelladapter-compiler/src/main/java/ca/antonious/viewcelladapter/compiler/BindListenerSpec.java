package ca.antonious.viewcelladapter.compiler;

import javax.lang.model.type.TypeMirror;

/**
 * Created by George on 2016-12-28.
 */

public class BindListenerSpec {
    private String bindListenerMethodName;
    private TypeMirror listenerType;

    public BindListenerSpec(String bindListenerMethodName, TypeMirror listenerType) {
        this.bindListenerMethodName = bindListenerMethodName;
        this.listenerType = listenerType;
    }

    public String getBindListenerMethodName() {
        return bindListenerMethodName;
    }

    public TypeMirror getListenerType() {
        return listenerType;
    }
}
