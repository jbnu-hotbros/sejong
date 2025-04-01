package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
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
    private Bullet bullet; // 불렛 객체를 직접 저장 (null이면 불렛 미사용)
    
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
     * 기본 불렛 설정 추가
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withBullet() {
        // 필요한 경우 불렛 목록 생성
        if (hwpxFile.headerXMLFile().refList().bullets() == null) {
            hwpxFile.headerXMLFile().refList().createBullets();
        }
        
        // 새 불렛 ID 생성
        int maxBulletId = StyleUtils.getMaxID(hwpxFile.headerXMLFile().refList().bullets().items(), item -> item.id());
        String bulletId = String.valueOf(maxBulletId + 1);
        
        // 불렛 객체 생성 및 설정
        this.bullet = hwpxFile.headerXMLFile().refList().bullets().addNew()
                .idAnd(bulletId)
                ._charAnd("●")
                .useImageAnd(false);
                
        return this;
    }
    
    /**
     * 커스텀 불렛 문자로 불렛 설정 추가
     * @param bulletChar 불렛 문자
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withBullet(String bulletChar) {
        withBullet(); // 기본 불렛 생성
        this.bullet._charAnd(bulletChar); // 문자만 변경
        return this;
    }
    
    /**
     * 커스텀 불렛 설정 추가 (문자와 체크 문자 지정)
     * @param bulletChar 불렛 문자
     * @param checkedChar 체크 문자
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withBullet(String bulletChar, String checkedChar) {
        withBullet(); // 기본 불렛 생성
        this.bullet._charAnd(bulletChar)
                   .checkedCharAnd(checkedChar);
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
        if (paraPrModifications != null || bullet != null) {
            paraPrIDRef = createNewParaPrWithBullet(refList, baseParaPrIDRef);
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
     * 불렛을 포함한 새 문단 모양을 생성합니다.
     */
    private String createNewParaPrWithBullet(RefList refList, String baseParaPrIDRef) {
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
                
                // 1. 사용자 지정 수정 적용 (있는 경우)
                if (paraPrModifications != null) {
                    paraPrModifications.accept(newParaPr);
                }
                
                // 2. 불렛 설정 적용 (있는 경우)
                if (bullet != null) {
                    // heading이 없으면 생성
                    if (newParaPr.heading() == null) {
                        newParaPr.createHeading();
                    }
                    // 불렛 설정
                    newParaPr.heading().typeAnd(ParaHeadingType.BULLET);
                    newParaPr.heading().idRefAnd(bullet.id());
                    newParaPr.heading().level((byte) 1); // 기본 레벨
                }
                
                refList.paraProperties().add(newParaPr);
                return newParaPrID;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseParaPrIDRef + "인 기본 문단 모양을 찾을 수 없습니다");
    }
} 