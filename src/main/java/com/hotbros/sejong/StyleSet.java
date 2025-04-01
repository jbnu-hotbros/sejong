package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;

/**
 * 스타일셋 인터페이스 - 문서의 기본 스타일을 관리하는 인터페이스
 */
public interface StyleSet {
    /**
     * 제목 스타일을 반환합니다.
     */
    Style title();
    
    /**
     * 개요1 스타일을 반환합니다.
     */
    Style outline1();
    
    /**
     * 개요2 스타일을 반환합니다.
     */
    Style outline2();
    
    /**
     * 개요3 스타일을 반환합니다.
     */
    Style outline3();
    
    /**
     * 개요4 스타일을 반환합니다.
     */
    Style outline4();
    
    /**
     * 개요5 스타일을 반환합니다.
     */
    Style outline5();
} 