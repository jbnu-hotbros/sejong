package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;

import java.util.Map;
import java.util.Objects;

public class StyleBuilder {

    private Style workingStyle; // 작업 대상이 되는 Style 객체 (복제본 또는 새 객체)

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
            this.workingStyle.id(id); // Style 객체의 id() 메서드는 setter 역할
        }
        // id가 null이거나 비어있는 경우, 현재 workingStyle의 ID를 변경하지 않음 (기존값 유지)
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
                // 잘못된 문자열 값이면 기존 타입 유지 또는 특정 기본값 설정 (여기서는 기존값 유지)
                System.err.println("StyleBuilder: 잘못된 StyleType 문자열 값입니다 - " + typeStr + ". 기존 타입을 유지합니다.");
            }
        }
        return this;
    }

    public StyleBuilder name(String name) {
        // 이름은 일반적으로 null이나 빈 문자열을 허용하지 않지만, hwpxlib 특성에 따라 다를 수 있음.
        // 여기서는 null이 아니고 비어있지 않은 경우에만 설정
        if (name != null && !name.trim().isEmpty()) {
            this.workingStyle.nameAnd(name);
        }
        return this;
    }

    public StyleBuilder engName(String engName) {
        // 영문 이름은 선택 사항이므로 null 설정 가능
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
        // langID는 특정 포맷(예: "1042")을 따를 수 있습니다.
        // 여기서는 단순 문자열로 받지만, 필요시 유효성 검사 추가 가능.
        if (langID != null) { // langID는 빈 문자열도 의미가 있을 수 있으므로 trim() 없이 처리
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
            // "0" 또는 "false" (대소문자 무관)일 때 false, 그 외에는 true로 간주하거나,
            // "1" 또는 "true"일 때 true, 그 외에는 false로 간주하는 정책을 정해야 함.
            // HWPX 스키마는 일반적으로 0 또는 1을 사용.
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
        // ID가 여전히 설정되지 않았거나 비정상적이라면 최종적으로 한번 더 보장.
        // (생성자에서 이미 ID를 설정하므로, 이 부분은 추가적인 안전장치 또는 다른 정책에 따라 조정 가능)
        if (this.workingStyle.id() == null || this.workingStyle.id().trim().isEmpty()) {
            this.workingStyle.id("style_final_" + System.nanoTime());
        }
        return this.workingStyle;
    }

    /**
     * 원본 스타일을 기반으로 Map의 내용으로 속성을 덮어쓰는 빌더를 생성하고 반환합니다.
     * @param originalStyle 복제할 원본 Style 객체. null이면 새로운 Style 객체로 시작합니다.
     * @param attributesToUpdate 덮어쓸 속성들이 담긴 Map. Map의 값으로 null이 오면 해당 속성 변경 안 함.
     * @return 속성이 적용된 StyleBuilder 인스턴스 (메서드 체이닝을 위해). 실제 객체는 build()로 얻습니다.
     */
    public static StyleBuilder fromMap(Style originalStyle, Map<String, Object> attributesToUpdate) {
        StyleBuilder builder = new StyleBuilder(originalStyle);

        if (attributesToUpdate == null) {
            return builder;
        }

        // 각 속성에 대해 Map에 키가 존재하고, 그 값이 null이 아닐 경우에만 setter 호출
        // (Objects.toString(value, defaultValue)는 value가 null이면 defaultValue를 반환)
        
        // ID는 명시적으로 null이 아닌 경우에만 업데이트
        if (attributesToUpdate.containsKey("id")) {
            String idVal = Objects.toString(attributesToUpdate.get("id"), null);
            if (idVal != null) builder.id(idVal);
        }

        if (attributesToUpdate.containsKey("type")) {
            Object typeVal = attributesToUpdate.get("type");
            if (typeVal instanceof StyleType) {
                builder.type((StyleType) typeVal);
            } else if (typeVal != null) {
                builder.type(typeVal.toString());
            }
        }
        if (attributesToUpdate.containsKey("name")) {
            String nameVal = Objects.toString(attributesToUpdate.get("name"), null);
            if (nameVal != null) builder.name(nameVal);
        }
        if (attributesToUpdate.containsKey("engName")) {
            // engName은 null일 수 있으므로, 키가 존재하면 값을 그대로 전달
            builder.engName(Objects.toString(attributesToUpdate.get("engName"), null));
        }
        if (attributesToUpdate.containsKey("paraPrIDRef")) {
            String paraPrIDRefVal = Objects.toString(attributesToUpdate.get("paraPrIDRef"), null);
            if (paraPrIDRefVal != null) builder.paraPrIDRef(paraPrIDRefVal);
        }
        if (attributesToUpdate.containsKey("charPrIDRef")) {
            String charPrIDRefVal = Objects.toString(attributesToUpdate.get("charPrIDRef"), null);
            if (charPrIDRefVal != null) builder.charPrIDRef(charPrIDRefVal);
        }
        if (attributesToUpdate.containsKey("nextStyleIDRef")) {
            String nextStyleIDRefVal = Objects.toString(attributesToUpdate.get("nextStyleIDRef"), null);
            if (nextStyleIDRefVal != null) builder.nextStyleIDRef(nextStyleIDRefVal);
        }
        if (attributesToUpdate.containsKey("langID")) {
            String langIDVal = Objects.toString(attributesToUpdate.get("langID"), null);
            if (langIDVal != null) builder.langID(langIDVal);
        }
        if (attributesToUpdate.containsKey("lockForm")) {
            Object lockFormVal = attributesToUpdate.get("lockForm");
            if (lockFormVal instanceof Boolean) {
                builder.lockForm((Boolean) lockFormVal);
            } else if (lockFormVal != null) {
                builder.lockForm(lockFormVal.toString());
            }
        }
        
        return builder;
    }
} 