// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.common;

import com.intellij.openapi.application.PathManager;
import java.nio.file.Paths;
import java.util.prefs.BackingStoreException;
import com.intellij.ide.Prefs;
import java.lang.reflect.Method;
import org.jdom.Attribute;
import java.util.Iterator;
import java.io.File;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.ide.plugins.PluginManager;
import io.zhile.research.intellij.ier.helper.ReflectionHelper;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import io.zhile.research.intellij.ier.helper.Constants;
import java.util.prefs.Preferences;
import org.jdom.Element;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.PropertiesComponentImpl;
import com.intellij.openapi.project.Project;
import io.zhile.research.intellij.ier.helper.NotificationHelper;
import java.util.ArrayList;
import java.util.List;

public class Resetter
{
    private static final String DEFAULT_VENDOR = "jetbrains";
    private static final String OLD_MACHINE_ID_KEY = "JetBrains.UserIdOnMachine";
    private static final String NEW_MACHINE_ID_KEY = "jetbrains.user_id_on_machine";
    private static final String DEVICE_ID_KEY = "jetbrains.device_id";
    private static final String EVAL_KEY = "evlsprt";
    private static final String AUTO_RESET_KEY;
    
    public static List<EvalRecord> getEvalRecords() {
        final List<EvalRecord> list = new ArrayList<EvalRecord>();
        final File evalDir = getEvalDir();
        if (evalDir.exists()) {
            final File[] files = evalDir.listFiles();
            if (files == null) {
                NotificationHelper.showError(null, "List eval license file failed!");
            }
            else {
                for (final File file : files) {
                    if (file.getName().endsWith(".key")) {
                        list.add(new LicenseFileRecord(file));
                    }
                }
            }
        }
        final File licenseDir = getLicenseDir();
        if (licenseDir.exists()) {
            final File[] files2 = licenseDir.listFiles();
            if (files2 == null) {
                NotificationHelper.showError(null, "List license file failed!");
            }
            else {
                for (final File file2 : files2) {
                    if (file2.getName().endsWith(".key") || file2.getName().endsWith(".license")) {
                        if (file2.length() <= 1024L) {
                            list.add(new NormalFileRecord(file2));
                        }
                    }
                }
            }
        }
        final Element state = ((PropertiesComponentImpl)PropertiesComponent.getInstance()).getState();
        if (state != null) {
            for (final Element element : state.getChildren()) {
                if (!element.getName().equals("property")) {
                    continue;
                }
                final Attribute attrName = element.getAttribute("name");
                final Attribute attrValue = element.getAttribute("value");
                if (attrName == null) {
                    continue;
                }
                if (attrValue == null) {
                    continue;
                }
                if (!attrName.getValue().startsWith("evlsprt")) {
                    continue;
                }
                list.add(new PropertyRecord(attrName.getValue()));
            }
        }
        final PreferenceRecord[] array3;
        final PreferenceRecord[] prefsValue = array3 = new PreferenceRecord[] { new PreferenceRecord("JetBrains.UserIdOnMachine", true), new PreferenceRecord("jetbrains.user_id_on_machine"), new PreferenceRecord("jetbrains.device_id") };
        for (final PreferenceRecord record : array3) {
            if (record.getValue() != null) {
                list.add(record);
            }
        }
        try {
            final List<String> prefsList = new ArrayList<String>();
            for (final String name : Preferences.userRoot().node("jetbrains").childrenNames()) {
                if (name.toLowerCase().startsWith(Constants.IDE_NAME_LOWER)) {
                    getAllPrefsKeys(Preferences.userRoot().node("jetbrains/" + name + "/" + Constants.IDE_HASH), prefsList);
                }
            }
            final Method methodGetProductCode = ReflectionHelper.getMethod(IdeaPluginDescriptor.class, "getProductCode", (Class<?>[])new Class[0]);
            if (null != methodGetProductCode) {
                for (final IdeaPluginDescriptor descriptor : PluginManager.getPlugins()) {
                    final String productCode = (String)methodGetProductCode.invoke(descriptor, new Object[0]);
                    if (null != productCode) {
                        if (!productCode.isEmpty()) {
                            getAllPrefsKeys(Preferences.userRoot().node("jetbrains/" + productCode.toLowerCase()), prefsList);
                        }
                    }
                }
            }
            for (String key : prefsList) {
                if (!key.contains("evlsprt")) {
                    continue;
                }
                if (key.startsWith("/")) {
                    key = key.substring(1).replace('/', '.');
                }
                list.add(new PreferenceRecord(key));
            }
        }
        catch (Exception e) {
            NotificationHelper.showError(null, "List eval preferences failed!");
        }
        if (SystemInfo.isWindows) {
            for (final String name2 : new String[] { "PermanentUserId", "PermanentDeviceId" }) {
                final File file3 = getSharedFile(name2);
                if (null != file3 && file3.exists()) {
                    list.add(new NormalFileRecord(file3));
                }
            }
        }
        return list;
    }
    
    public static void reset(final List<EvalRecord> records) {
        for (final EvalRecord record : records) {
            reset(record);
        }
    }
    
    public static void reset(final EvalRecord record) {
        try {
            record.reset();
        }
        catch (Exception e) {
            NotificationHelper.showError(null, e.getMessage());
        }
    }
    
    public static boolean isAutoReset() {
        return Prefs.getBoolean(Resetter.AUTO_RESET_KEY, false);
    }
    
    public static void setAutoReset(final boolean isAutoReset) {
        Prefs.putBoolean(Resetter.AUTO_RESET_KEY, isAutoReset);
        syncPrefs();
    }
    
    public static void syncPrefs() {
        try {
            Preferences.userRoot().sync();
        }
        catch (BackingStoreException e) {
            NotificationHelper.showError(null, "Flush preferences failed!");
        }
    }
    
    protected static File getSharedFile(final String fileName) {
        final String appData = System.getenv("APPDATA");
        if (appData == null) {
            return null;
        }
        return Paths.get(appData, "JetBrains", fileName).toFile();
    }
    
    protected static File getEvalDir() {
        final String configPath = PathManager.getConfigPath();
        return new File(configPath, "eval");
    }
    
    protected static File getLicenseDir() {
        return new File(PathManager.getConfigPath());
    }
    
    protected static void getAllPrefsKeys(final Preferences prefs, final List<String> list) throws BackingStoreException {
        final String[] childrenNames = prefs.childrenNames();
        if (childrenNames.length == 0) {
            for (final String key : prefs.keys()) {
                list.add(prefs.absolutePath() + "/" + key);
            }
            return;
        }
        for (final String childName : childrenNames) {
            getAllPrefsKeys(prefs.node(childName), list);
        }
    }
    
    static {
        AUTO_RESET_KEY = "Ide-Eval-Reset.auto_reset." + Constants.IDE_NAME_LOWER + "." + Constants.IDE_HASH;
    }
}
