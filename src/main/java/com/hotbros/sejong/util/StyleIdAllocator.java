package com.hotbros.sejong.util;

public class StyleIdAllocator {
    private int charPrId = 7;
    private int paraPrId = 16;
    private int styleId = 18;

    public int nextCharPrId() {
        return charPrId++;
    }

    public int nextParaPrId() {
        return paraPrId++;
    }

    public int nextStyleId() {
        return styleId++;
    }
}
