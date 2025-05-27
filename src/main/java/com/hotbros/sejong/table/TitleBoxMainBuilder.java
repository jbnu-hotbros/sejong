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

// Main 전용 TitleBox 빌더 (1행 1열, XML 예시와 동일하게)
public class TitleBoxMainBuilder {
    private static final long TOTAL_WIDTH = 41954L;
    private static final long CELL_HEIGHT = 6093L;
    private static final long CELL_WIDTH = 41954L;
    private static final long OUT_MARGIN = 283L;
    private static final long IN_MARGIN_LEFT_RIGHT = 510L;
    private static final long IN_MARGIN_TOP_BOTTOM = 141L;
    private final BorderFillRegistry borderFillRegistry;

    public TitleBoxMainBuilder(BorderFillRegistry borderFillRegistry) {
        this.borderFillRegistry = borderFillRegistry;
    }

    /**
     * XML 예시와 동일한 1행 1열 메인 타이틀 테이블 생성
     * @param borderFillId 테이블 테두리 borderFillId
     * @param cellBorderFillId 셀 테두리 borderFillId
     * @param style 셀 스타일
     * @return Table 객체
     */
    public Table build(String title, String borderFillId, String cellBorderFillId, Style style) {
        Table table = new Table();
        table.id("1856905794");
        table.zOrder(0);
        table.numberingType(NumberingType.TABLE);
        table.textWrap(TextWrapMethod.TOP_AND_BOTTOM);
        table.textFlow(TextFlowSide.BOTH_SIDES);
        table.lock(false);
        table.dropcapstyle(DropCapStyle.None);
        table.pageBreak(TablePageBreak.CELL);
        table.repeatHeader(true);
        table.rowCnt((short) 1);
        table.colCnt((short) 1);
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
        table.pos().horzRelTo(HorzRelTo.COLUMN);
        table.pos().vertAlign(VertAlign.TOP);
        table.pos().horzAlign(HorzAlign.LEFT);
        table.pos().vertOffset(0L);
        table.pos().horzOffset(0L);
        table.createOutMargin();
        table.outMargin().left(OUT_MARGIN);
        table.outMargin().right(OUT_MARGIN);
        table.outMargin().top(OUT_MARGIN);
        table.outMargin().bottom(OUT_MARGIN);
        table.createInMargin();
        table.inMargin().left(IN_MARGIN_LEFT_RIGHT);
        table.inMargin().right(IN_MARGIN_LEFT_RIGHT);
        table.inMargin().top(IN_MARGIN_TOP_BOTTOM);
        table.inMargin().bottom(IN_MARGIN_TOP_BOTTOM);

        // 행 생성
        var tr = table.addNewTr();
        tr.addTc(createTableCell(title, borderFillRegistry.getBorderFillByName("TITLE_BOX_MAIN").id(), style));
        return table;
    }

    private static Tc createTableCell(String title, String borderFillId, Style style) {
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

        // 문단 설정 (비어있는 문단)
        var para = sl.addNewPara();
        para.id("0");
        para.paraPrIDRef(style.paraPrIDRef());
        para.styleIDRef(style.id());
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        var run = para.addNewRun();
        run.charPrIDRef(style.charPrIDRef());
        if (title != null && !title.isEmpty()) {
            run.addNewT().addText(title);
        }

        // 셀 주소 설정
        tc.createCellAddr();
        tc.cellAddr().colAddr((short) 0);
        tc.cellAddr().rowAddr((short) 0);

        // 셀 범위 설정
        tc.createCellSpan();
        tc.cellSpan().colSpan((short) 1);
        tc.cellSpan().rowSpan((short) 1);

        // 셀 크기 설정
        tc.createCellSz();
        tc.cellSz().width(CELL_WIDTH);
        tc.cellSz().height(CELL_HEIGHT);

        // 셀 여백 설정
        tc.createCellMargin();
        tc.cellMargin().left(IN_MARGIN_LEFT_RIGHT);
        tc.cellMargin().right(IN_MARGIN_LEFT_RIGHT);
        tc.cellMargin().top(IN_MARGIN_TOP_BOTTOM);
        tc.cellMargin().bottom(IN_MARGIN_TOP_BOTTOM);

        return tc;
    }
} 