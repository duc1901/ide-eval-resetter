// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationGroup;
import com.intellij.icons.AllIcons;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.notification.NotificationType;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.project.Project;
import com.intellij.notification.Notification;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class NotificationHelper
{
    public static void checkAndExpire(final AnActionEvent e) {
        final DataKey<Notification> notificationKey = (DataKey<Notification>)DataKey.create("Notification");
        final Notification notification = (Notification)notificationKey.getData(e.getDataContext());
        if (null != notification) {
            notification.expire();
        }
    }
    
    public static Notification show(@Nullable final Project project, final String title, final String subtitle, final String content, final NotificationType type) {
        return show(project, title, subtitle, content, type, new AnAction[0]);
    }
    
    public static Notification show(@Nullable final Project project, final String title, final String subtitle, final String content, final NotificationType type, final AnAction action) {
        return show(project, title, subtitle, content, type, new AnAction[] { action });
    }
    
    public static Notification show(@Nullable final Project project, String title, final String subtitle, final String content, final NotificationType type, final AnAction[] actions) {
        if (title == null) {
            title = PluginHelper.getPluginName();
        }
        final NotificationGroup group = new NotificationGroup("io.zhile.research.ide-eval-resetter", NotificationDisplayType.BALLOON, true, (String)null, AllIcons.General.Reset);
        final Notification notification = group.createNotification(title, subtitle, content, type, NotificationListener.URL_OPENING_LISTENER);
        for (final AnAction action : actions) {
            notification.addAction(action);
        }
        notification.notify(project);
        return notification;
    }
    
    public static Notification showError(@Nullable final Project project, final String title, final String subtitle, final String content) {
        return show(project, title, subtitle, content, NotificationType.ERROR);
    }
    
    public static Notification showError(@Nullable final Project project, final String title, final String content) {
        return showError(project, title, null, content);
    }
    
    public static Notification showError(@Nullable final Project project, final String content) {
        return showError(project, null, null, content);
    }
    
    public static Notification showWarn(@Nullable final Project project, final String title, final String subtitle, final String content) {
        return show(project, title, subtitle, content, NotificationType.WARNING);
    }
    
    public static Notification showWarn(@Nullable final Project project, final String title, final String content) {
        return showWarn(project, title, null, content);
    }
    
    public static Notification showWarn(@Nullable final Project project, final String content) {
        return showWarn(project, null, null, content);
    }
    
    public static Notification showInfo(@Nullable final Project project, final String title, final String subtitle, final String content) {
        return show(project, title, subtitle, content, NotificationType.INFORMATION);
    }
    
    public static Notification showInfo(@Nullable final Project project, final String title, final String content) {
        return showInfo(project, title, null, content);
    }
    
    public static Notification showInfo(@Nullable final Project project, final String content) {
        return showInfo(project, null, null, content);
    }
}
