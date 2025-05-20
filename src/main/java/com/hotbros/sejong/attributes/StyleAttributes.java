package com.hotbros.sejong.attributes;

// kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
// DTO에서는 String으로 받고, Factory 등에서 Enum으로 변환하는 것을 고려할 수 있습니다.
// 또는 처음부터 StyleType을 사용해도 됩니다. 여기서는 우선 String으로 제안합니다.

public class StyleAttributes {
    private String id;          // 스타일 ID (필수)
    private String name;        // 스타일 이름 (필수)
    private String engName;     // 스타일 영어 이름 (선택)
    private String nextStyleIDRef; // 다음 스타일 ID 참조 (선택)

    // 기본 생성자
    public StyleAttributes() {
    }

    // 모든 필드를 받는 생성자
    public StyleAttributes(String id, String name, String engName, String nextStyleIDRef) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.nextStyleIDRef = nextStyleIDRef;
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

    // toString, equals, hashCode 등은 필요에 따라 추가

    /**
     * 이 DTO 객체의 내용을 Map<String, Object>로 변환합니다.
     * 빌더나 팩토리가 Map 기반으로 동작할 때 사용될 수 있습니다.
     * @return DTO의 속성을 담은 Map
     */
    public java.util.Map<String, Object> toMap() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        if (id != null) map.put("id", id);
        if (name != null) map.put("name", name);
        if (engName != null) map.put("engName", engName);
        if (nextStyleIDRef != null) map.put("nextStyleIDRef", nextStyleIDRef);
        // 다른 선택적 필드들도 동일하게 추가
        return map;
    }

    /**
     * Map<String, Object>에서 StyleAttributes DTO 객체를 생성합니다.
     * @param map DTO를 생성할 속성이 담긴 Map
     * @return 생성된 StyleAttributes 객체
     */
    public static StyleAttributes fromMap(java.util.Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        StyleAttributes attr = new StyleAttributes();
        attr.setId((String) map.get("id"));
        attr.setName((String) map.get("name"));
        attr.setEngName((String) map.get("engName"));
        attr.setNextStyleIDRef((String) map.get("nextStyleIDRef"));
        // 다른 선택적 필드들도 동일하게 추출
        return attr;
    }
}