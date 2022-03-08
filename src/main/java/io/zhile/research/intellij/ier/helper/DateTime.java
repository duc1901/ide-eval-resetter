// 
// Decompiled by Procyon v0.5.36
// 

package io.zhile.research.intellij.ier.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class DateTime
{
    public static final DateFormat DF_DATETIME;
    
    public static String getStringFromTimestamp(final long timestamp) {
        final Date date = new Date(timestamp);
        return DateTime.DF_DATETIME.format(date);
    }
    
    static {
        DF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
