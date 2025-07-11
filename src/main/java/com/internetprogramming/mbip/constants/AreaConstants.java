package com.internetprogramming.mbip.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Constants for area names used in the application
 */
public class AreaConstants {
    
    // Area names
    public static final String SKUDAI = "SKUDAI";
    public static final String LIMA_KEDAI = "LIMA_KEDAI";
    public static final String GELANG_PATAH = "GELANG_PATAH";
    public static final String KANGKAR_PULAI = "KANGKAR_PULAI";
    public static final String ISKANDAR_PUTERI = "ISKANDAR_PUTERI";
    public static final String ULU_CHOH = "ULU_CHOH";
    
    // All areas list
    public static final List<String> ALL_AREAS = Arrays.asList(
        SKUDAI,
        LIMA_KEDAI,
        GELANG_PATAH,
        KANGKAR_PULAI,
        ISKANDAR_PUTERI,
        ULU_CHOH
    );
    
    // Private constructor to prevent instantiation
    private AreaConstants() {}
} 