package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import java.util.function.Consumer;

public class SejongExample {

    public static void main(String[] args) throws Exception {

        // hwpxlib의 BlankFileMaker로 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();

        // 1. 스타일 생성
        // 1.1 커스텀 스타일 (파란색, 중앙 정렬) 생성
        Style customStyle = createStyle(hwpxFile, "customStyle", "커스텀 스타일", "Custom Style")
                .withCharPr(charPr -> {
                    charPr.height(1200);
                    charPr.textColor("#0000FF");
                })
                .withParaPr(paraPr -> {
                    if (paraPr.align() != null) {
                        paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    }
                })
                .build();
        
        // 1.2 제목 스타일 (굵게, 크게, 중앙 정렬, 여백) 생성
        Style titleStyle = createStyle(hwpxFile, "titleStyle", "제목 스타일", "Title Style")
                .withCharPr(charPr -> {
                    charPr.height(2000);
                    if (charPr.bold() == null) {
                        charPr.createBold();
                    }
                })
                .withParaPr(paraPr -> {
                    if (paraPr.align() != null) {
                        paraPr.align().horizontal(HorizontalAlign2.CENTER);
                    }
                    
                    if (paraPr.margin() == null) {
                        paraPr.createMargin();
                    }
                    
                    if (paraPr.margin().prev() == null) {
                        paraPr.margin().createPrev();
                    }
                    paraPr.margin().prev().value(400);
                    
                    if (paraPr.margin().next() == null) {
                        paraPr.margin().createNext();
                    }
                    paraPr.margin().next().value(200);
                })
                .build();
                
        // 2. 생성된 스타일을 사용하여 문단 추가
        addNewParagraph(hwpxFile, 0, customStyle, "새로운 스타일이 적용된 텍스트입니다!");
        addNewParagraph(hwpxFile, 0, titleStyle, "제목 스타일이 적용된 텍스트입니다!");

        // 3. 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "example_hwpxlib.hwpx");
        System.out.println("파일이 성공적으로 저장되었습니다: example_hwpxlib.hwpx");
    }

    /**
     * 문서에 새로운 문단을 추가합니다.
     */
    private static Para addNewParagraph(HWPXFile hwpxFile, int sectionIndex, Style style, String text) {
        if (hwpxFile.sectionXMLFileList().count() <= sectionIndex) {
            throw new IllegalArgumentException("유효하지 않은 섹션 인덱스: " + sectionIndex);
        }
        
        if (style == null) {
            throw new IllegalArgumentException("스타일 객체가 null입니다");
        }

        var section = hwpxFile.sectionXMLFileList().get(sectionIndex);
        var para = section.addNewPara();
        
        // 스타일 ID 설정
        para.styleIDRef(style.id());
        
        // 스타일에서 직접 문단 모양 ID와 글자 모양 ID 가져오기
        String paraPrIDRef = style.paraPrIDRef();
        if (paraPrIDRef != null) {
            para.paraPrIDRef(paraPrIDRef);
        }
        
        // 글자 모양 ID 설정을 위한 Run 추가
        var run = para.addNewRun();
        
        String charPrIDRef = style.charPrIDRef();
        if (charPrIDRef != null) {
            // Run에 직접 charPrIDRef 설정
            run.charPrIDRef(charPrIDRef);
        }
        
        run.addNewT().addText(text);
        return para;
    }

    /**
     * RefList에서 현재 사용 중인 최대 CharPr ID 값을 찾습니다.
     */
    private static int getMaxCharPrID(RefList refList) {
        int maxID = -1;
        
        if (refList.charProperties() == null) {
            throw new IllegalArgumentException("RefList.charProperties가 null입니다");
        }
        
        for (var charPr : refList.charProperties().items()) {
            try {
                int id = Integer.parseInt(charPr.id());
                maxID = Math.max(maxID, id);
            } catch (NumberFormatException e) {
                // 숫자가 아닌 ID는 무시
            }
        }
        
        return maxID;
    }
    
    /**
     * RefList에서 현재 사용 중인 최대 ParaPr ID 값을 찾습니다.
     */
    private static int getMaxParaPrID(RefList refList) {
        int maxID = -1;
        
        if (refList.paraProperties() == null) {
            throw new IllegalArgumentException("RefList.paraProperties가 null입니다");
        }
        
        for (var paraPr : refList.paraProperties().items()) {
            try {
                int id = Integer.parseInt(paraPr.id());
                maxID = Math.max(maxID, id);
            } catch (NumberFormatException e) {
                // 숫자가 아닌 ID는 무시
            }
        }
        
        return maxID;
    }
    
    /**
     * RefList에서 현재 사용 중인 최대 Style ID 값을 찾습니다.
     */
    private static int getMaxStyleID(RefList refList) {
        int maxID = -1;
        
        if (refList.styles() == null) {
            throw new IllegalArgumentException("RefList.styles가 null입니다");
        }
        
        for (var style : refList.styles().items()) {
            try {
                int id = Integer.parseInt(style.id());
                maxID = Math.max(maxID, id);
            } catch (NumberFormatException e) {
                // 숫자가 아닌 ID는 무시
            }
        }
        
        return maxID;
    }

    /**
     * 새로운 스타일을 생성하기 위한 빌더를 반환합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     * @param styleKey 스타일 키
     * @param styleName 스타일 이름
     * @param styleEngName 스타일 영문 이름
     * @return 스타일 빌더 객체
     */
    public static StyleBuilder createStyle(HWPXFile hwpxFile, String styleKey, String styleName, String styleEngName) {
        return new StyleBuilder(hwpxFile, styleKey, styleName, styleEngName);
    }

    /**
     * 스타일 빌더 클래스 - 하나의 스타일을 구성하기 위한 빌더 패턴 구현
     */
    public static class StyleBuilder {
        private final HWPXFile hwpxFile;
        private final RefList refList;
        private final String styleKey;
        private final String styleName;
        private final String styleEngName;
        private Style baseStyle;
        
        private Consumer<CharPr> charPrModifications;
        private Consumer<ParaPr> paraPrModifications;
        // 필요에 따라 Numbering, Bullet 등의 수정 함수 추가 가능
        
        public StyleBuilder(HWPXFile hwpxFile, String styleKey, String styleName, String styleEngName) {
            this.hwpxFile = hwpxFile;
            this.refList = hwpxFile.headerXMLFile().refList();
            this.styleKey = styleKey;
            this.styleName = styleName;
            this.styleEngName = styleEngName;
            
            if (refList == null) {
                throw new IllegalArgumentException("RefList가 null입니다");
            }
            
            if (refList.styles() == null || refList.styles().count() <= 0) {
                throw new IllegalArgumentException("Styles가 null이거나 비어 있습니다");
            }
            
            this.baseStyle = refList.styles().get(0);
        }
        
        /**
         * 글자 모양 설정
         * @param modifications 글자 모양 수정 함수
         * @return 현재 빌더 인스턴스
         */
        public StyleBuilder withCharPr(Consumer<CharPr> modifications) {
            this.charPrModifications = modifications;
            return this;
        }
        
        /**
         * 문단 모양 설정
         * @param modifications 문단 모양 수정 함수
         * @return 현재 빌더 인스턴스
         */
        public StyleBuilder withParaPr(Consumer<ParaPr> modifications) {
            this.paraPrModifications = modifications;
            return this;
        }
        
        /**
         * 스타일 구성 완료 및 생성
         * @return 생성된 스타일 객체
         */
        public Style build() {
            String baseCharPrIDRef = baseStyle.charPrIDRef();
            String baseParaPrIDRef = baseStyle.paraPrIDRef();
            
            // 글자 모양 생성 (설정되어 있는 경우)
            String charPrIDRef = baseCharPrIDRef;
            if (charPrModifications != null) {
                charPrIDRef = createNewCharPr(refList, baseCharPrIDRef, charPrModifications);
            }
            
            // 문단 모양 생성 (설정되어 있는 경우)
            String paraPrIDRef = baseParaPrIDRef;
            if (paraPrModifications != null) {
                paraPrIDRef = createNewParaPr(refList, baseParaPrIDRef, paraPrModifications);
            }
            
            // 새 스타일 ID 생성
            int maxStyleID = getMaxStyleID(refList);
            String newStyleID = String.valueOf(maxStyleID + 1);
            
            // 스타일 객체 생성 및 설정
            var newStyle = baseStyle.clone();
            newStyle.idAnd(newStyleID)
                    .nameAnd(styleName)
                    .engNameAnd(styleEngName)
                    .charPrIDRefAnd(charPrIDRef)
                    .paraPrIDRefAnd(paraPrIDRef);
            
            // 스타일 추가
            refList.styles().add(newStyle);
            
            // 생성된 스타일 복제본 반환 (방어적 복사)
            return newStyle.clone();
        }
    }

    /**
     * 새로운 글자 모양을 생성합니다.
     */
    private static String createNewCharPr(RefList refList, String baseCharPrIDRef, 
            Consumer<CharPr> modifications) {
        if (baseCharPrIDRef == null) {
            throw new IllegalArgumentException("기본 글자 모양 ID가 null입니다");
        }
        if (refList.charProperties() == null) {
            throw new IllegalArgumentException("RefList.charProperties가 null입니다");
        }

        // 최대 ID 찾기
        int maxID = getMaxCharPrID(refList);
        String newCharPrID = String.valueOf(maxID + 1);

        for (var charPr : refList.charProperties().items()) {
            if (charPr.id().equals(baseCharPrIDRef)) {
                var newCharPr = charPr.clone();
                newCharPr.id(newCharPrID);
                modifications.accept(newCharPr);
                refList.charProperties().add(newCharPr);
                return newCharPrID;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseCharPrIDRef + "인 기본 글자 모양을 찾을 수 없습니다");
    }

    /**
     * 새로운 문단 모양을 생성합니다.
     */
    private static String createNewParaPr(RefList refList, String baseParaPrIDRef,
            Consumer<ParaPr> modifications) {
        if (baseParaPrIDRef == null) {
            throw new IllegalArgumentException("기본 문단 모양 ID가 null입니다");
        }
        if (refList.paraProperties() == null) {
            throw new IllegalArgumentException("RefList.paraProperties가 null입니다");
        }

        // 최대 ID 찾기
        int maxID = getMaxParaPrID(refList);
        String newParaPrID = String.valueOf(maxID + 1);

        for (var paraPr : refList.paraProperties().items()) {
            if (paraPr.id().equals(baseParaPrIDRef)) {
                var newParaPr = paraPr.clone();
                newParaPr.id(newParaPrID);
                modifications.accept(newParaPr);
                refList.paraProperties().add(newParaPr);
                return newParaPrID;
            }
        }

        throw new IllegalArgumentException("ID가 " + baseParaPrIDRef + "인 기본 문단 모양을 찾을 수 없습니다");
    }
}