package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;

/**
 * 스타일 템플릿 클래스 - 스타일 및 관련 객체들의 템플릿을 담는 클래스입니다.
 * StyleBuilder로 생성된 각 스타일 컴포넌트(Style, CharPr, ParaPr, Bullet)의 템플릿을
 * 문서에 등록하기 전에 함께 보관합니다. 각 템플릿 객체는 임시 ID를 가지고 있으며,
 * 실제 등록 시점에 적절한 ID가 할당됩니다.
 */
public class StyleTemplate {
    private final Style style;
    private final CharPr charPr;
    private final ParaPr paraPr;
    private final Bullet bullet;
    
    /**
     * StyleTemplate 객체를 생성합니다.
     * 
     * @param style 스타일 템플릿 객체
     * @param charPr 글자 모양 템플릿 객체 (없을 경우 null)
     * @param paraPr 문단 모양 템플릿 객체 (없을 경우 null)
     * @param bullet 불렛 템플릿 객체 (없을 경우 null)
     */
    public StyleTemplate(Style style, CharPr charPr, ParaPr paraPr, Bullet bullet) {
        if (style == null) {
            throw new IllegalArgumentException("스타일 템플릿 객체는 null일 수 없습니다.");
        }
        this.style = style;
        this.charPr = charPr;
        this.paraPr = paraPr;
        this.bullet = bullet;
    }
    
    /**
     * 스타일 템플릿 객체를 반환합니다.
     * @return 스타일 템플릿 객체
     */
    public Style getStyle() {
        return style;
    }
    
    /**
     * 글자 모양 템플릿 객체를 반환합니다.
     * @return 글자 모양 템플릿 객체, 없을 경우 null
     */
    public CharPr getCharPr() {
        return charPr;
    }
    
    /**
     * 문단 모양 템플릿 객체를 반환합니다.
     * @return 문단 모양 템플릿 객체, 없을 경우 null
     */
    public ParaPr getParaPr() {
        return paraPr;
    }
    
    /**
     * 불렛 템플릿 객체를 반환합니다.
     * @return 불렛 템플릿 객체, 없을 경우 null
     */
    public Bullet getBullet() {
        return bullet;
    }
    
    /**
     * 스타일 템플릿이 글자 모양을 참조하는지 확인합니다.
     * @return 글자 모양 참조 여부
     */
    public boolean hasCharPrReference() {
        return charPr != null;
    }
    
    /**
     * 스타일 템플릿이 문단 모양을 참조하는지 확인합니다.
     * @return 문단 모양 참조 여부
     */
    public boolean hasParaPrReference() {
        return paraPr != null;
    }
    
    /**
     * 문단 모양 템플릿이 불렛을 참조하는지 확인합니다.
     * @return 불렛 참조 여부
     */
    public boolean hasBulletReference() {
        return bullet != null && paraPr != null;
    }
} 