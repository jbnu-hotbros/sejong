package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;

import java.util.HashMap;
import java.util.Map;

/**
 * 한글 문서의 스타일과 속성 ID를 관리하는 클래스입니다.
 * 스타일 ID, 문단 속성 ID, 글자 속성 ID를 자동으로 생성하고 관리합니다.
 */
public class StyleManager {
    // 기본 스타일 ID 상수
    public static final String STYLE_NORMAL = "0";     // 바탕글
    public static final String STYLE_HEADING = "1";    // 제목
    public static final String STYLE_EMPHASIS = "2";   // 강조
    
    // ID 카운터
    private int nextStyleId = 3;  // 기본 스타일 3개 이후부터 시작
    private int nextParaPrId = 2;  // 기본 문단 속성 2개 이후부터 시작
    private int nextCharPrId = 3;  // 기본 글자 속성 3개 이후부터 시작
    
    // 스타일 이름을 기준으로 ID를 저장하는 맵
    private Map<String, String> styleNameToIdMap = new HashMap<>();
    private Map<String, String> paraPrNameToIdMap = new HashMap<>();
    private Map<String, String> charPrNameToIdMap = new HashMap<>();
    
    // HWPXFile 참조
    private final HWPXFile hwpxFile;
    
    /**
     * 생성자
     * @param hwpxFile HWPX 파일 객체
     */
    public StyleManager(HWPXFile hwpxFile) {
        this.hwpxFile = hwpxFile;
        
        // 기본 스타일 이름과 ID 매핑
        styleNameToIdMap.put("바탕글", STYLE_NORMAL);
        styleNameToIdMap.put("제목", STYLE_HEADING);
        styleNameToIdMap.put("강조", STYLE_EMPHASIS);
        
        // 기본 문단 속성 이름과 ID 매핑
        paraPrNameToIdMap.put("기본", "0");
        paraPrNameToIdMap.put("제목", "1");
        
        // 기본 글자 속성 이름과 ID 매핑
        charPrNameToIdMap.put("기본", "0");
        charPrNameToIdMap.put("제목", "1");
        charPrNameToIdMap.put("강조", "2");
    }

    /**
     * 기본 스타일과 속성을 생성합니다.
     * 이 메서드는 문서에 필요한 기본 스타일을 초기화합니다.
     */
    public void createDefaultStyles() {
        // 1. 문단 속성 생성
        if (hwpxFile.headerXMLFile().refList().paraProperties() == null) {
            hwpxFile.headerXMLFile().refList().createParaProperties();
        }
        
        // 기본 문단 속성 (ID: 0)
        if (getParaPr("0") == null) {
            hwpxFile.headerXMLFile().refList().paraProperties().addNew()
                    .idAnd("0");
        }
                
        // 제목 문단 속성 (ID: 1)
        if (getParaPr("1") == null) {
            hwpxFile.headerXMLFile().refList().paraProperties().addNew()
                    .idAnd("1");
        }
        
        // 2. 글자 속성 생성
        if (hwpxFile.headerXMLFile().refList().charProperties() == null) {
            hwpxFile.headerXMLFile().refList().createCharProperties();
        }
        
        // 기본 글자 속성 (ID: 0)
        if (getCharPr("0") == null) {
            hwpxFile.headerXMLFile().refList().charProperties().addNew()
                    .idAnd("0");
        }
        
        // 제목 글자 속성 (ID: 1)
        if (getCharPr("1") == null) {
            hwpxFile.headerXMLFile().refList().charProperties().addNew()
                    .idAnd("1");
        }
                
        // 강조 글자 속성 (ID: 2)
        if (getCharPr("2") == null) {
            hwpxFile.headerXMLFile().refList().charProperties().addNew()
                    .idAnd("2");
        }
        
        // 3. 스타일 생성
        if (hwpxFile.headerXMLFile().refList().styles() == null) {
            hwpxFile.headerXMLFile().refList().createStyles();
        }
        
        // 기본 스타일(바탕글) 추가
        if (getStyle(STYLE_NORMAL) == null) {
            hwpxFile.headerXMLFile().refList().styles().addNew()
                    .idAnd(STYLE_NORMAL)
                    .typeAnd(StyleType.PARA)  // 문단 스타일
                    .nameAnd("바탕글")
                    .engNameAnd("Normal")
                    .paraPrIDRefAnd("0")      // 기본 문단 속성 참조
                    .charPrIDRefAnd("0")      // 기본 글자 속성 참조
                    .nextStyleIDRefAnd(STYLE_NORMAL)   // 다음 문단도 같은 스타일
                    .langIDAnd("1042")        // 한국어
                    .lockForm(false);         // 보호하지 않음
        }
        
        // 제목 스타일
        if (getStyle(STYLE_HEADING) == null) {
            hwpxFile.headerXMLFile().refList().styles().addNew()
                    .idAnd(STYLE_HEADING)
                    .typeAnd(StyleType.PARA)  // 문단 스타일
                    .nameAnd("제목")
                    .engNameAnd("Heading")
                    .paraPrIDRefAnd("1")      // 제목 문단 속성 참조
                    .charPrIDRefAnd("1")      // 제목 글자 속성 참조
                    .nextStyleIDRefAnd(STYLE_NORMAL)   // 다음 문단은 기본 스타일
                    .langIDAnd("1042")        // 한국어
                    .lockForm(false);         // 보호하지 않음
        }
                
        // 강조 스타일 (글자 스타일)
        if (getStyle(STYLE_EMPHASIS) == null) {
            hwpxFile.headerXMLFile().refList().styles().addNew()
                    .idAnd(STYLE_EMPHASIS)
                    .typeAnd(StyleType.CHAR)  // 글자 스타일
                    .nameAnd("강조")
                    .engNameAnd("Emphasis")
                    .charPrIDRefAnd("2")      // 강조 글자 속성 참조
                    .langIDAnd("1042")        // 한국어
                    .lockForm(false);         // 보호하지 않음
        }
    }
    
    /**
     * 새로운 스타일 ID를 생성합니다.
     * @return 생성된 스타일 ID
     */
    public String generateStyleId() {
        return String.valueOf(nextStyleId++);
    }
    
    /**
     * 새로운 문단 속성 ID를 생성합니다.
     * @return 생성된 문단 속성 ID
     */
    public String generateParaPrId() {
        return String.valueOf(nextParaPrId++);
    }
    
    /**
     * 새로운 글자 속성 ID를 생성합니다.
     * @return 생성된 글자 속성 ID
     */
    public String generateCharPrId() {
        return String.valueOf(nextCharPrId++);
    }
    
    /**
     * 스타일 이름으로 스타일 ID를 가져옵니다. 존재하지 않으면 null을 반환합니다.
     * @param styleName 스타일 이름
     * @return 스타일 ID 또는 null
     */
    public String getStyleIdByName(String styleName) {
        return styleNameToIdMap.get(styleName);
    }
    
    /**
     * 문단 속성 이름으로 문단 속성 ID를 가져옵니다. 존재하지 않으면 null을 반환합니다.
     * @param paraPrName 문단 속성 이름
     * @return 문단 속성 ID 또는 null
     */
    public String getParaPrIdByName(String paraPrName) {
        return paraPrNameToIdMap.get(paraPrName);
    }
    
    /**
     * 글자 속성 이름으로 글자 속성 ID를 가져옵니다. 존재하지 않으면 null을 반환합니다.
     * @param charPrName 글자 속성 이름
     * @return 글자 속성 ID 또는 null
     */
    public String getCharPrIdByName(String charPrName) {
        return charPrNameToIdMap.get(charPrName);
    }
    
    /**
     * 새로운 문단 속성을 추가합니다.
     * @param paraPrName 문단 속성 이름
     * @return 생성된 문단 속성 ID
     */
    public String addParaPr(String paraPrName) {
        // 이미 존재하는지 확인
        String existingId = getParaPrIdByName(paraPrName);
        if (existingId != null) {
            return existingId;
        }
        
        // 새 ID 생성
        String paraPrId = generateParaPrId();
        
        // 문단 속성 생성
        hwpxFile.headerXMLFile().refList().paraProperties().addNew()
                .idAnd(paraPrId);
        
        // 맵에 추가
        paraPrNameToIdMap.put(paraPrName, paraPrId);
        
        return paraPrId;
    }
    
    /**
     * 새로운 글자 속성을 추가합니다.
     * @param charPrName 글자 속성 이름
     * @return 생성된 글자 속성 ID
     */
    public String addCharPr(String charPrName) {
        // 이미 존재하는지 확인
        String existingId = getCharPrIdByName(charPrName);
        if (existingId != null) {
            return existingId;
        }
        
        // 새 ID 생성
        String charPrId = generateCharPrId();
        
        // 글자 속성 생성
        hwpxFile.headerXMLFile().refList().charProperties().addNew()
                .idAnd(charPrId);
        
        // 맵에 추가
        charPrNameToIdMap.put(charPrName, charPrId);
        
        return charPrId;
    }
    
    /**
     * 이름으로 문단 스타일을 추가합니다.
     * 
     * @param styleName 스타일 이름
     * @param paraPrName 문단 속성 이름 (없으면, 생성됨)
     * @param charPrName 글자 속성 이름 (없으면, 생성됨)
     * @param nextStyleName 다음 스타일 이름 (기본: 바탕글)
     * @return 생성된 스타일 ID
     */
    public String addStyle(String styleName, String paraPrName, String charPrName, String nextStyleName) {
        // 이미 존재하는지 확인
        String existingId = getStyleIdByName(styleName);
        if (existingId != null) {
            return existingId;
        }
        
        // 문단 속성 ID 확인 또는 생성
        String paraPrId = getParaPrIdByName(paraPrName);
        if (paraPrId == null) {
            paraPrId = addParaPr(paraPrName);
        }
        
        // 글자 속성 ID 확인 또는 생성
        String charPrId = getCharPrIdByName(charPrName);
        if (charPrId == null) {
            charPrId = addCharPr(charPrName);
        }
        
        // 다음 스타일 ID 확인
        String nextStyleId = getStyleIdByName(nextStyleName);
        if (nextStyleId == null) {
            nextStyleId = STYLE_NORMAL; // 기본적으로 바탕글 스타일 사용
        }
        
        // 새 스타일 ID 생성
        String styleId = generateStyleId();
        
        // 스타일 추가
        hwpxFile.headerXMLFile().refList().styles().addNew()
                .idAnd(styleId)
                .typeAnd(StyleType.PARA) // 기본 문단 스타일
                .nameAnd(styleName)
                .engNameAnd(styleName)
                .paraPrIDRefAnd(paraPrId)
                .charPrIDRefAnd(charPrId)
                .nextStyleIDRefAnd(nextStyleId)
                .langIDAnd("1042")
                .lockForm(false);
        
        // 맵에 추가
        styleNameToIdMap.put(styleName, styleId);
        
        return styleId;
    }
    
    /**
     * 이름으로 글자 스타일을 추가합니다.
     * 
     * @param styleName 스타일 이름
     * @param charPrName 글자 속성 이름 (없으면, 생성됨)
     * @return 생성된 스타일 ID
     */
    public String addCharStyle(String styleName, String charPrName) {
        // 이미 존재하는지 확인
        String existingId = getStyleIdByName(styleName);
        if (existingId != null) {
            return existingId;
        }
        
        // 글자 속성 ID 확인 또는 생성
        String charPrId = getCharPrIdByName(charPrName);
        if (charPrId == null) {
            charPrId = addCharPr(charPrName);
        }
        
        // 새 스타일 ID 생성
        String styleId = generateStyleId();
        
        // 스타일 추가
        hwpxFile.headerXMLFile().refList().styles().addNew()
                .idAnd(styleId)
                .typeAnd(StyleType.CHAR) // 글자 스타일
                .nameAnd(styleName)
                .engNameAnd(styleName)
                .charPrIDRefAnd(charPrId)
                .langIDAnd("1042")
                .lockForm(false);
        
        // 맵에 추가
        styleNameToIdMap.put(styleName, styleId);
        
        return styleId;
    }
    
    /**
     * 문단 속성 객체를 직접 가져옵니다.
     * @param paraPrId 문단 속성 ID
     * @return 문단 속성 객체 또는 null
     */
    public ParaPr getParaPr(String paraPrId) {
        for (ParaPr paraPr : hwpxFile.headerXMLFile().refList().paraProperties().items()) {
            if (paraPr.id().equals(paraPrId)) {
                return paraPr;
            }
        }
        return null;
    }
    
    /**
     * 글자 속성 객체를 직접 가져옵니다.
     * @param charPrId 글자 속성 ID
     * @return 글자 속성 객체 또는 null
     */
    public CharPr getCharPr(String charPrId) {
        for (CharPr charPr : hwpxFile.headerXMLFile().refList().charProperties().items()) {
            if (charPr.id().equals(charPrId)) {
                return charPr;
            }
        }
        return null;
    }
    
    /**
     * 스타일 객체를 직접 가져옵니다.
     * @param styleId 스타일 ID
     * @return 스타일 객체 또는 null
     */
    public Style getStyle(String styleId) {
        for (Style style : hwpxFile.headerXMLFile().refList().styles().items()) {
            if (style.id().equals(styleId)) {
                return style;
            }
        }
        return null;
    }
} 