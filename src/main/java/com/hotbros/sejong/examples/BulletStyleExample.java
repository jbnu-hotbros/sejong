package com.hotbros.sejong.examples;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import com.hotbros.sejong.StyleBuilder;
import com.hotbros.sejong.StyleUtils;

/**
 * 불렛 스타일 설정 예제 클래스
 * 다양한 불렛 설정 방법을 보여줍니다.
 */
public class BulletStyleExample {

    public static void main(String[] args) throws Exception {
        // 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 1. 기본 불렛 스타일 생성
        Style basicBulletStyle = StyleBuilder.create(hwpxFile, "기본 불렛", "Basic Bullet")
                .withBullet() // 기본 불렛 문자(•) 사용
                .build();
        
        // 2. 커스텀 불렛 문자 지정
        Style starBulletStyle = StyleBuilder.create(hwpxFile, "별표 불렛", "Star Bullet")
                .withBullet("★") // 별표 문자 사용
                .withCharPr(charPr -> {
                    charPr.height(1400);
                    charPr.textColor("#0000FF"); // 파란색
                })
                .build();
        
        // 3. 불렛 문자와 체크 문자 모두 지정
        Style diamondBulletStyle = StyleBuilder.create(hwpxFile, "다이아몬드 불렛", "Diamond Bullet")
                .withBullet("◆", "☑") // 불렛 문자와 체크 문자 모두 지정
                .withCharPr(charPr -> {
                    charPr.height(1600);
                    charPr.textColor("#FF0000"); // 빨간색
                })
                .withParaPr(paraPr -> {
                    if (paraPr.align() != null) {
                        paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    }
                    // 여백 설정
                    if (paraPr.margin() == null) {
                        paraPr.createMargin();
                    }
                    if (paraPr.margin().left() == null) {
                        paraPr.margin().createLeft();
                    }
                    paraPr.margin().left().value(800); // 왼쪽 여백 설정
                })
                .build();
        
        // 4. 불렛 레벨 직접 설정 (paraPr 설정에서 heading 수정)
        Style customLevelStyle = StyleBuilder.create(hwpxFile, "레벨 설정 불렛", "Custom Level Bullet")
                .withBullet("→")
                .withParaPr(paraPr -> {
                    // 불렛이 자동으로 설정되지만, 레벨을 변경하려면 헤딩을 직접 수정
                    if (paraPr.heading() != null) {
                        paraPr.heading().level((byte) 2); // 레벨 2로 설정
                    }
                })
                .build();
        
        // 5. 기존 스타일 복사 및 불렛 추가
        Style style0 = StyleUtils.findStyleById(hwpxFile.headerXMLFile().refList(), "0");
        Style basedOnExistingStyle = StyleBuilder.create(hwpxFile, "기존스타일 기반 불렛", "Bullet Based on Existing")
                .fromBaseStyle(style0)
                .withBullet("○")
                .build();

        // 텍스트 내용 추가
        addHeader(hwpxFile, "다양한 불렛 스타일 예제");
        
        // 1. 기본 불렛 예제
        addSubheader(hwpxFile, "1. 기본 불렛");
        addParagraph(hwpxFile, basicBulletStyle, "기본 불렛 문자(•)를 사용한 항목입니다.");
        addParagraph(hwpxFile, basicBulletStyle, "기본 체크 문자(✓)를 사용합니다.");
        addParagraph(hwpxFile, basicBulletStyle, "추가 설정 없이 간단하게 사용할 수 있습니다.");
        
        // 2. 별표 불렛 예제
        addSubheader(hwpxFile, "2. 별표 불렛");
        addParagraph(hwpxFile, starBulletStyle, "별표(★) 문자를 사용한 항목입니다.");
        addParagraph(hwpxFile, starBulletStyle, "글자 크기와 색상이 변경되었습니다.");
        addParagraph(hwpxFile, starBulletStyle, "불렛 문자만 변경한 간단한 예제입니다.");
        
        // 3. 다이아몬드 불렛 예제
        addSubheader(hwpxFile, "3. 다이아몬드 불렛 (여백 포함)");
        addParagraph(hwpxFile, diamondBulletStyle, "다이아몬드(◆) 문자와 체크(☑) 문자를 사용합니다.");
        addParagraph(hwpxFile, diamondBulletStyle, "왼쪽 여백이 추가되어 들여쓰기 효과가 있습니다.");
        addParagraph(hwpxFile, diamondBulletStyle, "글자 크기와 색상도 변경되었습니다.");
        
        // 4. 레벨 설정 불렛 예제
        addSubheader(hwpxFile, "4. 화살표 불렛 (레벨 2)");
        addParagraph(hwpxFile, customLevelStyle, "화살표(→) 문자를 사용한 항목입니다.");
        addParagraph(hwpxFile, customLevelStyle, "불렛 레벨이 2로 설정되었습니다.");
        addParagraph(hwpxFile, customLevelStyle, "레벨에 따라 들여쓰기가 달라질 수 있습니다.");
        
        // 5. 기존 스타일 기반 불렛 예제
        addSubheader(hwpxFile, "5. 기존 스타일 기반 불렛");
        addParagraph(hwpxFile, basedOnExistingStyle, "기존 스타일을 기반으로 불렛을 추가했습니다.");
        addParagraph(hwpxFile, basedOnExistingStyle, "원형(○) 문자를 사용한 항목입니다.");
        addParagraph(hwpxFile, basedOnExistingStyle, "기존 스타일의 특성을 유지하면서 불렛만 추가했습니다.");
        
        // 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "example_bullet_styles.hwpx");
        System.out.println("파일이 성공적으로 저장되었습니다: example_bullet_styles.hwpx");
    }
    
    /**
     * 제목을 추가합니다.
     */
    private static void addHeader(HWPXFile hwpxFile, String text) {
        Style headerStyle = StyleBuilder.create(hwpxFile, "제목 스타일", "Header Style")
                .withCharPr(charPr -> {
                    charPr.height(2400);
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
                    paraPr.margin().prev().value(800);
                    if (paraPr.margin().next() == null) {
                        paraPr.margin().createNext();
                    }
                    paraPr.margin().next().value(500);
                })
                .build();
                
        addParagraph(hwpxFile, headerStyle, text);
    }
    
    /**
     * 소제목을 추가합니다.
     */
    private static void addSubheader(HWPXFile hwpxFile, String text) {
        Style subheaderStyle = StyleBuilder.create(hwpxFile, "소제목 스타일", "Subheader Style")
                .withCharPr(charPr -> {
                    charPr.height(1600);
                    if (charPr.bold() == null) {
                        charPr.createBold();
                    }
                    charPr.textColor("#0066CC");
                })
                .withParaPr(paraPr -> {
                    if (paraPr.margin() == null) {
                        paraPr.createMargin();
                    }
                    if (paraPr.margin().prev() == null) {
                        paraPr.margin().createPrev();
                    }
                    paraPr.margin().prev().value(500);
                    if (paraPr.margin().next() == null) {
                        paraPr.margin().createNext();
                    }
                    paraPr.margin().next().value(300);
                })
                .build();
                
        addParagraph(hwpxFile, subheaderStyle, text);
    }
    
    /**
     * 문단을 추가합니다.
     */
    private static Para addParagraph(HWPXFile hwpxFile, Style style, String text) {
        var section = hwpxFile.sectionXMLFileList().get(0);
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
            run.charPrIDRef(charPrIDRef);
        }
        
        run.addNewT().addText(text);
        return para;
    }
} 