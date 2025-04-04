package com.hotbros.sejong.examples;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;

import com.hotbros.sejong.numbering.NumberingFactory;
import com.hotbros.sejong.numbering.NumberingRegistry;
import com.hotbros.sejong.numbering.NumberingService;
import com.hotbros.sejong.style.BasicStyleSetTemplate;
import com.hotbros.sejong.style.StyleService;
import com.hotbros.sejong.style.StyleSet;
import com.hotbros.sejong.style.StyleSetTemplate;

import java.io.File;

/**
 * 스타일과 넘버링 패키지를 모두 활용한 통합 예제
 */
public class CombinedExample {
    
    public static void main(String[] args) throws Exception {
        // 스타일과 넘버링을 모두 활용한 문서 생성 예제
        createCombinedExample();
    }
    
    /**
     * 스타일과 넘버링을 모두 활용한 문서 생성 예제
     */
    private static void createCombinedExample() throws Exception {
        System.out.println("=== 스타일과 넘버링 통합 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 스타일셋 설정
        StyleSetTemplate styleTemplate = new BasicStyleSetTemplate();
        StyleSet styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, styleTemplate);
        
        // 3. 넘버링 설정
        // 3-1. 넘버링 레지스트리와 서비스 생성
        NumberingRegistry numbRegistry = new NumberingRegistry();
        NumberingService numbService = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 3-2. 다양한 유형의 넘버링 생성 및 등록
        Numbering outline = NumberingFactory.create()
            .level(1)
                .format(NumberType1.ROMAN_CAPITAL)  // I, II, III 형식
                .text("^1.")
                .start(1)
                .done()
            .level(2)
                .format(NumberType1.HANGUL_SYLLABLE)  // □ 형식
                .text("□")
                .start(1)
                .done()
            .level(3)
                .format(NumberType1.HANGUL_SYLLABLE)  // ○ 형식
                .text("○")
                .start(1)
                .done()
            .level(4)
                .format(NumberType1.HANGUL_SYLLABLE)  // - 형식
                .text("-")
                .start(1)
                .done()
            .level(5)
                .format(NumberType1.HANGUL_SYLLABLE)  // ※ 형식
                .text("※")
                .start(1)
                .done()
            .build();
            
        numbRegistry.register("outline", outline);
        numbService.apply(outline);
        
        // 4. 문서 내용 생성
        // 4-1. 제목 추가
        addParagraph(hwpxFile, styleSet.getStyle("제목"), "스타일과 넘버링 통합 예제");
        
        // 4-2. 본문 내용 추가
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "이 예제는 스타일과 넘버링 기능을 함께 사용하는 방법을 보여줍니다.");
        
        // 4-3. 개요 1 수준 항목 - 레벨 1 넘버링 적용
        addParagraph(hwpxFile, styleSet.getStyle("개요1"), "넘버링과 스타일 기능 소개");
        
        // 4-4. 개요 2 수준 항목 - 레벨 2 넘버링 적용
        addParagraph(hwpxFile, styleSet.getStyle("개요2"), "넘버링 기능 활용");
        
        // 4-5. 본문 내용 추가
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "넘버링은 문서의 구조를 표현하는 데 중요한 역할을 합니다.");
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "다양한 형식의 넘버링을 적용하여 문서를 구조화할 수 있습니다.");
        
        // 4-6. 개요 2 수준 항목 - 레벨 2 넘버링 적용
        addParagraph(hwpxFile, styleSet.getStyle("개요2"), "스타일 기능 활용");
        
        // 4-7. 본문 내용 추가
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "스타일은 문서의 일관된 모양을 유지하는 데 중요한 역할을 합니다.");
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "미리 정의된 스타일을 사용하면 문서 작성이 더 효율적이고 일관성 있게 됩니다.");
        
        // 4-8. 개요 1 수준 항목 - 레벨 1 넘버링 적용
        addParagraph(hwpxFile, styleSet.getStyle("개요1"), "통합 활용 사례");
        
        // 4-9. 본문 내용 추가
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "스타일과 넘버링을 함께 사용하면 전문적인 문서를 쉽게 작성할 수 있습니다.");
        addParagraph(hwpxFile, styleSet.getStyle("개요3"), "이 예제는 Sejong 라이브러리의 다양한 기능을 활용하는 방법을 보여줍니다.");
        
        // 5. 파일 저장
        File outputFile = new File("CombinedExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("통합 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 지정된 스타일을 적용하여 문단을 추가합니다.
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
} 