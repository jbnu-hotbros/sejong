package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.Align;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;
// TODO: 필요한 다른 Enum 또는 객체 타입 import (예: Margin)

import java.util.Map;
import java.util.Objects;

public class ParaPrBuilder {

    private ParaPr workingParaPr; // 작업 대상 ParaPr 객체 (복제본 또는 새 객체)

    /**
     * 원본 ParaPr을 기반으로 빌더를 초기화합니다.
     * 원본 ParaPr은 반드시 제공되어야 하며, 이를 복제하여 사용합니다.
     * @param originalParaPr 복제할 원본 ParaPr 객체. null일 수 없습니다.
     * @throws IllegalArgumentException originalParaPr이 null인 경우 발생합니다.
     */
    public ParaPrBuilder(ParaPr originalParaPr) {
        if (originalParaPr == null) {
            throw new IllegalArgumentException("Original ParaPr cannot be null. ParaPrBuilder requires a base ParaPr to work upon.");
        }
        this.workingParaPr = originalParaPr.clone();
    }

    public ParaPrBuilder id(String id) {
        if (id != null && !id.trim().isEmpty()) {
            this.workingParaPr.id(id);
        }
        return this;
    }

    public ParaPrBuilder condense(String condenseStr) {
        if (condenseStr == null || condenseStr.trim().isEmpty()) {
            this.workingParaPr.condense(null); // 값 제거
            return this;
        }
        try {
            this.workingParaPr.condenseAnd(Byte.parseByte(condenseStr.trim()));
        } catch (NumberFormatException e) {
            System.err.println("ParaPrBuilder: 잘못된 condense 값 형식입니다 - " + condenseStr);
            this.workingParaPr.condense(null);
        }
        return this;
    }

    public ParaPrBuilder condense(byte condenseValue) {
        this.workingParaPr.condenseAnd(condenseValue);
        return this;
    }

    public ParaPrBuilder fontLineHeight(String fontLineHeightStr) {
        if (fontLineHeightStr == null || fontLineHeightStr.trim().isEmpty()){
            this.workingParaPr.fontLineHeight(null); // 값 제거
        } else {
            this.workingParaPr.fontLineHeightAnd("true".equalsIgnoreCase(fontLineHeightStr));
        }
        return this;
    }

    public ParaPrBuilder fontLineHeight(boolean fontLineHeightValue) {
        this.workingParaPr.fontLineHeightAnd(fontLineHeightValue);
        return this;
    }

    public ParaPrBuilder snapToGrid(String snapToGridStr) {
        if (snapToGridStr == null || snapToGridStr.trim().isEmpty()) {
            this.workingParaPr.snapToGrid(null); // 값 제거
        } else {
            this.workingParaPr.snapToGridAnd("true".equalsIgnoreCase(snapToGridStr));
        }
        return this;
    }

    public ParaPrBuilder snapToGrid(boolean snapToGridValue) {
        this.workingParaPr.snapToGridAnd(snapToGridValue);
        return this;
    }

    public ParaPrBuilder alignHorizontal(String horizontalAlignStr) {
        if (horizontalAlignStr == null || horizontalAlignStr.trim().isEmpty()) {
            if (this.workingParaPr.align() != null) {
                this.workingParaPr.align().horizontal(null); // 수평 정렬만 제거
                if (this.workingParaPr.align().vertical() == null) { // 수직 정렬도 없으면 Align 객체 제거
                    this.workingParaPr.removeAlign();
                }
            }
            return this;
        }
        try {
            if (this.workingParaPr.align() == null) {
                this.workingParaPr.createAlign();
            }
            this.workingParaPr.align().horizontalAnd(HorizontalAlign2.valueOf(horizontalAlignStr.toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.err.println("ParaPrBuilder: 잘못된 horizontalAlign 값입니다 - " + horizontalAlignStr);
            if (this.workingParaPr.align() != null) { // 잘못된 값이면 해당 속성 null 처리
                 this.workingParaPr.align().horizontal(null);
                 if (this.workingParaPr.align().vertical() == null) { 
                    this.workingParaPr.removeAlign();
                }
            }
        }
        return this;
    }

    public ParaPrBuilder alignHorizontal(HorizontalAlign2 horizontalAlignValue) {
        if (this.workingParaPr.align() == null && horizontalAlignValue != null) {
            this.workingParaPr.createAlign();
        }
        if (this.workingParaPr.align() != null) {
            this.workingParaPr.align().horizontalAnd(horizontalAlignValue);
            if (horizontalAlignValue == null && this.workingParaPr.align().vertical() == null) {
                this.workingParaPr.removeAlign();
            }
        }
        return this;
    }

    public ParaPrBuilder alignVertical(String verticalAlignStr) {
        if (verticalAlignStr == null || verticalAlignStr.trim().isEmpty()) {
            if (this.workingParaPr.align() != null) {
                this.workingParaPr.align().vertical(null); // 수직 정렬만 제거
                if (this.workingParaPr.align().horizontal() == null) { // 수평 정렬도 없으면 Align 객체 제거
                    this.workingParaPr.removeAlign();
                }
            }
            return this;
        }
        try {
            if (this.workingParaPr.align() == null) {
                this.workingParaPr.createAlign();
            }
            this.workingParaPr.align().verticalAnd(VerticalAlign1.valueOf(verticalAlignStr.toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.err.println("ParaPrBuilder: 잘못된 verticalAlign 값입니다 - " + verticalAlignStr);
             if (this.workingParaPr.align() != null) { // 잘못된 값이면 해당 속성 null 처리
                 this.workingParaPr.align().vertical(null);
                 if (this.workingParaPr.align().horizontal() == null) { 
                    this.workingParaPr.removeAlign();
                }
            }
        }
        return this;
    }

    public ParaPrBuilder alignVertical(VerticalAlign1 verticalAlignValue) {
        if (this.workingParaPr.align() == null && verticalAlignValue != null) {
            this.workingParaPr.createAlign();
        }
        if (this.workingParaPr.align() != null) {
            this.workingParaPr.align().verticalAnd(verticalAlignValue);
            if (verticalAlignValue == null && this.workingParaPr.align().horizontal() == null) {
                this.workingParaPr.removeAlign();
            }
        }
        return this;
    }

    public ParaPr build() {
        if (this.workingParaPr.id() == null || this.workingParaPr.id().trim().isEmpty()) {
            this.workingParaPr.id("paraPr_final_" + System.nanoTime());
        }
        return this.workingParaPr;
    }

    /**
     * 원본 ParaPr을 기반으로 Map의 내용으로 속성을 덮어쓰는 빌더를 생성하고 반환합니다.
     * @param originalParaPr 복제할 원본 ParaPr 객체. null이면 새로운 ParaPr 객체로 시작합니다.
     * @param attributesToUpdate 덮어쓸 속성들이 담긴 Map.
     * @return 속성이 적용된 ParaPrBuilder 인스턴스
     */
    @SuppressWarnings("unchecked")
    public static ParaPrBuilder fromMap(ParaPr originalParaPr, Map<String, Object> attributesToUpdate) {
        ParaPrBuilder builder = new ParaPrBuilder(originalParaPr);
        if (attributesToUpdate == null) {
            return builder;
        }

        if (attributesToUpdate.containsKey("id")) {
            builder.id(Objects.toString(attributesToUpdate.get("id"), null));
        }
        
        if (attributesToUpdate.containsKey("condense")) {
            Object cVal = attributesToUpdate.get("condense");
            if (cVal instanceof String) builder.condense((String) cVal);
            else if (cVal instanceof Number) builder.condense(((Number) cVal).byteValue());
            else if (cVal == null) builder.condense((String)null); // 명시적 null로 제거
        }
        if (attributesToUpdate.containsKey("fontLineHeight")) {
            Object flhVal = attributesToUpdate.get("fontLineHeight");
            if (flhVal instanceof String) builder.fontLineHeight((String) flhVal);
            else if (flhVal instanceof Boolean) builder.fontLineHeight((Boolean) flhVal);
            else if (flhVal == null) builder.fontLineHeight(null); // 명시적 null로 제거
        }
        if (attributesToUpdate.containsKey("snapToGrid")) {
            Object stgVal = attributesToUpdate.get("snapToGrid");
            if (stgVal instanceof String) builder.snapToGrid((String) stgVal);
            else if (stgVal instanceof Boolean) builder.snapToGrid((Boolean) stgVal);
            else if (stgVal == null) builder.snapToGrid(null); // 명시적 null로 제거
        }

        if (attributesToUpdate.containsKey("align")) {
            Object alignObj = attributesToUpdate.get("align");
            if (alignObj instanceof Map) {
                Map<String, Object> alignMap = (Map<String, Object>) alignObj;
                if (alignMap.containsKey("horizontal")) {
                    Object hAlign = alignMap.get("horizontal");
                    if (hAlign instanceof String) builder.alignHorizontal((String) hAlign);
                    else if (hAlign instanceof HorizontalAlign2) builder.alignHorizontal((HorizontalAlign2) hAlign);
                    else if (hAlign == null) builder.alignHorizontal((String)null); // 명시적 null로 제거
                }
                if (alignMap.containsKey("vertical")) {
                    Object vAlign = alignMap.get("vertical");
                    if (vAlign instanceof String) builder.alignVertical((String) vAlign);
                    else if (vAlign instanceof VerticalAlign1) builder.alignVertical((VerticalAlign1) vAlign);
                    else if (vAlign == null) builder.alignVertical((String)null); // 명시적 null로 제거
                }
            } else if (alignObj == null) { // align 키 자체가 null이면 Align 객체 전체 제거
                if (builder.workingParaPr.align() != null) {
                    builder.workingParaPr.removeAlign();
                }
            }
        }
        return builder;
    }
} 