package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import java.util.function.Function;

/**
 * ID 관련 유틸리티 메서드를 제공하는 클래스.
 * 범용적인 ID 관리 기능을 제공합니다.
 */
public class IdUtils {
    
    /**
     * 컬렉션에서 문자열 ID의 최대값을 찾아 반환합니다.
     * ID는 숫자 형식이어야 합니다. 숫자가 아닌 ID는 계산에서 제외됩니다.
     * 
     * @param <T> 항목 타입
     * @param items ID를 추출할 항목 컬렉션
     * @param idExtractor 항목에서 ID를 추출하는 함수
     * @return 최대 ID 값 (유효한 ID가 없는 경우 0)
     */
    public static <T> int getMaxID(Iterable<T> items, Function<T, String> idExtractor) {
        int maxID = 0;
        
        if (items == null) {
            return maxID;
        }
        
        for (var item : items) {
            try {
                String idStr = idExtractor.apply(item);
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    maxID = Math.max(maxID, id);
                }
            } catch (NumberFormatException e) {
                // 숫자가 아닌 ID는 무시
            }
        }
        
        return maxID;
    }

    /**
     * RefList에서 특정 ID를 가진 Style 객체를 찾습니다.
     * 
     * @param refList 스타일을 찾을 RefList
     * @param styleId 찾을 스타일 ID
     * @return 찾은 Style 객체, 없으면 null 반환
     */
    public static Style findStyleById(RefList refList, String styleId) {
        if (refList.styles() == null) {
            throw new IllegalArgumentException("RefList.styles가 null입니다");
        }
        
        for (Style style : refList.styles().items()) {
            if (styleId.equals(style.id())) {
                return style;
            }
        }
        
        return null;
    }
} 