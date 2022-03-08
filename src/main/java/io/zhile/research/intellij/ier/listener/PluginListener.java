// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.listener;

import com.intellij.openapi.actionSystem.AnAction;
import io.zhile.research.intellij.ier.tw.MainToolWindowFactory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import io.zhile.research.intellij.ier.helper.NotificationHelper;
import com.intellij.openapi.actionSystem.ActionManager;
import io.zhile.research.intellij.ier.helper.PluginHelper;
import org.jetbrains.annotations.NotNull;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.DynamicPluginListener;

public class PluginListener implements DynamicPluginListener
{
    public void pluginLoaded(@NotNull final IdeaPluginDescriptor pluginDescriptor) {
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (!PluginHelper.myself(pluginDescriptor)) {
            return;
        }
        ActionManager.getInstance().getAction("io.zhile.research.intellij.ier.action.ResetAction");
        NotificationHelper.showInfo(null, "Plugin installed successfully! Now enjoy it~<br>For more information, visit <a href='https://zhile.io/2020/11/18/jetbrains-eval-reset.html'>here</a>.");
    }
    
    public void beforePluginUnload(@NotNull final IdeaPluginDescriptor pluginDescriptor, final boolean isUpdate) {
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (!PluginHelper.myself(pluginDescriptor)) {
            return;
        }
        final AnAction optionsGroup = ActionManager.getInstance().getAction("WelcomeScreen.Options");
        if (optionsGroup instanceof DefaultActionGroup) {
            ((DefaultActionGroup)optionsGroup).remove(ActionManager.getInstance().getAction("io.zhile.research.intellij.ier.action.ResetAction"));
        }
        Disposer.dispose((Disposable)BrokenPluginsListener.getInstance());
        Disposer.dispose((Disposable)AppActivationListener.getInstance());
        Disposer.dispose((Disposable)AppEventListener.getInstance());
        MainToolWindowFactory.unregisterAll();
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        final String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        final Object[] args = { "pluginDescriptor", "io/zhile/research/intellij/ier/listener/PluginListener", null };
        switch (n) {
            default: {
                args[2] = "pluginLoaded";
                break;
            }
            case 1: {
                args[2] = "beforePluginUnload";
                break;
            }
        }
        throw new IllegalArgumentException(String.format(format, args));
    }
}
