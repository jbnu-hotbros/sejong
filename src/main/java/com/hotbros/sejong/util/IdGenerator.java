package com.hotbros.sejong.util;

public class IdGenerator {
    private int charPrId = 7;
    private int paraPrId = 16;
    private int styleId = 18;
    private int fontId = 2;
    private int borderFillId = 3;
    
    public int nextCharPrId() {
        return charPrId++;
    }

    public int nextParaPrId() {
        return paraPrId++;
    }

    public int nextStyleId() {
        return styleId++;
    }

    public int nextFontId() {
        return fontId++;
    }

    public int nextBorderFillId() {
        return borderFillId++;
    }
}
