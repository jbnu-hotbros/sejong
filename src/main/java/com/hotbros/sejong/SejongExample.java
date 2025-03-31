package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.Heading;

/**
 * hwpxlib를 사용한 예제 클래스
 */
public class SejongExample {

    public static void main(String[] args) throws Exception {

        // hwpxlib의 BlankFileMaker로 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 불렛 생성
        if (hwpxFile.headerXMLFile().refList().bullets() == null) {
            hwpxFile.headerXMLFile().refList().createBullets();
        }
        
        // 새로운 불렛 객체 생성
        int maxBulletId = StyleUtils.getMaxID(hwpxFile.headerXMLFile().refList().bullets().items(), item -> item.id());
        String bulletId = String.valueOf(maxBulletId + 1);
        
        Bullet bullet = hwpxFile.headerXMLFile().refList().bullets().addNew()
                .idAnd(bulletId)
                ._charAnd("•")           // 불렛 문자
                .checkedCharAnd("✓")     // 체크 문자
                .useImageAnd(false);     // 이미지 사용 여부
    

        // 1. 스타일 생성
        // 1.1 커스텀 스타일 (파란색, 중앙 정렬) 생성
        Style customStyle = StyleBuilder.create(hwpxFile, "커스텀 스타일", "Custom Style")
        
                .withCharPr(charPr -> {
                    charPr.height(1200);
                    charPr.textColor("#0000FF");
                })
                .withParaPr(paraPr -> {
                    if (paraPr.align() != null) {
                        paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    }
                })
                .build();
        
        // 1.2 제목 스타일 (굵게, 크게, 중앙 정렬, 여백) 생성
        Style titleStyle = StyleBuilder.create(hwpxFile, "제목 스타일", "Title Style")
                .withCharPr(charPr -> {
                    charPr.height(2000);
                    if (charPr.bold() == null) {
                        charPr.createBold();
                    }
                })
                .withParaPr(paraPr -> {
                    if (paraPr.align() != null) {
                        paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    }
                    
                    if (paraPr.margin() == null) {
                        paraPr.createMargin();
                    }
                    
                    if (paraPr.margin().prev() == null) {
                        paraPr.margin().createPrev();
                    }
                    paraPr.margin().prev().value(400);
                    
                    if (paraPr.margin().next() == null) {
                        paraPr.margin().createNext();
                    }
                    paraPr.margin().next().value(200);
                })
                .build();
                
        // 1.3 3번 스타일을 복사한 새 스타일 생성 (불렛 적용)
        Style style3 = StyleUtils.findStyleById(hwpxFile.headerXMLFile().refList(), "3");
        if (style3 == null) {
            throw new IllegalArgumentException("스타일 ID '3'을 찾을 수 없습니다.");
        }
        
        Style copiedStyle = StyleBuilder.create(hwpxFile, "3번 복사 불렛 스타일", "Copied Style 3 with Bullet")
                .fromBaseStyle(style3)
                .withCharPr(charPr -> {
                    // 선택적으로 글자 모양 수정 가능
                    charPr.textColor("#FF0000"); // 빨간색으로 변경
                })
                .withParaPr(paraPr -> {
                    // 불렛 설정 추가
                    paraPr.createHeading(); // void를 반환하므로 별도 변수에 할당하지 않음
                    Heading heading = paraPr.heading(); // 생성된 heading 객체 가져오기
                    heading.typeAnd(ParaHeadingType.BULLET);
                    heading.idRefAnd(bulletId);
                    heading.level((byte) 1);
                })
                .build();
                
        // 2. 생성된 스타일을 사용하여 문단 추가
        addNewParagraph(hwpxFile, 0, customStyle, "새로운 스타일이 적용된 텍스트입니다!");
        addNewParagraph(hwpxFile, 0, titleStyle, "제목 스타일이 적용된 텍스트입니다!");
        addNewParagraph(hwpxFile, 0, copiedStyle, "3번 스타일을 복사하여 만든 스타일입니다!");
        
        // 불렛이 적용된 문단 추가
        addNewParagraph(hwpxFile, 0, copiedStyle, "첫 번째 불렛 항목입니다.");
        addNewParagraph(hwpxFile, 0, copiedStyle, "두 번째 불렛 항목입니다.");
        addNewParagraph(hwpxFile, 0, copiedStyle, "세 번째 불렛 항목입니다.");

        // 3. 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "example_hwpxlib.hwpx");
        System.out.println("파일이 성공적으로 저장되었습니다: example_hwpxlib.hwpx");
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