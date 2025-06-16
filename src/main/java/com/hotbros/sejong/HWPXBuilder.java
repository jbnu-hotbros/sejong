package com.hotbros.sejong;

import java.util.List;

import com.hotbros.sejong.util.IdGenerator;
import com.hotbros.sejong.style.StylePreset;
import com.hotbros.sejong.style.StyleRegistry;
import com.hotbros.sejong.style.Theme;
import com.hotbros.sejong.table.BorderFillRegistry;
import com.hotbros.sejong.bullet.BulletRegistry;
import com.hotbros.sejong.font.FontRegistry;
import com.hotbros.sejong.section.SectionPreset;
import com.hotbros.sejong.table.TableBuilder;
import com.hotbros.sejong.table.TitleBoxMiddleBuilder;
import com.hotbros.sejong.table.TitleBoxMiddleGrayBuilder;
import com.hotbros.sejong.table.TitleBoxMainBuilder;
import com.hotbros.sejong.table.TitleBoxSubBuilder;
import com.hotbros.sejong.image.ImageBuilder;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Picture;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;

public class HWPXBuilder {
    private final HWPXFile hwpxFile;
    private final IdGenerator idGenerator;
    private final SectionXMLFile section;
    private final StyleRegistry styleRegistry;
    private final BorderFillRegistry borderFillRegistry;
    private final FontRegistry fontRegistry;
    private final RefList refList;
    private final BulletRegistry bulletRegistry;
    private final Theme currentTheme;
    private final ImageBuilder imageBuilder;

    private static final String NORMAL_PARA_ID = "0";

    private boolean hasFirstParagraphText = false;

    public HWPXBuilder() {
        this(Theme.GRAY); // 기본값은 BLUE 테마
    }

    public HWPXBuilder(Theme theme) {
        this.currentTheme = theme;
        this.hwpxFile = BlankFileMaker.make();
        this.idGenerator = new IdGenerator();
        this.section = hwpxFile.sectionXMLFileList().get(0);
        this.refList = hwpxFile.headerXMLFile().refList();
        
        this.fontRegistry = new FontRegistry(refList, idGenerator);
        this.borderFillRegistry = new BorderFillRegistry(refList, idGenerator);
        this.bulletRegistry = new BulletRegistry(refList, idGenerator);

        StylePreset stylePreset = new StylePreset(refList, fontRegistry, bulletRegistry, this.currentTheme);
        this.styleRegistry = new StyleRegistry(refList, idGenerator, stylePreset.getAllPresets());

        // ImageBuilder 초기화
        this.imageBuilder = new ImageBuilder(section, hwpxFile.contentHPFFile());

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

    // 본문스타일(기본스타일)로 텍스트를 추가하는 편의 메서드
    public void addBodyText(String text) {
        addParagraph("내용", text, false);
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

    // 메인 타이틀 박스 추가
    public void addTitleBoxMain(String title) {
        String borderFillId = borderFillRegistry.getBorderFillByName("default").id();
        String cellBorderFillId;
        
        // 테마에 따라 다른 BorderFill 사용
        if (currentTheme == Theme.GRAY) {
            cellBorderFillId = borderFillRegistry.getBorderFillByName("TITLE_BOX_MAIN_GRAY").id();
        } else {
            cellBorderFillId = borderFillRegistry.getBorderFillByName("TITLE_BOX_MAIN").id();
        }
        
        Style style = styleRegistry.getStyleByName("제목");

        Table table = TitleBoxMainBuilder.build(title, borderFillId, cellBorderFillId, style);

        Para para = createPara("내용", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    // 서브 타이틀 박스 추가
    public void addTitleBoxSub(String number, String title) {
        String[] styleNames = { "제목 테이블 번호", "내용", "제목 테이블 내용" };
        Style[] styles = new Style[3];
        for (int i = 0; i < 3; i++) {
            styles[i] = styleRegistry.getStyleByName(styleNames[i]);
        }
        String tableBorderFillId = borderFillRegistry.getBorderFillByName("default").id();
        String[] cellBorderFillIds = {
            borderFillRegistry.getBorderFillByName("TITLE_BOX_SUB_LEFT").id(),
            borderFillRegistry.getBorderFillByName("TITLE_BOX_SUB_CENTER").id(),
            borderFillRegistry.getBorderFillByName("TITLE_BOX_SUB_RIGHT").id()
        };
        Table table = TitleBoxSubBuilder.build(
            number, title,
            styles,
            tableBorderFillId,
            cellBorderFillIds
        );
        Para para = createPara("내용 왼쪽정렬", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    // 미들 타이틀 박스 추가
    public void addTitleBoxMiddle(String number, String title) {
        Style numberStyle = styleRegistry.getStyleByName("제목 테이블 번호");
        Style contentStyle = styleRegistry.getStyleByName("제목 테이블 내용");
        String tableBorderFillId = borderFillRegistry.getBorderFillByName("default").id();
        Table table;
        
        // 테마에 따라 다른 빌더 사용
        if (currentTheme == Theme.GRAY) {
            // GRAY 테마: 2컬럼 구조 사용
            String[] borderFillIds = {
                borderFillRegistry.getBorderFillByName("TITLE_BOX_MIDDLE_LEFT_GRAY").id(),
                borderFillRegistry.getBorderFillByName("TITLE_BOX_MIDDLE_CENTER_GRAY").id()
            };
            table = TitleBoxMiddleGrayBuilder.build(
                number, title,
                borderFillIds,
                numberStyle,
                contentStyle,
                tableBorderFillId
            );
        } else {
            // BLUE 테마: 3컬럼 구조 사용 (기존)
            String[] borderFillIds = {
                borderFillRegistry.getBorderFillByName("TITLE_BOX_MIDDLE_LEFT").id(),
                borderFillRegistry.getBorderFillByName("TITLE_BOX_MIDDLE_CENTER").id(),
                borderFillRegistry.getBorderFillByName("TITLE_BOX_MIDDLE_RIGHT").id()
            };
            table = TitleBoxMiddleBuilder.build(
                number, title,
                borderFillIds,
                numberStyle,
                contentStyle,
                tableBorderFillId
            );
        }
        
        Para para = createPara("내용", null, false);
        para.addNewRun().addNewTable().copyFrom(table);
        addParaSmart(para);
    }

    /**
     * 테마에 따라 다르게 처리되는 헤딩 메서드
     * level 0: BLUE 테마 → 서브타이틀 박스, GRAY 테마 → heading0 스타일
     * level 1: 모든 테마에서 개요1 스타일
     * level 2: 모든 테마에서 개요2 스타일
     * 
     * @param level 헤딩 레벨 (0: 서브타이틀/heading0, 1: 개요1, 2: 개요2)
     * @param number 번호 (BLUE 테마의 서브타이틀에서만 사용)
     * @param title 제목 텍스트
     */
    public void addThemedHeading(int level, String number, String title) {
        switch (level) {
            case 0:
                // level 0만 테마별로 다르게 처리
                switch (currentTheme) {
                    case BLUE:
                        addTitleBoxSub(number, title);
                        break;
                    case GRAY:
                        addParagraph("개요0", title, false); // heading0는 개요0 스타일로 처리
                        break;
                    default:
                        throw new IllegalArgumentException("지원하지 않는 테마: " + currentTheme);
                }
                break;
                
            case 1:
                // level 1은 모든 테마에서 개요1
                addParagraph("개요1", title, false);
                break;
                
            case 2:
                // level 2는 모든 테마에서 개요2
                addParagraph("개요2", title, false);
                break;
                
            default:
                throw new IllegalArgumentException("지원하지 않는 헤딩 레벨: " + level + " (지원: 0-2)");
        }
    }

    /**
     * 번호 없이 레벨과 제목만으로 테마별 헤딩을 추가하는 편의 메서드
     * @param level 헤딩 레벨
     * @param title 제목 텍스트
     */
    public void addThemedHeading(int level, String title) {
        addThemedHeading(level, null, title);
    }

    /**
     * 이미지를 문서에 추가합니다.
     * @param imageData 이미지 데이터 (바이트 배열)
     * @param width 이미지 너비
     * @param height 이미지 높이
     */
    public void addImage(byte[] imageData, int width, int height) {
        try {
            // 새로운 문단 생성
            Para para = createPara("내용 가운데정렬", null, false);
            
            // Run 생성 및 Picture 추가 (올바른 hwpxlib 패턴)
            Run run = para.addNewRun();
            Picture picture = run.addNewPicture();
            
            // ImageBuilder로 Picture 설정
            imageBuilder.configurePicture(picture, imageData, width, height);
            
            // 문단 추가
            addParaSmart(para);
            
        } catch (Exception e) {
            throw new RuntimeException("이미지 추가 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 기본 크기로 이미지를 문서에 추가합니다.
     * @param imageData 이미지 데이터 (바이트 배열)
     */
    public void addImage(byte[] imageData) {
        addImage(imageData, 400, 300); // 기본 크기: 400x300
    }

    public HWPXFile build() {
        return hwpxFile;
    }
}
