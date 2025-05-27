package com.hotbros.sejong.table;

import java.util.Arrays;

import com.hotbros.sejong.HWPXBuilder;

import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.table.Tc;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.NumberingType;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.TextWrapMethod;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.TextFlowSide;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.DropCapStyle;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.TablePageBreak;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.WidthRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.HeightRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.VertRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.HorzRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.VertAlign;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.HorzAlign;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.TextDirection;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.LineWrapMethod;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.VerticalAlign2;

public class TitleBoxMiddleBuilder {
    private static final long TOTAL_WIDTH = 47890L;
    private static final long CELL_HEIGHT = 3414L;
    private static final long[] CELL_WIDTHS = { 3060L, 565L, 44265L };
    public static final String[] BORDER_FILL_NAMES = { "TITLE_BOX_MIDDLE_LEFT", "TITLE_BOX_MIDDLE_CENTER", "TITLE_BOX_MIDDLE_RIGHT" };
    private final BorderFillRegistry borderFillRegistry;

    public TitleBoxMiddleBuilder(BorderFillRegistry borderFillRegistry) {
        this.borderFillRegistry = borderFillRegistry;
    }

    /**
     * 타이틀 테이블을 생성합니다.
     * 
     * @param number        왼쪽 셀에 들어갈 번호 (예: "Ⅰ")
     * @param title         오른쪽 셀에 들어갈 제목 (예: "사업 개요")
     * @param borderFillIds 각 셀의 borderFillId (3개)
     * @param paraPrIds     각 셀의 paraPrId (3개)
     * @param charPrIds     각 셀의 charPrId (3개)
     * @param styleIds      각 셀의 styleId (3개)
     * @return Table 객체
     */
    public Table build(
            String number, String title,
            String[] borderFillIds, Style numberStyle, Style contentStyle) {
        String[] cellTexts = { number, "", title };
        Table table = new Table();
        table.id("1856248017");
        table.zOrder(0);
        table.numberingType(NumberingType.TABLE);
        table.textWrap(TextWrapMethod.TOP_AND_BOTTOM);
        table.textFlow(TextFlowSide.BOTH_SIDES);
        table.lock(false);
        table.dropcapstyle(DropCapStyle.None);
        table.pageBreak(TablePageBreak.CELL);
        table.repeatHeader(true);
        table.rowCnt((short) 1);
        table.colCnt((short) 3);
        table.cellSpacing(0);
        table.borderFillIDRef(borderFillRegistry.getBorderFillByName("default").id());
        table.noAdjust(false);
        table.createSZ();
        table.sz().width(TOTAL_WIDTH);
        table.sz().widthRelTo(WidthRelTo.ABSOLUTE);
        table.sz().height(CELL_HEIGHT);
        table.sz().heightRelTo(HeightRelTo.ABSOLUTE);
        table.sz().protect(false);
        table.createPos();
        table.pos().treatAsChar(true);
        table.pos().affectLSpacing(false);
        table.pos().flowWithText(true);
        table.pos().allowOverlap(false);
        table.pos().holdAnchorAndSO(false);
        table.pos().vertRelTo(VertRelTo.PARA);
        table.pos().horzRelTo(HorzRelTo.PARA);
        table.pos().vertAlign(VertAlign.TOP);
        table.pos().horzAlign(HorzAlign.LEFT);
        table.pos().vertOffset(0L);
        table.pos().horzOffset(0L);
        table.createOutMargin();
        table.outMargin().left(141L);
        table.outMargin().right(141L);
        table.outMargin().top(141L);
        table.outMargin().bottom(141L);
        table.createInMargin();
        table.inMargin().left(141L);
        table.inMargin().right(141L);
        table.inMargin().top(141L);
        table.inMargin().bottom(141L);

        // 셀별 스타일 정보 준비
        String[] styleIds = { numberStyle.id(), "0", contentStyle.id() };
        String[] charPrIds = { numberStyle.charPrIDRef(), "0", contentStyle.charPrIDRef() };
        String[] paraPrIds = { numberStyle.paraPrIDRef(), "0", contentStyle.paraPrIDRef() };

        // 행 생성
        var tr = table.addNewTr();
        for (int col = 0; col < 3; col++) {
            tr.addTc(createTableCell(
                    col,
                    cellTexts[col],
                    borderFillIds[col],
                    CELL_WIDTHS[col],
                    CELL_HEIGHT,
                    styleIds[col],
                    paraPrIds[col],
                    charPrIds[col]));
        }
        return table;
    }

    private static Tc createTableCell(
            int col,
            String text,
            String borderFillId,
            long width,
            long height,
            String styleId,
            String paraPrId,
            String charPrId) {

        System.out.println("styleId: " + styleId);
        System.out.println("charPrId: " + charPrId);
        System.out.println("paraPrId: " + paraPrId);
        Tc tc = new Tc();
        tc.name("");
        tc.header(false);
        tc.hasMargin(false);
        tc.protect(false);
        tc.editable(false);
        tc.dirty(false);
        tc.borderFillIDRef(borderFillId);

        // SubList 설정
        tc.createSubList();
        var sl = tc.subList();
        sl.id("");
        sl.textDirection(TextDirection.HORIZONTAL);
        sl.lineWrap(LineWrapMethod.BREAK);
        sl.vertAlign(VerticalAlign2.CENTER);
        sl.linkListIDRef("0");
        sl.linkListNextIDRef("0");
        sl.textWidth(0);
        sl.textHeight(0);
        sl.hasTextRef(false);
        sl.hasNumRef(false);

        // 문단 설정
        var para = sl.addNewPara();
        para.id("2147483648");
        para.paraPrIDRef(paraPrId);
        para.styleIDRef(styleId);
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);

        // 텍스트 런 설정
        var run = para.addNewRun();
        run.charPrIDRef(charPrId);
        if (!text.isEmpty()) {
            var t = run.addNewT();
            t.addText(text);
        }

        // 셀 주소 설정
        tc.createCellAddr();
        tc.cellAddr().colAddr((short) col);
        tc.cellAddr().rowAddr((short) 0);

        // 셀 범위 설정
        tc.createCellSpan();
        tc.cellSpan().colSpan((short) 1);
        tc.cellSpan().rowSpan((short) 1);

        // 셀 크기 설정
        tc.createCellSz();
        tc.cellSz().width(width);
        tc.cellSz().height(height);

        // 셀 여백 설정
        tc.createCellMargin();
        tc.cellMargin().left(141L);
        tc.cellMargin().right(141L);
        tc.cellMargin().top(141L);
        tc.cellMargin().bottom(141L);
 
        return tc;
    }
}
