package com.hotbros.sejong.style;

import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.util.IdGenerator;
import com.hotbros.sejong.font.FontRegistry;
import com.hotbros.sejong.bullet.BulletRegistry;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.LineSpacing;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.SymMarkSort;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.UnderlineType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType3;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.CharShadowType;
import kr.dogfoot.hwpxlib.object.common.compatibility.Switch;
import kr.dogfoot.hwpxlib.object.common.compatibility.Case;
import kr.dogfoot.hwpxlib.object.common.compatibility.Default;
import kr.dogfoot.hwpxlib.object.common.HWPXObject;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.ParaMargin;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;


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
    private final BulletRegistry bulletRegistry;

    public StylePreset(HWPXFile hwpxFile, IdGenerator idGenerator, FontRegistry fontRegistry, BulletRegistry bulletRegistry) {
        this.hwpxFile = hwpxFile;
        this.idGenerator = idGenerator;
        this.fontRegistry = fontRegistry;
        this.bulletRegistry = bulletRegistry;
    }


    /**
     * case/default 모두에 줄간격(LineSpacing) 값을 일괄 적용
     */
    public static void setLineSpacingBoth(ParaPr paraPr, int value) {
        if (paraPr == null || paraPr.switchList() == null || paraPr.switchList().isEmpty()) return;
        Switch sw = (Switch) paraPr.switchList().get(paraPr.switchList().size() - 1);
        if (sw == null) return;
        // case
        for (Case c : sw.caseObjects()) {
            if ("http://www.hancom.co.kr/hwpml/2016/HwpUnitChar".equals(c.requiredNamespace())) {
                for (HWPXObject child : c.children()) {
                    if (child instanceof LineSpacing) {
                        ((LineSpacing) child).value(value);
                    }
                }
            }
        }
        // default
        Default def = sw.defaultObject();
        if (def != null) {
            for (HWPXObject child : def.children()) {
                if (child instanceof LineSpacing) {
                    ((LineSpacing) child).value(value);
                }
            }
        }
    }

    /**
     * case/default 모두에 들여쓰기(intent) 값을 일괄 적용
     */
    public static void setIntentBoth(ParaPr paraPr, int value) {
        if (paraPr == null || paraPr.switchList() == null || paraPr.switchList().isEmpty()) return;
        Switch sw = (Switch) paraPr.switchList().get(paraPr.switchList().size() - 1);
        if (sw == null) return;
        // case
        for (Case c : sw.caseObjects()) {
            if ("http://www.hancom.co.kr/hwpml/2016/HwpUnitChar".equals(c.requiredNamespace())) {
                for (HWPXObject child : c.children()) {
                    if (child instanceof ParaMargin) {
                        ParaMargin margin = (ParaMargin) child;
                        if (margin.intent() != null) {
                            margin.intent().value(value);
                        }
                    }
                }
            }
        }
        // default
        Default def = sw.defaultObject();
        if (def != null) {
            for (HWPXObject child : def.children()) {
                if (child instanceof ParaMargin) {
                    ParaMargin margin = (ParaMargin) child;
                    if (margin.intent() != null) {
                        margin.intent().value(value*2);
                    }
                }
            }
        }
    }

    // ===== 제목 프리셋 =====
    public StyleBlock titlePreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, TITLE_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("HY헤드라인M").id();
        System.out.println("HY헤드라인M fontId: " + fontId);
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(2000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        setLineSpacingBoth(paraPr, 130);

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 내용 프리셋 =====
    public StyleBlock bodyPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, BODY_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        System.out.println("맑은 고딕 fontId: " + fontId);
        charPr.fontRef().setAll(fontId);
        charPr.removeBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        setLineSpacingBoth(paraPr, 150);

        styleTag.name("내용");
        styleTag.engName("Body");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 개요 프리셋 (1~7) =====
    public StyleBlock heading1Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, BODY_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1400);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 150);
        setIntentBoth(paraPr, 3000);

        paraPr.heading().type(ParaHeadingType.BULLET);
        paraPr.heading().idRef(bulletRegistry.getBulletByName("개요1").id());
        paraPr.heading().level((byte) 0);

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    public StyleBlock heading2Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, BODY_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1400);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 150);
        setIntentBoth(paraPr, 3000);

        paraPr.heading().type(ParaHeadingType.BULLET);
        paraPr.heading().idRef(bulletRegistry.getBulletByName("개요2").id());
        paraPr.heading().level((byte) 0);

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    public StyleBlock heading3Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, HEADING3_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1200);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 160);
        setIntentBoth(paraPr, 4500);


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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // 본문 왼쪽정렬용 프리셋
    public StyleBlock bodyLeftPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, BODY_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        paraPr.align().horizontal(HorizontalAlign2.LEFT);

        styleTag.name("내용 왼쪽정렬");
        styleTag.engName("BodyLeft");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // 본문 가운데정렬용 프리셋
    public StyleBlock bodyCenterPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, BODY_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        paraPr.align().horizontal(HorizontalAlign2.CENTER);

        styleTag.name("내용 가운데정렬");
        styleTag.engName("BodyCenter");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return new StyleBlock(charPr, paraPr, styleTag);
    }


    // ===== 표 프리셋 =====
    public StyleBlock tableHeaderPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, TABLE_HEADER_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        setLineSpacingBoth(paraPr, 160);

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    public StyleBlock tableCellPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, TABLE_CELL_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.removeBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 160);

        styleTag.name("표 내용");
        styleTag.engName("TableCell");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 제목 테이블 번호 =====
    public StyleBlock titleTableNumberPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, "0").clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1600);
        charPr.textColor("#000000");
        charPr.shadeColor("none");
        charPr.useFontSpace(false);
        charPr.useKerning(false);
        charPr.symMark(SymMarkSort.NONE);
        charPr.borderFillIDRef("1");
        // ratio, spacing, relSz, offset 등은 원본 charPr id="0"의 복제본을 따름
        charPr.underline()
                .typeAnd(UnderlineType.NONE)
                .shapeAnd(LineType3.SOLID)
                .color("#000000");

        charPr.strikeout()
                .shapeAnd(LineType2.NONE)
                .color("#000000");

        charPr.outline().type(LineType1.NONE);

        charPr.shadow()
                .typeAnd(CharShadowType.NONE)
                .colorAnd("#C0C0C0")
                .offsetXAnd((short) 10)
                .offsetY((short) 10);
        charPr.createBold();

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        setLineSpacingBoth(paraPr, 160);

        styleTag.name("제목 테이블 번호");
        styleTag.engName("TitleTableNumber");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 제목 테이블 내용 =====
    public StyleBlock titleTableContentPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(hwpxFile, "0").clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(hwpxFile, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(hwpxFile, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("HY헤드라인M").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1600);
        charPr.textColor("#000000");
        charPr.shadeColor("none");
        charPr.useFontSpace(false);
        charPr.useKerning(false);
        
        charPr.symMark(SymMarkSort.NONE);
        charPr.borderFillIDRef("1");
        // ratio, spacing, relSz, offset 등은 원본 charPr id="0"의 복제본을 따름
        charPr.underline()
        .typeAnd(UnderlineType.NONE)
        .shapeAnd(LineType3.SOLID)
        .color("#000000");
        
        charPr.strikeout()
                .shapeAnd(LineType2.NONE)
                .color("#000000");

        charPr.outline().type(LineType1.NONE);

        charPr.shadow()
                .typeAnd(CharShadowType.NONE)
                .colorAnd("#C0C0C0")
                .offsetXAnd((short) 10)
                .offsetY((short) 10);

        paraPr.align().horizontal(HorizontalAlign2.JUSTIFY);
        setLineSpacingBoth(paraPr, 160);

        styleTag.name("제목 테이블 내용");
        styleTag.engName("TitleTableContent");

        String charPrId = String.valueOf(idGenerator.nextCharPrId());
        String paraPrId = String.valueOf(idGenerator.nextParaPrId());
        String styleId = String.valueOf(idGenerator.nextStyleId());

        charPr.id(charPrId);
        paraPr.id(paraPrId);
        styleTag.id(styleId);

        styleTag.charPrIDRef(charPrId);
        styleTag.paraPrIDRef(paraPrId);

        return new StyleBlock(charPr, paraPr, styleTag);
    }
}
