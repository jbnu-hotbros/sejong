package com.hotbros.sejong.factory;

import com.hotbros.sejong.dto.FontAttributeConfig;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontFamilyType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;

public class FontFactory {
    public static Font createDefaultFont(String fontName) {
        Font font = new Font();

        // 고정값 설정
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

        // config 속성 적용
        applyFontAttribute(font, fontName);

        return font;
    }

    public static void applyFontAttribute(Font font, String fontName) {
        if (font == null || fontName == null) {
            throw new IllegalArgumentException("Font and fontName cannot be null");
        }

        if (fontName != null) {
            font.face(fontName);
        }
    }

    // RefList에 Font 추가 (중복 방지)
    public static void addFontToRefList(RefList refList, Font font) {
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
