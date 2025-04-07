package com.hotbros.sejong.examples;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.LineSeg;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import com.hotbros.sejong.writer.HWPXWriter;

import com.hotbros.sejong.numbering.NumberingFactory;
import com.hotbros.sejong.numbering.NumberingRegistry;
import com.hotbros.sejong.numbering.NumberingService;
import com.hotbros.sejong.style.BasicStyleSetTemplate;
import com.hotbros.sejong.style.StyleService;
import com.hotbros.sejong.style.StyleSet;
import com.hotbros.sejong.style.StyleSetTemplate;

import java.io.File;

/**
 * ODT 변환 문제 디버깅을 위한 단계별 테스트 예제
 */
public class OdtDebugExample {
    
    public static void main(String[] args) throws Exception {
        // 각 테스트 케이스별로 진행
        testCase1_BlankFile();
        testCase2_BasicText();
        testCase3_OnlyStyles();
        testCase4_OnlyNumbering();
        testCase5_CombinedBasic();
    }
    
    /**
     * 테스트 케이스 1: 기본 빈 파일만 생성
     */
    private static void testCase1_BlankFile() throws Exception {
        System.out.println("=== 테스트 케이스 1: 빈 파일 생성 ===");
        
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        File outputFile = new File("debug_case1_blank.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 테스트 케이스 2: 간단한 텍스트만 추가
     */
    private static void testCase2_BasicText() throws Exception {
        System.out.println("=== 테스트 케이스 2: 간단한 텍스트 추가 ===");
        
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 간단한 텍스트만 추가 (lineseg와 추가 필드 포함)
        addParagraphWithLineSeg(hwpxFile, null, "첫 번째 문단입니다.");
        addParagraphWithLineSeg(hwpxFile, null, "두 번째 문단입니다.");
        
        File outputFile = new File("debug_case2_text.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 테스트 케이스 3: 스타일만 적용
     */
    private static void testCase3_OnlyStyles() throws Exception {
        System.out.println("=== 테스트 케이스 3: 스타일만 적용 ===");
        
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 스타일셋 설정
        StyleSetTemplate styleTemplate = new BasicStyleSetTemplate();
        StyleSet styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, styleTemplate);
        
        // 스타일 적용한 문단 추가
        // addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("제목"), "스타일 테스트 제목");
        // addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("개요1"), "개요 1 레벨 텍스트");
        // addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("개요2"), "개요 2 레벨 텍스트");
        // addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("개요3"), "개요 3 레벨 텍스트");
        
        File outputFile = new File("debug_case3_styles.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 테스트 케이스 4: 넘버링만 적용
     */
    private static void testCase4_OnlyNumbering() throws Exception {
        System.out.println("=== 테스트 케이스 4: 넘버링만 적용 ===");
        
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 넘버링 설정
        NumberingRegistry numbRegistry = new NumberingRegistry();
        NumberingService numbService = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 간단한 넘버링 생성 및 등록
        Numbering simpleNumbering = NumberingFactory.create()
            .level(1)
                .format(NumberType1.DIGIT)
                .text("^1.")
                .start(1)
                .done()
            .build();
            
        numbRegistry.register("simple", simpleNumbering);
        numbService.apply(simpleNumbering);
        
        // 넘버링 적용 문단 추가
        addParagraphWithLineSeg(hwpxFile, null, "넘버링 테스트 1");
        addParagraphWithLineSeg(hwpxFile, null, "넘버링 테스트 2");
        addParagraphWithLineSeg(hwpxFile, null, "넘버링 테스트 3");
        
        File outputFile = new File("debug_case4_numbering.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 테스트 케이스 5: 간단한 형태의 스타일+넘버링 조합
     */
    private static void testCase5_CombinedBasic() throws Exception {
        System.out.println("=== 테스트 케이스 5: 기본 조합 테스트 ===");
        
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 스타일셋 설정
        StyleSetTemplate styleTemplate = new BasicStyleSetTemplate();
        StyleSet styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, styleTemplate);
        
        // 넘버링 설정
        NumberingRegistry numbRegistry = new NumberingRegistry();
        NumberingService numbService = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 간단한 넘버링 생성 및 등록
        Numbering basicNumbering = NumberingFactory.create()
            .level(1)
                .format(NumberType1.DIGIT)
                .text("^1.")
                .start(1)
                .done()
            .level(2)
                .format(NumberType1.LATIN_SMALL)
                .text("^1.^2.")
                .start(1)
                .done()
            .build();
            
        numbRegistry.register("basic", basicNumbering);
        numbService.apply(basicNumbering);
        
        // 스타일과 넘버링이 조합된 문단 추가
        addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("제목"), "스타일과 넘버링 조합 테스트");
        addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("개요1"), "개요 레벨 1 with 넘버링");
        addParagraphWithLineSeg(hwpxFile, styleSet.getStyle("개요2"), "개요 레벨 2 with 넘버링");
        
        File outputFile = new File("debug_case5_combined.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 지정된 스타일을 적용하여 문단을 추가합니다.
     */
    private static Para addParagraph(HWPXFile hwpxFile, Style style, String text) {
        // 첫 번째 섹션에 문단 추가
        Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        
        // 스타일이 있는 경우 스타일 ID 참조 설정
        if (style != null) {
            para.styleIDRef(style.id());
            
            // 스타일이 참조하는 문단 모양 ID 설정
            if (style.paraPrIDRef() != null) {
                para.paraPrIDRef(style.paraPrIDRef());
            }
        }
        
        // 텍스트가 있는 경우 추가
        if (text != null && !text.isEmpty()) {
            Run run = para.addNewRun();
            
            // 스타일이 있고 참조하는 글자 모양 ID가 있는 경우 설정
            if (style != null && style.charPrIDRef() != null) {
                run.charPrIDRef(style.charPrIDRef());
            }
            
            run.addNewT().addText(text);
        }
        
        return para;
    }
    
    /**
     * lineseg와 추가 필드가 포함된 문단을 추가합니다.
     */
    private static Para addParagraphWithLineSeg(HWPXFile hwpxFile, Style style, String text) {
        // 첫 번째 섹션에 문단 추가
        Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        
        // 기본 문단 속성 설정
        para.paraPrIDRef("0");
        para.styleIDRef("0");
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        
        // 텍스트가 있는 경우 추가
        if (text != null && !text.isEmpty()) {
            Run run = para.addNewRun();
            run.charPrIDRef("0");
            run.addNewT().addText(text);
        }
        
        // linesegarray 추가
        para.createLineSegArray();
        LineSeg lineSeg = para.lineSegArray().addNew();
        lineSeg.textpos(0);
        lineSeg.vertpos(3200);
        lineSeg.vertsize(1000);
        lineSeg.textheight(1000);
        lineSeg.baseline(850);
        lineSeg.spacing(600);
        lineSeg.horzpos(0);
        lineSeg.horzsize(42520);
        lineSeg.flags(393216);
        
        return para;
    }
} 