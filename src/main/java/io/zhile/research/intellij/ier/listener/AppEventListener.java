// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.listener;

import io.zhile.research.intellij.ier.helper.ResetTimeHelper;
import io.zhile.research.intellij.ier.common.Resetter;
import io.zhile.research.intellij.ier.helper.BrokenPlugins;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.openapi.Disposable;
import com.intellij.ide.AppLifecycleListener;

public class AppEventListener implements AppLifecycleListener, Disposable
{
    private static AppEventListener instance;
    private static MessageBusConnection connection;
    
    protected AppEventListener() {
    }
    
    public static AppEventListener getInstance() {
        return AppEventListener.instance;
    }
    
    public synchronized void listen() {
        if (AppEventListener.connection != null) {
            return;
        }
        (AppEventListener.connection = ApplicationManager.getApplication().getMessageBus().connect()).subscribe(AppLifecycleListener.TOPIC, (Object)this);
    }
    
    public synchronized void stop() {
        if (AppEventListener.connection == null) {
            return;
        }
        AppEventListener.connection.disconnect();
        AppEventListener.connection = null;
    }
    
    public void appFrameCreated(final String[] commandLineArgs, @NotNull final Ref<Boolean> willOpenProject) {
        if (willOpenProject == null) {
            $$$reportNull$$$0(0);
        }
    }
    
    public void appStarting(@Nullable final Project projectFromCommandLine) {
    }
    
    public void projectFrameClosed() {
    }
    
    public void projectOpenFailed() {
    }
    
    public void welcomeScreenDisplayed() {
    }
    
    public void appClosing() {
        BrokenPlugins.fix();
        if (!Resetter.isAutoReset()) {
            return;
        }
        Resetter.reset(Resetter.getEvalRecords());
        ResetTimeHelper.resetLastResetTime();
    }
    
    public void dispose() {
        this.stop();
        AppEventListener.instance = null;
    }
    
    static {
        AppEventListener.instance = new AppEventListener();
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "willOpenProject", "io/zhile/research/intellij/ier/listener/AppEventListener", "appFrameCreated"));
    }
}
