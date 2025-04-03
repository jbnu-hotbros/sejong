package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import java.util.function.Consumer;

/**
 * 스타일 빌더 클래스 - 하나의 스타일을 구성하기 위한 빌더 패턴 구현
 * 빌더는 오직 스타일 객체의 생성만을 담당하며, HWPX 파일에 스타일 등록은 외부에서 처리해야 합니다.
 */
public class StyleBuilder {
    /**
     * 스타일 템플릿 생성을 위한 내부 참조용 빈 HWPX 파일.
     * 이 파일은 실제로 수정되지 않으며, 오직 참조와 클론 생성을 위한 용도로만 사용됩니다.
     */
    private static final HWPXFile TEMPLATE_FILE;
    
    // 정적 초기화 블록
    static {
        try {
            TEMPLATE_FILE = BlankFileMaker.make();
        } catch (Exception e) {
            throw new RuntimeException("템플릿 HWPX 파일 생성에 실패했습니다.", e);
        }
    }
    
    private final RefList refList;
    private final String styleName;
    private final String styleEngName;
    private Style baseStyle;
    
    private Consumer<CharPr> charPrModifications;
    private Consumer<ParaPr> paraPrModifications;
    private String bulletChar; // 불렛 문자
    private String checkedChar; // 체크 문자
    private boolean useBullet = false; // 불렛 사용 여부
    
    StyleBuilder(String styleName, String styleEngName) {
        this.refList = TEMPLATE_FILE.headerXMLFile().refList();
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
     * @param styleName 스타일 이름
     * @param styleEngName 스타일 영문 이름
     * @return 스타일 빌더 객체
     */
    public static StyleBuilder create(String styleName, String styleEngName) {
        return new StyleBuilder(styleName, styleEngName);
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
     * ID로 기본 스타일을 지정합니다.
     * 내부 템플릿 파일에서 해당 ID의 스타일을 찾아 기본 스타일로 설정합니다.
     * StyleConstants에 정의된 상수 사용 가능: StyleConstants.STYLE_ID_OUTLINE_1 등
     * @param styleId 스타일 ID
     * @return 현재 빌더 인스턴스
     * @throws IllegalArgumentException 스타일을 찾지 못한 경우
     */
    public StyleBuilder fromBaseStyleId(String styleId) {
        if (styleId == null) {
            throw new IllegalArgumentException("스타일 ID가 null입니다");
        }
        
        Style foundStyle = IdUtils.findStyleById(TEMPLATE_FILE.headerXMLFile().refList(), styleId);
        if (foundStyle == null) {
            throw new IllegalArgumentException("ID가 " + styleId + "인 스타일을 찾을 수 없습니다");
        }
        
        this.baseStyle = foundStyle;
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
        this.useBullet = true;
        this.bulletChar = "●";
        return this;
    }
    
    /**
     * 커스텀 불렛 문자로 불렛 설정 추가
     * @param bulletChar 불렛 문자
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withBullet(String bulletChar) {
        this.useBullet = true;
        this.bulletChar = bulletChar;
        return this;
    }
    
    /**
     * 커스텀 불렛 설정 추가 (문자와 체크 문자 지정)
     * @param bulletChar 불렛 문자
     * @param checkedChar 체크 문자
     * @return 현재 빌더 인스턴스
     */
    public StyleBuilder withBullet(String bulletChar, String checkedChar) {
        this.useBullet = true;
        this.bulletChar = bulletChar;
        this.checkedChar = checkedChar;
        return this;
    }
    
    /**
     * 스타일 객체만 생성하여 반환합니다.
     * 이 메소드는 StyleTemplate을 생성하고 Style 객체만 추출하여 반환합니다.
     * 스타일셋에서 사용하기 적합한 간소화된 메소드입니다.
     * 
     * @return 생성된 스타일 객체
     */
    public Style build() {
        // 스타일 템플릿 생성
        StyleTemplate template = buildTemplate();
        
        // 스타일 객체만 반환
        return template.getStyle();
    }
    
    /**
     * 스타일 템플릿을 생성합니다.
     * 스타일 객체와 함께 생성된 글자 모양, 문단 모양, 불렛 객체를 모두 포함한 템플릿을 반환합니다.
     * 이 메서드는 객체들을 생성만 하고 HWPX 파일에 등록하지 않습니다.
     * ID 할당은 등록 시점에 StyleService에서 수행합니다.
     * 
     * @return 스타일 템플릿 객체
     */
    public StyleTemplate buildTemplate() {
        String baseCharPrIDRef = baseStyle.charPrIDRef();
        String baseParaPrIDRef = baseStyle.paraPrIDRef();
        
        // 글자 모양 생성 (설정되어 있는 경우)
        CharPr newCharPr = null;
        String charPrIDRef = baseCharPrIDRef;
        if (charPrModifications != null) {
            // ID 없이 새 글자 모양 생성
            newCharPr = createNewCharPr(refList, baseCharPrIDRef, charPrModifications);
            charPrIDRef = "temp_charPr"; // 임시 참조 ID
        }
        
        // 불렛 생성 (필요한 경우)
        Bullet newBullet = null;
        if (useBullet) {
            newBullet = createBullet();
        }
        
        // 문단 모양 생성 (설정되어 있는 경우)
        ParaPr newParaPr = null;
        String paraPrIDRef = baseParaPrIDRef;
        if (paraPrModifications != null || useBullet) {
            // ID 없이 새 문단 모양 생성
            newParaPr = createNewParaPrWithBullet(refList, baseParaPrIDRef, newBullet);
            paraPrIDRef = "temp_paraPr"; // 임시 참조 ID
        }
        
        // 스타일 객체 생성 및 설정 (ID 없이)
        var newStyle = baseStyle.clone();
        newStyle.idAnd("temp_style") // 임시 ID
                .nameAnd(styleName)
                .engNameAnd(styleEngName)
                .charPrIDRefAnd(charPrIDRef)
                .paraPrIDRefAnd(paraPrIDRef);
        
        // StyleTemplate 객체 생성 및 반환
        return new StyleTemplate(newStyle, newCharPr, newParaPr, newBullet);
    }
    
    /**
     * 새로운 글자 모양을 생성합니다. ID는 임시값으로 설정됩니다.
     */
    private CharPr createNewCharPr(RefList refList, String baseCharPrIDRef, Consumer<CharPr> modifications) {
        if (baseCharPrIDRef == null) {
            throw new IllegalArgumentException("기본 글자 모양 ID가 null입니다");
        }
        if (refList.charProperties() == null) {
            throw new IllegalArgumentException("RefList.charProperties가 null입니다");
        }

        // 기본 글자 모양 찾기
        for (var charPr : refList.charProperties().items()) {
            if (charPr.id().equals(baseCharPrIDRef)) {
                var newCharPr = charPr.clone();
                newCharPr.id("temp_charPr"); // 임시 ID 설정
                
                // 사용자 지정 수정 적용
                modifications.accept(newCharPr);
                
                return newCharPr;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseCharPrIDRef + "인 기본 글자 모양을 찾을 수 없습니다");
    }
    
    private ParaPr createNewParaPrWithBullet(RefList refList, String baseParaPrIDRef, Bullet bullet) {
        if (baseParaPrIDRef == null) {
            throw new IllegalArgumentException("기본 문단 모양 ID가 null입니다");
        }
        if (refList.paraProperties() == null) {
            throw new IllegalArgumentException("RefList.paraProperties가 null입니다");
        }

        // 기본 문단 모양 찾기
        for (var paraPr : refList.paraProperties().items()) {
            if (paraPr.id().equals(baseParaPrIDRef)) {
                var newParaPr = paraPr.clone();
                newParaPr.id("temp_paraPr"); // 임시 ID 설정
                
                // 1. 사용자 지정 수정 적용 (있는 경우)
                if (paraPrModifications != null) {
                    paraPrModifications.accept(newParaPr);
                }
                
                // 2. 불렛 설정 적용 (있는 경우)
                if (useBullet && bullet != null) {
                    // heading이 없으면 생성
                    if (newParaPr.heading() == null) {
                        newParaPr.createHeading();
                    }
                    // 불렛 설정 (임시 ID 참조)
                    newParaPr.heading().typeAnd(ParaHeadingType.BULLET);
                    newParaPr.heading().idRefAnd(bullet.id());
                    newParaPr.heading().level((byte) 1); // 기본 레벨
                }
                
                return newParaPr;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseParaPrIDRef + "인 기본 문단 모양을 찾을 수 없습니다");
    }
    
    /**
     * 불렛 객체를 생성합니다. ID는 임시값으로 설정됩니다.
     */
    private Bullet createBullet() {
        Bullet bullet = new Bullet();
        
        // 임시 ID 설정
        bullet.idAnd("temp_bullet");
        
        // 불렛 문자 설정
        if (bulletChar != null) {
            bullet._charAnd(bulletChar);
        } else {
            bullet._charAnd("●"); // 기본 불렛 문자
        }
        
        // 체크 문자 설정 (있는 경우)
        if (checkedChar != null) {
            bullet.checkedCharAnd(checkedChar);
        }
        
        return bullet;
    }
} 