package com.internetprogramming.mbip.Entity;

public enum HomeArea {
    SKUDAI,
    LIMA_KEDAI,
    GELANG_PATAH,
    KANGKAR_PULAI,
    ISKANDAR_PUTERI,
    ULU_CHOH;

    public String getDisplayName() {
        return this.name();
    }
}

