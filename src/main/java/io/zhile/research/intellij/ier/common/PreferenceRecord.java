// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.common;

import com.intellij.ide.Prefs;
import java.util.prefs.Preferences;

public class PreferenceRecord implements EvalRecord
{
    private static final String DEFAULT_VALUE;
    private final String type = "PREFERENCE";
    private final String key;
    private final String value;
    private final boolean isRaw;
    
    public PreferenceRecord(final String key) {
        this(key, false);
    }
    
    public PreferenceRecord(final String key, final boolean isRaw) {
        this.key = key;
        this.isRaw = isRaw;
        this.value = (isRaw ? Preferences.userRoot().get(key, PreferenceRecord.DEFAULT_VALUE) : Prefs.get(key, PreferenceRecord.DEFAULT_VALUE));
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void reset() throws Exception {
        if (this.isRaw) {
            Preferences.userRoot().remove(this.key);
        }
        else {
            Prefs.remove(this.key);
        }
        Resetter.syncPrefs();
    }
    
    @Override
    public String toString() {
        return "PREFERENCE: " + this.key + " = " + ((null == this.value) ? "" : this.value);
    }
    
    static {
        DEFAULT_VALUE = null;
    }
}
