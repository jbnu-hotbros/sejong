package com.hotbros.sejong.table;

import java.util.List;

import kr.dogfoot.hwpxlib.object.common.baseobject.LeftRightTopBottom;
import kr.dogfoot.hwpxlib.object.content.section_xml.SubList;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.*;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Ctrl;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.T;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.ctrl.AutoNum;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.secpr.notepr.AutoNumFormat;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.shapeobject.ShapeSize;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.table.Tc;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.table.Tr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import com.hotbros.sejong.style.StyleRegistry;

/**
 * HWPX 파일에 테이블을 생성하는 빌더 클래스
 */
public class TableBuilder {
    private static final long TOTAL_WIDTH = 41954L;
    private static final long CELL_HEIGHT = 1282 * 2;
    
    private final StyleRegistry styleRegistry;

    /**
     * 생성자 - StyleRegistry를 받아서 초기화
     */
    public TableBuilder(StyleRegistry styleRegistry) {
        this.styleRegistry = styleRegistry;
    }

    /**
     * 캡션 지원 테이블을 생성합니다.
     * @param rows 행 수
     * @param cols 열 수
     * @param contents 테이블 내용
     * @param borderFillId 테이블 테두리 ID
     * @param headerBorderFillId 헤더 테두리 ID
     * @param headerStyleId 헤더 스타일 ID
     * @param bodyStyleId 본문 스타일 ID
     * @param headerCharPrId 헤더 문자 속성 ID
     * @param bodyCharPrId 본문 문자 속성 ID
     * @param headerParaPrId 헤더 문단 속성 ID
     * @param bodyParaPrId 본문 문단 속성 ID
     * @param captionText 캡션 텍스트 (null일 경우 캡션 생성하지 않음)
     * @return Table 객체
     */
    public Table buildTable(int rows, int cols, List<List<String>> contents, String borderFillId,
            String headerBorderFillId, String headerStyleId, String bodyStyleId, String headerCharPrId,
            String bodyCharPrId, String headerParaPrId, String bodyParaPrId, String captionText) {
        validateContent(rows, cols, contents);
        long cellWidth = TOTAL_WIDTH / cols;
        Table table = createTableBase(rows, cols, cellWidth, borderFillId, captionText);
        for (int row = 0; row < rows; row++) {
            Tr tr = table.addNewTr();
            for (int col = 0; col < cols; col++) {
                String text = contents.get(row).get(col);
                String effectiveBorderId = (row == 0) ? headerBorderFillId : borderFillId;
                String styleId = (row == 0) ? headerStyleId : bodyStyleId;
                String charPrId = (row == 0) ? headerCharPrId : bodyCharPrId;
                String paraPrId = (row == 0) ? headerParaPrId : bodyParaPrId;
                Tc cell = createCell(row, col, cellWidth, CELL_HEIGHT, text, effectiveBorderId, styleId, charPrId,
                        paraPrId);
                tr.addTc(cell);
            }
        }
        return table;
    }

    /**
     * 캡션 없는 테이블 생성 메서드
     */
    public Table buildTable(int rows, int cols, List<List<String>> contents, String borderFillId,
            String headerBorderFillId, String headerStyleId, String bodyStyleId, String headerCharPrId,
            String bodyCharPrId, String headerParaPrId, String bodyParaPrId) {
        return buildTable(rows, cols, contents, borderFillId, headerBorderFillId, headerStyleId, bodyStyleId,
                headerCharPrId, bodyCharPrId, headerParaPrId, bodyParaPrId, null);
    }

    private void validateContent(int rows, int cols, List<List<String>> contents) {
        if (contents.size() != rows) {
            throw new IllegalArgumentException("행 수가 일치하지 않습니다.");
        }
        for (int i = 0; i < rows; i++) {
            if (contents.get(i).size() != cols) {
                throw new IllegalArgumentException("[" + i + "]행의 열 수가 일치하지 않습니다.");
            }
        }
    }

    private Table createTableBase(int rows, int cols, long cellWidth, String borderFillId, String captionText) {
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

        // 캡션 설정 (사용자 제공 XML 구조와 동일하게)
        if (captionText != null && !captionText.trim().isEmpty()) {
            table.createCaption();
            table.caption().side(CaptionSide.BOTTOM);
            table.caption().fullSz(false);
            table.caption().width(8504L);
            table.caption().gap(850L);
            table.caption().lastWidth(TOTAL_WIDTH);
            
            // 캡션의 서브리스트 생성
            table.caption().createSubList();
            SubList subList = table.caption().subList();
            subList.id("");
            subList.textDirection(TextDirection.HORIZONTAL);
            subList.lineWrap(LineWrapMethod.BREAK);
            subList.vertAlign(VerticalAlign2.TOP);
            subList.linkListIDRef("0");
            subList.linkListNextIDRef("0");
            subList.textWidth(0);
            subList.textHeight(0);
            subList.hasTextRef(false);
            subList.hasNumRef(false);
            
            // 가운데 정렬 스타일 가져오기 (필수)
            Style centerStyle = styleRegistry.getStyleByName("내용 가운데정렬");
            if (centerStyle == null) {
                throw new RuntimeException("가운데 정렬 스타일을 찾을 수 없습니다.");
            }
            
            String paraPrId = centerStyle.paraPrIDRef();
            String charPrId = centerStyle.charPrIDRef();
            String styleId = centerStyle.id();
            
            // 캡션 문단 생성
            Para captionPara = subList.addNewPara();
            captionPara.id("0");
            captionPara.paraPrIDRef(paraPrId);
            captionPara.styleIDRef(styleId);
            captionPara.pageBreak(false);
            captionPara.columnBreak(false);
            captionPara.merged(false);
            
            // 캡션 실행 객체 생성
            Run captionRun = captionPara.addNewRun();
            captionRun.charPrIDRef(charPrId);
            
            // 캡션 텍스트만 추가 (autonum 제거)
            T captionMainText = captionRun.addNewT();
            captionMainText.addText(captionText);
        }
        
        return table;
    }

    private Tc createCell(int row, int col, long width, long height, String text, String borderFillId,
            String styleId, String charPrId, String paraPrId) {
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
        para.paraPrIDRef(paraPrId);
        para.styleIDRef(styleId);
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        Run run = para.addNewRun();
        run.charPrIDRef(charPrId);
        T t = run.addNewT();
        t.addText(text);
        para.createLineSegArray();
        // LineSeg seg = para.lineSegArray().addNew();
        // seg.textpos(0);
        // seg.vertpos(0);
        // seg.vertsize(1000);
        // seg.textheight(1000);
        // seg.baseline(850);
        // seg.spacing(600);
        // seg.horzpos(0);
        // seg.horzsize((int)width - 1024);
        // seg.flags(393216);
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
