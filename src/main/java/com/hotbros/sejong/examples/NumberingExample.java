package com.hotbros.sejong.examples;

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
 * 넘버링 기능을 테스트하기 위한 예제 클래스
 */
public class NumberingExample {

    public static void main(String[] args) throws Exception {
        // 하나의 넘버링에 여러 수준의 ParaHead를 정의하는 예제
        multiLevelNumberingExample();
    }
    
    /**
     * 하나의 넘버링에 여러 수준의 ParaHead를 정의하는 예제
     */
    private static void multiLevelNumberingExample() throws Exception {
        System.out.println("=== 다중 레벨 넘버링 예제 ===");
        
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
        
        // 6. 기존 넘버링을 클론하여 새로운 넘버링 생성
        Numbering clonedNumbering = existingNumbering.clone();
        clonedNumbering.id("1");  // ID를 그대로 "1"로 유지
        
        // 7. 클론한 넘버링의 ParaHead들 수정
        // 기존 ParaHead들이 충분하지 않을 경우를 대비하여 필요한 개수만큼 보충
        while (clonedNumbering.countOfParaHead() < 5) {
            clonedNumbering.addNewParaHead();
        }
        
        // 각 레벨별 ParaHead 수정
        // 첫 번째 수준(level 1) ParaHead - 아라비아 숫자 (1., 2., 3.)
        setNumberingFormat(clonedNumbering.getParaHead(0), (byte) 1, 1, NumberType1.DIGIT, "^1.");
        
        // 두 번째 수준(level 2) ParaHead - 소문자 알파벳 (a., b., c.)
        setNumberingFormat(clonedNumbering.getParaHead(1), (byte) 2, 1, NumberType1.LATIN_SMALL, "^1.^2.");
        
        // 세 번째 수준(level 3) ParaHead - 로마 소문자 (i., ii., iii.)
        setNumberingFormat(clonedNumbering.getParaHead(2), (byte) 3, 1, NumberType1.ROMAN_SMALL, "^1.^2.^3.");
        
        // 넷째 수준(level 4) ParaHead - 원형 숫자 (①, ②, ③)
        setNumberingFormat(clonedNumbering.getParaHead(3), (byte) 4, 1, NumberType1.CIRCLED_DIGIT, "^1.^2.^3.^4");
        
        // 다섯째 수준(level 5) ParaHead - 한글 (가., 나., 다.)
        setNumberingFormat(clonedNumbering.getParaHead(4), (byte) 5, 1, NumberType1.HANGUL_SYLLABLE, "^1.^2.^3.^4.^5.");
        
        System.out.println("다중 레벨 넘버링 설정 완료");
        
        // 13. 각 레벨에 해당하는 문단 추가
        addParagraph(hwpxFile, "== 다중 레벨 넘버링 예제 ==");
        
        // 13-1. 레벨 1 문단 추가 - 아라비아 숫자 스타일 (1., 2., 3.)
        Para level1Para1 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level1Para1.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level1Run1 = level1Para1.addNewRun();
        level1Run1.addNewT().addText("첫 번째 수준 항목");
        
        // 13-2. 레벨 2 문단 추가 - 알파벳 소문자 스타일 (a., b., c.)
        Para level2Para1 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level2Para1.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level2Run1 = level2Para1.addNewRun();
        level2Run1.addNewT().addText("두 번째 수준 항목 1");
        
        Para level2Para2 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level2Para2.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level2Run2 = level2Para2.addNewRun();
        level2Run2.addNewT().addText("두 번째 수준 항목 2");
        
        // 13-3. 레벨 3 문단 추가 - 로마자 소문자 스타일 (i., ii., iii.)
        Para level3Para1 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level3Para1.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level3Run1 = level3Para1.addNewRun();
        level3Run1.addNewT().addText("세 번째 수준 항목");
        
        // 13-4. 레벨 4 문단 추가 - 원형 숫자 스타일 (①, ②, ③)
        Para level4Para1 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level4Para1.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level4Run1 = level4Para1.addNewRun();
        level4Run1.addNewT().addText("네 번째 수준 항목");
        
        // 13-5. 레벨 5 문단 추가 - 한글 스타일 (가., 나., 다.)
        Para level5Para1 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level5Para1.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level5Run1 = level5Para1.addNewRun();
        level5Run1.addNewT().addText("다섯 번째 수준 항목");
        
        // 13-6. 다시 레벨 1로 돌아감 - 아라비아 숫자 스타일 (1., 2., 3.)
        Para level1Para2 = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        level1Para2.paraPrIDRef("1");  // ID를 "1"로 설정
        Run level1Run2 = level1Para2.addNewRun();
        level1Run2.addNewT().addText("다시 첫 번째 수준 항목");
        
        // 14. 기존 넘버링(id="1")을 제거하고 수정한 넘버링을 등록
        for (int i = 0; i < refList.numberings().count(); i++) {
            Numbering num = refList.numberings().get(i);
            if ("1".equals(num.id())) {
                refList.numberings().remove(i);
                break;
            }
        }
        
        // 15. 수정된 넘버링을 문서에 등록
        refList.numberings().add(clonedNumbering);
        
        // 16. 파일 저장
        File outputFile = new File("MultiLevelNumberingExample.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        System.out.println("다중 레벨 넘버링 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
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