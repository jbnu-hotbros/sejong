package com.hotbros.sejong.style.request;

import java.util.HashMap;
import java.util.Map;

public class CharPrAttributes {
    private String id;          // CharPr ID (필수)
    private String textColor;   // 글자 색 (예: "#RRGGBB", "transparent")
    private Boolean bold;       // 굵게
    private Boolean italic;     // 기울임
    private Boolean underline;  // 밑줄
    private Boolean strikeout;  // 취소선
    private Integer fontSizeHwpUnit; // HWP Unit으로 관리 (예: 1000은 10pt)
    private String fontRefId;   // 폰트 참조 ID

    // 기본 생성자
    public CharPrAttributes() {
    }

    // 모든 필드를 받는 생성자 (fontSize는 HWP Unit으로 받음)
    public CharPrAttributes(String id, String textColor, Boolean bold, Boolean italic, Boolean underline, Boolean strikeout, Integer fontSizeHwpUnit) {
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
    public Integer getFontSizeHwpUnit() {
        return fontSizeHwpUnit;
    }

    public void setFontSizeHwpUnit(Integer fontSizeHwpUnit) {
        this.fontSizeHwpUnit = fontSizeHwpUnit;
    }

    // pt 단위로 변환하는 getter/setter
    public Double getFontSizePt() {
        return fontSizeHwpUnit == null ? null : fontSizeHwpUnit / 100.0;
    }

    public void setFontSizePt(Double fontSizePt) {
        this.fontSizeHwpUnit = fontSizePt == null ? null : (int)(fontSizePt * 100);
    }

    public String getFontRefId() {
        return fontRefId;
    }
    
    public void setFontRefId(String fontRefId) {
        this.fontRefId = fontRefId;
    }

    // toMap, fromMap - Map<String, String>으로 변경
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (id != null) map.put("id", id);
        if (textColor != null) map.put("textColor", textColor);
        if (bold != null) map.put("bold", bold.toString());
        if (italic != null) map.put("italic", italic.toString());
        if (underline != null) map.put("underline", underline.toString());
        if (strikeout != null) map.put("strikeout", strikeout.toString());
        // 수치형 데이터도 문자열로 변환
        if (fontSizeHwpUnit != null) {
            map.put("fontSizePt", getFontSizePt().toString());
        }
        return map;
    }

    public static CharPrAttributes fromMap(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        CharPrAttributes attr = new CharPrAttributes();
        attr.setId(map.get("id"));
        attr.setTextColor(map.get("textColor"));
        
        String boldStr = map.get("bold");
        if (boldStr != null) {
            attr.setBold(Boolean.valueOf(boldStr));
        }
        
        String italicStr = map.get("italic");
        if (italicStr != null) {
            attr.setItalic(Boolean.valueOf(italicStr));
        }
        
        String underlineStr = map.get("underline");
        if (underlineStr != null) {
            attr.setUnderline(Boolean.valueOf(underlineStr));
        }
        
        String strikeoutStr = map.get("strikeout");
        if (strikeoutStr != null) {
            attr.setStrikeout(Boolean.valueOf(strikeoutStr));
        }
        
        String fontSizePtStr = map.get("fontSizePt");
        if (fontSizePtStr != null) {
            try {
                attr.setFontSizePt(Double.parseDouble(fontSizePtStr));
            } catch (NumberFormatException e) {
                System.err.println("CharPrAttributes.fromMap: 잘못된 fontSizePt 값입니다 - " + fontSizePtStr);
            }
        }
        return attr;
    }
} 