// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.ui.dialog;

import io.zhile.research.intellij.ier.ui.form.MainForm;
import javax.swing.JComponent;
import com.intellij.openapi.ui.DialogWrapper;

public class MainDialog extends DialogWrapper
{
    public MainDialog(final String title) {
        super(true);
        this.init();
        this.setTitle(title);
    }
    
    protected JComponent createCenterPanel() {
        final MainForm mainForm = new MainForm(this);
        return mainForm.getContent();
    }
    
    protected JComponent createSouthPanel() {
        return null;
    }
}
