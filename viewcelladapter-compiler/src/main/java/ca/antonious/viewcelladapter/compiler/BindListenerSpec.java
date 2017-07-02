package ca.antonious.viewcelladapter.compiler;

import javax.lang.model.type.TypeMirror;

/**
 * Created by George on 2016-12-28.
 */

public class BindListenerSpec {
    private String bindListenerMethodName;
    private TypeMirror listenerType;
    private boolean bindIfNull;

    public BindListenerSpec(String bindListenerMethodName, TypeMirror listenerType, boolean bindIfNull) {
        this.bindListenerMethodName = bindListenerMethodName;
        this.listenerType = listenerType;
        this.bindIfNull = bindIfNull;
    }

    public String getBindListenerMethodName() {
        return bindListenerMethodName;
    }

    public TypeMirror getListenerType() {
        return listenerType;
    }

    public boolean shouldBindIfNull() {
        return bindIfNull;
    }
}
