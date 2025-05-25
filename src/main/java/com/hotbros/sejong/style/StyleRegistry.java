package com.hotbros.sejong.style;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 사전에 정의된 스타일 레지스트리 클래스.
 * 외부에서는 String ID로 스타일에 접근하며,
 * 내부적으로는 Map<String, Style>로 관리됩니다.
 */
public class StyleRegistry {

    // 스타일을 관리하는 Map (ID → Style 객체)
    private final Map<String, Style> styleMap;
    private final StylePreset preset;
    private final RefList refList;

    public StyleRegistry(RefList refList, StylePreset preset) {
        this.styleMap = new HashMap<>();
        this.preset = preset;
        this.refList = refList;

        initialize();
    }

    // 생성자에서 필요한 스타일을 등록
    private void initialize() {
        // 모든 프리셋 메서드를 호출하여 스타일 등록
        Style[] presets = {
                preset.titlePreset(),
                preset.bodyPreset(),
                preset.heading1Preset(),
                preset.heading2Preset(),
                preset.heading3Preset(),
                preset.heading4Preset(),
                preset.heading5Preset(),
                preset.heading6Preset(),
                preset.heading7Preset(),
                preset.tableHeaderPreset(),
                preset.tableCellPreset()
        };

        for (Style style : presets) {
            // 스타일이 이미 등록되어 있는지 확인 (이름으로 체크)
            String styleName = style.name();
            if (styleName != null) {
                for (Style existingStyle : refList.styles().items()) {
                    if (styleName.equals(existingStyle.name())) {
                        throw new IllegalArgumentException("이미 존재하는 스타일 이름입니다: " + styleName);
                    }
                }
            }

            styleMap.put(style.name(), style);
            refList.styles().add(style);
        }
    }

    /**
     * ID를 통해 스타일을 조회하는 메서드
     *
     * @param id 스타일 ID (예: "TITLE")
     * @return Style 객체 (null 반환 가능)
     */
    public Style getStyleById(String id) {
        return styleMap.get(id);
    }

    /**
     * 지원하는 모든 스타일 ID 리스트를 반환
     *
     * @return Unmodifiable Map of IDs and Styles
     */
    public Map<String, Style> getAllStyles() {
        return Collections.unmodifiableMap(styleMap);
    }

    /**
     * 스타일의 간단한 설명 제공 (예: 디버깅용)
     */
    public String describeStyle(String id) {
        Style style = styleMap.get(id);
        if (style == null) {
            return "존재하지 않는 스타일입니다: " + id;
        }
        return String.format("Style ID: %s, Name: %s", id, style.name());
    }

    // 레지스트리에 새로운 스타일 추가 (내부용)
    public void registerStyle(String id, Style style) {
        if (styleMap.containsKey(id)) {
            throw new IllegalArgumentException("이미 존재하는 스타일 ID입니다: " + id);
        }
        styleMap.put(id, style);
    }
}
