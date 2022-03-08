// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.action;

import io.zhile.research.intellij.ier.helper.AppHelper;
import io.zhile.research.intellij.ier.helper.NotificationHelper;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.actionSystem.AnAction;

public class RestartAction extends AnAction implements DumbAware
{
    public RestartAction() {
        super("Restart IDE", "Restart my IDE", AllIcons.Actions.Restart);
    }
    
    public void actionPerformed(@NotNull final AnActionEvent e) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        NotificationHelper.checkAndExpire(e);
        AppHelper.restart();
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "e", "io/zhile/research/intellij/ier/action/RestartAction", "actionPerformed"));
    }
}
