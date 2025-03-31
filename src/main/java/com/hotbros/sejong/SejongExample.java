package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import com.hotbros.sejong.RefListManager;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;

public class SejongExample {

    public static void main(String[] args) throws Exception {

        // hwpxlib의 BlankFileMaker로 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();

        // 스타일 및 관련 요소 생성
        Map<String, String> styleIds = createStyles(hwpxFile);
        
        // 생성된 스타일을 사용하여 문단 추가
        addNewParagraph(hwpxFile, 0, styleIds.get("customStyle"), "새로운 스타일이 적용된 텍스트입니다!");
        addNewParagraph(hwpxFile, 0, styleIds.get("titleStyle"), "제목 스타일이 적용된 텍스트입니다!");

        // 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "example_hwpxlib.hwpx");
        System.out.println("파일이 성공적으로 저장되었습니다: example_hwpxlib.hwpx");

    }

    /**
     * RefList에서 현재 사용 중인 최대 CharPr ID 값을 찾습니다.
     * 
     * @param refList RefList 객체
     * @return 최대 ID 값
     * @throws IllegalArgumentException RefList의 charProperties가 null인 경우
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
     * 
     * @param refList RefList 객체
     * @return 최대 ID 값
     * @throws IllegalArgumentException RefList의 paraProperties가 null인 경우
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
     * 
     * @param refList RefList 객체
     * @return 최대 ID 값
     * @throws IllegalArgumentException RefList의 styles가 null인 경우
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
     * 새로운 글자 모양을 생성합니다.
     * 
     * @param refList         RefList 객체
     * @param baseCharPrIDRef 복사할 기본 글자 모양 ID
     * @param modifications   글자 모양 수정사항을 정의하는 함수
     * @return 생성된 새로운 글자 모양 ID
     * @throws IllegalArgumentException 기본 글자 모양을 찾을 수 없는 경우
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
     * 
     * @param refList         RefList 객체
     * @param baseParaPrIDRef 복사할 기본 문단 모양 ID
     * @param modifications   문단 모양 수정사항을 정의하는 함수
     * @return 생성된 새로운 문단 모양 ID
     * @throws IllegalArgumentException 기본 문단 모양을 찾을 수 없는 경우
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

    /**
     * 새로운 스타일을 생성합니다.
     * 
     * @param refList         RefList 객체
     * @param baseStyle       복사할 기본 스타일
     * @param newStyleName    새로운 스타일 이름
     * @param newStyleEngName 새로운 스타일 영문 이름
     * @param charPrIDRef     참조할 글자 모양 ID
     * @param paraPrIDRef     참조할 문단 모양 ID
     * @return 생성된 새로운 스타일
     * @throws IllegalArgumentException 스타일이 null인 경우
     */
    private static Style createNewStyle(RefList refList, Style baseStyle,
            String newStyleName, String newStyleEngName, String charPrIDRef, String paraPrIDRef) {
        if (baseStyle == null) {
            throw new IllegalArgumentException("복사할 기본 스타일이 null입니다");
        }
        if (refList.styles() == null) {
            throw new IllegalArgumentException("RefList.styles가 null입니다");
        }

        // 최대 ID 찾기
        int maxID = getMaxStyleID(refList);
        String newStyleID = String.valueOf(maxID + 1);

        var newStyle = baseStyle.clone();
        newStyle.idAnd(newStyleID)
                .nameAnd(newStyleName)
                .engNameAnd(newStyleEngName)
                .paraPrIDRefAnd(paraPrIDRef)
                .charPrIDRefAnd(charPrIDRef);

        refList.styles().add(newStyle);
        return newStyle;
    }

    /**
     * 문서에 새로운 문단을 추가합니다.
     * 
     * @param hwpxFile     HWPX 파일 객체
     * @param sectionIndex 섹션 인덱스
     * @param styleIDRef   적용할 스타일 ID
     * @param text         문단에 추가할 텍스트
     * @return 생성된 문단
     * @throws IllegalArgumentException 섹션 인덱스가 유효하지 않은 경우
     */
    private static Para addNewParagraph(HWPXFile hwpxFile, int sectionIndex, String styleIDRef, String text) {
        if (hwpxFile.sectionXMLFileList().count() <= sectionIndex) {
            throw new IllegalArgumentException("유효하지 않은 섹션 인덱스: " + sectionIndex);
        }

        var section = hwpxFile.sectionXMLFileList().get(sectionIndex);
        var para = section.addNewPara();
        
        // 스타일 ID 설정
        para.styleIDRef(styleIDRef);
        
        // 스타일에서 문단 모양 ID와 글자 모양 ID 가져와서 직접 설정
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null) {
            throw new IllegalArgumentException("headerXMLFile.refList가 null입니다");
        }
        if (refList.styles() == null) {
            throw new IllegalArgumentException("headerXMLFile.refList.styles가 null입니다");
        }
        
        for (var style : refList.styles().items()) {
            if (styleIDRef.equals(style.id())) {
                // 문단 모양 ID 직접 설정
                String paraPrIDRef = style.paraPrIDRef();
                if (paraPrIDRef != null) {
                    para.paraPrIDRef(paraPrIDRef);
                }
                
                // 글자 모양 ID 설정을 위한 Run 추가시 charPrIDRef 설정
                String charPrIDRef = style.charPrIDRef();
                var run = para.addNewRun();
                
                if (charPrIDRef != null) {
                    // Run에 직접 charPrIDRef 설정
                    run.charPrIDRef(charPrIDRef);
                }
                
                run.addNewT().addText(text);
                return para;
            }
        }
        
        // 스타일을 찾지 못한 경우 기본 텍스트 추가
        para.addNewRun().addNewT().addText(text);
        return para;
    }

    /**
     * 스타일 및 관련 요소를 생성합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     * @return 생성된 스타일 ID들의 맵 (키: 스타일 이름, 값: 스타일 ID)
     * @throws IllegalArgumentException RefList 또는 스타일 관련 요소가 null인 경우
     */
    private static Map<String, String> createStyles(HWPXFile hwpxFile) {
        var refList = hwpxFile.headerXMLFile().refList();
        if (refList == null) {
            throw new IllegalArgumentException("hwpxFile.headerXMLFile().refList가 null입니다");
        }
        
        var styles = refList.styles();
        if (styles == null || styles.count() <= 0) {
            throw new IllegalArgumentException("styles가 null이거나 비어 있습니다");
        }

        Map<String, String> styleIds = new HashMap<>();
        
        var baseStyle = styles.get(0);
        String baseCharPrIDRef = baseStyle.charPrIDRef();
        String baseParaPrIDRef = baseStyle.paraPrIDRef();

        // 1. 먼저 모든 CharPr 및 ParaPr 생성
        // 1.1 커스텀 스타일용 글자 모양 (파란색) 생성
        String customCharPrID = createNewCharPr(refList, baseCharPrIDRef, charPr -> {
            charPr.height(1200);
            charPr.textColor("#0000FF");
        });

        // 1.2 커스텀 스타일용 문단 모양 (중앙 정렬) 생성
        String customParaPrID = createNewParaPr(refList, baseParaPrIDRef, paraPr -> {
            if (paraPr.align() != null) {
                paraPr.align().horizontal(HorizontalAlign2.CENTER);
            }
        });

        // 1.3 제목 스타일용 글자 모양 (굵게, 크게) 생성
        String titleCharPrID = createNewCharPr(refList, baseCharPrIDRef, charPr -> {
            charPr.height(2000);
            // 글자 모양에 대한 bold 속성은 별도의 객체로 생성하여 처리
            if (charPr.bold() == null) {
                charPr.createBold();
            }
        });

        // 1.4 제목 스타일용 문단 모양 (중앙 정렬, 여백) 생성
        String titleParaPrID = createNewParaPr(refList, baseParaPrIDRef, paraPr -> {
            if (paraPr.align() != null) {
                paraPr.align().horizontal(HorizontalAlign2.CENTER);
            }
            // 문단 간격 속성을 올바르게 설정
            if (paraPr.margin() == null) {
                paraPr.createMargin();
            }
            
            // 위쪽 여백 설정 (이전 문단과의 간격)
            if (paraPr.margin().prev() == null) {
                paraPr.margin().createPrev();
            }
            paraPr.margin().prev().value(400);
            
            // 아래쪽 여백 설정 (다음 문단과의 간격)
            if (paraPr.margin().next() == null) {
                paraPr.margin().createNext();
            }
            paraPr.margin().next().value(200);
        });

        // 2. 생성된 ID를 사용하여 스타일 생성
        // 2.1 커스텀 스타일 생성
        var customStyle = createNewStyle(refList, baseStyle,
                "커스텀 스타일", "Custom Style", customCharPrID, customParaPrID);
        styleIds.put("customStyle", customStyle.id());
        
        // 2.2 제목 스타일 생성
        var titleStyle = createNewStyle(refList, baseStyle,
                "제목 스타일", "Title Style", titleCharPrID, titleParaPrID);
        styleIds.put("titleStyle", titleStyle.id());
        
        return styleIds;
    }
}