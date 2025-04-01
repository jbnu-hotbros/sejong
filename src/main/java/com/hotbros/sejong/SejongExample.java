package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * 리팩토링된 StyleBuilder와 StyleService를 사용한 예제 클래스
 */
public class SejongExample {
    // 스타일셋 이름 상수
    public static final String BASIC_STYLE_SET = "기본";
    public static final String MODERN_STYLE_SET = "모던";
    
    // 스타일셋 관리를 위한 Map 
    private static final Map<String, StyleSet> styleSets = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // hwpxlib의 BlankFileMaker로 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 스타일셋 초기화
        initializeStyleSets(hwpxFile);
        
        // 사용 가능한 스타일셋 목록 출력
        System.out.println("사용 가능한 스타일셋:");
        for (String name : styleSets.keySet()) {
            System.out.println(" - " + name);
        }
        
        // 기본 스타일셋 사용 예제
        useBasicStyleSet(hwpxFile);
        
        // 모던 스타일셋 사용 예제
        useModernStyleSet(hwpxFile);
        
        // 동적 스타일셋 선택 예제
        String userSelectedStyleSetName = "모던"; // 사용자 입력이나 설정에서 가져올 수 있음
        useSelectedStyleSet(hwpxFile, userSelectedStyleSetName);
        
        // 개별 스타일 생성 예제 (기존 방식 예시)
        useIndividualStyles(hwpxFile);
        
        // 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "style_set_example.hwpx");
        System.out.println("파일이 성공적으로 저장되었습니다: style_set_example.hwpx");
    }
    
    /**
     * 스타일셋 맵을 초기화합니다.
     */
    private static void initializeStyleSets(HWPXFile hwpxFile) {
        styleSets.put(BASIC_STYLE_SET, new BasicStyleSet(hwpxFile));
        styleSets.put(MODERN_STYLE_SET, new ModernStyleSet(hwpxFile));
    }
    
    /**
     * 스타일셋을 이름으로 가져옵니다.
     */
    public static StyleSet getStyleSet(String name) {
        StyleSet styleSet = styleSets.get(name);
        if (styleSet == null) {
            throw new IllegalArgumentException("등록되지 않은 스타일셋 이름: " + name);
        }
        return styleSet;
    }
    
    /**
     * 사용 가능한 스타일셋 이름 배열을 반환합니다.
     */
    public static String[] getAvailableStyleSetNames() {
        return styleSets.keySet().toArray(new String[0]);
    }
    
    /**
     * 기본 스타일셋을 사용하는 예제
     */
    private static void useBasicStyleSet(HWPXFile hwpxFile) {
        StyleSet basicStyleSet = styleSets.get(BASIC_STYLE_SET);
        
        addNewParagraph(hwpxFile, 0, basicStyleSet.title(), "기본 스타일셋 예제");
        addNewParagraph(hwpxFile, 0, basicStyleSet.outline1(), "1. 기본 스타일셋 특징");
        addNewParagraph(hwpxFile, 0, basicStyleSet.outline2(), "1.1 간결한 디자인");
    }
    
    /**
     * 모던 스타일셋을 사용하는 예제
     */
    private static void useModernStyleSet(HWPXFile hwpxFile) {
        StyleSet modernStyleSet = styleSets.get(MODERN_STYLE_SET);
        
        addNewParagraph(hwpxFile, 0, modernStyleSet.title(), "모던 스타일셋 예제");
        addNewParagraph(hwpxFile, 0, modernStyleSet.outline1(), "2. 모던 스타일셋 특징");
        addNewParagraph(hwpxFile, 0, modernStyleSet.outline2(), "2.1 현대적인 색상");
    }
    
    /**
     * 동적으로 선택된 스타일셋을 사용하는 예제
     */
    private static void useSelectedStyleSet(HWPXFile hwpxFile, String styleSetName) {
        StyleSet selectedStyleSet = getStyleSet(styleSetName);
        
        addNewParagraph(hwpxFile, 0, selectedStyleSet.title(), "동적 선택 스타일셋 예제 (" + styleSetName + ")");
        addNewParagraph(hwpxFile, 0, selectedStyleSet.outline1(), "3. 동적 스타일셋 선택");
        addNewParagraph(hwpxFile, 0, selectedStyleSet.outline2(), "3.1 사용자 선택에 따른 스타일 적용");
        addNewParagraph(hwpxFile, 0, selectedStyleSet.outline3(), "3.1.1 맵 기반 스타일셋 관리");
    }
    
    /**
     * 기존 방식대로 개별 스타일을 생성하고 사용하는 예제
     */
    private static void useIndividualStyles(HWPXFile hwpxFile) {
        // 커스텀 스타일 생성 및 등록
        StyleResult customStyleResult = StyleBuilder.create("커스텀 스타일", "Custom Style")
                .withCharPr(charPr -> {
                    charPr.heightAnd(1200)
                        .textColor("#0000FF");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.CENTER);
                })
                .buildResult();
        
        Style customStyle = StyleService.registerResult(hwpxFile, customStyleResult);
        
        // 문단 추가
        addNewParagraph(hwpxFile, 0, customStyle, "기존 방식으로 생성한 스타일 예제");
    }

    /**
     * 문서에 새로운 문단을 추가합니다.
     */
    private static Para addNewParagraph(HWPXFile hwpxFile, int sectionIndex, Style style, String text) {
        if (hwpxFile.sectionXMLFileList().count() <= sectionIndex) {
            throw new IllegalArgumentException("유효하지 않은 섹션 인덱스: " + sectionIndex);
        }
        
        if (style == null) {
            throw new IllegalArgumentException("스타일 객체가 null입니다");
        }

        var section = hwpxFile.sectionXMLFileList().get(sectionIndex);
        var para = section.addNewPara();
        
        // 스타일 ID 설정
        para.styleIDRef(style.id());
        
        // 스타일에서 직접 문단 모양 ID와 글자 모양 ID 가져오기
        String paraPrIDRef = style.paraPrIDRef();
        if (paraPrIDRef != null) {
            para.paraPrIDRef(paraPrIDRef);
        }
        
        // 글자 모양 ID 설정을 위한 Run 추가
        var run = para.addNewRun();
        
        String charPrIDRef = style.charPrIDRef();
        if (charPrIDRef != null) {
            // Run에 직접 charPrIDRef 설정
            run.charPrIDRef(charPrIDRef);
        }
        
        run.addNewT().addText(text);
        return para;
    }
}