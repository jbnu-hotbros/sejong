package com.hotbros.sejong.style;

import com.hotbros.sejong.style.request.StyleBuilderBlock;
import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.util.IdGenerator;
import com.hotbros.sejong.font.FontRegistry;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;

/**
 * HWPX에서 사용할 다양한 스타일 프리셋을 정의합니다.
 * 각 프리셋은 StyleBuilderBlock 인스턴스를 반환하며, build() 시 CharPr, ParaPr, Style,
 * fontRefId가 일관성 있게 생성됩니다.
 */
public class StylePreset {

    // ===== 스타일 ID 상수 =====
    private static final String TITLE_STYLE_ID = "0";
    private static final String BODY_STYLE_ID = "0";
    private static final String HEADING1_STYLE_ID = "2";
    private static final String HEADING2_STYLE_ID = "3";
    private static final String HEADING3_STYLE_ID = "4";
    private static final String HEADING4_STYLE_ID = "5";
    private static final String HEADING5_STYLE_ID = "6";
    private static final String HEADING6_STYLE_ID = "7";
    private static final String HEADING7_STYLE_ID = "8";
    private static final String TABLE_HEADER_STYLE_ID = "0";
    private static final String TABLE_CELL_STYLE_ID = "0";

    private final IdGenerator idGenerator;
    private final HWPXFile hwpxFile;
    private final FontRegistry fontRegistry;

    public StylePreset(HWPXFile hwpxFile, IdGenerator idGenerator, FontRegistry fontRegistry) {
        this.hwpxFile = hwpxFile;
        this.idGenerator = idGenerator;
        this.fontRegistry = fontRegistry;
    }

    // ===== 제목 프리셋 =====
    public Style titlePreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, TITLE_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(2000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        paraPr.lineSpacing().value(160);

        styleTag.name("제목");
        styleTag.engName("Title");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);
            

        return styleTag;
    }

    // ===== 본문 프리셋 =====
    public Style bodyPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, BODY_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.removeBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.JUSTIFY);
        paraPr.lineSpacing().value(160);

        styleTag.name("본문");
        styleTag.engName("Body");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    // ===== 개요 프리셋 (1~7) =====
    public Style heading1Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING1_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1600);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요1");
        styleTag.engName("Heading1");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style heading2Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING2_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1400);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요2");
        styleTag.engName("Heading2");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style heading3Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING3_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1200);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요3");
        styleTag.engName("Heading3");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style heading4Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING4_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1100);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요4");
        styleTag.engName("Heading4");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style heading5Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING5_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요5");
        styleTag.engName("Heading5");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style heading6Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING6_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(900);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요6");
        styleTag.engName("Heading6");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style heading7Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING7_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(800);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("개요7");
        styleTag.engName("Heading7");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    // ===== 표 프리셋 =====
    public Style tableHeaderPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, TABLE_HEADER_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        paraPr.lineSpacing().value(160);

        styleTag.name("표 헤더");
        styleTag.engName("TableHeader");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

    public Style tableCellPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, TABLE_CELL_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.removeBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        paraPr.lineSpacing().value(160);

        styleTag.name("표 본문");
        styleTag.engName("TableCell");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return styleTag;
    }

}