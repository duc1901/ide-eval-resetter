// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import com.intellij.openapi.application.ApplicationManager;
import io.zhile.research.intellij.ier.listener.AppEventListener;
import io.zhile.research.intellij.ier.listener.AppActivationListener;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import io.zhile.research.intellij.ier.listener.BrokenPluginsListener;

public class AppHelper
{
    public static void restart() {
        Disposer.dispose((Disposable)BrokenPluginsListener.getInstance());
        Disposer.dispose((Disposable)AppActivationListener.getInstance());
        Disposer.dispose((Disposable)AppEventListener.getInstance());
        ApplicationManager.getApplication().invokeLater((Runnable)new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().restart();
            }
        });
    }
}
