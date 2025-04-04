package com.hotbros.sejong.style;

import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import static com.hotbros.sejong.style.StyleConstants.*;

/**
 * 기본 스타일셋 템플릿 구현 클래스 - 기본적인 제목, 개요 스타일을 정의합니다.
 * 스타일을 정의하고 StyleTemplate 형태로 보관만 하며, 실제 등록은 별도로 수행됩니다.
 * 모든 스타일은 외부 파일에 의존하지 않고 내부 템플릿만 사용합니다.
 */
public class BasicStyleSetTemplate implements StyleSetTemplate {
    private final StyleTemplate titleTemplate;
    private final StyleTemplate outline1Template;
    private final StyleTemplate outline2Template;
    private final StyleTemplate outline3Template;
    private final StyleTemplate outline4Template;
    private final StyleTemplate outline5Template;
    
    /**
     * 생성자 - 기본 스타일셋 템플릿의 스타일 템플릿 객체들을 생성합니다.
     * 모든 스타일은 내부 템플릿 파일을 기반으로 생성됩니다.
     */
    public BasicStyleSetTemplate() {
        // 제목 스타일 템플릿 생성
        this.titleTemplate = StyleBuilder.create("제목", "Title")
                .fromBaseStyleId(STYLE_ID_NORMAL) // 기본 제목 스타일
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
                .buildTemplate();
        
        // 개요1 스타일 템플릿 생성
        this.outline1Template = StyleBuilder.create("개요1", "Outline1")
                .fromBaseStyleId(STYLE_ID_OUTLINE_1)
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
                .buildTemplate();
        
        // 개요2 스타일 템플릿 생성
        this.outline2Template = StyleBuilder.create("개요2", "Outline2")
                .fromBaseStyleId(STYLE_ID_OUTLINE_2)
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
                .buildTemplate();
        
        // 개요3 스타일 템플릿 생성
        this.outline3Template = StyleBuilder.create("개요3", "Outline3")
                .fromBaseStyleId(STYLE_ID_OUTLINE_3)
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
                .buildTemplate();
        
        // 개요4 스타일 템플릿 생성
        this.outline4Template = StyleBuilder.create("개요4", "Outline4")
                .fromBaseStyleId(STYLE_ID_OUTLINE_4)
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
                .buildTemplate();
        
        // 개요5 스타일 템플릿 생성
        this.outline5Template = StyleBuilder.create("개요5", "Outline5")
                .fromBaseStyleId(STYLE_ID_OUTLINE_5)
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
                .buildTemplate();
    }
    
    @Override
    public StyleTemplate titleTemplate() {
        return titleTemplate;
    }
    
    @Override
    public StyleTemplate outline1Template() {
        return outline1Template;
    }
    
    @Override
    public StyleTemplate outline2Template() {
        return outline2Template;
    }
    
    @Override
    public StyleTemplate outline3Template() {
        return outline3Template;
    }
    
    @Override
    public StyleTemplate outline4Template() {
        return outline4Template;
    }
    
    @Override
    public StyleTemplate outline5Template() {
        return outline5Template;
    }
    
    @Override
    public StyleTemplate[] getAllTemplates() {
        return new StyleTemplate[] {
            titleTemplate,
            outline1Template,
            outline2Template,
            outline3Template,
            outline4Template,
            outline5Template
        };
    }
} 