package com.hotbros.sejong;

import java.util.List;

import com.hotbros.sejong.dto.CharPrAttributes;
import com.hotbros.sejong.dto.ParaHeadAttributes;
import com.hotbros.sejong.dto.ParaPrAttributes;
import com.hotbros.sejong.dto.StyleAttributes;
import com.hotbros.sejong.dto.StyleBlock;
import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.builder.NumberingBuilder;
import com.hotbros.sejong.util.StyleIdAllocator;
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
import com.hotbros.sejong.style.StylePresets;
import com.hotbros.sejong.style.StyleBuilderBlock;
import com.hotbros.sejong.util.FontPresetManager;
import com.hotbros.sejong.style.NumberingPresets;

public class HWPXBuilder {
    private static final String ORIGINAL_STYLE_ID = "0"; // 예: 바탕글 스타일 ID
    private static final String ORIGINAL_OUTLINE_ID_1 = "2"; // 예: 바탕글 넘버링 ID
    private static final String ORIGINAL_OUTLINE_ID_2 = "3"; // 예: 바탕글 넘버링 ID
    private static final String ORIGINAL_OUTLINE_ID_3 = "4"; // 예: 바탕글 넘버링 ID
    private static final String ORIGINAL_OUTLINE_ID_4 = "5"; // 예: 바탕글 넘버링 ID
    private static final String ORIGINAL_OUTLINE_ID_5 = "6"; // 예: 바탕글 넘버링 ID
    private static final String ORIGINAL_OUTLINE_ID_6 = "7"; // 예: 바탕글 넘버링 ID
    private static final String ORIGINAL_OUTLINE_ID_7 = "8"; // 예: 바탕글 넘버링 ID

    private HWPXFile hwpxFile;
    private RefList refList;
    private StyleIdAllocator allocator;
    private SectionXMLFile section;
    private boolean hasFirstParagraphText = false;


    public HWPXBuilder() {
        this.hwpxFile = BlankFileMaker.make();
        this.refList = hwpxFile.headerXMLFile().refList();
        this.allocator = new StyleIdAllocator();
        this.section = hwpxFile.sectionXMLFileList().get(0);
        
        // 기본 스타일, 넘버링 등 초기화
        init();
    }

    /**
     * HWPXBuilder 초기화 - 기본 스타일과 넘버링을 설정합니다.
     * 문서 생성에 필요한 기본 요소들을 미리 등록합니다.
     */
    private void init() {
        // 폰트 프리셋 등록
        FontPresetManager.registerPresets(refList);
        // 스타일 등록
        addAllPresetStyles();
        // 넘버링 등록
        addDefaultNumbering();
        System.out.println("✅ HWPXBuilder 초기화 완료: 기본 스타일 및 넘버링 등록됨");
    }
    
    /**
     * 모든 프리셋 스타일을 추가합니다.
     * StylePresets 클래스에서 정의된 모든 스타일을 등록합니다.
     */
    private void addAllPresetStyles() {
        // 각 프리셋 스타일 등록
        addPresetStyle(StylePresets.titlePreset(hwpxFile));
        addPresetStyle(StylePresets.bodyPreset(hwpxFile));
        addPresetStyle(StylePresets.heading1Preset(hwpxFile));
        addPresetStyle(StylePresets.heading2Preset(hwpxFile));
        addPresetStyle(StylePresets.heading3Preset(hwpxFile));
        addPresetStyle(StylePresets.tableHeaderPreset(hwpxFile));
        addPresetStyle(StylePresets.tableCellPreset(hwpxFile));
        
        System.out.println("✅ 프리셋 스타일 등록 완료: 총 8개 스타일");
    }
    
    /**
     * 단일 프리셋 스타일을 추가합니다.
     */
    private void addPresetStyle(StyleBuilderBlock block) {
        // StyleBuilderBlock에서 빌더를 통해 객체 생성
        CharPr charPr = block.getCharPrBuilder().build();
        ParaPr paraPr = block.getParaPrBuilder().build();
        Style style = block.getStyleBuilder().build();
        
        // 실제 ID 할당
        String styleId = String.valueOf(allocator.nextStyleId());
        String charPrId = String.valueOf(allocator.nextCharPrId());
        String paraPrId = String.valueOf(allocator.nextParaPrId());
        
        charPr.id(charPrId);
        paraPr.id(paraPrId);
        style.id(styleId);
        style.charPrIDRef(charPrId);
        style.paraPrIDRef(paraPrId);

        // RefList에 등록
        if (refList.charProperties() == null) {
            refList.createCharProperties();
        }
        refList.charProperties().add(charPr);

        if (refList.paraProperties() == null) {
            refList.createParaProperties();
        }
        refList.paraProperties().add(paraPr);

        if (refList.styles() == null) {
            refList.createStyles();
        }
        refList.styles().add(style);

        System.out.println("  - 스타일 추가됨: " + style.name() + " (ID: " + style.id() + ")");
    }
    
    /**
     * 기본 넘버링을 추가합니다.
     * 문서에서 사용할 기본 넘버링 형식을 등록합니다.
     */
    private void addDefaultNumbering() {
        Numbering updated = NumberingPresets.createDefaultNumbering(refList);
        // 기존 넘버링을 모두 삭제하고 새 넘버링을 추가
        refList.numberings().removeAll();
        refList.numberings().add(updated);

        System.out.println("기본 넘버링 추가 완료");
        System.out.println("  - 넘버링 ID: " + updated.id());
        System.out.println("  - 넘버링 시작: " + updated.start());
    }

    public void addFirstStyledText(Style style, String text) {
        if(hasFirstParagraphText) {
            return;
        }

        Run run= new Run();
        run.charPrIDRef(style.charPrIDRef());
        run.addNewT().addText(text);

        Para para=section.getPara(0);
        para.paraPrIDRef(style.paraPrIDRef());

        para.removeRun(0);
        para.addRun(run);
    }

    public Para createStyledParagraph(Style style, String text, boolean pageBreak) {
        if (style == null || text == null) {
            System.out.println("style 또는 text가 null입니다.");
            return null;
        }

        Run run = new Run();
        run.charPrIDRef(style.charPrIDRef());
        run.addNewT().addText(text);

        // 첫 번째 섹션에 문단 추가
        Para para = new Para();
        para.id("0");
        para.styleIDRef(style.id());
        para.paraPrIDRef(style.paraPrIDRef());
        para.pageBreak(pageBreak);
        para.columnBreak(false);
        para.merged(false);
        para.addRun(run);

        return para;
    }

    public HWPXFile build() throws Exception {
        // 초기화는 이미 생성자에서 완료되었으므로 바로 결과 리턴
        return hwpxFile;
    }

    /**
     * 2차원 리스트 데이터를 받아 표를 생성하고 섹션에 추가합니다.
     * 헤더(첫 행)는 회색 배경, 나머지는 일반 셀로 처리합니다.
     */
    public void addTableWithHeader(List<List<String>> contents) {
        if (contents == null || contents.isEmpty() || contents.get(0).isEmpty()) {
            throw new IllegalArgumentException("contents가 비어있거나 올바르지 않습니다.");
        }
        // 1. BorderFill 정의 및 등록
        BorderFill normalBorderFill = TableBuilder.createBorderFill(false, null);  // 일반 셀용 보더 (배경색 없음)
        BorderFill headerBorderFill = TableBuilder.createBorderFill(true, "#E6E6E6");  // 헤더 셀용 보더 (회색 배경)
        TableBuilder.addBorderFill(hwpxFile, "3", normalBorderFill);         // 일반 셀용 보더 등록
        TableBuilder.addBorderFill(hwpxFile, "4", headerBorderFill);    // 헤더 셀용 보더 등록

        // 2. 표 생성 (헤더는 "4", 일반 셀은 "3" 사용)
        int rows = contents.size();
        int cols = contents.get(0).size();
        Table table = TableBuilder.buildTable(rows, cols, contents, "3", "4");

        // 3. Para 객체 생성 및 표 삽입
        Para para = new Para();
        para.id("table-para");
        para.paraPrIDRef("0");
        para.styleIDRef("0");
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        para.addNewRun().addNewTable().copyFrom(table);

        // 4. 섹션에 추가
        section.addPara(para);
    }
}
