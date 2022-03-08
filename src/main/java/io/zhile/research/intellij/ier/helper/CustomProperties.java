// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

public class CustomProperties
{
    public static void fix() {
        final String key = "idea.ignore.disabled.plugins";
        System.clearProperty(key);
    }
}
