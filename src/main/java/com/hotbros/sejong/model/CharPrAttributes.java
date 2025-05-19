package com.hotbros.sejong.model;

import java.util.HashMap;
import java.util.Map;

public class CharPrAttributes {
    private String id;          // CharPr ID (필수)
    private String textColor;   // 글자 색 (예: "#RRGGBB", "transparent")
    private Boolean bold;       // 굵게
    private Boolean italic;     // 기울임
    private Boolean underline;  // 밑줄
    private Boolean strikeout;  // 취소선
    private Double fontSize;    // 글자 크기 (HWP Unit, 예: 10.0은 10pt)

    // 기본 생성자
    public CharPrAttributes() {
    }

    // 모든 필드를 받는 생성자 (편의용)
    public CharPrAttributes(String id, String textColor, Boolean bold, Boolean italic, Boolean underline, Boolean strikeout, Double fontSize) {
        this.id = id;
        this.textColor = textColor;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.strikeout = strikeout;
        this.fontSize = fontSize;
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

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
        this.fontSize = fontSize;
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
        if (fontSize != null) map.put("fontSize", fontSize / 100.0); // HWP 단위를 pt로 변환해서 저장
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
        
        Object fsObj = map.get("fontSize");
        if (fsObj instanceof Number) {
            // pt 단위를 HWP 단위로 변환하여 DTO에 저장
            attr.setFontSize(((Number) fsObj).doubleValue() * 100.0); 
        } else if (fsObj instanceof String) {
            try {
                attr.setFontSize(Double.parseDouble((String) fsObj) * 100.0);
            } catch (NumberFormatException e) {
                // Log error or handle as needed
            }
        }
        return attr;
    }
} 