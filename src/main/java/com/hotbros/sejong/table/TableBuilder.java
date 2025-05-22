package com.hotbros.sejong.table;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.common.baseobject.LeftRightTopBottom;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
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
    private HWPXFile hwpxFile;

    public TableBuilder(HWPXFile hwpxFile) {
        this.hwpxFile = hwpxFile;
    }
    
    /**
     * 테두리 스타일을 생성합니다.
     * @param id 테두리 ID
     * @return 생성된 BorderFill 객체
     */
    public static BorderFill createBorderFill() {
        BorderFill borderFill = new BorderFill();
        borderFill.threeD(false);
        borderFill.shadow(false);
        borderFill.centerLine(CenterLineSort.NONE);
        borderFill.breakCellSeparateLine(false);

        borderFill.createSlash();
        borderFill.slash()
                .typeAnd(SlashType.NONE)
                .CrookedAnd(false)
                .isCounter(false);

        borderFill.createBackSlash();
        borderFill.backSlash()
                .typeAnd(SlashType.NONE)
                .CrookedAnd(false)
                .isCounter(false);

        borderFill.createLeftBorder();
        borderFill.leftBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12) // 0.12 mm
                .colorAnd("#000000");

        borderFill.createRightBorder();
        borderFill.rightBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12) // 0.12 mm
                .colorAnd("#000000");

        borderFill.createTopBorder();
        borderFill.topBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12) // 0.12 mm
                .colorAnd("#000000");

        borderFill.createBottomBorder();
        borderFill.bottomBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12) // 0.12 mm
                .colorAnd("#000000");

        borderFill.createDiagonal();
        borderFill.diagonal()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_1) // 0.1 mm
                .colorAnd("#000000");
                
        return borderFill;
    }
    
    /**
     * HWPXFile에 BorderFill을 추가합니다.
     * @param hwpxFile HWPX 파일 객체
     * @param borderFill 추가할 BorderFill 객체
     */
    public void addBorderFill() {
        // BorderFill 생성 및 추가
        BorderFill borderFill = createBorderFill();
        borderFill.id("3");

        if (hwpxFile.headerXMLFile().refList().borderFills() == null) {
            hwpxFile.headerXMLFile().refList().createBorderFills();
        }
        hwpxFile.headerXMLFile().refList().borderFills().add(borderFill);
    }

    public Table build() {


        Table table = new Table();

        // <hp:tbl> attributes
        table.id("1853460188");
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
        table.borderFillIDRef("3"); // 위에서 생성한 BorderFill의 ID 참조
        table.noAdjust(false);

        table.createSZ();
        ShapeSize sz = table.sz();
        sz.width(41954L);
        sz.widthRelTo(WidthRelTo.ABSOLUTE);
        sz.height(2564L);
        sz.heightRelTo(HeightRelTo.ABSOLUTE);
        sz.protect(false);

        // <hp:pos>
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

        // <hp:outMargin>
        // package kr.dogfoot.hwpxlib.object.common.baseobject; leftrighttopbottom
        table.createOutMargin();
        LeftRightTopBottom outMargin = table.outMargin();
        outMargin.left(283L);
        outMargin.right(283L);
        outMargin.top(283L);
        outMargin.bottom(283L);

        // <hp:inMargin>
        // 파라미터 타입들은 전부다 long인듯 
        table.createInMargin();
        LeftRightTopBottom inMargin =  table.inMargin();
        inMargin.left(510L);
        inMargin.right(510L);
        inMargin.top(141L);
        inMargin.bottom(141L);

        // First <hp:tr>
        Tr tr1 = table.addNewTr();

        // First <hp:tc> in first <hp:tr>
        Tc tc1_1 = tr1.addNewTc();
        tc1_1.name("");
        tc1_1.header(false);
        tc1_1.hasMargin(false);
        tc1_1.protect(false);
        tc1_1.editable(false);
        tc1_1.dirty(false);
        tc1_1.borderFillIDRef("3");

        tc1_1.createSubList();
        SubList subList1_1 = tc1_1.subList();
        subList1_1.id("");
        subList1_1.textDirection(TextDirection.HORIZONTAL);
        subList1_1.lineWrap(LineWrapMethod.BREAK);
        subList1_1.vertAlign(VerticalAlign2.CENTER);
        subList1_1.linkListIDRef("0");
        subList1_1.linkListNextIDRef("0");
        subList1_1.textWidth(0);
        subList1_1.textHeight(0);
        subList1_1.hasTextRef(false);
        subList1_1.hasNumRef(false);

        Para p1_1 = subList1_1.addNewPara();
        p1_1.id("0");
        p1_1.paraPrIDRef("0");
        p1_1.styleIDRef("0");
        p1_1.pageBreak(false);
        p1_1.columnBreak(false);
        p1_1.merged(false);

        Run run1_1 = p1_1.addNewRun();
        run1_1.charPrIDRef("0");
        T t1_1 = run1_1.addNewT();
        t1_1.addText("11");

        p1_1.createLineSegArray();
        LineSeg lineSeg1_1 = p1_1.lineSegArray().addNew();
        lineSeg1_1.textpos(0);
        lineSeg1_1.vertpos(0);
        lineSeg1_1.vertsize(1000);
        lineSeg1_1.textheight(1000);
        lineSeg1_1.baseline(850);
        lineSeg1_1.spacing(600);
        lineSeg1_1.horzpos(0);
        lineSeg1_1.horzsize(40932);
        lineSeg1_1.flags(393216);

        tc1_1.createCellAddr();
        tc1_1.cellAddr().colAddr((short)0);
        tc1_1.cellAddr().rowAddr((short)0);

        tc1_1.createCellSpan();
        tc1_1.cellSpan().colSpan((short)1);
        tc1_1.cellSpan().rowSpan((short)1);

        tc1_1.createCellSz();
        tc1_1.cellSz().width(20977L);
        tc1_1.cellSz().height(282L);

        tc1_1.createCellMargin();
        tc1_1.cellMargin().left(510L);
        tc1_1.cellMargin().right(510L);
        tc1_1.cellMargin().top(141L);
        tc1_1.cellMargin().bottom(141L);



        return table;
    }
}
