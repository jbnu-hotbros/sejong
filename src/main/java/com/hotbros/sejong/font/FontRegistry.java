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
        // 기존 폰트 등록
        addFontToRefList(createFont("HY헤드라인M"));
        addFontToRefList(createFont("맑은 고딕"));
        // RefList에서 함초롬 폰트 찾아서 등록 (중복 방지)
        // 이미 refList에 있으면 추가하지 않음
    }

    // 폰트 이름으로 Font 객체 조회 (refList에서 직접 탐색)
    public Font getFontByName(String name) {
        if (refList != null && refList.fontfaces() != null) {
            for (var fontface : refList.fontfaces().fontfaces()) {
                for (var font : fontface.fonts()) {
                    // System.out.println(font.face());
                    if (font.face().equals(name)) {
                        return font;
                    }
                }
            }
        }
        return null;
    }

    // 전체 Font 리스트 반환 (읽기 전용, refList에서 직접 수집)
    public List<Font> getAllFonts() {
        List<Font> fonts = new ArrayList<>();
        if (refList != null && refList.fontfaces() != null) {
            for (var fontface : refList.fontfaces().fontfaces()) {
                for (var font : fontface.fonts()) {
                    fonts.add(font);
                }
            }
        }
        return Collections.unmodifiableList(fonts);
    }

    // RefList에 Font 추가 (중복 방지)
    private void addFontToRefList(Font font) {
        if (refList == null || refList.fontfaces() == null || font == null) {
            return;
        }
        for (var fontface : refList.fontfaces().fontfaces()) {
            boolean exists = false;
            for (var f : fontface.fonts()) {
                if (f.face().equals(font.face())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                fontface.addFont(font);
            }
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