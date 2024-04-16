package com.example.webduck.global.util;

import java.text.DecimalFormat;

public class RuntimeUtil {

    final static DecimalFormat memoryFormat = new DecimalFormat("#,##0.00 Mb");

    final static double MB_Numeral = 1024d * 1024d;

    public RuntimeUtil() {

    }

    public static double getTotalMemoryInMb() {
        return Runtime.getRuntime().totalMemory() / MB_Numeral;
    }

    public static double getFreeMemoryInMb() {
        return Runtime.getRuntime().freeMemory() / MB_Numeral;
    }

    public static double getUsedMemoryInMb() {
        return getTotalMemoryInMb() - getFreeMemoryInMb();
    }

    public static String getTotalMemoryStringInMb() {
        return memoryFormat.format(getTotalMemoryInMb());
    }

    public static String getFreeMemoryStringInMb() {
        return memoryFormat.format(getFreeMemoryInMb());
    }

    public static String getUsedMemoryStringInMb() {
        return memoryFormat.format(getUsedMemoryInMb());
    }

    public static String getAvailableProcessors() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

}
