package com.hotbros.sejong.style;

import com.hotbros.sejong.builder.CharPrBuilder;
import com.hotbros.sejong.builder.ParaPrBuilder;
import com.hotbros.sejong.builder.StyleBuilder;
import com.hotbros.sejong.util.HWPXObjectFinder;
import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;

/**
 * HWPX에서 사용할 다양한 스타일 프리셋을 정의합니다.
 * 각 프리셋은 StyleBuilderBlock 인스턴스를 반환하며, build() 시 CharPr, ParaPr, Style, fontRefId가 일관성 있게 생성됩니다.
 */
public class StylePresets {

    // ===== 제목 프리셋 =====
    public static StyleBuilderBlock titlePreset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "0");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1")
                .bold(true)
                .height(2000)
                .textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.CENTER)
                .lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("제목")
                .engName("Title");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }

    // ===== 본문 프리셋 =====
    public static StyleBuilderBlock bodyPreset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "0");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1")
                .bold(false)
                .height(1000)
                .textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.JUSTIFY)
                .lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("본문")
                .engName("Body");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }

    // ===== 개요 프리셋 (1~7) =====
    public static StyleBuilderBlock heading1Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "2");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(1600).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요1").engName("Heading1");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock heading2Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "3");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(1400).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요2").engName("Heading2");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock heading3Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "4");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(1200).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요3").engName("Heading3");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock heading4Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "5");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(1100).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요4").engName("Heading4");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock heading5Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "6");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(1000).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요5").engName("Heading5");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock heading6Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "7");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(900).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요6").engName("Heading6");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock heading7Preset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "8");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(800).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("개요7").engName("Heading7");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }

    // ===== 표 프리셋 =====
    public static StyleBuilderBlock tableHeaderPreset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "0");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(true).height(1000).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.CENTER).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("표 헤더").engName("TableHeader");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }
    public static StyleBuilderBlock tableCellPreset(HWPXFile hwpxFile) {
        Style baseStyle = HWPXObjectFinder.findStyleById(hwpxFile, "0");
        CharPr baseCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, baseStyle.charPrIDRef());
        ParaPr baseParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, baseStyle.paraPrIDRef());
        CharPrBuilder charPrBuilder = new CharPrBuilder(baseCharPr)
                .fontRef("1").bold(false).height(1000).textColor("#000000");
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(baseParaPr)
                .alignHorizontal(HorizontalAlign2.LEFT).lineSpacing(160);
        StyleBuilder styleBuilder = new StyleBuilder(baseStyle)
                .name("표 본문").engName("TableCell");
        return new StyleBuilderBlock(charPrBuilder, paraPrBuilder, styleBuilder);
    }

}