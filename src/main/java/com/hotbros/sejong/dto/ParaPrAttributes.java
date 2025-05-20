package com.hotbros.sejong.dto;

import java.util.HashMap;
import java.util.Map;

public class ParaPrAttributes {
    private String id;                  // ParaPr ID (필수)
    private String alignHorizontal;     // 수평 정렬 (예: "CENTER", "LEFT", "RIGHT", "JUSTIFY", "DISTRIBUTE")
    private String alignVertical;       // 수직 정렬 (예: "TOP", "CENTER", "BOTTOM")
    private Boolean snapToGrid;         // 그리드에 스냅
    private Double lineSpacing;         // 줄 간격 (HWP 값, 예: 160은 160%)
    // 추가적으로 고려할 수 있는 ParaPr 속성들:
    // private Integer indent;             // 들여쓰기 (HWP 값)
    // private Integer leftMargin;         // 왼쪽 여백 (HWP 값)
    // private Integer rightMargin;        // 오른쪽 여백 (HWP 값)
    // private Integer topMargin;          // 위쪽 여백 (HWP 값)
    // private Integer bottomMargin;       // 아래쪽 여백 (HWP 값)
    // private String lineSpacingType;    // 줄간격 종류 (예: "PERCENT", "FIXED")


    // 기본 생성자
    public ParaPrAttributes() {
    }

    // 모든 필드를 받는 생성자 (편의용)
    public ParaPrAttributes(String id, String alignHorizontal, String alignVertical, Boolean snapToGrid, Double lineSpacing) {
        this.id = id;
        this.alignHorizontal = alignHorizontal;
        this.alignVertical = alignVertical;
        this.snapToGrid = snapToGrid;
        this.lineSpacing = lineSpacing;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlignHorizontal() {
        return alignHorizontal;
    }

    public void setAlignHorizontal(String alignHorizontal) {
        this.alignHorizontal = alignHorizontal;
    }

    public String getAlignVertical() {
        return alignVertical;
    }

    public void setAlignVertical(String alignVertical) {
        this.alignVertical = alignVertical;
    }

    public Boolean getSnapToGrid() {
        return snapToGrid;
    }

    public void setSnapToGrid(Boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }

    public Double getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(Double lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    // toMap, fromMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (id != null) map.put("id", id);
        
        // align 관련 처리는 ParaPrBuilder의 fromMap 구조를 따름
        // ParaPrBuilder가 "align" 키 아래에 Map을 기대한다면, 여기서도 그렇게 구성
        Map<String, String> alignMap = new HashMap<>();
        boolean alignMapNeeded = false;
        if (alignHorizontal != null) {
            alignMap.put("horizontal", alignHorizontal);
            alignMapNeeded = true;
        }
        if (alignVertical != null) {
            alignMap.put("vertical", alignVertical);
            alignMapNeeded = true;
        }
        if (alignMapNeeded) {
            map.put("align", alignMap);
        }

        if (snapToGrid != null) map.put("snapToGrid", snapToGrid);
        if (lineSpacing != null) map.put("lineSpacing", lineSpacing);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static ParaPrAttributes fromMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        ParaPrAttributes attr = new ParaPrAttributes();
        attr.setId((String) map.get("id"));

        Object alignObj = map.get("align");
        if (alignObj instanceof Map) {
            Map<String, String> alignMap = (Map<String, String>) alignObj;
            attr.setAlignHorizontal(alignMap.get("horizontal"));
            attr.setAlignVertical(alignMap.get("vertical"));
        }
        
        attr.setSnapToGrid((Boolean) map.get("snapToGrid"));

        Object lsObj = map.get("lineSpacing");
        if (lsObj instanceof Number) {
            attr.setLineSpacing(((Number) lsObj).doubleValue());
        } else if (lsObj instanceof String) {
            try {
                attr.setLineSpacing(Double.parseDouble((String) lsObj));
            } catch (NumberFormatException e) {
                // Log or handle
            }
        }
        return attr;
    }
} 