package com.hotbros.sejong.builder;

import java.util.List;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.common.baseobject.LeftRightTopBottom;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.borderfill.FillBrush;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.CenterLineSort;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineWidth;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.SlashType;
import kr.dogfoot.hwpxlib.object.content.section_xml.SubList;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.*;

import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.T;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.LineSeg;

import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.shapeobject.ShapeSize;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.table.Tc;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.table.Tr;


public class TableBuilder {
    private static final long TOTAL_WIDTH = 41954L;
    private static final long CELL_HEIGHT = 1282*2;



    public static Table buildTable(int rows, int cols, List<List<String>> contents, String borderFillId, String headerBorderFillId) {
        validateContent(rows, cols, contents);
        long cellWidth = TOTAL_WIDTH / cols;
        Table table = createTableBase(rows, cols, cellWidth, borderFillId);
        for (int row = 0; row < rows; row++) {
            Tr tr = table.addNewTr();
            for (int col = 0; col < cols; col++) {
                String text = contents.get(row).get(col);
                String effectiveBorderId = (row == 0) ? headerBorderFillId : borderFillId;
                Tc cell = createCell(row, col, cellWidth, CELL_HEIGHT, text, effectiveBorderId);
                tr.addTc(cell);
            }
        }
        return table;
    }

    private static void validateContent(int rows, int cols, List<List<String>> contents) {
        if (contents.size() != rows) {
            throw new IllegalArgumentException("행 수가 일치하지 않습니다.");
        }
        for (int i = 0; i < rows; i++) {
            if (contents.get(i).size() != cols) {
                throw new IllegalArgumentException("[" + i + "]행의 열 수가 일치하지 않습니다.");
            }
        }
    }

    private static Table createTableBase(int rows, int cols, long cellWidth, String borderFillId) {
        Table table = new Table();
        table.id("1853460188");
        table.zOrder(0);
        table.numberingType(NumberingType.TABLE);
        table.textWrap(TextWrapMethod.TOP_AND_BOTTOM);
        table.textFlow(TextFlowSide.BOTH_SIDES);
        table.lock(false);
        table.dropcapstyle(DropCapStyle.None);
        table.pageBreak(TablePageBreak.CELL);
        table.repeatHeader(true);
        table.rowCnt((short) rows);
        table.colCnt((short) cols);
        table.cellSpacing(0);
        table.borderFillIDRef(borderFillId);
        table.noAdjust(false);
        table.createSZ();
        ShapeSize sz = table.sz();
        sz.width(TOTAL_WIDTH);
        sz.widthRelTo(WidthRelTo.ABSOLUTE);
        sz.height(CELL_HEIGHT * rows);
        sz.heightRelTo(HeightRelTo.ABSOLUTE);
        sz.protect(false);
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
        LeftRightTopBottom outMargin = table.outMargin();
        outMargin.left(283L);
        outMargin.right(283L);
        outMargin.top(283L);
        outMargin.bottom(283L);
        table.createInMargin();
        LeftRightTopBottom inMargin = table.inMargin();
        inMargin.left(510L);
        inMargin.right(510L);
        inMargin.top(141L);
        inMargin.bottom(141L);
        return table;
    }

    private static Tc createCell(int row, int col, long width, long height, String text, String borderFillId) {
        Tc tc = new Tc();
        tc.name("");
        tc.header(false);
        tc.hasMargin(false);
        tc.protect(false);
        tc.editable(false);
        tc.dirty(false);
        tc.borderFillIDRef(borderFillId);
        tc.createSubList();
        SubList sl = tc.subList();
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
        Para para = sl.addNewPara();
        para.id("para-" + row + "-" + col);
        para.paraPrIDRef("0");
        para.styleIDRef("0");
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        Run run = para.addNewRun();
        run.charPrIDRef("0");
        T t = run.addNewT();
        t.addText(text);
        para.createLineSegArray();
        LineSeg seg = para.lineSegArray().addNew();
        seg.textpos(0);
        seg.vertpos(0);
        seg.vertsize(1000);
        seg.textheight(1000);
        seg.baseline(850);
        seg.spacing(600);
        seg.horzpos(0);
        seg.horzsize((int)width - 1024);
        seg.flags(393216);
        tc.createCellAddr();
        tc.cellAddr().rowAddr((short) row);
        tc.cellAddr().colAddr((short) col);
        tc.createCellSpan();
        tc.cellSpan().rowSpan((short) 1);
        tc.cellSpan().colSpan((short) 1);
        tc.createCellSz();
        tc.cellSz().width(width);
        tc.cellSz().height(height);
        tc.createCellMargin();
        LeftRightTopBottom margin = tc.cellMargin();
        margin.left(510L);
        margin.right(510L);
        margin.top(141L);
        margin.bottom(141L);
        return tc;
    }
    
}
