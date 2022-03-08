// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.listener;

import io.zhile.research.intellij.ier.helper.BrokenPlugins;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationActivationListener;

public class BrokenPluginsListener implements ApplicationActivationListener, Disposable
{
    private static BrokenPluginsListener instance;
    private static MessageBusConnection connection;
    
    protected BrokenPluginsListener() {
    }
    
    public static BrokenPluginsListener getInstance() {
        return BrokenPluginsListener.instance;
    }
    
    public synchronized void listen() {
        if (BrokenPluginsListener.connection != null) {
            return;
        }
        (BrokenPluginsListener.connection = ApplicationManager.getApplication().getMessageBus().connect()).subscribe(ApplicationActivationListener.TOPIC, (Object)this);
    }
    
    public synchronized void stop() {
        if (BrokenPluginsListener.connection == null) {
            return;
        }
        BrokenPluginsListener.connection.disconnect();
        BrokenPluginsListener.connection = null;
    }
    
    public void applicationActivated(@NotNull final IdeFrame ideFrame) {
        if (ideFrame == null) {
            $$$reportNull$$$0(0);
        }
        BrokenPlugins.fix();
    }
    
    public void applicationDeactivated(@NotNull final IdeFrame ideFrame) {
        if (ideFrame == null) {
            $$$reportNull$$$0(1);
        }
        this.applicationActivated(ideFrame);
    }
    
    public void delayedApplicationDeactivated(@NotNull final IdeFrame ideFrame) {
        if (ideFrame == null) {
            $$$reportNull$$$0(2);
        }
    }
    
    public void dispose() {
        this.stop();
        BrokenPluginsListener.instance = null;
    }
    
    static {
        BrokenPluginsListener.instance = new BrokenPluginsListener();
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        final String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        final Object[] args = { "ideFrame", "io/zhile/research/intellij/ier/listener/BrokenPluginsListener", null };
        switch (n) {
            default: {
                args[2] = "applicationActivated";
                break;
            }
            case 1: {
                args[2] = "applicationDeactivated";
                break;
            }
            case 2: {
                args[2] = "delayedApplicationDeactivated";
                break;
            }
        }
        throw new IllegalArgumentException(String.format(format, args));
    }
}
