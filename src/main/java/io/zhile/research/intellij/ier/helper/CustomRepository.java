// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.updateSettings.impl.UpdateSettings;
import org.jetbrains.annotations.NotNull;

public class CustomRepository
{
    public static final String DEFAULT_HOST = "https://plugins.zhile.io";
    
    public static void checkAndAdd(@NotNull final String host) {
        if (host == null) {
            $$$reportNull$$$0(0);
        }
        final List<String> hosts = (List<String>)UpdateSettings.getInstance().getStoredPluginHosts();
        for (final String s : hosts) {
            if (s.equalsIgnoreCase(host)) {
                return;
            }
        }
        hosts.add(host);
        final Method method = ReflectionHelper.getMethod(UpdateSettings.class, "setThirdPartyPluginsAllowed", Boolean.TYPE);
        if (method != null) {
            try {
                method.invoke(UpdateSettings.getInstance(), true);
            }
            catch (Exception e) {
                NotificationHelper.showError(null, "Enable third party plugins failed!");
            }
        }
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "host", "io/zhile/research/intellij/ier/helper/CustomRepository", "checkAndAdd"));
    }
}
