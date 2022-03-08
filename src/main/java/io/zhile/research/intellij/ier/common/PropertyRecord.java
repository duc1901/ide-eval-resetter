// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.common;

import com.intellij.ide.util.PropertiesComponent;

public class PropertyRecord implements EvalRecord
{
    private final String type = "PROPERTY";
    private final String key;
    private final String value;
    
    public PropertyRecord(final String key) {
        this.key = key;
        this.value = PropertiesComponent.getInstance().getValue(key);
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void reset() throws Exception {
        PropertiesComponent.getInstance().unsetValue(this.key);
    }
    
    @Override
    public String toString() {
        return "PROPERTY: " + this.key + " = " + ((null == this.value) ? "" : this.value);
    }
}
