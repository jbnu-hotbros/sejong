package com.hotbros.sejong.style;

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint STYLE-010
 * @Description 스타일 정의 단계의 구성요소
 * @Reason 
 * - StyleSetTemplate: 여러 스타일 템플릿의 집합
 * - StyleTemplate: 개별 스타일의 정의
 * - 이 단계에서는 아직 실제 ID나 참조가 없음
 * @Status ACTIVE
 */
public interface StyleSetTemplate {
    /**
     * 제목 스타일 템플릿을 반환합니다.
     */
    StyleTemplate titleTemplate();
    
    /**
     * 개요1 스타일 템플릿을 반환합니다.
     */
    StyleTemplate outline1Template();
    
    /**
     * 개요2 스타일 템플릿을 반환합니다.
     */
    StyleTemplate outline2Template();
    
    /**
     * 개요3 스타일 템플릿을 반환합니다.
     */
    StyleTemplate outline3Template();
    
    /**
     * 개요4 스타일 템플릿을 반환합니다.
     */
    StyleTemplate outline4Template();
    
    /**
     * 개요5 스타일 템플릿을 반환합니다.
     */
    StyleTemplate outline5Template();
    
    /**
     * 이 스타일셋 템플릿의 모든 스타일 템플릿을 배열로 반환합니다.
     * 스타일 등록에 사용됩니다.
     */
    StyleTemplate[] getAllTemplates();
} 