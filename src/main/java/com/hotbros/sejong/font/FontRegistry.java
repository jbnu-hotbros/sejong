package com.hotbros.sejong.font;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontFamilyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hotbros.sejong.util.IdGenerator;

public class FontRegistry {
    private final RefList refList;
    private final IdGenerator idGenerator;

    public FontRegistry(RefList refList, IdGenerator idGenerator) {
        this.refList = refList;
        this.idGenerator = idGenerator;
        initialize();
    }

    private void initialize() {
        // 가장 많이 쓸 addFont로 기본 폰트 등록
        addFont("HY헤드라인M");
        addFont("맑은 고딕");
    }

    // 1. 객체만 생성 (id 없음, 등록X)
    public Font buildFont(String fontName) {
        Font font = new Font();
        font.face(fontName);
        font.type(FontType.TTF);
        font.isEmbedded(false);
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

    // 2. Font를 레지스트리에 등록 (id 부여, 중복 방지)
    public Font registerFont(String name, Font font) {
        if (getFontByName(name) != null) {
            return getFontByName(name);
        }
        String id = String.valueOf(idGenerator.nextFontId());
        font.id(id);
        addFontToRefList(font);
        return font;
    }

    // 3. 이름으로 바로 등록 (내부적으로 build+register)
    public Font addFont(String fontName) {
        Font font = buildFont(fontName);
        return registerFont(fontName, font);
    }

    // 4. 조회
    public Font getFontByName(String name) {
        System.out.println("getFontByName: " + name);
        if (refList != null && refList.fontfaces() != null && refList.fontfaces().fontfaces() != null) {
            for (var fontface : refList.fontfaces().fontfaces()) {
                if (fontface.fonts() != null) {
                    for (var font : fontface.fonts()) {
                        if (font.face().equals(name)) {
                            return font;
                        }
                    }
                }
            }
        }
        return null;
    }

    // 5. 전체 Font 리스트 반환 (읽기 전용)
    public List<Font> getAllFonts() {
        List<Font> fonts = new ArrayList<>();
        if (refList != null && refList.fontfaces() != null && refList.fontfaces().fontfaces() != null) {
            for (var fontface : refList.fontfaces().fontfaces()) {
                if (fontface.fonts() != null) {
                    for (var font : fontface.fonts()) {
                        fonts.add(font);
                    }
                }
            }
        }
        return Collections.unmodifiableList(fonts);
    }

    // RefList에 Font 추가 (중복 방지)
    private void addFontToRefList(Font font) {
        if (refList == null || font == null) {
            return;
        }
        if (refList.fontfaces() == null) {
            refList.createFontfaces();
        }
        if (!refList.fontfaces().fontfaces().iterator().hasNext()) {
            refList.fontfaces().addNewFontface();
        }

        for (var fontface : refList.fontfaces().fontfaces()) {
            boolean exists = false;
            if (fontface.fonts() != null) {
                for (var f : fontface.fonts()) {
                    if (f.face().equals(font.face())) {
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                fontface.addFont(font);
            }
        }
    }
}
