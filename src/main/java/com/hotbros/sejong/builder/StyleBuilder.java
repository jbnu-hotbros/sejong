package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
// import com.hotbros.sejong.attributes.StyleAttributes; // DTO import

// import java.util.Map; // Map 더 이상 직접 사용 안 함
// import java.util.Objects; // Objects 더 이상 직접 사용 안 함

public class StyleBuilder {

    private Style workingStyle;

    /**
     * 원본 스타일을 기반으로 빌더를 초기화합니다.
     * 원본 스타일은 반드시 제공되어야 하며, 이를 복제하여 사용합니다.
     * @param originalStyle 복제할 원본 Style 객체. null일 수 없습니다.
     * @throws IllegalArgumentException originalStyle이 null인 경우 발생합니다.
     */
    public StyleBuilder(Style originalStyle) {
        if (originalStyle == null) {
            throw new IllegalArgumentException("Original Style cannot be null. StyleBuilder requires a base Style to work upon.");
        }
        this.workingStyle = originalStyle.clone();
    }
    
    public StyleBuilder id(String id) {
        if (id != null && !id.trim().isEmpty()) {
            this.workingStyle.id(id);
        }
        return this;
    }

    public StyleBuilder type(StyleType type) {
        if (type != null) {
            this.workingStyle.typeAnd(type);
        }
        return this;
    }

    public StyleBuilder type(String typeStr) {
        if (typeStr != null && !typeStr.trim().isEmpty()) {
            try {
                this.workingStyle.typeAnd(StyleType.valueOf(typeStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.err.println("StyleBuilder: 잘못된 StyleType 문자열 값입니다 - " + typeStr + ". 기존 타입을 유지합니다.");
            }
        }
        return this;
    }

    public StyleBuilder name(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.workingStyle.nameAnd(name);
        }
        return this;
    }

    public StyleBuilder engName(String engName) {
        this.workingStyle.engNameAnd(engName);
        return this;
    }

    public StyleBuilder paraPrIDRef(String paraPrIDRef) {
        if (paraPrIDRef != null && !paraPrIDRef.trim().isEmpty()) {
            this.workingStyle.paraPrIDRefAnd(paraPrIDRef);
        }
        return this;
    }

    public StyleBuilder charPrIDRef(String charPrIDRef) {
        if (charPrIDRef != null && !charPrIDRef.trim().isEmpty()) {
            this.workingStyle.charPrIDRefAnd(charPrIDRef);
        }
        return this;
    }

    public StyleBuilder nextStyleIDRef(String nextStyleIDRef) {
        if (nextStyleIDRef != null && !nextStyleIDRef.trim().isEmpty()) {
            this.workingStyle.nextStyleIDRefAnd(nextStyleIDRef);
        }
        return this;
    }

    public StyleBuilder langID(String langID) {
        if ((langID != null)) {
            this.workingStyle.langIDAnd(langID);
        }
        return this;
    }

    public StyleBuilder lockForm(Boolean lockForm) {
        if (lockForm != null) {
            this.workingStyle.lockFormAnd(lockForm);
        }
        return this;
    }
    
    public StyleBuilder lockForm(String lockFormStr) {
        if (lockFormStr != null && !lockFormStr.trim().isEmpty()) {
            boolean val = "1".equals(lockFormStr) || "true".equalsIgnoreCase(lockFormStr);
            this.workingStyle.lockFormAnd(val);
        }
        return this;
    }

    /**
     * 최종적으로 수정된 Style 객체를 반환합니다.
     * @return 수정된 Style 객체 (원본의 복제본이거나 새로 생성된 객체).
     */
    public Style build() {
        if (this.workingStyle.id() == null || this.workingStyle.id().trim().isEmpty()) {
            this.workingStyle.id("style_final_" + System.nanoTime());
        }
        return this.workingStyle;
    }

    /**
     * 원본 Style을 기반으로 StyleAttributes DTO의 내용으로 속성을 덮어쓰는 빌더를 생성하고 반환합니다.
     * @param originalStyle 복제할 원본 Style 객체.
     * @param attributesToApply 적용할 속성이 담긴 StyleAttributes DTO 객체.
     *                          (StyleAttributes DTO는 type, charPrIDRef, paraPrIDRef 필드를 포함하도록 확장되었다고 가정)
     * @return 속성이 적용된 StyleBuilder 인스턴스
     */
    public static StyleBuilder fromAttributes(Style originalStyle, com.hotbros.sejong.dto.StyleAttributes attributesToApply) {
        StyleBuilder builder = new StyleBuilder(originalStyle);
        if (attributesToApply == null) {
            return builder;
        }

        if (attributesToApply.getId() != null) {
            builder.id(attributesToApply.getId());
        }
        if (attributesToApply.getName() != null) {
            builder.name(attributesToApply.getName());
        }
        builder.engName(attributesToApply.getEngName());
        
        if (attributesToApply.getNextStyleIDRef() != null) {
            builder.nextStyleIDRef(attributesToApply.getNextStyleIDRef());
        }
        
        if (attributesToApply.getCharPrIDRef() != null) {
            builder.charPrIDRef(attributesToApply.getCharPrIDRef());
        }
        if (attributesToApply.getParaPrIDRef() != null) {
            builder.paraPrIDRef(attributesToApply.getParaPrIDRef());
        }

        return builder;
    }
} 