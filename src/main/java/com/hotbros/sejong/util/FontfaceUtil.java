package com.hotbros.sejong.util;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontFamilyType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.FontType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Fontface;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.TypeInfo;

public class FontfaceUtil {
    
    /**
     * 기본 설정으로 폰트를 생성합니다.
     * 
     * @param id 폰트 ID
     * @param fontName 폰트 이름
     * @return 생성된 Font 객체
     */
    public static Font createFont(String fontName) {
        Font font = new Font();
        font.face(fontName);
        font.type(FontType.TTF);
        font.isEmbedded(false);
        
        font.createTypeInfo();  // TypeInfo 객체 생성
        TypeInfo typeInfo = font.typeInfo();  // 생성된 TypeInfo 객체 가져오기
        
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

    /**
     * RefList에서 모든 Fontface를 순회하며 Font를 추가합니다.
     * 
     * @param refList 참조리스트
     * @param font 추가할 폰트
     * @param id 부여할 ID
     */
    public static void addFontToAllFontfaces(RefList refList, Font font, String id) {
        if (refList == null || refList.fontfaces() == null) {
            return;
        }
        
        // 폰트에 ID 설정
        font.id(id);
        
        // 모든 Fontface 순회
        for (Fontface fontface : refList.fontfaces().fontfaces()) {
            fontface.addFont(font);
        }
    }
    
    
}
