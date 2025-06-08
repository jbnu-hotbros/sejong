package com.hotbros.sejong.style;

import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.font.FontRegistry;
import com.hotbros.sejong.bullet.BulletRegistry;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
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
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;


/**
 * HWPX에서 사용할 다양한 스타일 프리셋을 정의합니다.
 * 각 프리셋은 StyleBuilderBlock 인스턴스를 반환하며, build() 시 CharPr, ParaPr, Style,
 * fontRefId가 일관성 있게 생성됩니다.
 */
public class StylePreset {

    // ===== 스타일 ID 상수 =====
    private static final String DEFAULT_STYLE_ID = "0";

    private final RefList refList;
    private final FontRegistry fontRegistry;
    private final BulletRegistry bulletRegistry;
    private final Theme theme;

    public StylePreset(RefList refList, FontRegistry fontRegistry, BulletRegistry bulletRegistry, Theme theme) {
        this.refList = refList;
        this.fontRegistry = fontRegistry;
        this.bulletRegistry = bulletRegistry;
        this.theme = theme;
    }


    /**
     * case/default 모두에 줄간격(LineSpacing) 값을 일괄 적용
     */
    private static void setLineSpacingBoth(ParaPr paraPr, int value) {
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
    private static void setMarginLeftBoth(ParaPr paraPr, int value) {
        if (paraPr == null || paraPr.switchList() == null || paraPr.switchList().isEmpty()) return;
        Switch sw = (Switch) paraPr.switchList().get(paraPr.switchList().size() - 1);
        if (sw == null) return;
        // case
        for (Case c : sw.caseObjects()) {
            if ("http://www.hancom.co.kr/hwpml/2016/HwpUnitChar".equals(c.requiredNamespace())) {
                for (HWPXObject child : c.children()) {
                    if (child instanceof ParaMargin) {
                        ParaMargin margin = (ParaMargin) child;
                        if (margin.left() != null) {
                            margin.left().value(value);
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
                    if (margin.left() != null) {
                        margin.left().value(value*2);
                    }
                }
            }
        }
    }

    // ===== 제목 프리셋 =====
    public StyleBlock titlePreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 내용 프리셋 =====
    public StyleBlock bodyPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 개요 프리셋 (1~7) =====
    public StyleBlock heading1Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1400);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 150);
        
        // 테마에 따른 marginLeft 설정
        int marginLeft = (theme == Theme.GRAY) ? 3000 : 1500;
        setMarginLeftBoth(paraPr, marginLeft);

        paraPr.heading().type(ParaHeadingType.BULLET);
        paraPr.heading().idRef(bulletRegistry.getBulletByName("개요1").id());
        paraPr.heading().level((byte) 0);

        styleTag.name("개요1");
        styleTag.engName("Heading1");

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    public StyleBlock heading2Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1400);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 150);
        
        // 테마에 따른 marginLeft 설정
        int marginLeft = (theme == Theme.GRAY) ? 4500 : 3000;
        setMarginLeftBoth(paraPr, marginLeft);

        paraPr.heading().type(ParaHeadingType.BULLET);
        paraPr.heading().idRef(bulletRegistry.getBulletByName("개요2").id());
        paraPr.heading().level((byte) 0);

        styleTag.name("개요2");
        styleTag.engName("Heading2");
        return new StyleBlock(charPr, paraPr, styleTag);
        
    }

    public StyleBlock heading0Preset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1400);
        charPr.textColor("#000000");
        charPr.createBold();

        paraPr.align().horizontal(HorizontalAlign2.LEFT);
        setLineSpacingBoth(paraPr, 150);
        
        // heading0는 항상 1500 (테마 관계없이 동일)
        setMarginLeftBoth(paraPr, 1500);

        paraPr.heading().type(ParaHeadingType.BULLET);
        paraPr.heading().idRef(bulletRegistry.getBulletByName("개요0").id());
        paraPr.heading().level((byte) 0);

        styleTag.name("개요0");
        styleTag.engName("Heading0");

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // 본문 왼쪽정렬용 프리셋
    public StyleBlock bodyLeftPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        paraPr.align().horizontal(HorizontalAlign2.LEFT);

        styleTag.name("내용 왼쪽정렬");
        styleTag.engName("BodyLeft");

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // 본문 가운데정렬용 프리셋
    public StyleBlock bodyCenterPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        paraPr.align().horizontal(HorizontalAlign2.CENTER);

        styleTag.name("내용 가운데정렬");
        styleTag.engName("BodyCenter");

        return new StyleBlock(charPr, paraPr, styleTag);
    }


    // ===== 표 프리셋 =====
    public StyleBlock tableHeaderPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.createBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.CENTER);
        setLineSpacingBoth(paraPr, 160);

        styleTag.name("표 헤더");
        styleTag.engName("TableHeader");

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    public StyleBlock tableCellPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("맑은 고딕").id();
        charPr.fontRef().setAll(fontId);
        charPr.removeBold();
        charPr.height(1000);
        charPr.textColor("#000000");

        paraPr.align().horizontal(HorizontalAlign2.JUSTIFY);
        setLineSpacingBoth(paraPr, 160);

        styleTag.name("표 내용");
        styleTag.engName("TableCell");

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 제목 테이블 번호 =====
    public StyleBlock titleTableNumberPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        String fontId = fontRegistry.getFontByName("함초롬바탕").id();
        charPr.fontRef().setAll(fontId);
        charPr.height(1600);
        charPr.textColor("#FFFFFF");
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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // ===== 제목 테이블 내용 =====
    public StyleBlock titleTableContentPreset() {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

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

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    // 모든 프리셋을 배열로 반환
    public StyleBlock[] getAllPresets() {
        return new StyleBlock[] {
            titlePreset(),
            bodyPreset(),
            heading0Preset(),
            heading1Preset(),
            heading2Preset(),
            tableHeaderPreset(),
            tableCellPreset(),
            titleTableNumberPreset(),
            titleTableContentPreset(),
            bodyLeftPreset(),
            bodyCenterPreset(),
        };
    }
}
