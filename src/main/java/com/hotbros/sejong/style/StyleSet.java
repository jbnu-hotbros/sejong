package com.hotbros.sejong.style;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import java.util.Map;
import java.util.HashMap;

/**
 * 스타일셋 클래스 - HWPX 파일에 등록된 스타일을 제공합니다.
 * 이미 HWPX 파일에 등록되어 고유 ID를 가진 스타일 객체들을 
 * 제공하여 문서 작성에 직접 사용할 수 있게 합니다.
 */
public class StyleSet {
    private final Style titleStyle;
    private final Style outline1Style;
    private final Style outline2Style;
    private final Style outline3Style;
    private final Style outline4Style;
    private final Style outline5Style;
    private final Map<String, Style> styleMap = new HashMap<>();
    
    /**
     * 등록된 스타일 맵을 이용하여 스타일셋을 생성합니다.
     * 
     * @param registeredStyles 스타일 이름과 등록된 스타일 객체의 맵
     */
    public StyleSet(Map<String, Style> registeredStyles) {
        this.titleStyle = registeredStyles.get("제목");
        this.outline1Style = registeredStyles.get("개요1");
        this.outline2Style = registeredStyles.get("개요2");
        this.outline3Style = registeredStyles.get("개요3");
        this.outline4Style = registeredStyles.get("개요4");
        this.outline5Style = registeredStyles.get("개요5");
        
        // 필수 스타일이 모두 있는지 확인
        if (titleStyle == null || outline1Style == null || outline2Style == null ||
            outline3Style == null || outline4Style == null || outline5Style == null) {
            throw new IllegalArgumentException("필수 스타일이 모두 등록되지 않았습니다.");
        }
        
        // 스타일 맵 초기화
        styleMap.put("제목", titleStyle);
        styleMap.put("개요1", outline1Style);
        styleMap.put("개요2", outline2Style);
        styleMap.put("개요3", outline3Style);
        styleMap.put("개요4", outline4Style);
        styleMap.put("개요5", outline5Style);
        
        // 추가 스타일들도 맵에 추가
        for (Map.Entry<String, Style> entry : registeredStyles.entrySet()) {
            if (!styleMap.containsKey(entry.getKey())) {
                styleMap.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * 등록된 제목 스타일을 반환합니다.
     */
    private Style title() {
        return titleStyle;
    }
    
    /**
     * 등록된 개요1 스타일을 반환합니다.
     */
    private Style outline1() {
        return outline1Style;
    }
    
    /**
     * 등록된 개요2 스타일을 반환합니다.
     */
    private Style outline2() {
        return outline2Style;
    }
    
    /**
     * 등록된 개요3 스타일을 반환합니다.
     */
    private Style outline3() {
        return outline3Style;
    }
    
    /**
     * 등록된 개요4 스타일을 반환합니다.
     */
    private Style outline4() {
        return outline4Style;
    }
    
    /**
     * 등록된 개요5 스타일을 반환합니다.
     */
    private Style outline5() {
        return outline5Style;
    }
    
    /**
     * 이 스타일셋에 포함된 모든 등록된 스타일을 배열로 반환합니다.
     */
    public Style[] getAllStyles() {
        return styleMap.values().toArray(new Style[0]);
    }
    
    /**
     * 지정된 이름의 스타일을 반환합니다.
     * 런타임에 스타일을 동적으로 결정할 때 사용합니다.
     * 
     * @param styleName 스타일 이름 ("제목", "개요1", "개요2" 등)
     * @return 요청된 이름의 스타일 객체, 없으면 null 반환
     */
    public Style getStyle(String styleName) {
        if (styleName == null) {
            return null;
        }
        return styleMap.get(styleName);
    }
} 