package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import java.util.function.Function;

/**
 * 스타일 관련 유틸리티 메서드를 제공하는 클래스
 */
public class StyleUtils {
    
    /**
     * RefList에서 현재 사용 중인 최대 CharPr ID 값을 찾습니다.
     */
    public static int getMaxCharPrID(RefList refList) {
        if (refList.charProperties() == null) {
            throw new IllegalArgumentException("RefList.charProperties가 null입니다");
        }
        
        return getMaxID(refList.charProperties().items(), item -> item.id());
    }
    
    /**
     * RefList에서 현재 사용 중인 최대 ParaPr ID 값을 찾습니다.
     */
    public static int getMaxParaPrID(RefList refList) {
        if (refList.paraProperties() == null) {
            throw new IllegalArgumentException("RefList.paraProperties가 null입니다");
        }
        
        return getMaxID(refList.paraProperties().items(), item -> item.id());
    }
    
    /**
     * RefList에서 현재 사용 중인 최대 Style ID 값을 찾습니다.
     */
    public static int getMaxStyleID(RefList refList) {
        if (refList.styles() == null) {
            throw new IllegalArgumentException("RefList.styles가 null입니다");
        }
        
        return getMaxID(refList.styles().items(), item -> item.id());
    }
    
    /**
     * 리스트에서 최대 ID 값을 찾습니다.
     */
    public static <T> int getMaxID(Iterable<T> items, Function<T, String> idExtractor) {
        int maxID = -1;
        
        if (items == null) {
            return maxID;
        }
        
        for (var item : items) {
            try {
                int id = Integer.parseInt(idExtractor.apply(item));
                maxID = Math.max(maxID, id);
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