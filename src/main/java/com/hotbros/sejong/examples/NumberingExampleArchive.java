package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ValueUnit1;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;

import java.io.File;

/**
 * 넘버링 기능 테스트 예제 클래스 - 아카이브용
 * (참고용으로만 사용하세요)
 */
public class NumberingExampleArchive {

    public static void main(String[] args) throws Exception {
        // 기본 넘버링 예제 실행
        // basicNumberingExample();
        
        // 다양한 넘버링 유형 예제
        // multipleNumberingTypesExample();
        
        // 모든 과정이 통합된 넘버링 예제
        // completeNumberingProcess();
        
        // 원형 숫자 넘버링(①, ②, ③...) 예제
        // circledNumberingExample();
        
        // 간단한 넘버링 형식(start, numFormat, text)만 변경하는 예제
        // simpleNumberingFormatExample();
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
     * 원형 숫자 넘버링(①, ②, ③...)을 설정하는 예제
     */
    private static void circledNumberingExample() throws Exception {
        System.out.println("=== 원형 숫자 넘버링(①, ②, ③...) 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 문서의 참조 목록(RefList) 가져오기
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 3. 넘버링 컨테이너 확인
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
        
        // 5. 기존 넘버링이 없으면 예외 발생
        if (existingNumbering == null) {
            throw new IllegalStateException("ID가 '1'인 넘버링을 찾을 수 없습니다.");
        }
        
        // 6. 기존 넘버링을 복제하여 새 넘버링 생성
        Numbering clonedNumbering = existingNumbering.clone();
        clonedNumbering.id("circled_numbering");
        
        // 7. ParaHead가 없으면 예외 발생
        if (clonedNumbering.countOfParaHead() == 0) {
            throw new IllegalStateException("복제된 넘버링에 ParaHead가 없습니다.");
        }
        
        // 8. 복제된 넘버링의 ParaHead 상세 속성 설정
        ParaHead paraHead = clonedNumbering.getParaHead(0);
        
        // 레벨과 번호 형식 설정
        setNumberingFormat(paraHead, (byte) 7, 1, NumberType1.CIRCLED_DIGIT, "^7");
        
        System.out.println("원형 숫자 넘버링(①, ②, ③...) 설정 완료");
        
        // 9. 복제된 넘버링을 문서에 등록
        refList.numberings().add(clonedNumbering);
        
        // 10. 넘버링이 적용된 문단 추가
        for (int i = 0; i < 3; i++) {
            Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
            para.paraPrIDRef(clonedNumbering.id());
            Run run = para.addNewRun();
            run.addNewT().addText((i + 1) + "번째 원형 숫자 항목");
        }
        
        // 11. 파일 저장
        File outputFile = new File("CircledNumberingExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("원형 숫자 넘버링 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * 간단한 넘버링 형식 변경 예제
     */
    private static void simpleNumberingFormatExample() throws Exception {
        System.out.println("=== 간단한 넘버링 형식 변경 예제 ===");
        
        // 1. 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 2. 문서의 참조 목록(RefList) 가져오기
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 3. 기존 넘버링(id="1") 찾기
        Numbering existingNumbering = null;
        for (int i = 0; i < refList.numberings().count(); i++) {
            Numbering num = refList.numberings().get(i);
            if ("1".equals(num.id())) {
                existingNumbering = num;
                break;
            }
        }
        
        if (existingNumbering == null) {
            throw new IllegalStateException("ID가 '1'인 넘버링을 찾을 수 없습니다.");
        }
        
        // 4. 다양한 번호 형식으로 복제하여 생성
        
        // 4-1. 아라비아 숫자 (1, 2, 3, ...)
        Numbering arabicNumbering = existingNumbering.clone();
        arabicNumbering.id("simple_arabic");
        ParaHead arabicHead = arabicNumbering.getParaHead(0);
        setNumberingFormat(arabicHead, arabicHead.level(), 0, NumberType1.DIGIT, "^1.");
        refList.numberings().add(arabicNumbering);
        
        // 4-2. 로마자 대문자 (I, II, III, ...)
        Numbering romanNumbering = existingNumbering.clone();
        romanNumbering.id("simple_roman");
        ParaHead romanHead = romanNumbering.getParaHead(0);
        setNumberingFormat(romanHead, romanHead.level(), 0, NumberType1.ROMAN_CAPITAL, "^1.");
        refList.numberings().add(romanNumbering);
        
        // 4-3. 한글 (가, 나, 다, ...)
        Numbering hangulNumbering = existingNumbering.clone();
        hangulNumbering.id("simple_hangul");
        ParaHead hangulHead = hangulNumbering.getParaHead(0);
        setNumberingFormat(hangulHead, hangulHead.level(), 0, NumberType1.HANGUL_SYLLABLE, "^1.");
        refList.numberings().add(hangulNumbering);
        
        // 5. 넘버링이 적용된 문단 추가
        // 5-1. 아라비아 숫자 넘버링
        addParagraph(hwpxFile, "== 아라비아 숫자 넘버링 ==");
        for (int i = 0; i < 3; i++) {
            Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
            para.paraPrIDRef(arabicNumbering.id());
            Run run = para.addNewRun();
            run.addNewT().addText("아라비아 숫자 항목 " + (i + 1));
        }
        
        // 5-2. 로마자 대문자 넘버링
        addParagraph(hwpxFile, "== 로마자 대문자 넘버링 ==");
        for (int i = 0; i < 3; i++) {
            Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
            para.paraPrIDRef(romanNumbering.id());
            Run run = para.addNewRun();
            run.addNewT().addText("로마자 대문자 항목 " + (i + 1));
        }
        
        // 5-3. 한글 넘버링
        addParagraph(hwpxFile, "== 한글 넘버링 ==");
        for (int i = 0; i < 3; i++) {
            Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
            para.paraPrIDRef(hangulNumbering.id());
            Run run = para.addNewRun();
            run.addNewT().addText("한글 항목 " + (i + 1));
        }
        
        // 6. 파일 저장
        File outputFile = new File("SimpleNumberingFormatExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("간단한 넘버링 형식 변경 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
    
    /**
     * ParaHead 객체의 속성을 설정하는 유틸리티 함수 (메서드 체이닝 지원)
     * 
     * @param paraHead 설정할 ParaHead 객체
     * @param level 레벨 (1~7 범위, 수준별 문단 머리 정의)
     * @param start 시작 번호 (0부터 시작하여 실제로는 1, 2, 3...으로 표시됨)
     * @param numFormat 번호 포맷 (NumberType1 열거형 사용)
     * @param text 형식 문자열 (^1, ^2 등은 실제 번호가 표시될 위치)
     * @return 설정이 완료된 ParaHead 객체 (메서드 체이닝 지원)
     */
    private static ParaHead setNumberingFormat(ParaHead paraHead, byte level, int start, NumberType1 numFormat, String text) {
        paraHead.level(level);
        paraHead.start(start);
        paraHead.numFormat(numFormat);
        paraHead.text(text);
        return paraHead;
    }
    
    /**
     * ParaHead 객체의 핵심 속성만 설정하는 간소화 버전 (level 유지)
     */
    private static ParaHead setNumberingFormat(ParaHead paraHead, int start, NumberType1 numFormat, String text) {
        paraHead.start(start);
        paraHead.numFormat(numFormat);
        paraHead.text(text);
        return paraHead;
    }
    
    /**
     * 넘버링 객체를 생성하고 RefList에 등록합니다.
     */
    private static Numbering createNumbering(RefList refList, String id, NumberType1 numberType, 
                                            String formatString, int startNumber) {
        // 넘버링이 없으면 생성
        if (refList.numberings() == null) {
            refList.createNumberings();
        }
        
        // 새 넘버링 객체 생성 및 ID 설정
        Numbering numbering = refList.numberings().addNew();
        numbering.id(id);
        
        // ParaHead 객체 생성 및 속성 설정
        ParaHead paraHead = numbering.addNewParaHead();
        setNumberingFormat(paraHead, (byte) 1, startNumber, numberType, formatString + "^1");
        
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