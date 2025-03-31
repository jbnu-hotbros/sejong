package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import java.util.function.Consumer;

/**
 * 스타일 빌더 클래스 - 하나의 스타일을 구성하기 위한 빌더 패턴 구현
 */
public class StyleBuilder {
    private final HWPXFile hwpxFile;
    private final RefList refList;
    private final String styleName;
    private final String styleEngName;
    private Style baseStyle;
    
    private Consumer<CharPr> charPrModifications;
    private Consumer<ParaPr> paraPrModifications;
    // 필요에 따라 Numbering, Bullet 등의 수정 함수 추가 가능
    
    StyleBuilder(HWPXFile hwpxFile, String styleName, String styleEngName) {
        this.hwpxFile = hwpxFile;
        this.refList = hwpxFile.headerXMLFile().refList();
        this.styleName = styleName;
        this.styleEngName = styleEngName;
        
        if (refList == null) {
            throw new IllegalArgumentException("RefList가 null입니다");
        }
        
        if (refList.styles() == null || refList.styles().count() <= 0) {
            throw new IllegalArgumentException("Styles가 null이거나 비어 있습니다");
        }
        
        this.baseStyle = refList.styles().get(0);
    }
    
    /**
     * StyleBuilder 인스턴스를 생성합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     * @param styleName 스타일 이름
     * @param styleEngName 스타일 영문 이름
     * @return 스타일 빌더 객체
     */
    public static StyleBuilder create(HWPXFile hwpxFile, String styleName, String styleEngName) {
        return new StyleBuilder(hwpxFile, styleName, styleEngName);
    }
    
    /**
     * 기본 스타일을 지정합니다.
     * @param baseStyle 기본 스타일 객체
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder fromBaseStyle(Style baseStyle) {
        if (baseStyle == null) {
            throw new IllegalArgumentException("기본 스타일이 null입니다");
        }
        this.baseStyle = baseStyle;
        return this;
    }
    
    /**
     * 글자 모양 설정
     * @param modifications 글자 모양 수정 함수
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withCharPr(Consumer<CharPr> modifications) {
        this.charPrModifications = modifications;
        return this;
    }
    
    /**
     * 문단 모양 설정
     * @param modifications 문단 모양 수정 함수
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withParaPr(Consumer<ParaPr> modifications) {
        this.paraPrModifications = modifications;
        return this;
    }
    
    /**
     * 스타일 구성 완료 및 생성
     * @return 생성된 스타일 객체
     */
    public Style build() {
        String baseCharPrIDRef = baseStyle.charPrIDRef();
        String baseParaPrIDRef = baseStyle.paraPrIDRef();
        
        // 글자 모양 생성 (설정되어 있는 경우)
        String charPrIDRef = baseCharPrIDRef;
        if (charPrModifications != null) {
            charPrIDRef = createNewCharPr(refList, baseCharPrIDRef, charPrModifications);
        }
        
        // 문단 모양 생성 (설정되어 있는 경우)
        String paraPrIDRef = baseParaPrIDRef;
        if (paraPrModifications != null) {
            paraPrIDRef = createNewParaPr(refList, baseParaPrIDRef, paraPrModifications);
        }
        
        // 새 스타일 ID 생성
        int maxStyleID = StyleUtils.getMaxStyleID(refList);
        String newStyleID = String.valueOf(maxStyleID + 1);
        
        // 스타일 객체 생성 및 설정
        var newStyle = baseStyle.clone();
        newStyle.idAnd(newStyleID)
                .nameAnd(styleName)
                .engNameAnd(styleEngName)
                .charPrIDRefAnd(charPrIDRef)
                .paraPrIDRefAnd(paraPrIDRef);
        
        // 스타일 추가
        refList.styles().add(newStyle);
        
        // 생성된 스타일 복제본 반환 (방어적 복사)
        return newStyle.clone();
    }

    /**
     * 새로운 글자 모양을 생성합니다.
     */
    private String createNewCharPr(RefList refList, String baseCharPrIDRef, 
            Consumer<CharPr> modifications) {
        if (baseCharPrIDRef == null) {
            throw new IllegalArgumentException("기본 글자 모양 ID가 null입니다");
        }
        if (refList.charProperties() == null) {
            throw new IllegalArgumentException("RefList.charProperties가 null입니다");
        }

        // 최대 ID 찾기
        int maxID = StyleUtils.getMaxCharPrID(refList);
        String newCharPrID = String.valueOf(maxID + 1);

        for (var charPr : refList.charProperties().items()) {
            if (charPr.id().equals(baseCharPrIDRef)) {
                var newCharPr = charPr.clone();
                newCharPr.id(newCharPrID);
                modifications.accept(newCharPr);
                refList.charProperties().add(newCharPr);
                return newCharPrID;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseCharPrIDRef + "인 기본 글자 모양을 찾을 수 없습니다");
    }

    /**
     * 새로운 문단 모양을 생성합니다.
     */
    private String createNewParaPr(RefList refList, String baseParaPrIDRef,
            Consumer<ParaPr> modifications) {
        if (baseParaPrIDRef == null) {
            throw new IllegalArgumentException("기본 문단 모양 ID가 null입니다");
        }
        if (refList.paraProperties() == null) {
            throw new IllegalArgumentException("RefList.paraProperties가 null입니다");
        }

        // 최대 ID 찾기
        int maxID = StyleUtils.getMaxParaPrID(refList);
        String newParaPrID = String.valueOf(maxID + 1);

        for (var paraPr : refList.paraProperties().items()) {
            if (paraPr.id().equals(baseParaPrIDRef)) {
                var newParaPr = paraPr.clone();
                newParaPr.id(newParaPrID);
                modifications.accept(newParaPr);
                refList.paraProperties().add(newParaPr);
                return newParaPrID;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseParaPrIDRef + "인 기본 문단 모양을 찾을 수 없습니다");
    }
} 