// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import java.nio.file.Path;
import java.io.IOException;
import com.intellij.openapi.util.io.FileUtil;
import java.io.File;
import java.nio.file.Paths;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;

public class BrokenPlugins
{
    private static final Logger LOG;
    
    public static void fix() {
        final String content = "[]";
        final String fileName = "brokenPlugins.json";
        final Path brokenPluginsPath = Paths.get(PathManager.getPluginsPath(), fileName);
        final File brokenPluginsFile = brokenPluginsPath.toFile();
        if (!brokenPluginsFile.exists() || content.length() == brokenPluginsFile.length()) {
            return;
        }
        File tmp = null;
        try {
            tmp = File.createTempFile(fileName, null);
            FileUtil.writeToFile(tmp, content);
            FileUtil.copy(tmp, brokenPluginsFile);
        }
        catch (IOException e) {
            BrokenPlugins.LOG.warn("Set broken plugins failed", (Throwable)e);
        }
        finally {
            if (null != tmp) {
                FileUtil.delete(tmp);
            }
        }
    }
    
    static {
        LOG = Logger.getInstance((Class)BrokenPlugins.class);
    }
}
