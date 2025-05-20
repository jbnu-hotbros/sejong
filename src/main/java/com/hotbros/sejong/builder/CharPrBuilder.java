package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bold;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Italic;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.FontReference; // Not used yet
// 필요한 다른 Enum 타입 import (예: 밑줄 종류 등)
// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.UnderlineSort;

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


    public CharPr build() {
        // ID가 여전히 설정되지 않았거나 비정상적이라면 최종적으로 한번 더 보장.
        if (this.workingCharPr.id() == null || this.workingCharPr.id().trim().isEmpty()) {
            this.workingCharPr.id("charPr_final_" + System.nanoTime());
        }
        return this.workingCharPr;
    }

    /**
     * 원본 CharPr을 기반으로 CharPrAttributes DTO의 내용으로 속성을 덮어쓰는 빌더를 생성하고 반환합니다.
     * fromMap 메서드는 이 메서드로 대체됩니다.
     * @param originalCharPr 복제할 원본 CharPr 객체.
     * @param attributesToApply 적용할 속성이 담긴 CharPrAttributes DTO 객체.
     * @return 속성이 적용된 CharPrBuilder 인스턴스
     */
    public static CharPrBuilder fromAttributes(CharPr originalCharPr, com.hotbros.sejong.dto.CharPrAttributes attributesToApply) {
        CharPrBuilder builder = new CharPrBuilder(originalCharPr);
        if (attributesToApply == null) {
            return builder;
        }

        if (attributesToApply.getId() != null) {
            builder.id(attributesToApply.getId());
        }
        
        if (attributesToApply.getFontSizeHwpUnit() != null) {
            builder.height(attributesToApply.getFontSizeHwpUnit().intValue());
        }
        
        if (attributesToApply.getTextColor() != null) { // textColor는 null일 수 있음 (색 없음)
            builder.textColor(attributesToApply.getTextColor());
        }
        
        // shadeColor는 현재 CharPrAttributes DTO에 없음. 필요시 DTO에 추가 후 여기서 처리.
        // if (attributesToApply.getShadeColor() != null) {
        //     builder.shadeColor(attributesToApply.getShadeColor());
        // }

        if (attributesToApply.getBold() != null) {
            builder.bold(attributesToApply.getBold());
        }
        if (attributesToApply.getItalic() != null) {
            builder.italic(attributesToApply.getItalic());
        }

        


        return builder;
    }
} 