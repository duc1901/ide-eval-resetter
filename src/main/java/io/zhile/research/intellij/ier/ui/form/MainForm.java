// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.ui.form;

import javax.swing.JComponent;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.BorderFactory;
import com.intellij.uiDesigner.core.GridConstraints;
import javax.swing.JScrollPane;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.AbstractButton;
import java.awt.Font;
import io.zhile.research.intellij.ier.helper.AppHelper;
import com.intellij.openapi.ui.Messages;
import java.util.Iterator;
import java.util.List;
import io.zhile.research.intellij.ier.common.EvalRecord;
import io.zhile.research.intellij.ier.helper.ResetTimeHelper;
import java.awt.Dimension;
import com.intellij.icons.AllIcons;
import javax.swing.ListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import io.zhile.research.intellij.ier.common.Resetter;
import io.zhile.research.intellij.ier.helper.PluginHelper;
import java.awt.Component;
import com.intellij.openapi.util.Disposer;
import javax.swing.DefaultListModel;
import com.intellij.openapi.ui.DialogWrapper;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.intellij.openapi.Disposable;

public class MainForm implements Disposable
{
    private JPanel rootPanel;
    private JButton btnReset;
    private JList lstMain;
    private JLabel lblLastResetTime;
    private JButton btnReload;
    private JLabel lblFound;
    private JLabel lblLastResetTimeLabel;
    private JCheckBox chkResetAuto;
    private JLabel lblVersion;
    private final DialogWrapper dialogWrapper;
    private final DefaultListModel<String> listModel;
    
    public MainForm(final DialogWrapper dialogWrapper) {
        this.$$$setupUI$$$();
        this.listModel = new DefaultListModel<String>();
        this.dialogWrapper = dialogWrapper;
        if (dialogWrapper != null) {
            Disposer.register(dialogWrapper.getDisposable(), (Disposable)this);
        }
    }
    
    public JPanel getContent() {
        boldFont(this.lblFound);
        boldFont(this.lblLastResetTimeLabel);
        this.reloadLastResetTime();
        this.lblVersion.setText("v" + PluginHelper.getPluginVersion());
        this.chkResetAuto.setSelected(Resetter.isAutoReset());
        this.chkResetAuto.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Resetter.setAutoReset(MainForm.this.chkResetAuto.isSelected());
            }
        });
        this.lstMain.setModel(this.listModel);
        this.reloadRecordItems();
        this.btnReload.setIcon(AllIcons.Actions.Refresh);
        this.btnReload.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                MainForm.this.reloadLastResetTime();
                MainForm.this.reloadRecordItems();
            }
        });
        this.btnReset.setIcon(AllIcons.General.Reset);
        this.btnReset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                MainForm.this.resetEvalItems();
            }
        });
        if (null != this.dialogWrapper) {
            this.dialogWrapper.getRootPane().setDefaultButton(this.btnReset);
        }
        this.rootPanel.setMinimumSize(new Dimension(600, 240));
        return this.rootPanel;
    }
    
    private void reloadLastResetTime() {
        this.lblLastResetTime.setText(ResetTimeHelper.getLastResetTimeStr());
    }
    
    private void reloadRecordItems() {
        this.listModel.clear();
        final List<EvalRecord> recordItemList = Resetter.getEvalRecords();
        for (final EvalRecord record : recordItemList) {
            this.listModel.addElement(record.toString());
        }
    }
    
    private void resetEvalItems() {
        if (0 != Messages.showYesNoDialog("Your IDE will restart after reset!\nAre your sure to reset?", PluginHelper.getPluginName(), AllIcons.General.Reset)) {
            return;
        }
        Resetter.reset(Resetter.getEvalRecords());
        ResetTimeHelper.resetLastResetTime();
        this.listModel.clear();
        if (null != this.dialogWrapper) {
            this.dialogWrapper.close(0);
        }
        AppHelper.restart();
    }
    
    private static void boldFont(final Component component) {
        final Font font = component.getFont();
        component.setFont(font.deriveFont(font.getStyle() | 0x1));
    }
    
    public void dispose() {
        for (final AbstractButton button : new AbstractButton[] { this.chkResetAuto, this.btnReload, this.btnReset }) {
            for (final ActionListener listener : button.getActionListeners()) {
                button.removeActionListener(listener);
            }
        }
        this.rootPanel.removeAll();
    }
    
    private /* synthetic */ void $$$setupUI$$$() {
        final JPanel rootPanel = new JPanel();
        (this.rootPanel = rootPanel).setLayout(new BorderLayout(0, 0));
        final JPanel comp = new JPanel();
        comp.setLayout(new FlowLayout(0, 5, 5));
        rootPanel.add(comp, "North");
        final JLabel label = new JLabel();
        (this.lblLastResetTimeLabel = label).setText("Last Reset Time\uff1a");
        comp.add(label);
        final JLabel label2 = new JLabel();
        (this.lblLastResetTime = label2).setText("");
        comp.add(label2);
        final JPanel comp2 = new JPanel();
        comp2.setLayout(new BorderLayout(0, 0));
        rootPanel.add(comp2, "Center");
        final JPanel comp3 = new JPanel();
        comp3.setLayout(new FlowLayout(0, 5, 5));
        comp2.add(comp3, "North");
        final JLabel label3 = new JLabel();
        (this.lblFound = label3).setText("Found\uff1a");
        comp3.add(label3);
        final JPanel comp4 = new JPanel();
        comp4.setLayout((LayoutManager)new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1, false, false));
        comp2.add(comp4, "Center");
        final JScrollPane comp5 = new JScrollPane();
        comp4.add(comp5, new GridConstraints(0, 0, 1, 1, 0, 3, 7, 7, (Dimension)null, (Dimension)null, (Dimension)null));
        final JList list = new JList();
        (this.lstMain = list).setSelectionMode(0);
        comp5.setViewportView(list);
        final JPanel comp6 = new JPanel();
        comp6.setLayout(new BorderLayout(0, 0));
        rootPanel.add(comp6, "South");
        final JPanel comp7 = new JPanel();
        comp7.setLayout(new FlowLayout(2, 5, 5));
        comp6.add(comp7, "Center");
        final JCheckBox checkBox = new JCheckBox();
        (this.chkResetAuto = checkBox).setText("Auto reset before per restart");
        comp7.add(checkBox);
        final JButton button = new JButton();
        (this.btnReload = button).setText("Reload");
        comp7.add(button);
        final JButton button2 = new JButton();
        (this.btnReset = button2).setText("Reset");
        comp7.add(button2);
        final JPanel comp8 = new JPanel();
        comp8.setLayout(new BorderLayout(0, 0));
        comp6.add(comp8, "West");
        comp8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5), null, 0, 0, null, null));
        final JLabel label4 = new JLabel();
        (this.lblVersion = label4).setEnabled(false);
        label4.setText("v1.0.0");
        comp8.add(label4, "Center");
    }
}
