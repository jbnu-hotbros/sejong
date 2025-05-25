package com.hotbros.sejong.style.request;

import com.hotbros.sejong.dto.ParaPrAttributes;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.LineSpacing;
import kr.dogfoot.hwpxlib.object.common.HWPXObject;
import kr.dogfoot.hwpxlib.object.common.compatibility.Case;
import kr.dogfoot.hwpxlib.object.common.compatibility.Default;
import kr.dogfoot.hwpxlib.object.common.compatibility.InSwitchObject;
import kr.dogfoot.hwpxlib.object.common.compatibility.Switch;
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
            // null 또는 빈 문자열이면 아무 작업도 하지 않고 반환
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

    public ParaPrBuilder condense(Byte condenseValue) {
        if (condenseValue == null) {
            return this;
        }
        this.workingParaPr.condenseAnd(condenseValue);
        return this;
    }


    public ParaPrBuilder alignHorizontal(HorizontalAlign2 horizontalAlignValue) {
        if (horizontalAlignValue == null) {
            // horizontalAlignValue가 null이면 아무 작업도 하지 않고 반환
            return this;
        }
        
        if (this.workingParaPr.align() == null) { // null이 아닌 유효한 값이 왔을 때만 align 객체 생성 고려
            this.workingParaPr.createAlign();
        }
        this.workingParaPr.align().horizontalAnd(horizontalAlignValue);
        // Align 객체 자체의 제거 로직은 여기서 불필요해짐 (null일 때 값을 설정하지 않으므로)
        // 만약 vertical도 null이어서 align 객체를 지우고 싶다면 별도의 removeAlignIfEmpty 같은 메서드가 필요할 수 있음
        return this;
    }

    public ParaPrBuilder alignVertical(String verticalAlignStr) {
        if (verticalAlignStr == null || verticalAlignStr.trim().isEmpty()) {
            // null 또는 빈 문자열이면 아무 작업도 하지 않고 반환
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
        if (verticalAlignValue == null) {
            // verticalAlignValue가 null이면 아무 작업도 하지 않고 반환
            return this;
        }

        if (this.workingParaPr.align() == null) { // null이 아닌 유효한 값이 왔을 때만 align 객체 생성 고려
            this.workingParaPr.createAlign();
        }
        this.workingParaPr.align().verticalAnd(verticalAlignValue);
        // Align 객체 자체의 제거 로직은 여기서 불필요해짐
        return this;
    }

    public ParaPrBuilder lineSpacing(Integer value) { // HWP 단위 값 (예: 160 -> 160%)
        if (value == null) {
            // value가 null이면 아무 작업도 하지 않고 반환
            return this;
        }
        
        Switch currentSwitch= getSwitch();
        if (currentSwitch==null) {
            System.out.println("ParaPrBuilder: switch가 없습니다.");
            return this;
        }
        Case currentCase= getCase(currentSwitch, "http://www.hancom.co.kr/hwpml/2016/HwpUnitChar");
        if (currentCase==null) {
            System.out.println("ParaPrBuilder: case가 없습니다.");
            return this;
        }
        Default currentDefault= getDefault(currentSwitch);
        if (currentDefault==null) {
            System.out.println("ParaPrBuilder: default가 없습니다.");
            return this;
        }

    
        LineSpacing CaseLineSpacing = findFirstLineSpacing(currentCase);
        CaseLineSpacing.value(value);

        LineSpacing defaultLineSpacing = findFirstLineSpacing(currentDefault);
        defaultLineSpacing.value(value);
        
        
        return this;
    }

    private static LineSpacing findFirstLineSpacing(InSwitchObject inSwitchObject) {
        if (inSwitchObject == null) return null;

        Iterable<HWPXObject> children = inSwitchObject.children();
        if (children == null) return null;

        for (HWPXObject child : children) {
            if (child instanceof LineSpacing) {
                return (LineSpacing) child;
            }
        }
        return null;
    }


    /**
     * workingParaPr에서 마지막 Switch 객체를 가져오거나 새로 생성합니다.
     * @return Switch 객체
     */
    private Switch getSwitch() {
        if (this.workingParaPr.switchList() == null || this.workingParaPr.switchList().isEmpty()) {
            return null;
        }
        // 기존 Switch가 여러 개 있을 경우, 마지막 Switch를 가져오는 정책
        return this.workingParaPr.switchList().get(this.workingParaPr.switchList().size() - 1);
    }



    /**
     * Switch 내에서 특정 requiredNamespace를 가진 Case 객체를 찾거나 새로 생성합니다.
     * @param sw Switch 객체
     * @param requiredNamespace 찾거나 생성할 Case의 requiredNamespace
     * @return 찾거나 생성된 Case 객체
     */
    private Case getCase(Switch sw, String requiredNamespace) {
        if (sw.caseObjects() != null) {
            for (Case c : sw.caseObjects()) {
                if (requiredNamespace.equals(c.requiredNamespace())) {
                    return c;
                }
            }
        }
        return null;
    }
    
    /**
     * Switch 내에서 Default 객체를 가져오거나 새로 생성합니다.
     * @param sw Switch 객체
     * @return Default 객체
     */
    private Default getDefault(Switch sw) {
        if (sw.defaultObject() == null) {
            return null;
        }
        return sw.defaultObject();
    }



    public ParaPr build() {
        if (this.workingParaPr.id() == null || this.workingParaPr.id().trim().isEmpty()) {
            throw new IllegalStateException("ParaPr ID must not be null or empty. Please set a valid ID using the id() method.");
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
    public static ParaPrBuilder fromAttributes(ParaPr originalParaPr, ParaPrAttributes attributesToApply) {
        ParaPrBuilder builder = new ParaPrBuilder(originalParaPr);
        if (attributesToApply == null) {
            return builder;
        }
    
        builder.id(attributesToApply.getId())
               .condense(attributesToApply.getCondense())
               .alignHorizontal(attributesToApply.getAlignHorizontal())
               .alignVertical(attributesToApply.getAlignVertical())
               .lineSpacing(attributesToApply.getLineSpacing());
    
        return builder;
    }

} 