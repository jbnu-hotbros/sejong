package com.hotbros.sejong;

import com.hotbros.sejong.content.ParagraphBuilder;
import com.hotbros.sejong.style.StyleSetManager;
import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;

/**
 * 리팩토링된 StyleBuilder와 StyleService를 사용한 예제 클래스
 */
public class SejongExample {
    
    public static void main(String[] args) throws Exception {
        // hwpxlib의 BlankFileMaker로 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 스타일셋 매니저 생성 및 초기화
        StyleSetManager styleSetManager = new StyleSetManager(hwpxFile);
        
        // 문단 빌더 생성
        ParagraphBuilder paragraphBuilder = new ParagraphBuilder(hwpxFile);
        
        // 사용 가능한 스타일셋 목록 출력
        System.out.println("사용 가능한 스타일셋:");
        for (String name : styleSetManager.getAvailableStyleSetNameSet()) {
            System.out.println(" - " + name);
        }
        
        // 기본 스타일셋 사용 예제
        useBasicStyleSet(hwpxFile, styleSetManager, paragraphBuilder);
        
        // 모던 스타일셋 사용 예제
        useModernStyleSet(hwpxFile, styleSetManager, paragraphBuilder);
        
        // 불렛 스타일셋 사용 예제
        useBulletStyleSet(hwpxFile, styleSetManager, paragraphBuilder);
        
        // 동적 스타일셋 선택 예제
        String userSelectedStyleSetName = "모던"; // 사용자 입력이나 설정에서 가져올 수 있음
        useSelectedStyleSet(hwpxFile, styleSetManager, paragraphBuilder, userSelectedStyleSetName);
        
        // 개별 스타일 생성 예제 (기존 방식 예시)
        useIndividualStyles(hwpxFile, paragraphBuilder);
        
        // 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "style_set_example.hwpx");
        System.out.println("파일이 성공적으로 저장되었습니다: style_set_example.hwpx");
    }
    
    /**
     * 기본 스타일셋을 사용하는 예제
     */
    private static void useBasicStyleSet(HWPXFile hwpxFile, StyleSetManager styleSetManager, ParagraphBuilder paragraphBuilder) {
        StyleSet basicStyleSet = styleSetManager.getStyleSet("기본");
        
        paragraphBuilder.addParagraph(0, basicStyleSet.title(), "기본 스타일셋 예제");
        paragraphBuilder.addParagraph(0, basicStyleSet.outline1(), "1. 기본 스타일셋 특징");
        paragraphBuilder.addParagraph(0, basicStyleSet.outline2(), "1.1 간결한 디자인");
    }
    
    /**
     * 모던 스타일셋을 사용하는 예제
     */
    private static void useModernStyleSet(HWPXFile hwpxFile, StyleSetManager styleSetManager, ParagraphBuilder paragraphBuilder) {
        StyleSet modernStyleSet = styleSetManager.getStyleSet("모던");
        
        paragraphBuilder.addParagraph(0, modernStyleSet.title(), "모던 스타일셋 예제");
        paragraphBuilder.addParagraph(0, modernStyleSet.outline1(), "2. 모던 스타일셋 특징");
        paragraphBuilder.addParagraph(0, modernStyleSet.outline2(), "2.1 현대적인 색상");
    }
    
    /**
     * 불렛 스타일셋을 사용하는 예제
     */
    private static void useBulletStyleSet(HWPXFile hwpxFile, StyleSetManager styleSetManager, ParagraphBuilder paragraphBuilder) {
        StyleSet bulletStyleSet = styleSetManager.getStyleSet("불렛");
        
        paragraphBuilder.addParagraph(0, bulletStyleSet.title(), "불렛 스타일셋 예제");
        paragraphBuilder.addParagraph(0, bulletStyleSet.outline1(), "불렛 개요 레벨 1");
        paragraphBuilder.addParagraph(0, bulletStyleSet.outline2(), "불렛 개요 레벨 2");
        paragraphBuilder.addParagraph(0, bulletStyleSet.outline3(), "불렛 개요 레벨 3");
        paragraphBuilder.addParagraph(0, bulletStyleSet.outline4(), "불렛 개요 레벨 4");
        paragraphBuilder.addParagraph(0, bulletStyleSet.outline5(), "불렛 개요 레벨 5");
    }
    
    /**
     * 동적으로 선택된 스타일셋을 사용하는 예제
     */
    private static void useSelectedStyleSet(HWPXFile hwpxFile, StyleSetManager styleSetManager, ParagraphBuilder paragraphBuilder, String styleSetName) {
        StyleSet selectedStyleSet = styleSetManager.getStyleSet(styleSetName);
        
        paragraphBuilder.addParagraph(0, selectedStyleSet.title(), "동적 선택 스타일셋 예제 (" + styleSetName + ")");
        paragraphBuilder.addParagraph(0, selectedStyleSet.outline1(), "3. 동적 스타일셋 선택");
        paragraphBuilder.addParagraph(0, selectedStyleSet.outline2(), "3.1 사용자 선택에 따른 스타일 적용");
        paragraphBuilder.addParagraph(0, selectedStyleSet.outline3(), "3.1.1 맵 기반 스타일셋 관리");
    }
    
    /**
     * 기존 방식대로 개별 스타일을 생성하고 사용하는 예제
     */
    private static void useIndividualStyles(HWPXFile hwpxFile, ParagraphBuilder paragraphBuilder) {
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
        paragraphBuilder.addParagraph(0, customStyle, "기존 방식으로 생성한 스타일 예제");
    }
}