package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.common.ObjectList;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.*;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.*;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;

/**
 * HWPX 문서의 참조 목록(RefList)을 관리하는 클래스입니다.
 * 글꼴, 테두리/배경, 글자 모양, 탭, 번호 매기기, 문단 모양, 스타일 등을 정의합니다.
 */
public class RefListManager {
    private final HWPXFile hwpxFile;
    
    /**
     * RefListManager 생성자
     * 
     * @param hwpxFile 관리할 HWPXFile 객체
     */
    public RefListManager(HWPXFile hwpxFile) {
        this.hwpxFile = hwpxFile;
    }
    
    /**
     * HWPX 파일의 참조 목록을 초기화합니다.
     */
    public void initialize() {
        hwpxFile.headerXMLFile().createRefList();
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 1. 글꼴 정의 (가장 기본)
        refList.createFontfaces();
        initializeFontfaces();
        
        // // 2. 테두리/배경 정의
        // refList.createBorderFills();
        // initializeBorderFills();
        
        // // 3. 글자 모양 정의
        // refList.createCharProperties();
        // initializeCharProperties();
        
        // // 4. 탭 정의
        // refList.createTabProperties();
        // initializeTabProperties();
        
        // // 5. 번호 매기기 정의
        // refList.createNumberings();
        // initializeNumberings();
        
        // // 6. 문단 모양 정의
        // refList.createParaProperties();
        // initializeParaProperties();
        
        // // 7. 스타일 정의 (최종)
        // refList.createStyles();
        // initializeStyles();
    }
    
    /**
     * 글꼴 정의를 초기화합니다.
     */
    private void initializeFontfaces() {
        Fontfaces fontfaces = hwpxFile.headerXMLFile().refList().fontfaces();
        
        // 언어별 글꼴 정의
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.HANGUL));
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.LATIN));
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.HANJA));
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.JAPANESE));
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.OTHER));
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.SYMBOL));
        addFonts(fontfaces.addNewFontface().langAnd(LanguageType.USER));
    }
    
    /**
     * 언어별 기본 폰트들을 추가합니다.
     */
    private void addFonts(Fontface fontface) {
        // 첫 번째 폰트 추가 (기본 고딕 계열)
        Font font1 = fontface.addNewFont();
        font1.idAnd("0")
            .faceAnd("맑은 고딕")
            .typeAnd(FontType.TTF)
            .isEmbeddedAnd(false);
        
        font1.createTypeInfo();
        font1.typeInfo()
            .familyTypeAnd(FontFamilyType.FCAT_GOTHIC)
            .weightAnd(8)
            .proportionAnd(4)
            .contrastAnd(0)
            .strokeVariationAnd(1)
            .armStyleAnd(true)
            .letterformAnd(true)
            .midlineAnd(1)
            .xHeight(1);
            
        // 두 번째 폰트 추가 (기본 명조 계열)
        Font font2 = fontface.addNewFont();
        font2.idAnd("1")
            .faceAnd("함초롬바탕")
            .typeAnd(FontType.TTF)
            .isEmbeddedAnd(false);
            
        font2.createTypeInfo();
        font2.typeInfo()
            .familyTypeAnd(FontFamilyType.FCAT_GOTHIC)
            .weightAnd(8)
            .proportionAnd(4)
            .contrastAnd(0)
            .strokeVariationAnd(1)
            .armStyleAnd(true)
            .letterformAnd(true)
            .midlineAnd(1)
            .xHeight(1);
    }
    
    /**
     * 테두리/배경 정의를 초기화합니다.
     */
    // private void initializeBorderFills() {
    //     // 기본 테두리/배경 추가
    //     ObjectList<BorderFill> borderFills = hwpxFile.headerXMLFile().refList().borderFills();
    //     BorderFill borderFill = borderFills.addNew()
    //             .idAnd("1")
    //             .threeDAnd(false)
    //             .shadowAnd(false)
    //             .centerLineAnd(CenterLineSort.NONE)
    //             .breakCellSeparateLine(false);
        
    //     // 채우기 정보 설정
    //     borderFill.createFillBrush();
    //     borderFill.fillBrush().createWinBrush();
    //     borderFill.fillBrush().winBrush()
    //             .faceColorAnd("none")
    //             .hatchColorAnd("#000000")
    //             .alpha(0f);
    // }
    
    // /**
    //  * 글자 모양 정의를 초기화합니다.
    //  */
    // private void initializeCharProperties() {
    //     // 기본 글자 모양
    //     CharPr charPr = hwpxFile.headerXMLFile().refList().charProperties().addNew()
    //             .idAnd("0")
    //             .heightAnd(1000)  // 10pt (1pt = 100)
    //             .textColorAnd("#000000")
    //             .shadeColorAnd("none")
    //             .useFontSpaceAnd(false)
    //             .useKerningAnd(false)
    //             .symMarkAnd(SymMarkSort.NONE);
        
    //     // 폰트 참조 설정
    //     charPr.createFontRef();
    //     charPr.fontRef().set("0", "0", "0", "0", "0", "0", "0");
        
    //     // 비율 설정
    //     charPr.createRatio();
    //     charPr.ratio().set((short)100, (short)100, (short)100, (short)100, (short)100, (short)100, (short)100);
        
    //     // 자간 설정
    //     charPr.createSpacing();
    //     charPr.spacing().set((short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0);
        
    //     // 상대 크기 설정
    //     charPr.createRelSz();
    //     charPr.relSz().set((short)100, (short)100, (short)100, (short)100, (short)100, (short)100, (short)100);
        
    //     // 오프셋 설정
    //     charPr.createOffset();
    //     charPr.offset().set((short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0);
        
    //     // 밑줄 없음 설정
    //     charPr.createUnderline();
    //     charPr.underline()
    //             .typeAnd(UnderlineType.NONE)
    //             .shapeAnd(LineType3.SOLID)
    //             .color("#000000");
    // }
    
    // /**
    //  * 탭 정의를 초기화합니다.
    //  */
    // private void initializeTabProperties() {
    //     // 기본 탭 정의
    //     hwpxFile.headerXMLFile().refList().tabProperties().addNew()
    //             .idAnd("0")
    //             .autoTabLeftAnd(false)
    //             .autoTabRight(false);
        
    //     // 자동 왼쪽 탭 정의
    //     hwpxFile.headerXMLFile().refList().tabProperties().addNew()
    //             .idAnd("1")
    //             .autoTabLeftAnd(true)
    //             .autoTabRight(false);
    // }
    
    // /**
    //  * 번호 매기기 정의를 초기화합니다.
    //  */
    // private void initializeNumberings() {
    //     // 기본 번호 매기기
    //     Numbering numbering = hwpxFile.headerXMLFile().refList().numberings().addNew()
    //             .idAnd("1")
    //             .startAnd(0);
        
    //     // 1단계 번호 (숫자 + 점)
    //     numbering.addNewParaHead()
    //             .startAnd(1)
    //             .levelAnd((byte)1)
    //             .alignAnd(HorizontalAlign1.LEFT)
    //             .useInstWidthAnd(true)
    //             .autoIndentAnd(true)
    //             .widthAdjustAnd(0)
    //             .textOffsetTypeAnd(ValueUnit1.PERCENT)
    //             .textOffsetAnd(50)
    //             .numFormatAnd(NumberType1.DIGIT)
    //             .charPrIDRefAnd("0")
    //             .checkableAnd(false)
    //             .text("^1.");
        
    //     // 2단계 번호 (한글 + 점)
    //     numbering.addNewParaHead()
    //             .startAnd(1)
    //             .levelAnd((byte)2)
    //             .alignAnd(HorizontalAlign1.LEFT)
    //             .useInstWidthAnd(true)
    //             .autoIndentAnd(true)
    //             .widthAdjustAnd(0)
    //             .textOffsetTypeAnd(ValueUnit1.PERCENT)
    //             .textOffsetAnd(50)
    //             .numFormatAnd(NumberType1.HANGUL_SYLLABLE)
    //             .charPrIDRefAnd("0")
    //             .checkableAnd(false)
    //             .text("^2.");
    // }
    
    // /**
    //  * 문단 모양 정의를 초기화합니다.
    //  */
    // private void initializeParaProperties() {
    //     // 기본 문단 모양
    //     ObjectList<ParaPr> paraProperties = hwpxFile.headerXMLFile().refList().paraProperties();
    //     ParaPr paraPr = paraProperties.addNew()
    //             .idAnd("0")
    //             .tabPrIDRefAnd("0")
    //             .condenseAnd((byte)0)
    //             .fontLineHeightAnd(false)
    //             .snapToGridAnd(true)
    //             .suppressLineNumbersAnd(false)
    //             .checked(false);
        
    //     // 정렬 설정 (왼쪽 정렬)
    //     paraPr.createAlign();
    //     paraPr.align()
    //             .horizontalAnd(HorizontalAlign2.LEFT)
    //             .vertical(VerticalAlign1.BASELINE);
        
    //     // 여백 설정
    //     paraPr.createMargin();
    //     paraPr.margin()
    //             .interval(0)
    //             .leftAnd(0)
    //             .rightAnd(0)
    //             .prev(0)
    //             .next(0);
        
    //     // 들여쓰기 설정
    //     paraPr.createLineSpacing();
    //     paraPr.lineSpacing()
    //             .typeAnd(LineSpacingType.PERCENT)
    //             .height(160)
    //             .valueAnd(60);
    // }
    
    // /**
    //  * 스타일 정의를 초기화합니다.
    //  */
    // private void initializeStyles() {
    //     // 기본 스타일 (바탕글)
    //     hwpxFile.headerXMLFile().refList().styles().addNew()
    //             .idAnd("0")
    //             .typeAnd(StyleType.PARA)
    //             .nameAnd("바탕글")
    //             .engNameAnd("Normal")
    //             .paraPrIDRefAnd("0")   // 문단 모양 참조
    //             .charPrIDRefAnd("0")   // 글자 모양 참조
    //             .nextStyleIDRefAnd("0")
    //             .langIDAnd("1042")
    //             .lockForm(false);
    // }
} 