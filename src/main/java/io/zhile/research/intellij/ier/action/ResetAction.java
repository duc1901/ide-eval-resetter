// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.action;

import io.zhile.research.intellij.ier.helper.CustomRepository;
import io.zhile.research.intellij.ier.listener.AppActivationListener;
import io.zhile.research.intellij.ier.listener.AppEventListener;
import io.zhile.research.intellij.ier.listener.BrokenPluginsListener;
import io.zhile.research.intellij.ier.helper.BrokenPlugins;
import io.zhile.research.intellij.ier.helper.CustomProperties;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import com.intellij.openapi.extensions.PluginDescriptor;
import io.zhile.research.intellij.ier.helper.PluginHelper;
import io.zhile.research.intellij.ier.tw.MainToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowEP;
import com.intellij.openapi.wm.ToolWindowManager;
import io.zhile.research.intellij.ier.ui.dialog.MainDialog;
import io.zhile.research.intellij.ier.helper.NotificationHelper;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.actionSystem.AnAction;

public class ResetAction extends AnAction implements DumbAware
{
    public ResetAction() {
        super("Eval Reset", "Reset my IDE eval information", AllIcons.General.Reset);
        final AnAction optionsGroup = ActionManager.getInstance().getAction("WelcomeScreen.Options");
        if (optionsGroup instanceof DefaultActionGroup) {
            ((DefaultActionGroup)optionsGroup).add((AnAction)this);
        }
    }
    
    public void actionPerformed(@NotNull final AnActionEvent e) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        final Project project = e.getProject();
        NotificationHelper.checkAndExpire(e);
        if (project == null) {
            final MainDialog mainDialog = new MainDialog("Eval Reset");
            mainDialog.show();
            return;
        }
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Eval Reset");
        if (null == toolWindow) {
            final ToolWindowEP ep = new ToolWindowEP();
            ep.id = "Eval Reset";
            ep.anchor = ToolWindowAnchor.BOTTOM.toString();
            ep.icon = "AllIcons.General.Reset";
            ep.factoryClass = MainToolWindowFactory.class.getName();
            ep.setPluginDescriptor((PluginDescriptor)PluginHelper.getPluginDescriptor());
            ToolWindowManagerEx.getInstanceEx(project).initToolWindow(ep);
            toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Eval Reset");
        }
        toolWindow.show((Runnable)null);
    }
    
    static {
        CustomProperties.fix();
        BrokenPlugins.fix();
        BrokenPluginsListener.getInstance().listen();
        AppEventListener.getInstance().listen();
        AppActivationListener.getInstance().listen();
        CustomRepository.checkAndAdd("https://plugins.zhile.io");
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "e", "io/zhile/research/intellij/ier/action/ResetAction", "actionPerformed"));
    }
}
