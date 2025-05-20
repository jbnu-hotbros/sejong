package com.hotbros.sejong.dto;

import java.util.HashMap;
import java.util.Map;

public class CharPrAttributes {
    private String id;          // CharPr ID (필수)
    private String textColor;   // 글자 색 (예: "#RRGGBB", "transparent")
    private Boolean bold;       // 굵게
    private Boolean italic;     // 기울임
    private Boolean underline;  // 밑줄
    private Boolean strikeout;  // 취소선
    private Double fontSizeHwpUnit; // HWP Unit으로 관리 (예: 1000.0은 10pt)

    // 기본 생성자
    public CharPrAttributes() {
    }

    // 모든 필드를 받는 생성자 (fontSize는 HWP Unit으로 받음)
    public CharPrAttributes(String id, String textColor, Boolean bold, Boolean italic, Boolean underline, Boolean strikeout, Double fontSizeHwpUnit) {
        this.id = id;
        this.textColor = textColor;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.strikeout = strikeout;
        this.fontSizeHwpUnit = fontSizeHwpUnit;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public Boolean getUnderline() {
        return underline;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    public Boolean getStrikeout() {
        return strikeout;
    }

    public void setStrikeout(Boolean strikeout) {
        this.strikeout = strikeout;
    }

    // fontSizeHwpUnit에 대한 직접 getter/setter (내부용 또는 HWP Unit을 직접 다룰 때 사용)
    public Double getFontSizeHwpUnit() {
        return fontSizeHwpUnit;
    }

    public void setFontSizeHwpUnit(Double fontSizeHwpUnit) {
        this.fontSizeHwpUnit = fontSizeHwpUnit;
    }

    // pt 단위로 변환하는 getter/setter
    public Double getFontSizePt() {
        return fontSizeHwpUnit == null ? null : fontSizeHwpUnit / 100.0;
    }

    public void setFontSizePt(Double fontSizePt) {
        this.fontSizeHwpUnit = fontSizePt == null ? null : fontSizePt * 100.0;
    }

    // toMap, fromMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (id != null) map.put("id", id);
        if (textColor != null) map.put("textColor", textColor);
        if (bold != null) map.put("bold", bold);
        if (italic != null) map.put("italic", italic);
        if (underline != null) map.put("underline", underline);
        if (strikeout != null) map.put("strikeout", strikeout);
        // HWP Unit을 pt 단위로 변환하여 "fontSizePt" 키로 저장
        if (fontSizeHwpUnit != null) map.put("fontSizePt", fontSizeHwpUnit / 100.0);
        return map;
    }

    public static CharPrAttributes fromMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        CharPrAttributes attr = new CharPrAttributes();
        attr.setId((String) map.get("id"));
        attr.setTextColor((String) map.get("textColor"));
        attr.setBold((Boolean) map.get("bold"));
        attr.setItalic((Boolean) map.get("italic"));
        attr.setUnderline((Boolean) map.get("underline"));
        attr.setStrikeout((Boolean) map.get("strikeout"));
        
        // "fontSizePt" 키로 pt 단위 값을 가져와 HWP Unit으로 변환하여 저장
        Object fsObj = map.get("fontSizePt");
        if (fsObj instanceof Number) {
            attr.setFontSizeHwpUnit(((Number) fsObj).doubleValue() * 100.0); 
        } else if (fsObj instanceof String) {
            try {
                attr.setFontSizeHwpUnit(Double.parseDouble((String) fsObj) * 100.0);
            } catch (NumberFormatException e) {
                // Log error or handle as needed
                System.err.println("CharPrAttributes.fromMap: 잘못된 fontSizePt 값입니다 - " + fsObj);
            }
        }
        return attr;
    }
} 