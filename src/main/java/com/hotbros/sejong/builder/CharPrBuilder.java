package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bold;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Italic;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.FontReference; // Not used yet
// 필요한 다른 Enum 타입 import (예: 밑줄 종류 등)
// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.UnderlineSort;

import java.util.Map;
import java.util.Objects;

public class CharPrBuilder {

    private CharPr workingCharPr; // 작업 대상 CharPr 객체 (복제본 또는 새 객체)

    /**
     * 원본 CharPr을 기반으로 빌더를 초기화합니다.
     * 원본 CharPr은 반드시 제공되어야 하며, 이를 복제하여 사용합니다.
     * @param originalCharPr 복제할 원본 CharPr 객체. null일 수 없습니다.
     * @throws IllegalArgumentException originalCharPr이 null인 경우 발생합니다.
     */
    public CharPrBuilder(CharPr originalCharPr) {
        if (originalCharPr == null) {
            throw new IllegalArgumentException("Original CharPr cannot be null. CharPrBuilder requires a base CharPr to work upon.");
        }
        this.workingCharPr = originalCharPr.clone();
    }

    public CharPrBuilder id(String id) {
        if (id != null && !id.trim().isEmpty()) { // ID는 null이나 공백이 아니어야 유효
            this.workingCharPr.id(id);
        }
        // id가 null이거나 비어있으면 원본/기존 ID 유지 (변경 안 함)
        return this;
    }

    public CharPrBuilder height(String heightStr) {
        if (heightStr != null && !heightStr.trim().isEmpty()) { // 유효한 문자열일 때만 처리
            try {
                String numericPart = heightStr.replaceAll("[^0-9]", "");
                if (!numericPart.isEmpty()) {
                    int heightValue = Integer.parseInt(numericPart);
                    if (heightValue > 0) {
                        this.workingCharPr.heightAnd(heightValue);
                    } else {
                        System.err.println("CharPrBuilder: 높이 값은 0보다 커야 합니다 - " + heightStr);
                        // 유효하지 않은 값은 원본 유지 (변경 안 함)
                    }
                } else {
                     // 숫자 부분이 없으면 원본 유지 (변경 안 함)
                }
            } catch (NumberFormatException e) {
                System.err.println("CharPrBuilder: 잘못된 높이 값 형식입니다 - " + heightStr);
                // 예외 발생 시 원본 유지 (변경 안 함)
            }
        }
        // heightStr이 null이거나 비어있으면 원본 유지 (변경 안 함)
        return this;
    }

    public CharPrBuilder height(int heightValue) { 
        if (heightValue > 0) {
            this.workingCharPr.heightAnd(heightValue);
        } else {
            System.err.println("CharPrBuilder: 높이 값은 0보다 커야 합니다 - " + heightValue);
            // 유효하지 않은 값은 원본 유지 (변경 안 함)
        }
        return this;
    }

    // textColor와 shadeColor는 null로 설정하여 "색 없음"을 표현할 수 있으므로,
    // null이 들어오면 그대로 설정하도록 유지 (hwpxlib의 CharPr.textColor(null)이 이를 지원한다고 가정)
    // 만약 null을 "변경 안 함"으로 하려면 if (textColorStr != null) 로 감싸야 함.
    public CharPrBuilder textColor(String textColorStr) {
        this.workingCharPr.textColorAnd(textColorStr); 
        return this;
    }

    public CharPrBuilder shadeColor(String shadeColorStr) {
        this.workingCharPr.shadeColorAnd(shadeColorStr);
        return this;
    }
    
    // Boolean 속성들은 명시적으로 true/false가 주어지면 변경
    // 문자열 버전은 null 또는 빈 문자열이면 변경 안 함 (원본 유지)
    public CharPrBuilder useFontSpace(String useFontSpaceStr) {
        if (useFontSpaceStr != null && !useFontSpaceStr.trim().isEmpty()) {
            this.workingCharPr.useFontSpaceAnd("true".equalsIgnoreCase(useFontSpaceStr));
        }
        return this;
    }

    public CharPrBuilder useFontSpace(boolean useFontSpaceValue) {
        this.workingCharPr.useFontSpaceAnd(useFontSpaceValue);
        return this;
    }

    public CharPrBuilder useKerning(String useKerningStr) {
        if (useKerningStr != null && !useKerningStr.trim().isEmpty()) {
            this.workingCharPr.useKerningAnd("true".equalsIgnoreCase(useKerningStr));
        }
        return this;
    }

    public CharPrBuilder useKerning(boolean useKerningValue) {
        this.workingCharPr.useKerningAnd(useKerningValue);
        return this;
    }
    
    public CharPrBuilder bold(String boldStr) {
        if (boldStr != null && !boldStr.trim().isEmpty()) {
            boolean isBold = "true".equalsIgnoreCase(boldStr);
            if (isBold) {
                if (this.workingCharPr.bold() == null) {
                    this.workingCharPr.createBold();
                }
            } else {
                this.workingCharPr.removeBold(); 
            }
        }
        // boldStr이 null이거나 비어있으면 원본 유지
        return this;
    }

    public CharPrBuilder bold(boolean boldValue) {
        if (boldValue) {
            if (this.workingCharPr.bold() == null) {
                this.workingCharPr.createBold();
            }
        } else {
            this.workingCharPr.removeBold();
        }
        return this;
    }

    public CharPrBuilder italic(String italicStr) {
        if (italicStr != null && !italicStr.trim().isEmpty()) {
            boolean isItalic = "true".equalsIgnoreCase(italicStr);
            if (isItalic) {
                if (this.workingCharPr.italic() == null) { 
                    this.workingCharPr.createItalic();
                }
            } else {
                this.workingCharPr.removeItalic(); 
            }
        }
        // italicStr이 null이거나 비어있으면 원본 유지
        return this;
    }

    public CharPrBuilder italic(boolean italicValue) {
        if (italicValue) {
            if (this.workingCharPr.italic() == null) {
                this.workingCharPr.createItalic();
            }
        } else {
            this.workingCharPr.removeItalic();
        }
        return this;
    }

    // TODO: 밑줄, 글꼴 ID 참조 등 다른 속성 설정 메서드들 추가

    public CharPr build() {
        // ID가 여전히 설정되지 않았거나 비정상적이라면 최종적으로 한번 더 보장.
        if (this.workingCharPr.id() == null || this.workingCharPr.id().trim().isEmpty()) {
            this.workingCharPr.id("charPr_final_" + System.nanoTime());
        }
        return this.workingCharPr;
    }

    /**
     * 원본 CharPr을 기반으로 Map의 내용으로 속성을 덮어쓰는 빌더를 생성하고 반환합니다.
     * @param originalCharPr 복제할 원본 CharPr 객체. null이면 새로운 CharPr 객체로 시작합니다.
     * @param attributesToUpdate 덮어쓸 속성들이 담긴 Map.
     * @return 속성이 적용된 CharPrBuilder 인스턴스
     */
    public static CharPrBuilder fromMap(CharPr originalCharPr, Map<String, Object> attributesToUpdate) {
        CharPrBuilder builder = new CharPrBuilder(originalCharPr);
        if (attributesToUpdate == null) {
            return builder;
        }

        // 맵의 값이 null이면 해당 속성 변경 시도 안 함 (원본 값 보존 위주)
        // 단, textColor, shadeColor는 명시적 null로 색 없음을 표현할 수 있으므로 fromMap에서도 null 전달 허용.
        // (만약 fromMap에서 null을 무시하려면 각 builder.xxx 호출 전에 attributesToUpdate.get(key) != null 체크 필요)

        if (attributesToUpdate.containsKey("id")) {
            // ID는 null이거나 비면 설정하지 않는 것이 일반적이므로, builder.id() 내부 로직에 맡김
            builder.id(Objects.toString(attributesToUpdate.get("id"), null));
        }
        
        if (attributesToUpdate.containsKey("height")) {
            Object hVal = attributesToUpdate.get("height");
            if (hVal instanceof String) builder.height((String) hVal);
            else if (hVal instanceof Number) builder.height(((Number) hVal).intValue());
            // hVal이 null이면 builder.height((String)null) 등이 호출되나, 수정된 setter는 null/빈문자열 무시
        }
        if (attributesToUpdate.containsKey("textColor")) {
            // textColor는 null 설정이 의미 있을 수 있음 (색상 제거)
            builder.textColor(Objects.toString(attributesToUpdate.get("textColor"), null));
        }
        if (attributesToUpdate.containsKey("shadeColor")) {
            // shadeColor도 null 설정이 의미 있을 수 있음
            builder.shadeColor(Objects.toString(attributesToUpdate.get("shadeColor"), null));
        }

        if (attributesToUpdate.containsKey("useFontSpace")) {
            Object ufsVal = attributesToUpdate.get("useFontSpace");
            if (ufsVal instanceof String) builder.useFontSpace((String) ufsVal);
            else if (ufsVal instanceof Boolean) builder.useFontSpace((Boolean) ufsVal);
        }
        if (attributesToUpdate.containsKey("useKerning")) {
             Object ukVal = attributesToUpdate.get("useKerning");
            if (ukVal instanceof String) builder.useKerning((String) ukVal);
            else if (ukVal instanceof Boolean) builder.useKerning((Boolean) ukVal);
        }

        if (attributesToUpdate.containsKey("bold")) {
            Object bVal = attributesToUpdate.get("bold");
            if (bVal instanceof String) builder.bold((String) bVal);
            else if (bVal instanceof Boolean) builder.bold((Boolean) bVal);
        }
        if (attributesToUpdate.containsKey("italic")) {
            Object iVal = attributesToUpdate.get("italic");
            if (iVal instanceof String) builder.italic((String) iVal);
            else if (iVal instanceof Boolean) builder.italic((Boolean) iVal);
        }
        
        // TODO: 다른 속성들도 map에서 꺼내서 설정 (underline, fontIdRef 등)
        return builder;
    }
} 