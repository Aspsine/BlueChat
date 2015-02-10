package com.aspsine.bluechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aspsine on 2015/2/5.
 */
public class DateUtils {
    public static final String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        return simpleDateFormat.format(date);
    }

}
