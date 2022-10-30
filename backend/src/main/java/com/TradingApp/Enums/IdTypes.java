package com.TradingApp.Enums;

public enum IdTypes {

    SECURITY_XYZ(7671296), SECURITY_ABC(2954262);

    private Integer idValue;

    public Integer getIdValue() {
        return this.idValue;
    }

    private IdTypes(Integer idValue) {
        this.idValue = idValue;
    }
}
