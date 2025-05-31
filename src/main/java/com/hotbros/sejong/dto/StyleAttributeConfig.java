package com.hotbros.sejong.dto;

import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;

public class StyleAttributeConfig {
    // --- 글자 속성 ---
    private String fontName;      // 폰트명(fontidref지만 편의적으로)
    private Integer fontSize;     // 크기 (단위: HWPUNIT, 예: 1000 = 10pt)
    private Boolean bold;         // 굵게 여부
    private String textColor;     // 글자 색상
    private Short spacing; // 자간 (spacing)
    private Short ratio; // 장평 (proportion)

    // --- 문단 속성 ---
    private HorizontalAlign2 align; // 문단 정렬
    private Integer lineSpacing;    // 줄간격 (HWPUNIT)
    private Integer marginLeft;     // 왼쪽 여백 (HWPUNIT)
    private ParaHeadingType headingType; // 개요 타입
    private String bulletName;     // 개요 이름(bulletidref지만 편의적으로)

    // --- 스타일 이름 ---
    private String styleName;       // 스타일 이름 (한글)
    private String styleEngName;    // 스타일 이름 (영문)

    // --- Getter/Setter ---

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Short getSpacing() {
        return spacing;
    }

    public void setSpacing(Short spacing) {
        this.spacing = spacing;
    }

    public Short getRatio() {
        return ratio;
    }

    public void setRatio(Short ratio) {
        this.ratio = ratio;
    }

    public HorizontalAlign2 getAlign() {
        return align;
    }

    public void setAlign(HorizontalAlign2 align) {
        this.align = align;
    }

    public Integer getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(Integer lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public Integer getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(Integer marginLeft) {
        this.marginLeft = marginLeft;
    }

    public ParaHeadingType getHeadingType() {
        return headingType;
    }

    public void setHeadingType(ParaHeadingType headingType) {
        this.headingType = headingType;
    }

    public String getBulletName() {
        return bulletName;
    }

    public void setBulletName(String bulletName) {
        this.bulletName = bulletName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleEngName() {
        return styleEngName;
    }

    public void setStyleEngName(String styleEngName) {
        this.styleEngName = styleEngName;
    }
}
