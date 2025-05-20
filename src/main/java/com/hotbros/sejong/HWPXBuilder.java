package com.hotbros.sejong;

import com.hotbros.sejong.builder.CharPrBuilder;
import com.hotbros.sejong.builder.ParaPrBuilder;
import com.hotbros.sejong.builder.StyleBuilder;
import com.hotbros.sejong.dto.CharPrAttributes;
import com.hotbros.sejong.dto.ParaPrAttributes;
import com.hotbros.sejong.dto.StyleAttributes;
import com.hotbros.sejong.util.HWPXObjectFinder;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;

import java.util.HashMap;
import java.util.Map;

public class HWPXBuilder {
    private static final String ORIGINAL_STYLE_ID = "0";    // 예: 바탕글 스타일 ID
    private static final String NEW_CHAR_PR_ID = "7";
    private static final String NEW_PARA_PR_ID = "16";
    private static final String NEW_STYLE_ID = "18";

    private HWPXFile hwpxFile;
    private RefList refList;

    public HWPXBuilder() {
        this.hwpxFile = BlankFileMaker.make();
        this.refList = hwpxFile.headerXMLFile().refList();
    }

    public HWPXFile build() throws Exception {

        // 원본 스타일 및 참조 객체 찾기
        Style originalStyle = HWPXObjectFinder.findStyleById(hwpxFile, ORIGINAL_STYLE_ID);
        if (originalStyle == null) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")을 찾을 수 없습니다. 프로그램 실행을 중단합니다.");
        }

        String originalCharPrId = originalStyle.charPrIDRef();
        if (originalCharPrId == null || originalCharPrId.trim().isEmpty()) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 유효한 charPrIDRef를 가지고 있지 않습니다.");
        }
        CharPr sourceCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, originalCharPrId);
        if (sourceCharPr == null) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 참조하는 CharPr(ID: " + originalCharPrId + ")을 찾을 수 없습니다.");
        }

        String originalParaPrId = originalStyle.paraPrIDRef();
        if (originalParaPrId == null || originalParaPrId.trim().isEmpty()) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 유효한 paraPrIDRef를 가지고 있지 않습니다.");
        }
        ParaPr sourceParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, originalParaPrId);
        if (sourceParaPr == null) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 참조하는 ParaPr(ID: " + originalParaPrId + ")을 찾을 수 없습니다.");
        }
        
        // 2. CharPr 생성 (글자 스타일) - ID NEW_CHAR_PR_ID
        Map<String, Object> charPrMap = new HashMap<>();
        charPrMap.put("id", NEW_CHAR_PR_ID);
        charPrMap.put("textColor", "#FF0000"); // 빨간색
        charPrMap.put("bold", true);       // 굵게
        // fontSize는 pt 단위로 제공, CharPrAttributes.fromMap에서 HWP Unit으로 변환
        // charPrMap.put("fontSizePt", 12.0); // 예시: 12pt

        CharPrAttributes charPrDto = CharPrAttributes.fromMap(charPrMap);
        CharPr charPr = CharPrBuilder.fromAttributes(sourceCharPr, charPrDto).build();
        
        if (refList.charProperties() == null) {
            refList.createCharProperties(); 
        }
        refList.charProperties().add(charPr);


        // 3. ParaPr 생성 (문단 스타일) - ID NEW_PARA_PR_ID
        Map<String, Object> paraPrMap = new HashMap<>();
        paraPrMap.put("id", NEW_PARA_PR_ID);
        Map<String, Object> alignMap = new HashMap<>();
        alignMap.put("horizontal", "CENTER"); // 가운데 정렬
        paraPrMap.put("align", alignMap); // ParaPrAttributes.fromMap은 이 중첩 Map을 처리할 수 있어야 함
        paraPrMap.put("snapToGrid", false);
        // paraPrMap.put("lineSpacing", 160.0); // 예시: 줄간격 160% (HWP Unit 값)

        ParaPrAttributes paraPrDto = ParaPrAttributes.fromMap(paraPrMap);
        ParaPr paraPr = ParaPrBuilder.fromAttributes(sourceParaPr, paraPrDto).build();
        
        if (refList.paraProperties() == null) {
            refList.createParaProperties(); 
        }
        refList.paraProperties().add(paraPr);


        // 4. Style 생성 (문단 스타일로 CharPr과 ParaPr 참조) - ID NEW_STYLE_ID
        Map<String, Object> styleMap = new HashMap<>();
        styleMap.put("id", NEW_STYLE_ID);
        styleMap.put("name", "나의커스텀스타일");
        styleMap.put("engName", "MyCustomStyle");
        styleMap.put("charPrIDRef", charPr.id()); 
        styleMap.put("paraPrIDRef", paraPr.id());

        StyleAttributes styleDto = StyleAttributes.fromMap(styleMap);
        // StyleBuilder.fromAttributes는 originalStyle과 styleDto를 받음.
        // Style의 type은 StyleBuilder 내부에서 설정되거나, 추가적인 type() 메서드로 설정해야 함.
        // 현재 StyleBuilder의 기본 생성자는 StyleType을 설정하지 않음.
        // 명시적으로 설정하려면 StyleBuilder에 type(StyleType type) 메서드를 사용해야 함.
        StyleBuilder styleBuilder = StyleBuilder.fromAttributes(originalStyle, styleDto);
        // StyleType.PARA로 명시적으로 설정. StyleAttributes DTO에 type이 없으므로 빌더에서 직접 설정.
        // styleBuilder.type(StyleType.PARA); // 이 부분은 StyleBuilder의 fromAttributes가 어떻게 type을 다루는지에 따라 달라짐.
                                         // 혹은 StyleAttributes에서 제거되었으므로 HWPXBuilder에서 Style 객체 생성 후 직접 설정
        Style customStyle = styleBuilder.build();
        // StyleType이 StyleAttributes에서 제거되었고, StyleBuilder의 fromAttributes가 StyleType을 설정하지 않으므로,
        // Style 객체 생성 후 직접 설정해주는 것이 명확함.
        
        if (refList.styles() == null) {
            refList.createStyles(); 
        }
        refList.styles().add(customStyle);

        System.out.println("새로운 스타일 '" + customStyle.name() + "' (ID: " + customStyle.id() + ") 추가됨");
        System.out.println("  - 참조 CharPr ID: " + customStyle.charPrIDRef());
        System.out.println("  - 참조 ParaPr ID: " + customStyle.paraPrIDRef());
        System.out.println("  - 스타일 타입: " + customStyle.type());

        return hwpxFile;
    }
}
