package com.hotbros.sejong.util;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;

import java.util.HashMap;
import java.util.Map;

public class FontPresetManager {
    private static final Map<String, String> nameToId = new HashMap<>();
    private static int nextId = 3; // id는 3부터 시작

    /**
     * 문서 생성 시점에 호출하여 프리셋 폰트를 등록합니다.
     * @param refList RefList 객체
     */
    public static void registerPresets(RefList refList) {
        // 예시 프리셋 등록 (필요에 따라 추가)
        registerFont(refList, "DEFAULT", "맑은 고딕");
        registerFont(refList, "TITLE", "굴림체");
        registerFont(refList, "BODY", "돋움체");
    }

    private static void registerFont(RefList refList, String name, String fontName) {
        Font font = FontfaceUtil.createFont(fontName);
        String id = String.valueOf(nextId++);
        FontfaceUtil.addFontToAllFontfaces(refList, font, id);
        nameToId.put(name, id);
    }

    /**
     * 프리셋 이름으로 id를 반환합니다.
     * @param name 프리셋 이름
     * @return id (문자열)
     */
    public static String getId(String name) {
        return nameToId.get(name);
    }
} 