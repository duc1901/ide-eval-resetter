// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import java.lang.reflect.Method;

public class ReflectionHelper
{
    public static Method getMethod(final Class<?> klass, final String methodName, final Class<?>... methodParameterTypes) {
        try {
            return klass.getMethod(methodName, methodParameterTypes);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }
}
