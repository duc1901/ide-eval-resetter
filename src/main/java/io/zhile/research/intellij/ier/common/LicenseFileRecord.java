// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.common;

import io.zhile.research.intellij.ier.helper.DateTime;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import com.intellij.openapi.util.io.FileUtil;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Date;
import java.io.File;

public class LicenseFileRecord implements EvalRecord
{
    private final String type = "LICENSE";
    private final File file;
    private final Date expireDate;
    
    public LicenseFileRecord(final File file) {
        this.file = file;
        try (final DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            this.expireDate = new Date(~dis.readLong() + 2592000000L);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void reset() throws Exception {
        if (!FileUtil.delete(this.file)) {
            throw new Exception("Remove LICENSE failed: " + this.file.getAbsolutePath());
        }
        try (final DataOutputStream dos = new DataOutputStream(new FileOutputStream(this.file))) {
            dos.writeLong(~System.currentTimeMillis());
        }
    }
    
    @Override
    public String toString() {
        return "LICENSE: " + this.file.getName() + ", UNTIL: " + DateTime.DF_DATETIME.format(this.expireDate);
    }
}
