package com.hotbros.sejong.util;

public class IdManager {
    private int nextCharPrId = 1;
    private int nextParaPrId = 1;
    private int nextBulletId = 1;
    private int nextNumberingId = 1;
    
    public synchronized String generateCharPrId() {
        return String.valueOf(nextCharPrId++);
    }
    
    public synchronized String generateParaPrId() {
        return String.valueOf(nextParaPrId++);
    }
    
    public synchronized String generateBulletId() {
        return String.valueOf(nextBulletId++);
    }
    
    public synchronized String generateNumberingId() {
        return String.valueOf(nextNumberingId++);
    }
    
    public void updateMaxCharPrId(int maxId) {
        nextCharPrId = Math.max(nextCharPrId, maxId + 1);
    }
    
    public void updateMaxParaPrId(int maxId) {
        nextParaPrId = Math.max(nextParaPrId, maxId + 1);
    }
    
    public void updateMaxBulletId(int maxId) {
        nextBulletId = Math.max(nextBulletId, maxId + 1);
    }
    
    public void updateMaxNumberingId(int maxId) {
        nextNumberingId = Math.max(nextNumberingId, maxId + 1);
    }
} 