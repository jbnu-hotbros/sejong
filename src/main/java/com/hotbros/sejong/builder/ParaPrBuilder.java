package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;

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

    public ParaPrBuilder lineSpacingValue(Integer value) { // HWP 단위 값 (예: 160 -> 160%)
        if (value != null) {
            if (this.workingParaPr.lineSpacing() == null) {
                this.workingParaPr.createLineSpacing();
            }
            this.workingParaPr.lineSpacing().value(value.intValue());
            // 기본 단위를 PERCENT로 할지, 아니면 타입을 받는 메서드를 추가할지 결정 필요.
            // 여기서는 HWP 기본값인 hwpxlib.object.content.header_xml.enumtype.LineSpacingSort.PERCENT 를 가정.
            // 명시적으로 하려면: this.workingParaPr.lineSpacing().type(kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineSpacingSort.PERCENT);
        } else {
            if (this.workingParaPr.lineSpacing() != null) {
                this.workingParaPr.removeLineSpacing(); // 또는 lineSpacing 객체의 값을 null로 설정
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
     * 원본 ParaPr을 기반으로 ParaPrAttributes DTO의 내용으로 속성을 덮어쓰는 빌더를 생성하고 반환합니다.
     * fromMap 메서드는 이 메서드로 대체됩니다.
     * @param originalParaPr 복제할 원본 ParaPr 객체.
     * @param attributesToApply 적용할 속성이 담긴 ParaPrAttributes DTO 객체.
     * @return 속성이 적용된 ParaPrBuilder 인스턴스
     */
    public static ParaPrBuilder fromAttributes(ParaPr originalParaPr, com.hotbros.sejong.dto.ParaPrAttributes attributesToApply) {
        ParaPrBuilder builder = new ParaPrBuilder(originalParaPr);
        if (attributesToApply == null) {
            return builder;
        }

        if (attributesToApply.getId() != null) {
            builder.id(attributesToApply.getId());
        }
        
        // condense, fontLineHeight는 현재 ParaPrAttributes DTO에 있지만,
        // HWPXBuilder 예제에서 직접 사용되지 않았으므로 YAGNI에 따라 여기서의 처리는 보류하거나
        // 필요시 DTO 필드에 맞춰 빌더 메서드 호출.
        // 예: if (attributesToApply.getCondense() != null) builder.condense(attributesToApply.getCondense().byteValue()); // DTO가 Byte를 반환한다면
        // 예: if (attributesToApply.getFontLineHeight() != null) builder.fontLineHeight(attributesToApply.getFontLineHeight());

        if (attributesToApply.getSnapToGrid() != null) {
            builder.snapToGrid(attributesToApply.getSnapToGrid());
        }

        if (attributesToApply.getAlignHorizontal() != null) {
            builder.alignHorizontal(attributesToApply.getAlignHorizontal());
        }
        if (attributesToApply.getAlignVertical() != null) {
            builder.alignVertical(attributesToApply.getAlignVertical());
        }

        if (attributesToApply.getLineSpacing() != null) {
            // ParaPrAttributes의 getLineSpacing()은 Double (HWP 값)을 반환.
            // lineSpacingValue 메서드는 Integer를 받으므로 intValue()로 변환.
            builder.lineSpacingValue(attributesToApply.getLineSpacing().intValue());
        }

        return builder;
    }
} 