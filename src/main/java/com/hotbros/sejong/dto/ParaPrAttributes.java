package com.hotbros.sejong.dto;

import java.util.HashMap;
import java.util.Map;

import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;

public class ParaPrAttributes {
    private String id;                  // ParaPr ID (필수)
    private HorizontalAlign2 alignHorizontal;     // 수평 정렬 (예: CENTER, LEFT, RIGHT, JUSTIFY, DISTRIBUTE)
    private VerticalAlign1 alignVertical;       // 수직 정렬 (예: TOP, CENTER, BOTTOM)
    private Integer lineSpacing;         // 줄 간격 (HWP 값, 예: 160은 160%)
    private Byte condense;              // 자간 (HWP 값, 예: -50 ~ 50)


    // 기본 생성자
    public ParaPrAttributes() {
    }

    // 모든 필드를 받는 생성자 (편의용)
    public ParaPrAttributes(String id, HorizontalAlign2 alignHorizontal, VerticalAlign1 alignVertical, Integer lineSpacing, Byte condense) {
        this.id = id;
        this.alignHorizontal = alignHorizontal;
        this.alignVertical = alignVertical;
        this.lineSpacing = lineSpacing;
        this.condense = condense;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HorizontalAlign2 getAlignHorizontal() {
        return alignHorizontal;
    }

    public void setAlignHorizontal(HorizontalAlign2 alignHorizontal) {
        this.alignHorizontal = alignHorizontal;
    }

    public VerticalAlign1 getAlignVertical() {
        return alignVertical;
    }

    public void setAlignVertical(VerticalAlign1 alignVertical) {
        this.alignVertical = alignVertical;
    }

    public Integer getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(Integer lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public Byte getCondense() {
        return condense;
    }

    public void setCondense(Byte condense) {
        this.condense = condense;
    }

    // toMap, fromMap - Map<String, String>으로 변경
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (id != null) map.put("id", id);
        
        // enum 값을 문자열로 변환하여 저장
        if (alignHorizontal != null) {
            map.put("alignHorizontal", alignHorizontal.str());
        }
        if (alignVertical != null) {
            map.put("alignVertical", alignVertical.str());
        }

        if (lineSpacing != null) map.put("lineSpacing", lineSpacing.toString());
        if (condense != null) map.put("condense", condense.toString());
        return map;
    }

    public static ParaPrAttributes fromMap(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        ParaPrAttributes attr = new ParaPrAttributes();
        attr.setId(map.get("id"));
        
        // 문자열을 enum으로 변환
        String hAlign = map.get("alignHorizontal");
        if (hAlign != null) {
            attr.setAlignHorizontal(HorizontalAlign2.fromString(hAlign));
        }
        
        String vAlign = map.get("alignVertical");
        if (vAlign != null) {
            attr.setAlignVertical(VerticalAlign1.fromString(vAlign));
        }
        
        String lsStr = map.get("lineSpacing");
        if (lsStr != null) {
            try {
                attr.setLineSpacing(Integer.parseInt(lsStr));
            } catch (NumberFormatException e) {
                System.err.println("ParaPrAttributes.fromMap: 잘못된 lineSpacing 값입니다 - " + lsStr);
            }
        }

        String conStr = map.get("condense");
        if (conStr != null) {
            try {
                attr.setCondense(Byte.parseByte(conStr));
            } catch (NumberFormatException e) {
                System.err.println("ParaPrAttributes.fromMap: 잘못된 condense 값입니다 - " + conStr);
            }
        }
        return attr;
    }
} 