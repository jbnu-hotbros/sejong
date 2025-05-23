package com.hotbros.sejong.builder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import com.hotbros.sejong.dto.CharPrAttributes;


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

    public CharPrBuilder height(Integer heightValue) { 
        if (heightValue == null) {
            // null이면 아무것도 하지 않음
            return this;
        }
        
        if (heightValue > 0) {
            this.workingCharPr.heightAnd(heightValue);
        } else {
            System.err.println("CharPrBuilder: 높이 값은 0보다 커야 합니다 - " + heightValue);
            // 유효하지 않은 값은 원본 유지 (변경 안 함)
        }
        return this;
    }

    // textColor는 null로 설정하여 "색 없음"을 표현할 수 있으므로,
    // null이 들어오면 그대로 설정하도록 유지 (hwpxlib의 CharPr.textColor(null)이 이를 지원한다고 가정)
    public CharPrBuilder textColor(String textColorStr) {
        if (textColorStr != null) {
            this.workingCharPr.textColorAnd(textColorStr);
        }
        // null이면 기존 값 유지
        return this;
    }
    
    /**
     * 모든 언어(한글, 영문, 한자, 일본어, 기타, 기호, 사용자)에 대해 동일한 fontRef ID를 설정합니다.
     * 
     * @param fontRefId 설정할 fontRef ID 값
     * @return 현재 빌더 인스턴스
     */
    public CharPrBuilder fontRef(String fontRefId) {
        if (fontRefId == null || fontRefId.trim().isEmpty()) {
            // ID가 null이거나 비어있으면 fontRef 제거
            this.workingCharPr.removeFontRef();
            return this;
        }
        
        // fontRef가 없으면 생성
        if (this.workingCharPr.fontRef() == null) {
            this.workingCharPr.createFontRef();
        }
        
        // 모든 언어에 동일한 ID 설정
        this.workingCharPr.fontRef()
            .hangulAnd(fontRefId)
            .latinAnd(fontRefId)
            .hanjaAnd(fontRefId)
            .japaneseAnd(fontRefId)
            .otherAnd(fontRefId)
            .symbolAnd(fontRefId)
            .user(fontRefId);
            
        return this;
    }
    
    public CharPrBuilder bold(Boolean boldValue) {
        if (boldValue == null) {
            return this;
        }
        
        if (boldValue) {
            if (this.workingCharPr.bold() == null) {
                this.workingCharPr.createBold();
            }
        } else {
            this.workingCharPr.removeBold();
        }
        return this;
    }

    public CharPrBuilder italic(Boolean italicValue) {
        if (italicValue == null) {
            return this;
        }
        
        if (italicValue) {
            if (this.workingCharPr.italic() == null) {
                this.workingCharPr.createItalic();
            }
        } else {
            this.workingCharPr.removeItalic();
        }
        return this;
    }

    public CharPrBuilder underline(Boolean underlineValue) {
        if (underlineValue == null) {
            return this;
        }
        
        if (underlineValue) {
            if (this.workingCharPr.underline() == null) {
                this.workingCharPr.createUnderline();
            }
        } else {
            this.workingCharPr.removeUnderline();
        }
        return this;
    }

    public CharPrBuilder strikeout(Boolean strikeoutValue) {
        if (strikeoutValue == null) {
            return this;
        }
        
        if (strikeoutValue) {
            if (this.workingCharPr.strikeout() == null) {
                this.workingCharPr.createStrikeout();
            }
        } else {
            this.workingCharPr.removeStrikeout();
        }
        return this;
    }

    public CharPr build() {
        // ID가 설정되지 않았거나 비어있을 경우 예외 발생
        if (this.workingCharPr.id() == null || this.workingCharPr.id().trim().isEmpty()) {
            throw new IllegalStateException("CharPr ID must not be null or empty. Please set a valid ID using the id() method.");
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
    public static CharPrBuilder fromAttributes(CharPr originalCharPr, CharPrAttributes attributesToApply) {
        CharPrBuilder builder = new CharPrBuilder(originalCharPr);
        if (attributesToApply == null) {
            return builder;
        }
        
        // 빌더 메서드 호출
        builder.id(attributesToApply.getId())
               .textColor(attributesToApply.getTextColor())
               .height(attributesToApply.getFontSizeHwpUnit())
               .bold(attributesToApply.getBold())
               .italic(attributesToApply.getItalic())
               .underline(attributesToApply.getUnderline())
               .strikeout(attributesToApply.getStrikeout());
        
        // 폰트 참조 설정 (CharPrAttributes에 fontRefId가 있다면)
        if (attributesToApply.getFontRefId() != null) {
            builder.fontRef(attributesToApply.getFontRefId());
        }
        
        return builder;
    }
} 