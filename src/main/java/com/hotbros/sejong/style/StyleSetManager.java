package com.hotbros.sejong.style;

import com.hotbros.sejong.BasicStyleSet;
import com.hotbros.sejong.BulletStyleSet;
import com.hotbros.sejong.ModernStyleSet;
import com.hotbros.sejong.StyleSet;
import kr.dogfoot.hwpxlib.object.HWPXFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 스타일셋을 관리하는 매니저 클래스입니다.
 * 스타일셋의 생성, 등록, 조회 등의 기능을 제공합니다.
 */
public class StyleSetManager {
    // 스타일셋 관리를 위한 Map
    private final Map<String, StyleSet> styleSets = new HashMap<>();
    private final HWPXFile hwpxFile;
    
    /**
     * 스타일셋 매니저를 생성하고 기본 스타일셋을 초기화합니다.
     * 
     * @param hwpxFile 스타일이 적용될 HWPX 파일
     */
    public StyleSetManager(HWPXFile hwpxFile) {
        this.hwpxFile = hwpxFile;
        initializeStyleSets();
    }
    
    /**
     * 기본 스타일셋 맵을 초기화합니다.
     */
    private void initializeStyleSets() {
        styleSets.put("기본", new BasicStyleSet(hwpxFile));
        styleSets.put("모던", new ModernStyleSet(hwpxFile));
        styleSets.put("불렛", new BulletStyleSet(hwpxFile));
    }
    
    /**
     * 스타일셋을 이름으로 가져옵니다.
     * 
     * @param name 가져올 스타일셋의 이름 (예: "기본", "모던", "불렛")
     * @return 요청된 스타일셋
     * @throws IllegalArgumentException 등록되지 않은 스타일셋 이름일 경우
     */
    public StyleSet getStyleSet(String name) {
        StyleSet styleSet = styleSets.get(name);
        if (styleSet == null) {
            throw new IllegalArgumentException("등록되지 않은 스타일셋 이름: " + name);
        }
        return styleSet;
    }
    
    /**
     * 사용 가능한 스타일셋 이름 배열을 반환합니다.
     * 
     * @return 사용 가능한 스타일셋 이름 배열
     */
    public String[] getAvailableStyleSetNames() {
        return styleSets.keySet().toArray(new String[0]);
    }
    
    /**
     * 사용 가능한 스타일셋 이름 집합을 반환합니다.
     * 
     * @return 사용 가능한 스타일셋 이름 집합
     */
    public Set<String> getAvailableStyleSetNameSet() {
        return styleSets.keySet();
    }
    
    /**
     * 새로운 스타일셋을 등록합니다.
     * 
     * @param name 등록할 스타일셋의 이름
     * @param styleSet 등록할 스타일셋 객체
     * @throws IllegalArgumentException 이미 존재하는 스타일셋 이름일 경우
     */
    public void registerStyleSet(String name, StyleSet styleSet) {
        if (styleSets.containsKey(name)) {
            throw new IllegalArgumentException("이미 존재하는 스타일셋 이름: " + name);
        }
        styleSets.put(name, styleSet);
    }
    
    /**
     * 기존 스타일셋을 업데이트합니다.
     * 
     * @param name 업데이트할 스타일셋의 이름
     * @param styleSet 새로운 스타일셋 객체
     * @throws IllegalArgumentException 등록되지 않은 스타일셋 이름일 경우
     */
    public void updateStyleSet(String name, StyleSet styleSet) {
        if (!styleSets.containsKey(name)) {
            throw new IllegalArgumentException("등록되지 않은 스타일셋 이름: " + name);
        }
        styleSets.put(name, styleSet);
    }
    
    /**
     * 스타일셋을 삭제합니다.
     * 
     * @param name 삭제할 스타일셋의 이름
     * @return 삭제 성공 여부
     */
    public boolean removeStyleSet(String name) {
        return styleSets.remove(name) != null;
    }
} 