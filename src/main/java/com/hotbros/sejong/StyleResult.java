package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;

/**
 * 스타일 관련 객체들을 함께 담는 결과 클래스.
 * 스타일 빌드 결과로 생성된 Style 객체와 함께 
 * 연관된 CharPr, ParaPr, Bullet 객체를 함께 보관합니다.
 */
public class StyleResult {
    private final Style style;
    private final CharPr charPr;
    private final ParaPr paraPr;
    private final Bullet bullet;
    
    /**
     * StyleResult 객체를 생성합니다.
     * 
     * @param style 스타일 객체
     * @param charPr 글자 모양 객체 (없을 경우 null)
     * @param paraPr 문단 모양 객체 (없을 경우 null)
     * @param bullet 불렛 객체 (없을 경우 null)
     */
    public StyleResult(Style style, CharPr charPr, ParaPr paraPr, Bullet bullet) {
        if (style == null) {
            throw new IllegalArgumentException("스타일 객체는 null일 수 없습니다.");
        }
        this.style = style;
        this.charPr = charPr;
        this.paraPr = paraPr;
        this.bullet = bullet;
    }
    
    /**
     * 스타일 객체를 반환합니다.
     * @return 스타일 객체
     */
    public Style getStyle() {
        return style;
    }
    
    /**
     * 글자 모양 객체를 반환합니다.
     * @return 글자 모양 객체, 없을 경우 null
     */
    public CharPr getCharPr() {
        return charPr;
    }
    
    /**
     * 문단 모양 객체를 반환합니다.
     * @return 문단 모양 객체, 없을 경우 null
     */
    public ParaPr getParaPr() {
        return paraPr;
    }
    
    /**
     * 불렛 객체를 반환합니다.
     * @return 불렛 객체, 없을 경우 null
     */
    public Bullet getBullet() {
        return bullet;
    }
    
    /**
     * 스타일이 글자 모양을 참조하는지 확인합니다.
     * @return 글자 모양 참조 여부
     */
    public boolean hasCharPrReference() {
        return charPr != null;
    }
    
    /**
     * 스타일이 문단 모양을 참조하는지 확인합니다.
     * @return 문단 모양 참조 여부
     */
    public boolean hasParaPrReference() {
        return paraPr != null;
    }
    
    /**
     * 문단 모양이 불렛을 참조하는지 확인합니다.
     * @return 불렛 참조 여부
     */
    public boolean hasBulletReference() {
        return bullet != null && paraPr != null;
    }
} 