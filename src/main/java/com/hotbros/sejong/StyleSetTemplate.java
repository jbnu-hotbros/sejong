package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;

/**
 * 스타일셋 템플릿 인터페이스 - 등록 전 스타일 정의를 담당합니다.
 * 문서에 등록되기 전의 스타일 정의를 관리하는 템플릿으로,
 * 다양한 문서에 재사용할 수 있습니다.
 */
public interface StyleSetTemplate {
    /**
     * 제목 스타일 결과를 반환합니다.
     */
    StyleResult titleResult();
    
    /**
     * 개요1 스타일 결과를 반환합니다.
     */
    StyleResult outline1Result();
    
    /**
     * 개요2 스타일 결과를 반환합니다.
     */
    StyleResult outline2Result();
    
    /**
     * 개요3 스타일 결과를 반환합니다.
     */
    StyleResult outline3Result();
    
    /**
     * 개요4 스타일 결과를 반환합니다.
     */
    StyleResult outline4Result();
    
    /**
     * 개요5 스타일 결과를 반환합니다.
     */
    StyleResult outline5Result();
    
    /**
     * 이 스타일셋 템플릿의 모든 스타일 결과를 배열로 반환합니다.
     * 스타일 등록에 사용됩니다.
     */
    StyleResult[] getAllResults();
    
    /**
     * 이 템플릿의 이름을 반환합니다.
     */
    String getName();
} 