// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.tw;

import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.content.Content;
import com.intellij.openapi.Disposable;
import javax.swing.JComponent;
import com.intellij.ui.content.ContentFactory;
import com.intellij.openapi.ui.DialogWrapper;
import io.zhile.research.intellij.ier.ui.form.MainForm;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.wm.ToolWindowFactory;

public class MainToolWindowFactory implements ToolWindowFactory, DumbAware
{
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (toolWindow == null) {
            $$$reportNull$$$0(1);
        }
        final MainForm mainForm = new MainForm(null);
        final Content content = ContentFactory.SERVICE.getInstance().createContent((JComponent)mainForm.getContent(), "", true);
        content.setDisposer((Disposable)mainForm);
        toolWindow.getContentManager().addContent(content);
    }
    
    public static void unregisterAll() {
        for (final Project project : ProjectManager.getInstance().getOpenProjects()) {
            ToolWindowManager.getInstance(project).unregisterToolWindow("Eval Reset");
        }
    }
    
    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        final String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        final Object[] args = new Object[3];
        switch (n) {
            default: {
                args[0] = "project";
                break;
            }
            case 1: {
                args[0] = "toolWindow";
                break;
            }
        }
        args[1] = "io/zhile/research/intellij/ier/tw/MainToolWindowFactory";
        args[2] = "createToolWindowContent";
        throw new IllegalArgumentException(String.format(format, args));
    }
}
