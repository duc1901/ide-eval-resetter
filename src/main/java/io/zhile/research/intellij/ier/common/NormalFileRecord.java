// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.common;

import com.intellij.openapi.util.io.FileUtil;
import java.io.File;

public class NormalFileRecord implements EvalRecord
{
    private final String type = "FILE";
    private final File file;
    
    public NormalFileRecord(final File file) {
        this.file = file;
    }
    
    @Override
    public void reset() throws Exception {
        if (!FileUtil.delete(this.file)) {
            throw new Exception("Remove FILE failed: " + this.file.getAbsolutePath());
        }
    }
    
    @Override
    public String toString() {
        return "FILE: " + this.file.getName();
    }
}
