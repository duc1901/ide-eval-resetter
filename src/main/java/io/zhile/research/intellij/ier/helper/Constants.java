// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.application.ApplicationNamesInfo;

public class Constants
{
    public static final String ACTION_NAME = "Eval Reset";
    public static final String PLUGIN_ID_STR = "io.zhile.research.ide-eval-resetter";
    public static final String IDE_NAME;
    public static final String IDE_NAME_LOWER;
    public static final String IDE_HASH;
    public static final String PLUGIN_PREFS_PREFIX = "Ide-Eval-Reset";
    public static final String RESET_ACTION_ID = "io.zhile.research.intellij.ier.action.ResetAction";
    public static final String RESTART_ACTION_ID = "io.zhile.research.intellij.ier.action.RestartAction";
    
    static {
        IDE_NAME = ApplicationNamesInfo.getInstance().getProductName();
        IDE_NAME_LOWER = Constants.IDE_NAME.toLowerCase();
        IDE_HASH = Integer.toHexString(FileUtil.pathHashCode(PathManager.getHomePath()));
    }
}
