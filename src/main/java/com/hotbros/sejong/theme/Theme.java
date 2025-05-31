package com.hotbros.sejong.theme;

import com.hotbros.sejong.dto.*;
import com.hotbros.sejong.global.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Theme: Key별 Config 세트를 정의하는 클래스
 * - Key: StyleName, BorderFillName, FontName, BulletName
 * - Value: 각 Config 객체 (StyleAttributeConfig, BorderFillAttributeConfig 등)
 */
public class Theme {

    // 스타일 설정: StyleName → StyleAttributeConfig
    private Map<StyleName, StyleAttributeConfig> styleConfigMap = new HashMap<>();

    // 보더 설정: BorderFillName → BorderFillAttributeConfig
    private Map<BorderFillName, BorderFillAttributeConfig> borderFillConfigMap = new HashMap<>();


    // 불릿 설정: BulletName → BulletAttributeConfig
    private Map<BulletName, BulletAttributeConfig> bulletConfigMap = new HashMap<>();

    // ================================
    // Getter & Setter
    // ================================

    public Map<StyleName, StyleAttributeConfig> getStyleConfigMap() {
        return styleConfigMap;
    }

    public void setStyleConfigMap(Map<StyleName, StyleAttributeConfig> styleConfigMap) {
        this.styleConfigMap = styleConfigMap;
    }

    public Map<BorderFillName, BorderFillAttributeConfig> getBorderFillConfigMap() {
        return borderFillConfigMap;
    }

    public void setBorderFillConfigMap(Map<BorderFillName, BorderFillAttributeConfig> borderFillConfigMap) {
        this.borderFillConfigMap = borderFillConfigMap;
    }

    public Map<BulletName, BulletAttributeConfig> getBulletConfigMap() {
        return bulletConfigMap;
    }

    public void setBulletConfigMap(Map<BulletName, BulletAttributeConfig> bulletConfigMap) {
        this.bulletConfigMap = bulletConfigMap;
    }

}
