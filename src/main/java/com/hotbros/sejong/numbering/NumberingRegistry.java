package com.hotbros.sejong.numbering;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import java.util.HashMap;
import java.util.Map;

/**
 * 넘버링 객체들을 관리하는 레지스트리
 * 이름으로 넘버링을 등록하고 조회할 수 있습니다.
 */
public class NumberingRegistry {
    private final Map<String, Numbering> numberings = new HashMap<>();
    
    /**
     * 새로운 넘버링을 레지스트리에 등록합니다.
     * 
     * @param name 등록할 넘버링의 이름
     * @param numbering 등록할 넘버링 객체
     * @throws IllegalArgumentException 넘버링이 null이거나 이름이 null 또는 빈 문자열인 경우
     */
    public void register(String name, Numbering numbering) {
        if (numbering == null) {
            throw new IllegalArgumentException("넘버링이 null입니다");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름이 null이거나 비어있습니다");
        }
        numberings.put(name, numbering);
    }
    
    /**
     * 등록된 넘버링 중 지정된 이름의 넘버링을 반환합니다.
     * 
     * @param name 가져올 넘버링의 이름
     * @return 해당 이름의 넘버링 (없을 경우 null)
     */
    public Numbering get(String name) {
        if (name == null) {
            return null;
        }
        return numberings.get(name);
    }
} 