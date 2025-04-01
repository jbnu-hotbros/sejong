package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;

import static com.hotbros.sejong.StyleConstants.*;

/**
 * 모던 스타일셋 구현 클래스 - 현대적인 디자인의 제목, 개요 스타일을 제공합니다.
 */
public class ModernStyleSet implements StyleSet {
    private final Style titleStyle;
    private final Style outline1Style;
    private final Style outline2Style;
    private final Style outline3Style;
    private final Style outline4Style;
    private final Style outline5Style;
    
    /**
     * 생성자 - 모던 스타일셋을 생성하고 스타일을 등록합니다.
     * @param hwpxFile HWPX 파일
     */
    public ModernStyleSet(HWPXFile hwpxFile) {
        if (hwpxFile == null) {
            throw new IllegalArgumentException("HWPX 파일이 null입니다");
        }
        
        // 제목 스타일 (파란색, 크게)
        StyleResult titleStyleResult = StyleBuilder.create("모던 제목", "Modern Title")
                .withCharPr(charPr -> {
                    charPr.heightAnd(2600)
                        .textColor("#2C3E50");
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    
                    paraPr.createMargin();
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(800);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(400);
                })
                .buildResult();
        
        this.titleStyle = StyleService.registerResult(hwpxFile, titleStyleResult);
        
        // 개요1 스타일 (진한 파란색) - 한글 개요1 스타일(ID:2) 기반
        Style baseStyle1 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_1);
        StyleResult outline1StyleResult = StyleBuilder.create("모던 개요1", "Modern Outline1")
                .fromBaseStyle(baseStyle1)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1600)
                        .textColor("#3498DB");
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(0);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(350);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(200);
                })
                .buildResult();
        
        this.outline1Style = StyleService.registerResult(hwpxFile, outline1StyleResult);
        
        // 개요2 스타일 (하늘색) - 한글 개요2 스타일(ID:3) 기반
        Style baseStyle2 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_2);
        StyleResult outline2StyleResult = StyleBuilder.create("모던 개요2", "Modern Outline2")
                .fromBaseStyle(baseStyle2)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1400)
                        .textColor("#2980B9");
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(700);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(250);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(150);
                })
                .buildResult();
        
        this.outline2Style = StyleService.registerResult(hwpxFile, outline2StyleResult);
        
        // 개요3 스타일 (회색) - 한글 개요3 스타일(ID:4) 기반
        Style baseStyle3 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_3);
        StyleResult outline3StyleResult = StyleBuilder.create("모던 개요3", "Modern Outline3")
                .fromBaseStyle(baseStyle3)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1200)
                        .textColor("#7F8C8D");
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(1400);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(200);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(150);
                })
                .buildResult();
        
        this.outline3Style = StyleService.registerResult(hwpxFile, outline3StyleResult);
        
        // 개요4 스타일 (연한 회색) - 한글 개요4 스타일(ID:5) 기반
        Style baseStyle4 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_4);
        StyleResult outline4StyleResult = StyleBuilder.create("모던 개요4", "Modern Outline4")
                .fromBaseStyle(baseStyle4)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1100)
                        .textColor("#95A5A6");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(2100);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(150);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(100);
                })
                .buildResult();
        
        this.outline4Style = StyleService.registerResult(hwpxFile, outline4StyleResult);
        
        // 개요5 스타일 (매우 연한 회색) - 한글 개요5 스타일(ID:6) 기반
        Style baseStyle5 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_5);
        StyleResult outline5StyleResult = StyleBuilder.create("모던 개요5", "Modern Outline5")
                .fromBaseStyle(baseStyle5)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1000)
                        .textColor("#BDC3C7");
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(2800);
                    
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