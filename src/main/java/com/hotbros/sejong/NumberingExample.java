package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 넘버링 기능을 테스트하기 위한 예제 클래스
 */
public class NumberingExample {

    public static void main(String[] args) throws Exception {
        // 기본 넘버링 예제 실행
        basicNumberingExample();
        
        // 다양한 넘버링 유형 예제
        multipleNumberingTypesExample();
        
        // 모든 과정이 통합된 넘버링 예제
        completeNumberingProcess();
        
        // 넘버링 셋 개념을 구현한 예제
        numberingSetExample();
    }
    
    /**
     * 기본적인 넘버링 생성 및 적용 예제
     */
    private static void basicNumberingExample() throws Exception {
        System.out.println("=== 기본 넘버링 예제 실행 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 넘버링 생성 및 등록
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 2-1. 아라비아 숫자 넘버링 (1, 2, 3...) 생성
        Numbering numbering1 = createNumbering(refList, "num1", NumberType1.DIGIT, "1. ", 0);
        
        // 3. 넘버링을 적용한 문단 추가
        addNumberedParagraph(hwpxFile, numbering1, "첫 번째 항목");
        addNumberedParagraph(hwpxFile, numbering1, "두 번째 항목");
        addNumberedParagraph(hwpxFile, numbering1, "세 번째 항목");
        
        // 일반 문단 추가
        addParagraph(hwpxFile, "넘버링이 없는 일반 문단입니다.");
        
        // 4. 파일 저장
        File outputFile = new File("BasicNumberingExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("기본 넘버링 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 다양한 넘버링 유형 테스트 예제
     */
    private static void multipleNumberingTypesExample() throws Exception {
        System.out.println("=== 다양한 넘버링 유형 예제 실행 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. RefList 가져오기
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 2-1. 아라비아 숫자 넘버링 (1, 2, 3...)
        Numbering arabic = createNumbering(refList, "arabic", NumberType1.DIGIT, "1. ", 0);
        
        // 2-2. 알파벳 대문자 넘버링 (A, B, C...)
        Numbering upperAlpha = createNumbering(refList, "upperAlpha", NumberType1.LATIN_CAPITAL, "A. ", 0);
        
        // 2-3. 알파벳 소문자 넘버링 (a, b, c...)
        Numbering lowerAlpha = createNumbering(refList, "lowerAlpha", NumberType1.LATIN_SMALL, "a) ", 0);
        
        // 2-4. 로마자 대문자 넘버링 (I, II, III...)
        Numbering upperRoman = createNumbering(refList, "upperRoman", NumberType1.ROMAN_CAPITAL, "I. ", 0);
        
        // 2-5. 로마자 소문자 넘버링 (i, ii, iii...)
        Numbering lowerRoman = createNumbering(refList, "lowerRoman", NumberType1.ROMAN_SMALL, "i. ", 0);
        
        // 2-6. 한글 넘버링 (가, 나, 다...)
        Numbering hangul = createNumbering(refList, "hangul", NumberType1.HANGUL_SYLLABLE, "가. ", 0);
        
        // 2-7. 원 숫자 넘버링 (①, ②, ③...)
        Numbering circleNum = createNumbering(refList, "circleNum", NumberType1.CIRCLED_DIGIT, "", 0);
        
        // 3. 문서에 다양한 넘버링 적용
        addParagraph(hwpxFile, "다양한 넘버링 유형 테스트");
        
        // 3-1. 아라비아 숫자 넘버링
        addParagraph(hwpxFile, "== 아라비아 숫자 넘버링 ==");
        addNumberedParagraph(hwpxFile, arabic, "아라비아 숫자 첫 번째 항목");
        addNumberedParagraph(hwpxFile, arabic, "아라비아 숫자 두 번째 항목");
        
        // 3-2. 알파벳 대문자 넘버링
        addParagraph(hwpxFile, "== 알파벳 대문자 넘버링 ==");
        addNumberedParagraph(hwpxFile, upperAlpha, "알파벳 대문자 첫 번째 항목");
        addNumberedParagraph(hwpxFile, upperAlpha, "알파벳 대문자 두 번째 항목");
        
        // 3-3. 알파벳 소문자 넘버링
        addParagraph(hwpxFile, "== 알파벳 소문자 넘버링 ==");
        addNumberedParagraph(hwpxFile, lowerAlpha, "알파벳 소문자 첫 번째 항목");
        addNumberedParagraph(hwpxFile, lowerAlpha, "알파벳 소문자 두 번째 항목");
        
        // 3-4. 로마자 대문자 넘버링
        addParagraph(hwpxFile, "== 로마자 대문자 넘버링 ==");
        addNumberedParagraph(hwpxFile, upperRoman, "로마자 대문자 첫 번째 항목");
        addNumberedParagraph(hwpxFile, upperRoman, "로마자 대문자 두 번째 항목");
        
        // 3-5. 로마자 소문자 넘버링
        addParagraph(hwpxFile, "== 로마자 소문자 넘버링 ==");
        addNumberedParagraph(hwpxFile, lowerRoman, "로마자 소문자 첫 번째 항목");
        addNumberedParagraph(hwpxFile, lowerRoman, "로마자 소문자 두 번째 항목");
        
        // 3-6. 한글 넘버링
        addParagraph(hwpxFile, "== 한글 넘버링 ==");
        addNumberedParagraph(hwpxFile, hangul, "한글 넘버링 첫 번째 항목");
        addNumberedParagraph(hwpxFile, hangul, "한글 넘버링 두 번째 항목");
        
        // 3-7. 원 넘버링
        addParagraph(hwpxFile, "== 원 넘버링 ==");
        addNumberedParagraph(hwpxFile, circleNum, "원 넘버링 첫 번째 항목");
        addNumberedParagraph(hwpxFile, circleNum, "원 넘버링 두 번째 항목");
        
        // 4. 파일 저장
        File outputFile = new File("MultipleNumberingTypesExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("다양한 넘버링 유형 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 넘버링 생성 및 적용 전체 과정을 하나의 함수에 통합한 예제
     * 공부용으로 단계별로 상세히 주석 처리됨
     */
    private static void completeNumberingProcess() throws Exception {
        System.out.println("=== 넘버링 생성 전체 과정 통합 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 문서의 참조 목록(RefList) 가져오기
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 3. 넘버링 컨테이너가 없으면 즉시 예외 발생
        if (refList.numberings() == null) {
            throw new IllegalStateException("넘버링 컨테이너가 존재하지 않습니다.");
        }
        
        // 4. 기존 넘버링(id="1") 찾기
        Numbering existingNumbering = null;
        for (int i = 0; i < refList.numberings().count(); i++) {
            Numbering num = refList.numberings().get(i);
            if ("1".equals(num.id())) {
                existingNumbering = num;
                break;
            }
        }
        
        // 5. 기존 넘버링이 없으면 즉시 예외 발생
        if (existingNumbering == null) {
            throw new IllegalStateException("ID가 '1'인 넘버링을 찾을 수 없습니다.");
        }
        
        // 6. ParaHead가 없으면 즉시 예외 발생
        if (existingNumbering.countOfParaHead() == 0) {
            throw new IllegalStateException("기존 넘버링에 ParaHead가 없습니다.");
        }
        
        // 7. 기존 넘버링에서 ParaHead 가져오기
        ParaHead existingParaHead = existingNumbering.getParaHead(0);
        
        // 8. 아라비아 숫자 넘버링(1, 2, 3...) 객체 생성 (기존 넘버링 복사)
        Numbering arabicNumbering = refList.numberings().addNew();
        arabicNumbering.id("arabic");
        
        // 9. 새 ParaHead 생성 및 기존 속성 복사
        ParaHead paraHead = arabicNumbering.addNewParaHead();
        paraHead.level(existingParaHead.level());  // 기존 레벨 복사
        paraHead.start(existingParaHead.start());  // 기존 시작 번호 복사
        paraHead.numFormat(NumberType1.DIGIT);     // 번호 포맷은 명시적으로 설정
        paraHead.text("1. ^1");                   // 형식 문자열은 명시적으로 설정
        
        System.out.println("기존 넘버링(id=1)에서 속성을 복사하여 새 넘버링을 생성했습니다.");
        
        // 10. 넘버링이 적용된 문단 추가 - 첫 번째 항목
        Para firstPara = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        firstPara.paraPrIDRef(arabicNumbering.id());
        Run firstRun = firstPara.addNewRun();
        firstRun.addNewT().addText("첫 번째 항목");
        
        // 11. 넘버링이 적용된 문단 추가 - 두 번째 항목
        Para secondPara = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        secondPara.paraPrIDRef(arabicNumbering.id());
        Run secondRun = secondPara.addNewRun();
        secondRun.addNewT().addText("두 번째 항목");
        
        // 12. 넘버링이 적용된 문단 추가 - 세 번째 항목
        Para thirdPara = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        thirdPara.paraPrIDRef(arabicNumbering.id());
        Run thirdRun = thirdPara.addNewRun();
        thirdRun.addNewT().addText("세 번째 항목");
        
        // 13. 일반 문단 추가 (넘버링 없음)
        Para normalPara = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        Run normalRun = normalPara.addNewRun();
        normalRun.addNewT().addText("넘버링이 없는 일반 문단입니다.");
        
        // 14. 한글 넘버링(가, 나, 다...) 객체 생성 - 또 다른 예시로 기존 넘버링 복사 후 수정
        Numbering hangulNumbering = refList.numberings().addNew();
        hangulNumbering.id("hangul");
        
        // 15. 기존 넘버링에서 ParaHead 정보 복사 후 수정
        ParaHead hangulParaHead = hangulNumbering.addNewParaHead();
        hangulParaHead.level(existingParaHead.level());    // 기존 레벨 복사
        hangulParaHead.start(existingParaHead.start());    // 기존 시작 번호 복사
        hangulParaHead.numFormat(NumberType1.HANGUL_SYLLABLE);  // 한글 포맷으로 변경
        hangulParaHead.text("가. ^1");                    // 한글 형식으로 변경
        
        // 16. 한글 넘버링이 적용된 문단 추가
        Para hangulPara = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        hangulPara.paraPrIDRef(hangulNumbering.id());
        Run hangulRun = hangulPara.addNewRun();
        hangulRun.addNewT().addText("한글 넘버링 항목");
        
        // 17. 파일 저장
        File outputFile = new File("CompleteNumberingProcess.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("넘버링 생성 전체 과정 통합 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 넘버링 셋 개념을 구현한 예제
     */
    private static void numberingSetExample() throws Exception {
        System.out.println("=== 넘버링 셋 개념 구현 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 문서의 참조 목록(RefList) 가져오기
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 3. 넘버링 컨테이너 확인
        if (refList.numberings() == null) {
            throw new IllegalStateException("넘버링 컨테이너가 존재하지 않습니다.");
        }
        
        // 4. 기존 넘버링(id="1") 찾기 - 베이스 넘버링으로 사용
        Numbering baseNumbering = null;
        for (int i = 0; i < refList.numberings().count(); i++) {
            Numbering num = refList.numberings().get(i);
            if ("1".equals(num.id())) {
                baseNumbering = num;
                break;
            }
        }
        
        if (baseNumbering == null || baseNumbering.countOfParaHead() == 0) {
            throw new IllegalStateException("기본 넘버링이 없거나 ParaHead가 없습니다.");
        }
        
        // 5. 넘버링 셋 생성 - Map을 사용해 ID와 Numbering 객체 관리
        Map<String, Numbering> numberingSet = new HashMap<>();
        
        // 6. 다양한 넘버링 스타일 생성하여 셋에 추가
        
        // 6-1. 아라비아 숫자 넘버링 (1, 2, 3...)
        Numbering arabicNumbering = cloneNumbering(baseNumbering, "arabic");
        customizeParaHead(arabicNumbering, NumberType1.DIGIT, "1. ");
        numberingSet.put("arabic", arabicNumbering);
        
        // 6-2. 알파벳 대문자 넘버링 (A, B, C...)
        Numbering upperAlphaNumbering = cloneNumbering(baseNumbering, "upperAlpha");
        customizeParaHead(upperAlphaNumbering, NumberType1.LATIN_CAPITAL, "A. ");
        numberingSet.put("upperAlpha", upperAlphaNumbering);
        
        // 6-3. 알파벳 소문자 넘버링 (a, b, c...)
        Numbering lowerAlphaNumbering = cloneNumbering(baseNumbering, "lowerAlpha");
        customizeParaHead(lowerAlphaNumbering, NumberType1.LATIN_SMALL, "a) ");
        numberingSet.put("lowerAlpha", lowerAlphaNumbering);
        
        // 6-4. 로마자 대문자 넘버링 (I, II, III...)
        Numbering upperRomanNumbering = cloneNumbering(baseNumbering, "upperRoman");
        customizeParaHead(upperRomanNumbering, NumberType1.ROMAN_CAPITAL, "I. ");
        numberingSet.put("upperRoman", upperRomanNumbering);
        
        // 6-5. 한글 넘버링 (가, 나, 다...)
        Numbering hangulNumbering = cloneNumbering(baseNumbering, "hangul");
        customizeParaHead(hangulNumbering, NumberType1.HANGUL_SYLLABLE, "가. ");
        numberingSet.put("hangul", hangulNumbering);
        
        // 6-6. 원 숫자 넘버링 (①, ②, ③...)
        Numbering circleNumbering = cloneNumbering(baseNumbering, "circle");
        customizeParaHead(circleNumbering, NumberType1.CIRCLED_DIGIT, "");
        numberingSet.put("circle", circleNumbering);
        
        System.out.println("넘버링 셋 구축 완료. 총 " + numberingSet.size() + "개의 넘버링 스타일이 준비되었습니다.");
        
        // 7. 사용자가 선택한 넘버링만 실제 문서에 적용
        // (예: 사용자가 'arabic', 'hangul', 'circle' 스타일 선택)
        String[] selectedStyles = {"arabic", "hangul", "circle"};
        
        System.out.println("사용자가 선택한 넘버링 스타일을 문서에 적용합니다: " + Arrays.toString(selectedStyles));
        
        // 8. 선택된 넘버링만 문서에 추가
        for (String style : selectedStyles) {
            Numbering numbering = numberingSet.get(style);
            if (numbering != null) {
                // 8-1. 넘버링을 문서에 등록
                refList.numberings().add(numbering);
                
                // 8-2. 해당 넘버링을 사용하는 문단 추가
                Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
                para.paraPrIDRef(numbering.id());
                Run run = para.addNewRun();
                run.addNewT().addText(style + " 스타일의 첫 번째 항목");
                
                // 두 번째 항목 추가
                Para para2 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
                para2.paraPrIDRef(numbering.id());
                Run run2 = para2.addNewRun();
                run2.addNewT().addText(style + " 스타일의 두 번째 항목");
            }
        }
        
        // 9. 일반 문단 추가 (넘버링 없음)
        Para normalPara = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        Run normalRun = normalPara.addNewRun();
        normalRun.addNewT().addText("넘버링 셋 테스트 예제가 완료되었습니다.");
        
        // 10. 파일 저장
        File outputFile = new File("NumberingSetExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("넘버링 셋 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 기존 넘버링을 복제하여 새 넘버링 생성
     * 
     * @param baseNumbering 기존 넘버링
     * @param newId 새 넘버링 ID
     * @return 복제된 넘버링 객체
     */
    private static Numbering cloneNumbering(Numbering baseNumbering, String newId) {
        // Numbering 객체 clone
        Numbering cloned = baseNumbering.clone();
        cloned.id(newId);
        
        return cloned;
    }
    
    /**
     * 넘버링의 ParaHead 속성 커스터마이징
     * 
     * @param numbering 넘버링 객체
     * @param numberType 번호 유형
     * @param prefix 접두사 (예: "1. ", "가. ")
     */
    private static void customizeParaHead(Numbering numbering, NumberType1 numberType, String prefix) {
        if (numbering.countOfParaHead() > 0) {
            ParaHead paraHead = numbering.getParaHead(0);
            paraHead.numFormat(numberType);
            paraHead.text(prefix + "^1");
        }
    }
    
    /**
     * 넘버링 객체를 생성하고 RefList에 등록합니다.
     * 
     * @param refList 참조 목록
     * @param id 넘버링 ID
     * @param numberType 번호 유형 (NumberType1 열거형 사용)
     * @param formatString 형식 문자열 (예: "1.", "A.", "가.")
     * @param startNumber 시작 번호 (0부터 시작)
     * @return 생성된 넘버링 객체
     */
    private static Numbering createNumbering(RefList refList, String id, NumberType1 numberType, 
                                              String formatString, int startNumber) {
        // 넘버링이 없으면 생성
        if (refList.numberings() == null) {
            refList.createNumberings();
        }
        
        // 새 넘버링 객체 생성
        Numbering numbering = refList.numberings().addNew();
        numbering.id(id);
        
        // 문단 머리말 설정 - 첫 번째 ParaHead 객체 생성
        ParaHead paraHead = numbering.addNewParaHead();
        
        // 수준 설정 (1부터 시작하는 레벨)
        paraHead.level((byte) 1);
        
        // 시작 번호 설정
        paraHead.start(startNumber);
        
        // 번호 포맷 설정
        paraHead.numFormat(numberType);
        
        // 형식 문자열 설정 (텍스트)
        paraHead.text(formatString + "^1");
        
        return numbering;
    }
    
    /**
     * 넘버링이 적용된 문단을 추가합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param numbering 적용할 넘버링 객체
     * @param text 문단에 들어갈 텍스트
     * @return 추가된 문단 객체
     */
    private static Para addNumberedParagraph(HWPXFile hwpxFile, Numbering numbering, String text) {
        // 첫 번째 섹션에 문단 추가
        Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        
        // 넘버링 ID 참조 설정 - 헤딩(Heading) 객체를 통해 설정
        para.paraPrIDRef(numbering.id());
        
        // 텍스트가 있는 경우 추가
        if (text != null && !text.isEmpty()) {
            Run run = para.addNewRun();
            run.addNewT().addText(text);
        }
        
        return para;
    }
    
    /**
     * 일반 문단을 추가합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param text 문단에 들어갈 텍스트
     * @return 추가된 문단 객체
     */
    private static Para addParagraph(HWPXFile hwpxFile, String text) {
        // 첫 번째 섹션에 문단 추가
        Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        
        // 텍스트가 있는 경우 추가
        if (text != null && !text.isEmpty()) {
            Run run = para.addNewRun();
            run.addNewT().addText(text);
        }
        
        return para;
    }
} 