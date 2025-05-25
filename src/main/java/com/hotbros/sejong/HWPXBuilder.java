package com.hotbros.sejong;

import java.util.List;

import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.util.IdGenerator;
import com.hotbros.sejong.style.StylePreset;
import com.hotbros.sejong.style.StyleRegistry;
import com.hotbros.sejong.table.BorderFillRegistry;
import com.hotbros.sejong.font.FontRegistry;
import com.hotbros.sejong.numbering.NumberingRegistry;
import com.hotbros.sejong.table.TableBuilder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.RunItem;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;

public class HWPXBuilder {
    private HWPXFile hwpxFile;
    private IdGenerator idGenerator;
    private SectionXMLFile section;
    private boolean hasFirstParagraphText = false;
    private StyleRegistry styleRegistry;
    private BorderFillRegistry borderFillRegistry;
    private FontRegistry fontRegistry;
    private NumberingRegistry numberingRegistry;
    private RefList refList;

    private static final String NORMAL_PARA_ID = "0";
    private static final String NORMAL_BORDER_ID = "default";
    private static final String HEADER_BORDER_ID = "grayFill";

    public HWPXBuilder() {
        this.hwpxFile = BlankFileMaker.make();
        this.idGenerator = new IdGenerator();
        this.section = hwpxFile.sectionXMLFileList().get(0);
        this.refList = hwpxFile.headerXMLFile().refList();
        
        this.fontRegistry = new FontRegistry(refList, idGenerator);
        this.borderFillRegistry = new BorderFillRegistry(refList, idGenerator);
        this.numberingRegistry = new NumberingRegistry(refList);

        this.styleRegistry = new StyleRegistry(refList, new StylePreset(hwpxFile, idGenerator, fontRegistry));
    }
    
    
    // 2. 중복 로직 유틸리티 메서드로 분리 (1번, 3번)
    private Run createStyledRun(Style style, String text) {
        Run run = new Run();
        run.charPrIDRef(style.charPrIDRef());
        run.addNewT().addText(text);
        return run;
    }

    // 스타일 이름, 텍스트, 페이지브레이크로 Para를 생성하고 바로 섹션에 추가
    public void addParagraph(String styleName, String text, boolean pageBreak) {
        Style style = styleRegistry.getStyleById(styleName);
        if (style == null) {
            throw new IllegalArgumentException("해당 이름의 스타일을 찾을 수 없습니다: " + styleName);
        }
        Para para = new Para();
        para.id(NORMAL_PARA_ID);
        para.styleIDRef(style.id());
        para.paraPrIDRef(style.paraPrIDRef());
        para.pageBreak(pageBreak);
        para.columnBreak(false);
        para.merged(false);
        para.addRun(createStyledRun(style, text));
        section.addPara(para);
    }

    // 첫 번째 문단 텍스트를 스타일과 함께 교체
    public void addFirstStyledText(String styleName, String text) {
        Style style = styleRegistry.getStyleById(styleName);
        if (style == null) {
            throw new IllegalArgumentException("해당 이름의 스타일을 찾을 수 없습니다: " + styleName);
        }
        if (hasFirstParagraphText) {
            return;
        }
        Para para = section.getPara(0);
        para.paraPrIDRef(style.paraPrIDRef());
        para.removeRun(0);
        para.addRun(createStyledRun(style, text));
    }

    // 표를 추가하는 함수 (기존과 동일)
    public void addTableWithHeader(List<List<String>> contents) {
        if (contents == null || contents.isEmpty() || contents.get(0).isEmpty()) {
            throw new IllegalArgumentException("contents가 비어있거나 올바르지 않습니다.");
        }
        int rows = contents.size();
        int cols = contents.get(0).size();
        
        BorderFill normalBorderFill = borderFillRegistry.getBorderFillByName("default");
        BorderFill headerBorderFill = borderFillRegistry.getBorderFillByName("grayFill");
        Table table = TableBuilder.buildTable(rows, cols, contents, normalBorderFill.id(), headerBorderFill.id());

        Para para = new Para();
        para.id("0");
        para.paraPrIDRef(styleRegistry.getStyleById("표 본문").paraPrIDRef());
        para.styleIDRef(styleRegistry.getStyleById("표 본문").id());
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        para.addNewRun().addNewTable().copyFrom(table);
        section.addPara(para);
    }

    public HWPXFile build() {
        return hwpxFile;
    }
}
