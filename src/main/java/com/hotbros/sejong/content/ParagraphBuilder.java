package com.hotbros.sejong.content;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;

/**
 * 문단 생성 및 스타일 적용을 담당하는 빌더 클래스
 */
public class ParagraphBuilder {
    private final HWPXFile hwpxFile;
    
    /**
     * ParagraphBuilder 인스턴스를 생성합니다.
     * 
     * @param hwpxFile 문단을 추가할 HWPX 파일
     */
    public ParagraphBuilder(HWPXFile hwpxFile) {
        if (hwpxFile == null) {
            throw new IllegalArgumentException("HWPX 파일이 null입니다");
        }
        this.hwpxFile = hwpxFile;
    }
    
    /**
     * 지정된 섹션에 새로운 문단을 추가합니다.
     * 
     * @param sectionIndex 문단을 추가할 섹션 인덱스
     * @param style 적용할 스타일
     * @param text 문단에 들어갈 텍스트
     * @return 생성된 문단 객체
     * @throws IllegalArgumentException 유효하지 않은 섹션 인덱스이거나 스타일이 null인 경우
     */
    public Para addParagraph(int sectionIndex, Style style, String text) {
        validateInput(sectionIndex, style);
        
        SectionXMLFile section = hwpxFile.sectionXMLFileList().get(sectionIndex);
        Para para = section.addNewPara();
        
        applyStyle(para, style);
        addText(para, text);
        
        return para;
    }
    
    /**
     * 첫 번째 섹션에 새로운 문단을 추가합니다.
     * 
     * @param style 적용할 스타일
     * @param text 문단에 들어갈 텍스트
     * @return 생성된 문단 객체
     * @throws IllegalArgumentException 스타일이 null인 경우
     */
    public Para addParagraph(Style style, String text) {
        return addParagraph(0, style, text);
    }
    
    /**
     * 입력 매개변수의 유효성을 검증합니다.
     */
    private void validateInput(int sectionIndex, Style style) {
        if (hwpxFile.sectionXMLFileList().count() <= sectionIndex) {
            throw new IllegalArgumentException("유효하지 않은 섹션 인덱스: " + sectionIndex);
        }
        
        if (style == null) {
            throw new IllegalArgumentException("스타일 객체가 null입니다");
        }
    }
    
    /**
     * 문단에 스타일을 적용합니다.
     */
    private void applyStyle(Para para, Style style) {
        // 스타일 ID 설정
        para.styleIDRef(style.id());
        
        // 스타일에서 직접 문단 모양 ID 가져오기
        String paraPrIDRef = style.paraPrIDRef();
        if (paraPrIDRef != null) {
            para.paraPrIDRef(paraPrIDRef);
        }
    }
    
    /**
     * 문단에 텍스트를 추가합니다.
     */
    private void addText(Para para, String text) {
        var run = para.addNewRun();
        
        // 스타일에서 글자 모양 ID 가져오기 (문단의 styleIDRef에서 Style 객체를 다시 얻어옴)
        String styleIDRef = para.styleIDRef();
        if (styleIDRef != null) {
            Style style = getStyleById(styleIDRef);
            if (style != null) {
                String charPrIDRef = style.charPrIDRef();
                if (charPrIDRef != null) {
                    run.charPrIDRef(charPrIDRef);
                }
            }
        }
        
        run.addNewT().addText(text);
    }
    
    /**
     * ID로 스타일 객체를 가져옵니다.
     */
    private Style getStyleById(String styleId) {
        if (hwpxFile.headerXMLFile() == null || 
            hwpxFile.headerXMLFile().refList() == null || 
            hwpxFile.headerXMLFile().refList().styles() == null) {
            return null;
        }
        
        for (Style style : hwpxFile.headerXMLFile().refList().styles().items()) {
            if (style.id().equals(styleId)) {
                return style;
            }
        }
        
        return null;
    }
} 