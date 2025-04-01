package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;

import static com.hotbros.sejong.StyleConstants.*;

/**
 * 불렛 스타일셋 구현 클래스 - 모든 개요 스타일에 불렛을 적용한 스타일셋입니다.
 */
public class BulletStyleSet implements StyleSet {
    private final Style titleStyle;
    private final Style outline1Style;
    private final Style outline2Style;
    private final Style outline3Style;
    private final Style outline4Style;
    private final Style outline5Style;
    
    /**
     * 생성자 - 불렛 스타일셋을 생성하고 스타일을 등록합니다.
     * @param hwpxFile HWPX 파일
     */
    public BulletStyleSet(HWPXFile hwpxFile) {
        if (hwpxFile == null) {
            throw new IllegalArgumentException("HWPX 파일이 null입니다");
        }
        
        // 제목 스타일
        StyleResult titleStyleResult = StyleBuilder.create("불렛 제목", "Bullet Title")
                .withCharPr(charPr -> {
                    charPr.heightAnd(2200);
                    charPr.createBold();
                    charPr.textColor("#222222");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    
                    paraPr.createMargin();
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(700);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(350);
                })
                .buildResult();
        
        this.titleStyle = StyleService.registerResult(hwpxFile, titleStyleResult);
        
        // 개요1 스타일 - 한글 개요1 스타일(ID:2) 기반으로 생성 + 불렛
        Style baseStyle1 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_1);
        StyleResult outline1StyleResult = StyleBuilder.create("불렛 개요1", "Bullet Outline1")
                .fromBaseStyle(baseStyle1)
                .withBullet("●")  // 불렛 추가
                .withCharPr(charPr -> {
                    charPr.heightAnd(1600);
                    charPr.createBold();
                    charPr.textColor("#333333");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(500);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(300);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(200);
                })
                .buildResult();
        
        this.outline1Style = StyleService.registerResult(hwpxFile, outline1StyleResult);
        
        // 개요2 스타일 - 한글 개요2 스타일(ID:3) 기반으로 생성 + 불렛
        Style baseStyle2 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_2);
        StyleResult outline2StyleResult = StyleBuilder.create("불렛 개요2", "Bullet Outline2")
                .fromBaseStyle(baseStyle2)
                .withBullet("○")  // 불렛 추가
                .withCharPr(charPr -> {
                    charPr.heightAnd(1400);
                    charPr.createBold();
                    charPr.textColor("#444444");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(1000);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(200);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(150);
                })
                .buildResult();
        
        this.outline2Style = StyleService.registerResult(hwpxFile, outline2StyleResult);
        
        // 개요3 스타일 - 한글 개요3 스타일(ID:4) 기반으로 생성 + 불렛
        Style baseStyle3 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_3);
        StyleResult outline3StyleResult = StyleBuilder.create("불렛 개요3", "Bullet Outline3")
                .fromBaseStyle(baseStyle3)
                .withBullet("▪")  // 불렛 추가
                .withCharPr(charPr -> {
                    charPr.heightAnd(1200);
                    charPr.createBold();
                    charPr.textColor("#555555");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(1500);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(150);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(150);
                })
                .buildResult();
        
        this.outline3Style = StyleService.registerResult(hwpxFile, outline3StyleResult);
        
        // 개요4 스타일 - 한글 개요4 스타일(ID:5) 기반으로 생성 + 불렛
        Style baseStyle4 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_4);
        StyleResult outline4StyleResult = StyleBuilder.create("불렛 개요4", "Bullet Outline4")
                .fromBaseStyle(baseStyle4)
                .withBullet("▫")  // 불렛 추가
                .withCharPr(charPr -> {
                    charPr.heightAnd(1100);
                    charPr.textColor("#666666");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(2000);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(120);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(100);
                })
                .buildResult();
        
        this.outline4Style = StyleService.registerResult(hwpxFile, outline4StyleResult);
        
        // 개요5 스타일 - 한글 개요5 스타일(ID:6) 기반으로 생성 + 불렛
        Style baseStyle5 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_5);
        StyleResult outline5StyleResult = StyleBuilder.create("불렛 개요5", "Bullet Outline5")
                .fromBaseStyle(baseStyle5)
                .withBullet("-")  // 불렛 추가
                .withCharPr(charPr -> {
                    charPr.heightAnd(1000);
                    charPr.textColor("#777777");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(2500);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(100);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(100);
                })
                .buildResult();
        
        this.outline5Style = StyleService.registerResult(hwpxFile, outline5StyleResult);
    }

    @Override
    public Style title() {
        return titleStyle;
    }

    @Override
    public Style outline1() {
        return outline1Style;
    }

    @Override
    public Style outline2() {
        return outline2Style;
    }

    @Override
    public Style outline3() {
        return outline3Style;
    }

    @Override
    public Style outline4() {
        return outline4Style;
    }

    @Override
    public Style outline5() {
        return outline5Style;
    }
} 