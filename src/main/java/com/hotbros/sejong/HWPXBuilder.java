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

    /**
     * 스타일 이름, 텍스트, pageBreak 등 주요 속성을 받아 Para를 생성하는 공통 메서드
     */
    private Para createPara(String styleName, String text, boolean pageBreak) {
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
        if (text != null) {
            para.addRun(createStyledRun(style, text));
        }
        return para;
    }

    /**
     * 첫 문단은 교체(set), 이후 문단은 추가(add)하는 내부 유틸
     */
    private void addParaSmart(Para para) {
        if (!hasFirstParagraphText) {
            Para first = section.getPara(0);
            first.styleIDRef(para.styleIDRef());
            first.paraPrIDRef(para.paraPrIDRef());
            first.pageBreak(para.pageBreak());
            first.columnBreak(para.columnBreak());
            first.merged(para.merged());
            first.removeAllRuns();
            for (Run run : para.runs()) {
                first.addRun(run);
            }
            hasFirstParagraphText = true;
        } else {
            section.addPara(para);
        }
    }

    // 스타일 이름, 텍스트, 페이지브레이크로 Para를 생성하고 바로 섹션에 추가
    public void addParagraph(String styleName, String text, boolean pageBreak) {
        Para para = createPara(styleName, text, pageBreak);
        addParaSmart(para);
    }

    // 첫 번째 문단 텍스트를 스타일과 함께 교체
    // 더 이상 필요 없음: addParagraph에서 자동 처리

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

        Para para = createPara("표 본문", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    public HWPXFile build() {
        return hwpxFile;
    }
}
