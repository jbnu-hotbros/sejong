package com.hotbros.sejong;

import com.hotbros.sejong.builder.CharPrBuilder;
import com.hotbros.sejong.builder.ParaPrBuilder;
import com.hotbros.sejong.builder.StyleBuilder;
import com.hotbros.sejong.util.HWPXObjectFinder;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
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
        Map<String, Object> charPrAttributes = new HashMap<>();
        charPrAttributes.put("id", NEW_CHAR_PR_ID);
        charPrAttributes.put("textColor", "#FF0000"); // 빨간색
        charPrAttributes.put("bold", true);       // 굵게

        CharPr charPr = CharPrBuilder.fromMap(sourceCharPr, charPrAttributes).build();
        // 생성된 CharPr을 Header.xml에 추가 (빌더에서 ID 설정했으므로 charPr.id()는 NEW_CHAR_PR_ID가 됨)
        if (refList.charProperties() == null) {
            refList.createCharProperties(); 
        }
        refList.charProperties().add(charPr);


        // 3. ParaPr 생성 (문단 스타일) - ID NEW_PARA_PR_ID
        Map<String, Object> paraPrAttributes = new HashMap<>();
        paraPrAttributes.put("id", NEW_PARA_PR_ID);
        Map<String, Object> alignMap = new HashMap<>();
        alignMap.put("horizontal", "CENTER"); // 가운데 정렬
        paraPrAttributes.put("align", alignMap);
        paraPrAttributes.put("snapToGrid", false);

        ParaPr paraPr = ParaPrBuilder.fromMap(sourceParaPr, paraPrAttributes).build();
        // 생성된 ParaPr을 Header.xml에 추가 (빌더에서 ID 설정했으므로 paraPr.id()는 NEW_PARA_PR_ID가 됨)
        if (refList.paraProperties() == null) {
            refList.createParaProperties(); 
        }
        refList.paraProperties().add(paraPr);


        // 4. Style 생성 (문단 스타일로 CharPr과 ParaPr 참조) - ID NEW_STYLE_ID
        Map<String, Object> styleAttributes = new HashMap<>();
        styleAttributes.put("id", NEW_STYLE_ID);
        styleAttributes.put("name", "나의커스텀스타일");
        styleAttributes.put("engName", "MyCustomStyle");
        styleAttributes.put("type", StyleType.PARA); // 문단 스타일
        styleAttributes.put("charPrIDRef", charPr.id()); // 위에서 생성한 CharPr의 ID NEW_CHAR_PR_ID 참조
        styleAttributes.put("paraPrIDRef", paraPr.id()); // 위에서 생성한 ParaPr의 ID NEW_PARA_PR_ID 참조


        Style customStyle = StyleBuilder.fromMap(originalStyle, styleAttributes).build();
        // 생성된 Style을 Header.xml에 추가
        if (refList.styles() == null) {
            refList.createStyles(); 
        }
        refList.styles().add(customStyle);

        System.out.println("새로운 스타일 '" + customStyle.name() + "' (ID: " + customStyle.id() + ") 추가됨");
        System.out.println("  - 참조 CharPr ID: " + customStyle.charPrIDRef());
        System.out.println("  - 참조 ParaPr ID: " + customStyle.paraPrIDRef());

        return hwpxFile;
    }
}
