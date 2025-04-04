package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;

import com.hotbros.sejong.style.StyleBuilder;
import com.hotbros.sejong.style.StyleService;
import com.hotbros.sejong.style.StyleSet;
import com.hotbros.sejong.style.StyleTemplate;
import com.hotbros.sejong.style.StyleSetTemplate;
import com.hotbros.sejong.style.BasicStyleSetTemplate;

import java.io.File;

/**
 * Sejong 라이브러리 사용 예제
 */
public class SejongExample {
    
    public static void main(String[] args) throws Exception {
        // 1. 기본 스타일셋 예제
        basicStyleSetExample();
    }
    
    /**
     * 기본 스타일셋을 사용한 문서 생성 예제
     */
    private static void basicStyleSetExample() throws Exception {
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 스타일셋 템플릿 생성
        StyleSetTemplate template = new BasicStyleSetTemplate();
        
        // 3. 스타일셋 템플릿을 등록하여 사용 가능한 스타일셋 얻기
        StyleSet styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, template);
        
        // 4. 등록된 스타일셋을 사용하여 문서 작성
        // 여기서는 정적 메서드 호출 방식과 동적 스타일 이름 지정 방식을 둘 다 보여줍니다.
        
        // 정적 메서드 호출 방식 (기존 방식)
        addParagraph(hwpxFile, styleSet.title(), "Sejong 라이브러리 사용 예제");
        
        // 동적 스타일 이름 지정 방식 (새로운 방식)
        addParagraphWithStyleName(hwpxFile, styleSet, "개요1", "1. 스타일셋 소개");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "1.1 스타일셋이란?");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "스타일셋은 문서에서 사용할 스타일들의 모음입니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "미리 정의된 스타일을 사용하면 일관된 문서 작성이 가능합니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "1.2 스타일셋 종류");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "BasicStyleSetTemplate: 기본 스타일셋 템플릿");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "ModernStyleSetTemplate: 현대적인 디자인의 스타일셋 템플릿");
        
        addParagraphWithStyleName(hwpxFile, styleSet, "개요1", "2. 새로운 스타일 시스템 구조");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "2.1 스타일셋 템플릿 (StyleSetTemplate)");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "등록되기 전 스타일 정의를 담당합니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "재사용 가능한 템플릿으로 작동합니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "2.2 스타일셋 (StyleSet)");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "등록된 스타일을 제공합니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "문서 작성에 직접 사용됩니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "2.3 스타일 서비스 (StyleService)");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "스타일 등록을 담당합니다.");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요3", "템플릿을 등록하여 사용 가능한 스타일셋을 생성합니다.");
        
        addParagraphWithStyleName(hwpxFile, styleSet, "개요1", "3. 사용 방법");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "3.1 스타일셋 템플릿 생성");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "3.2 템플릿 등록하여 스타일셋 얻기");
        addParagraphWithStyleName(hwpxFile, styleSet, "개요2", "3.3 스타일셋으로 문서 작성");
        
        // 5. 파일 저장
        File outputFile = new File("StyleSetExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 지정된 스타일을 적용하여 문단을 추가합니다.
     * 스타일의 ID뿐만 아니라 문단 모양 참조와 글자 모양 참조도 함께 설정합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param style 적용할 스타일
     * @param text 문단에 들어갈 텍스트
     * @return 추가된 문단 객체
     */
    private static Para addParagraph(HWPXFile hwpxFile, Style style, String text) {
        // 첫 번째 섹션에 문단 추가
        Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        
        // 스타일 ID 참조 설정
        para.styleIDRef(style.id());
        
        // 스타일이 참조하는 문단 모양 ID 설정
        if (style.paraPrIDRef() != null) {
            para.paraPrIDRef(style.paraPrIDRef());
        }
        
        // 텍스트가 있는 경우 추가
        if (text != null && !text.isEmpty()) {
            Run run = para.addNewRun();
            
            // 스타일이 참조하는 글자 모양 ID 설정
            if (style.charPrIDRef() != null) {
                run.charPrIDRef(style.charPrIDRef());
            }
            
            run.addNewT().addText(text);
        }
        
        return para;
    }
    
    /**
     * 스타일 이름을 사용하여 문단을 추가합니다.
     * 런타임에 스타일을 결정하는 경우 사용합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param styleSet 스타일셋
     * @param styleName 스타일 이름 ("제목", "개요1", "개요2" 등)
     * @param text 문단에 들어갈 텍스트
     * @return 추가된 문단 객체, 스타일이 없으면 기본 스타일로 추가
     */
    private static Para addParagraphWithStyleName(HWPXFile hwpxFile, StyleSet styleSet, String styleName, String text) {
        Style style = styleSet.getStyle(styleName);
        if (style == null) {
            System.out.println("경고: '" + styleName + "' 스타일을 찾을 수 없습니다. 기본 스타일을 사용합니다.");
            // 기본 스타일 사용(첫 번째 스타일)
            style = styleSet.getAllStyles()[0];
        }
        return addParagraph(hwpxFile, style, text);
    }
    
    /**
     * 개별 스타일을 직접 생성하고 등록하는 예제
     */
    private static void createCustomStyle(HWPXFile hwpxFile) {
        // 커스텀 스타일 생성
        StyleTemplate customStyleTemplate = StyleBuilder.create("커스텀 스타일", "Custom Style")
                .withCharPr(charPr -> {
                    charPr.heightAnd(1800);
                    charPr.createBold();
                    charPr.textColor("#FF0000"); // 빨간색
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    
                    paraPr.createMargin();
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(500);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(200);
                })
                .buildTemplate();
        
        // 스타일 등록
        Style customStyle = StyleService.registerStyleTemplate(hwpxFile, customStyleTemplate);
        
        // 등록된 스타일로 문단 추가
        addParagraph(hwpxFile, customStyle, "커스텀 스타일이 적용된 문단입니다.");
    }
}