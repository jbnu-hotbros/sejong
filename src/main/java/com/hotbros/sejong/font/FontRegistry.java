package com.hotbros.sejong.font;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontFamilyType;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import com.hotbros.sejong.util.IdGenerator;

public class FontRegistry {
    // 폰트를 관리하는 Map (name → Font 객체)
    private final Map<String, Font> fontMap;
    private final RefList refList;
    private final IdGenerator idGenerator;

    public FontRegistry(RefList refList, IdGenerator idGenerator) {
        this.fontMap = new HashMap<>();
        this.refList = refList;
        this.idGenerator = idGenerator;
        initialize();
    }

    private void initialize() {
        registerFont("맑은 고딕", createFont("맑은 고딕"));
        registerFont("굴림체", createFont("굴림체"));
        registerFont("돋움체", createFont("돋움체"));
    }

    // Font 등록 (name으로)
    public void registerFont(String name, Font font) {
        if (fontMap.containsKey(name)) {
            throw new IllegalArgumentException("이미 존재하는 폰트 이름입니다: " + name);
        }
        // id는 생성 시 할당됨
        fontMap.put(name, font);
        addFontToRefList(font);
    }

    // name으로 Font 조회
    public Font getFontByName(String name) {
        return fontMap.get(name);
    }

    // 전체 Font 반환 (읽기 전용)
    public Map<String, Font> getAllFonts() {
        return Collections.unmodifiableMap(fontMap);
    }

    // RefList에 Font 추가
    private void addFontToRefList(Font font) {
        if (refList == null || refList.fontfaces() == null) {
            return;
        }
        for (var fontface : refList.fontfaces().fontfaces()) {
            fontface.addFont(font);
        }
    }

    // Font 생성 유틸 (id는 생성 시 할당)
    public Font createFont(String fontName) {
        Font font = new Font();
        String id = String.valueOf(idGenerator.nextFontId());
        font.face(fontName);
        font.type(FontType.TTF);
        font.isEmbedded(false);
        font.id(id);
        font.createTypeInfo();
        var typeInfo = font.typeInfo();
        typeInfo.familyType(FontFamilyType.FCAT_GOTHIC);
        typeInfo.weight(6);
        typeInfo.proportion(4);
        typeInfo.contrast(0);
        typeInfo.strokeVariation(1);
        typeInfo.armStyle(true);
        typeInfo.letterform(true);
        typeInfo.midline(1);
        typeInfo.xHeight(1);
        return font;
    }
} 