package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
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
        
        // 1. 스타일 생성 및 등록
        // 1.1 커스텀 스타일 (파란색, 중앙 정렬) 생성 및 등록
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
        
        // 1.2 제목 스타일 (굵게, 크게, 중앙 정렬, 여백) 생성 및 등록
        StyleResult titleStyleResult = StyleBuilder.create("제목 스타일", "Title Style")
                .withCharPr(charPr -> {
                    charPr.heightAnd(2000)
                        .createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    
                    paraPr.createMargin();
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(400);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(200);
                })
                .buildResult();
                
        Style titleStyle = StyleService.registerResult(hwpxFile, titleStyleResult);
        
        // 1.3 3번 스타일을 기반으로 불렛 스타일 생성 및 등록
        Style style3 = StyleService.findStyleById(hwpxFile, "3");
        if (style3 == null) {
            throw new IllegalArgumentException("스타일 ID '3'을 찾을 수 없습니다.");
        }
        
        // StyleBuilder의 withBullet() 메소드를 사용하여 불렛 설정
        StyleResult bulletStyleResult = StyleBuilder.create("불렛 스타일", "Bullet Style")
                .fromBaseStyle(style3)
                .withBullet("•")
                .withCharPr(charPr -> {
                    charPr.textColor("#FF0000");
                })
                .buildResult();
        
        Style bulletStyle = StyleService.registerResult(hwpxFile, bulletStyleResult);
        
        // 1.4 여백과 정렬을 모두 갖춘 스타일 생성 및 등록
        StyleResult complexStyleResult = StyleBuilder.create("복합 스타일", "Complex Style")
                .withCharPr(charPr -> {
                    charPr.createBold();
                    charPr.heightAnd(1500)
                        .textColor("#006600");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.JUSTIFY);
                    
                    paraPr.createMargin();
                    paraPr.margin().createIntent();
                    paraPr.margin().intent().value(500);
                    
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(800);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(300);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(300);
                })
                .buildResult();
        
        Style complexStyle = StyleService.registerResult(hwpxFile, complexStyleResult);
        
        // 2. 생성된 스타일을 사용하여 문단 추가
        addNewParagraph(hwpxFile, 0, customStyle, "새로운 스타일이 적용된 텍스트입니다!");
        addNewParagraph(hwpxFile, 0, titleStyle, "제목 스타일이 적용된 텍스트입니다!");
        
        // 불렛이 적용된 문단 추가
        addNewParagraph(hwpxFile, 0, bulletStyle, "첫 번째 불렛 항목입니다.");
        addNewParagraph(hwpxFile, 0, bulletStyle, "두 번째 불렛 항목입니다.");
        addNewParagraph(hwpxFile, 0, bulletStyle, "세 번째 불렛 항목입니다.");

        // 복합 스타일 적용 문단 추가
        addNewParagraph(hwpxFile, 0, complexStyle, "복합 스타일이 적용된 문단입니다.");
        addNewParagraph(hwpxFile, 0, complexStyle, "불필요한 null 체크 없이 코드를 작성했습니다.");
        
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