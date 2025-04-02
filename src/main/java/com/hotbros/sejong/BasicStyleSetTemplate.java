package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;

import static com.hotbros.sejong.StyleConstants.*;

/**
 * 기본 스타일셋 템플릿 구현 클래스 - 기본적인 제목, 개요 스타일을 정의합니다.
 * 스타일을 정의하고 StyleResult 형태로 보관만 하며, 실제 등록은 별도로 수행됩니다.
 */
public class BasicStyleSetTemplate implements StyleSetTemplate {
    private final StyleResult titleStyleResult;
    private final StyleResult outline1StyleResult;
    private final StyleResult outline2StyleResult;
    private final StyleResult outline3StyleResult;
    private final StyleResult outline4StyleResult;
    private final StyleResult outline5StyleResult;
    
    /**
     * 생성자 - 기본 스타일셋 템플릿의 스타일 결과 객체들을 생성합니다.
     * @param hwpxFile HWPX 파일 (기본 스타일 참조용)
     */
    public BasicStyleSetTemplate(HWPXFile hwpxFile) {
        // HWPX 파일이 null인지 확인
        if (hwpxFile == null) {
            throw new IllegalArgumentException("HWPX 파일이 null입니다");
        }
        
        // 제목 스타일 결과 생성
        Style baseStyle = StyleService.findStyleById(hwpxFile, "1"); // 기본 제목 스타일
        this.titleStyleResult = StyleBuilder.create("제목", "Title")
                .fromBaseStyle(baseStyle)
                .withCharPr(charPr -> {
                    charPr.heightAnd(2200);
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    
                    paraPr.createMargin();
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(850);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(550);
                })
                .buildResult();
        
        // 개요1 스타일 결과 생성
        Style baseStyle1 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_1);
        this.outline1StyleResult = StyleBuilder.create("개요1", "Outline1")
                .fromBaseStyle(baseStyle1)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1600);
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(0);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(300);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(200);
                })
                .buildResult();
        
        // 개요2 스타일 결과 생성
        Style baseStyle2 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_2);
        this.outline2StyleResult = StyleBuilder.create("개요2", "Outline2")
                .fromBaseStyle(baseStyle2)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1400);
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(600);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(200);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(150);
                })
                .buildResult();
        
        // 개요3 스타일 결과 생성
        Style baseStyle3 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_3);
        this.outline3StyleResult = StyleBuilder.create("개요3", "Outline3")
                .fromBaseStyle(baseStyle3)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1200);
                    charPr.createBold();
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(1200);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(150);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(150);
                })
                .buildResult();
        
        // 개요4 스타일 결과 생성
        Style baseStyle4 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_4);
        this.outline4StyleResult = StyleBuilder.create("개요4", "Outline4")
                .fromBaseStyle(baseStyle4)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1100);
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(1800);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(150);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(100);
                })
                .buildResult();
        
        // 개요5 스타일 결과 생성
        Style baseStyle5 = StyleService.findStyleById(hwpxFile, STYLE_ID_OUTLINE_5);
        this.outline5StyleResult = StyleBuilder.create("개요5", "Outline5")
                .fromBaseStyle(baseStyle5)
                .withCharPr(charPr -> {
                    charPr.heightAnd(1000);
                })
                .withParaPr(paraPr -> {
                    paraPr.createAlign();
                    paraPr.align().horizontal(HorizontalAlign2.LEFT);
                    
                    paraPr.createMargin();
                    paraPr.margin().createLeft();
                    paraPr.margin().left().value(2400);
                    
                    paraPr.margin().createPrev();
                    paraPr.margin().prev().value(100);
                    
                    paraPr.margin().createNext();
                    paraPr.margin().next().value(100);
                })
                .buildResult();
    }
    
    @Override
    public String getName() {
        return "기본";
    }
    
    @Override
    public StyleResult titleResult() {
        return titleStyleResult;
    }
    
    @Override
    public StyleResult outline1Result() {
        return outline1StyleResult;
    }
    
    @Override
    public StyleResult outline2Result() {
        return outline2StyleResult;
    }
    
    @Override
    public StyleResult outline3Result() {
        return outline3StyleResult;
    }
    
    @Override
    public StyleResult outline4Result() {
        return outline4StyleResult;
    }
    
    @Override
    public StyleResult outline5Result() {
        return outline5StyleResult;
    }
    
    @Override
    public StyleResult[] getAllResults() {
        return new StyleResult[] {
            titleStyleResult,
            outline1StyleResult,
            outline2StyleResult,
            outline3StyleResult,
            outline4StyleResult,
            outline5StyleResult
        };
    }
} 