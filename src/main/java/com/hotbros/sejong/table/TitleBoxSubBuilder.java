package com.hotbros.sejong.table;

import java.util.Arrays;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.table.Tc;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.*;

public class TitleBoxSubBuilder {
    // XML 예시 기준 값
    private static final long CELL_HEIGHT = 2697L;
    private static final long[] CELL_WIDTHS = { 2573L, 1131L, 25073L };
    private static final long TOTAL_WIDTH = Arrays.stream(CELL_WIDTHS).sum();
    private static final long OUT_MARGIN = 283L;
    private static final long IN_MARGIN_LEFT_RIGHT = 510L;
    private static final long IN_MARGIN_TOP_BOTTOM = 141L;

    private static final String[] BORDER_FILL_NAMES = {
        "TITLE_BOX_SUB_LEFT", "TITLE_BOX_SUB_CENTER", "TITLE_BOX_SUB_RIGHT"
    };

    /**
     * Sub 타이틀 테이블 생성
     * @param number 왼쪽 셀 텍스트 (예: "1")
     * @param title 오른쪽 셀 텍스트 (예: "사업명")
     * @param styles 각 셀의 Style (3개, left/center/right)
     * @param tableBorderFillId 테이블 전체 borderFillId
     * @param cellBorderFillIds 각 셀의 borderFillId (3개)
     * @return Table 객체
     */
    public static Table build(
            String number, String title,
            Style[] styles,
            String tableBorderFillId,
            String[] cellBorderFillIds
    ) {
        String[] cellTexts = { number, "", title };
        Table table = new Table();
        table.id("1856856435");
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
        table.borderFillIDRef(tableBorderFillId);
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
        for (int col = 0; col < 3; col++) {
            String borderFillId = cellBorderFillIds[col];
            Style style = styles[col];
            tr.addTc(createTableCell(
                    col,
                    cellTexts[col],
                    borderFillId,
                    CELL_WIDTHS[col],
                    CELL_HEIGHT,
                    style.id(),
                    style.paraPrIDRef(),
                    style.charPrIDRef()
            ));
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
            String charPrId
    ) {
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
        if (text != null && !text.isEmpty()) {
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
        tc.cellMargin().left(IN_MARGIN_LEFT_RIGHT);
        tc.cellMargin().right(IN_MARGIN_LEFT_RIGHT);
        tc.cellMargin().top(IN_MARGIN_TOP_BOTTOM);
        tc.cellMargin().bottom(IN_MARGIN_TOP_BOTTOM);

        return tc;
    }
} 