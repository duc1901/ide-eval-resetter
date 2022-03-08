// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import io.zhile.research.intellij.ier.common.Resetter;
import com.intellij.ide.Prefs;

public class ResetTimeHelper
{
    public static final long RESET_PERIOD = 2160000000L;
    private static final String RESET_KEY;
    
    public static long getLastResetTime() {
        return Prefs.getLong(ResetTimeHelper.RESET_KEY, 0L);
    }
    
    public static void resetLastResetTime() {
        Prefs.putLong(ResetTimeHelper.RESET_KEY, System.currentTimeMillis());
        Resetter.syncPrefs();
    }
    
    public static boolean overResetPeriod() {
        return System.currentTimeMillis() - getLastResetTime() > 2160000000L;
    }
    
    public static String getLastResetTimeStr() {
        final long lastResetTime = getLastResetTime();
        return (lastResetTime > 0L) ? DateTime.getStringFromTimestamp(lastResetTime) : "Not yet";
    }
    
    static {
        RESET_KEY = "Ide-Eval-Reset." + Constants.IDE_NAME_LOWER + "." + Constants.IDE_HASH;
    }
}
