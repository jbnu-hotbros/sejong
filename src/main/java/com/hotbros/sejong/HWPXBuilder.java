package com.hotbros.sejong;

import java.util.Arrays;
import java.util.List;

import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.util.IdGenerator;
import com.hotbros.sejong.style.StylePreset;
import com.hotbros.sejong.style.StyleRegistry;
import com.hotbros.sejong.table.BorderFillRegistry;
import com.hotbros.sejong.bullet.BulletRegistry;
import com.hotbros.sejong.font.FontRegistry;
import com.hotbros.sejong.numbering.NumberingRegistry;
import com.hotbros.sejong.section.SectionPreset;
import com.hotbros.sejong.table.TableBuilder;
import com.hotbros.sejong.table.TitleBoxMiddleBuilder;
import com.hotbros.sejong.table.TitleBoxMainBuilder;
import com.hotbros.sejong.table.TitleBoxSubBuilder;

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
    private final HWPXFile hwpxFile;
    private final IdGenerator idGenerator;
    private final SectionXMLFile section;
    private final StyleRegistry styleRegistry;
    private final BorderFillRegistry borderFillRegistry;
    private final FontRegistry fontRegistry;
    private final RefList refList;
    private final BulletRegistry bulletRegistry;
    private final TitleBoxMiddleBuilder titleBoxMiddleBuilder;
    private final TitleBoxMainBuilder titleBoxMainBuilder;
    private final TitleBoxSubBuilder titleBoxSubBuilder;

    private static final String NORMAL_PARA_ID = "0";

    private boolean hasFirstParagraphText = false;

    public HWPXFile getHwpxFile() {
        return hwpxFile.clone();
    }

    public HWPXBuilder() {
        this.hwpxFile = BlankFileMaker.make();
        this.idGenerator = new IdGenerator();
        this.section = hwpxFile.sectionXMLFileList().get(0);
        this.refList = hwpxFile.headerXMLFile().refList();
        
        this.fontRegistry = new FontRegistry(refList, idGenerator);
        this.borderFillRegistry = new BorderFillRegistry(refList, idGenerator);
        this.bulletRegistry = new BulletRegistry(refList);

        this.styleRegistry = new StyleRegistry(refList, new StylePreset(hwpxFile, idGenerator, fontRegistry, bulletRegistry));
        this.titleBoxMiddleBuilder = new TitleBoxMiddleBuilder(borderFillRegistry);
        this.titleBoxMainBuilder = new TitleBoxMainBuilder(borderFillRegistry);
        this.titleBoxSubBuilder = new TitleBoxSubBuilder(borderFillRegistry);

        initialize();
    }
    

    private void initialize() {
        SectionPreset.setFirstRunSecPrDefault(section);
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
        Style style = styleRegistry.getStyleByName(styleName);

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
            first.columnBreak(para.columnBreak());
            
            first.getRun(0).charPrIDRef(para.getRun(0).charPrIDRef());
            
            first.removeLineSegArray();

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

        String headerStyleId = styleRegistry.getStyleByName("표 헤더").id();
        String bodyStyleId = styleRegistry.getStyleByName("표 내용").id();
        String headerCharPrId = styleRegistry.getStyleByName("표 헤더").charPrIDRef();
        String bodyCharPrId = styleRegistry.getStyleByName("표 내용").charPrIDRef();
        String headerParaPrId = styleRegistry.getStyleByName("표 헤더").paraPrIDRef();
        String bodyParaPrId = styleRegistry.getStyleByName("표 내용").paraPrIDRef();

        Table table = TableBuilder.buildTable(
            rows, cols, contents,
            normalBorderFill.id(), headerBorderFill.id(),
            headerStyleId, bodyStyleId,
            headerCharPrId, bodyCharPrId,
            headerParaPrId, bodyParaPrId
        );

        Para para = createPara("내용 가운데정렬", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    /**
     * Table 객체를 직접 받아서 문단에 삽입하는 메서드 (타이틀 테이블 등 커스텀 테이블용)
     */
    public void addTitleBoxMiddle(String number, String title) {
        String[] borderFillIds = Arrays.stream(TitleBoxMiddleBuilder.BORDER_FILL_NAMES)
            .map(name -> borderFillRegistry.getBorderFillByName(name).id())
            .toArray(String[]::new);

        Style numberStyle = styleRegistry.getStyleByName("제목 테이블 번호");
        Style contentStyle = styleRegistry.getStyleByName("제목 테이블 내용");

        Table table = titleBoxMiddleBuilder.build(number, title, 
            borderFillIds,
            numberStyle,
            contentStyle
        );

        Para para = createPara("내용", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    /**
     * Main TitleBox 테이블을 문단에 삽입하는 메서드
     */
    public void addTitleBoxMain(String title) {
        String borderFillId = borderFillRegistry.getBorderFillByName("default").id();
        String cellBorderFillId = borderFillRegistry.getBorderFillByName("TITLE_BOX_MAIN").id();
        Style style = styleRegistry.getStyleByName("제목");

        Table table = titleBoxMainBuilder.build(title, borderFillId, cellBorderFillId, style);

        Para para = createPara("내용", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    /**
     * Sub TitleBox 테이블을 문단에 삽입하는 메서드
     */
    public void addTitleBoxSub(String number, String title) {
        // 스타일 이름 배열 (left, center, right)
        String[] styleNames = { "제목 테이블 번호", "내용", "제목 테이블 내용" };
        Style[] styles = new Style[3];
        for (int i = 0; i < 3; i++) {
            styles[i] = styleRegistry.getStyleByName(styleNames[i]);
        }
        Table table = titleBoxSubBuilder.build(
            number, title,
            styles
        );
        Para para = createPara("내용 왼쪽정렬", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    public HWPXFile build() {
        return hwpxFile;
    }
}
