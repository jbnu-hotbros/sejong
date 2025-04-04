package com.hotbros.sejong.numbering;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;

import java.util.HashMap;
import java.util.Map;

/**
 * 넘버링 패키지의 사용 방법을 보여주는 예제 클래스
 */
public class NumberingPackageExample {

    public static void main(String[] args) throws Exception {
        // 기본 예제
        simpleNumberingExample();
        
        // 기존 넘버링 복제 및 수정 예제
        cloneNumberingExample();
        
        // 선택적 옵션 설정 예제
        optionalSettingsExample();
        
        // 다양한 스타일 비교 예제
        multipleStylesExample();
    }
    
    /**
     * 단순화된 넘버링 사용 예제
     */
    private static void simpleNumberingExample() throws Exception {
        System.out.println("=== 단순화된 넘버링 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 넘버링 레지스트리와 서비스 생성
        NumberingRegistry registry = new NumberingRegistry();
        NumberingService service = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 3. 넘버링 생성 및 등록
        Numbering simpleNumbering = NumberingFactory.create()
            .level(1)
                .format(NumberType1.DIGIT)
                .text("§^1.")
                .start(1)
                .done()
            .build();
            
        registry.register("simple", simpleNumbering);
        
        // 4. 등록된 넘버링 조회 및 문서에 적용
        Numbering retrievedNumbering = registry.get("simple");
        if (retrievedNumbering != null) {
            service.apply(retrievedNumbering);
        }
        
        System.out.println("단순화된 넘버링 예제가 완료되었습니다.");
    }
    
    /**
     * 기존 넘버링을 복제하고 수정하는 예제
     */
    private static void cloneNumberingExample() throws Exception {
        System.out.println("=== 기존 넘버링 복제 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 넘버링 레지스트리와 서비스 생성
        NumberingRegistry registry = new NumberingRegistry();
        NumberingService service = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 3. 복제할 넘버링 생성 및 등록
        Numbering clonedNumbering = NumberingFactory.create()
            .level(1)
                .format(NumberType1.DIGIT)
                .text("§^1.")
                .start(1)
                .done()
            .level(2)
                .format(NumberType1.LATIN_SMALL)
                .text("§^1.^2)")
                .start(1)
                .done()
            .level(3)
                .format(NumberType1.ROMAN_SMALL)
                .text("§^1.^2).^3.")
                .start(1)
                .done()
            .build();
            
        registry.register("cloned", clonedNumbering);
        
        // 4. 등록된 넘버링 조회 및 문서에 적용
        Numbering retrievedNumbering = registry.get("cloned");
        if (retrievedNumbering != null) {
            service.apply(retrievedNumbering);
        }
        
        System.out.println("넘버링 복제 예제가 완료되었습니다.");
    }
    
    /**
     * 선택적으로 옵션을 설정하는 예제
     */
    private static void optionalSettingsExample() throws Exception {
        System.out.println("=== 선택적 옵션 설정 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 넘버링 레지스트리와 서비스 생성
        NumberingRegistry registry = new NumberingRegistry();
        NumberingService service = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 3. 다양한 옵션의 넘버링 생성 및 등록
        Numbering numbering1 = NumberingFactory.create()
            .level(1)
                .format(NumberType1.ROMAN_CAPITAL)
                .done()
            .build();
        registry.register("roman", numbering1);
            
        Numbering numbering2 = NumberingFactory.create()
            .level(1)
                .start(5)
                .done()
            .level(2)
                .text("~^2~")
                .done()
            .build();
        registry.register("custom", numbering2);
        
        // 4. 등록된 넘버링 조회 및 문서에 적용
        Numbering retrieved1 = registry.get("roman");
        if (retrieved1 != null) {
            service.apply(retrieved1);
        }
        
        Numbering retrieved2 = registry.get("custom");
        if (retrieved2 != null) {
            service.apply(retrieved2);
        }
        
        System.out.println("선택적 옵션 설정 예제가 완료되었습니다.");
    }
    
    /**
     * 여러 스타일의 넘버링을 비교하는 예제
     */
    private static void multipleStylesExample() throws Exception {
        System.out.println("=== 다양한 넘버링 스타일 비교 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 넘버링 레지스트리와 서비스 생성
        NumberingRegistry registry = new NumberingRegistry();
        NumberingService service = new NumberingService(hwpxFile.headerXMLFile().refList());
        
        // 3. 다양한 스타일의 넘버링 생성
        Map<String, Numbering> numberings = new HashMap<>();
        
        // 십진 개요 번호
        numberings.put("decimal", NumberingFactory.create()
            .level(1)
                .format(NumberType1.DIGIT)
                .text("^1.")
                .done()
            .level(2)
                .format(NumberType1.DIGIT)
                .text("^1.^2.")
                .done()
            .build());
            
        // 법률 문서 스타일
        numberings.put("legal", NumberingFactory.create()
            .level(1)
                .format(NumberType1.ROMAN_CAPITAL)
                .text("^1.")
                .done()
            .level(2)
                .format(NumberType1.LATIN_CAPITAL)
                .text("^2.")
                .done()
            .build());
            
        // 한글 문서 스타일
        numberings.put("korean", NumberingFactory.create()
            .level(1)
                .format(NumberType1.HANGUL_SYLLABLE)
                .text("【^1】")
                .done()
            .level(2)
                .format(NumberType1.DIGIT)
                .text("【^1-^2】")
                .done()
            .build());
        
        // 4. 모든 넘버링 등록 및 적용
        numberings.forEach((name, numbering) -> {
            registry.register(name, numbering);
            service.apply(numbering);
        });
        
        System.out.println("다양한 넘버링 스타일 비교 예제가 완료되었습니다.");
    }
}