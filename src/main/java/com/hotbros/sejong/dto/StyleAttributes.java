package com.hotbros.sejong.dto;


public class StyleAttributes {
    private String id;          // 스타일 ID (필수)
    private String name;        // 스타일 이름 (필수)
    private String engName;     // 스타일 영어 이름 (선택)
    private String nextStyleIDRef; // 다음 스타일 ID 참조 (선택)
    private String charPrIDRef; // 글자 모양 ID 참조
    private String paraPrIDRef; // 문단 모양 ID 참조

    // 기본 생성자
    public StyleAttributes() {
    }

    // 모든 필드를 받는 생성자 (새 필드 포함)
    public StyleAttributes(String id, String name, String engName, String nextStyleIDRef, 
                            String charPrIDRef, String paraPrIDRef) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.nextStyleIDRef = nextStyleIDRef;
        this.charPrIDRef = charPrIDRef;
        this.paraPrIDRef = paraPrIDRef;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getNextStyleIDRef() {
        return nextStyleIDRef;
    }

    public void setNextStyleIDRef(String nextStyleIDRef) {
        this.nextStyleIDRef = nextStyleIDRef;
    }

    public String getCharPrIDRef() { 
        return charPrIDRef; 
    }

    public void setCharPrIDRef(String charPrIDRef) { 
        this.charPrIDRef = charPrIDRef; 
    }

    public String getParaPrIDRef() { 
        return paraPrIDRef; 
    }

    public void setParaPrIDRef(String paraPrIDRef) { 
        this.paraPrIDRef = paraPrIDRef; 
    }

    // toString, equals, hashCode 등은 필요에 따라 추가

    /**
     * 이 DTO 객체의 내용을 Map<String, String>로 변환합니다.
     * 빌더나 팩토리가 Map 기반으로 동작할 때 사용될 수 있습니다.
     * @return DTO의 속성을 담은 Map
     */
    public java.util.Map<String, String> toMap() {
        java.util.Map<String, String> map = new java.util.HashMap<>();
        if (id != null) map.put("id", id);
        if (name != null) map.put("name", name);
        if (engName != null) map.put("engName", engName);
        if (nextStyleIDRef != null) map.put("nextStyleIDRef", nextStyleIDRef);
        if (charPrIDRef != null) map.put("charPrIDRef", charPrIDRef);
        if (paraPrIDRef != null) map.put("paraPrIDRef", paraPrIDRef);
        return map;
    }

    /**
     * Map<String, String>에서 StyleAttributes DTO 객체를 생성합니다.
     * @param map DTO를 생성할 속성이 담긴 Map
     * @return 생성된 StyleAttributes 객체
     */
    public static StyleAttributes fromMap(java.util.Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StyleAttributes attr = new StyleAttributes();
        attr.setId(map.get("id"));
        attr.setName(map.get("name"));
        attr.setEngName(map.get("engName"));
        attr.setNextStyleIDRef(map.get("nextStyleIDRef"));
        attr.setCharPrIDRef(map.get("charPrIDRef"));
        attr.setParaPrIDRef(map.get("paraPrIDRef"));
        return attr;
    }
}