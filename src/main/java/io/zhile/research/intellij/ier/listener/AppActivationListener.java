// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.listener;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import io.zhile.research.intellij.ier.helper.NotificationHelper;
import io.zhile.research.intellij.ier.common.Resetter;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionManager;
import io.zhile.research.intellij.ier.helper.ResetTimeHelper;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationActivationListener;

public class AppActivationListener implements ApplicationActivationListener, Disposable
{
    private static AppActivationListener instance;
    private static MessageBusConnection connection;
    
    protected AppActivationListener() {
    }
    
    public static AppActivationListener getInstance() {
        return AppActivationListener.instance;
    }
    
    public synchronized void listen() {
        if (AppActivationListener.connection != null) {
            return;
        }
        (AppActivationListener.connection = ApplicationManager.getApplication().getMessageBus().connect()).subscribe(ApplicationActivationListener.TOPIC, (Object)this);
    }
    
    public synchronized void stop() {
        if (AppActivationListener.connection == null) {
            return;
        }
        AppActivationListener.connection.disconnect();
        AppActivationListener.connection = null;
    }
    
    public void applicationActivated(@NotNull final IdeFrame ideFrame) {
        if (ideFrame == null) {
            $$$reportNull$$$0(0);
        }
        if (!ResetTimeHelper.overResetPeriod()) {
            return;
        }
        this.stop();
        AnAction action = ActionManager.getInstance().getAction("io.zhile.research.intellij.ier.action.ResetAction");
        NotificationType type = NotificationType.INFORMATION;
        final String message = "It has been a long time since the last reset!\nWould you like to reset it again?";
        if (Resetter.isAutoReset()) {
            action = ActionManager.getInstance().getAction("io.zhile.research.intellij.ier.action.RestartAction");
            type = NotificationType.WARNING;
        }
        NotificationHelper.show(null, null, null, message, type, action);
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
        AppActivationListener.instance = null;
    }
    
    static {
        AppActivationListener.instance = new AppActivationListener();
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        final String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        final Object[] args = { "ideFrame", "io/zhile/research/intellij/ier/listener/AppActivationListener", null };
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
