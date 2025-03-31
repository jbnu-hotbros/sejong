package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import com.hotbros.sejong.RefListManager;

public class SejongExample {

    // public static void main(String[] args) throws Exception {
    //     Sejong sejong = new Sejong();
    //     String outputPath = args.length > 0 ? args[0] : "example.hwpx";
        
    //     sejong.createEmptyHwpx();
    //     sejong.addParagraph(0, "안녕하세요! 이것은 HWPX 라이브러리 테스트입니다.");
    //     sejong.addParagraph(0, "이것은 두 번째 단락입니다.");


    //     // var hwpxFile= sejong.getHwpxFile();
         
    //     System.out.println("outputPath: " + outputPath);
        
    //     sejong.saveToFile(outputPath);
    // }

    // public static void main(String[] args) {
    //     // BlankFileMaker를 사용하여 빈 HWPX 파일 생성
    //     HWPXFile hwpxFile = BlankFileMaker.createBlankHwpx("SejongExample");
        
    //     // 첫 번째 섹션의 참조를 가져옴
    //     SectionXMLFile section = hwpxFile.sectionXMLFileList().get(0);
        
    //     // 단락 추가 및 텍스트 설정
    //     section.addNewPara().addNewRun().addNewT().addText("안녕하세요!");

    //     try {
    //         // 파일로 저장
    //         HWPXWriter.toFilepath(hwpxFile, "example1234.hwpx");
    //         System.out.println("파일이 성공적으로 저장되었습니다: example1234.hwpx");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    
    public static void main(String[] args) {
        try {
            // hwpxlib의 BlankFileMaker로 빈 HWPX 파일 생성
            HWPXFile hwpxFile = BlankFileMaker.make();
            
            // RefList에 접근하여 스타일 및 관련 속성 수정
            modifyStyles(hwpxFile);
            
            // 파일 저장
            HWPXWriter.toFilepath(hwpxFile, "example_hwpxlib.hwpx");
            System.out.println("파일이 성공적으로 저장되었습니다: example_hwpxlib.hwpx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * HWPXFile의 RefList에 접근하여 스타일 및 관련 속성을 수정하는 메서드
     */
    private static void modifyStyles(HWPXFile hwpxFile) {
        // 기본 스타일 가져오기 (일반적으로 ID가 "0"인 스타일이 "바탕글" 스타일)
        var refList = hwpxFile.headerXMLFile().refList();
        var styles = refList.styles();
        
        if (styles != null && styles.count() > 0) {
            // 첫 번째 스타일 가져오기 (보통 바탕글 스타일)
            var baseStyle = styles.get(0);
            System.out.println("기본 스타일 이름: " + baseStyle.name());
            
            // 스타일이 참조하는 글자 모양과 문단 모양 ID 확인
            String baseCharPrIDRef = baseStyle.charPrIDRef();
            String baseParaPrIDRef = baseStyle.paraPrIDRef();
            
            System.out.println("기본 글자 모양 ID: " + baseCharPrIDRef);
            System.out.println("기본 문단 모양 ID: " + baseParaPrIDRef);
            
            // 1. 새로운 글자 모양 만들기 (기존 것 복사)
            String newCharPrID = "custom_char_1";
            if (baseCharPrIDRef != null && refList.charProperties() != null) {
                for (var charPr : refList.charProperties().items()) {
                    if (charPr.id().equals(baseCharPrIDRef)) {
                        // 글자 모양 복제 - clone() 메서드 사용
                        var newCharPr = charPr.clone();
                        
                        // ID 설정
                        newCharPr.id(newCharPrID);
                        
                        // 글자 모양 변경
                        newCharPr.height(1200);  // 12pt로 변경
                        newCharPr.textColor("#0000FF");  // 파란색으로 변경
                        
                        // 복제한 글자 모양 추가
                        refList.charProperties().add(newCharPr);
                        
                        System.out.println("새 글자 모양 생성: ID=" + newCharPrID);
                        break;
                    }
                }
            }
            
            // 2. 새로운 문단 모양 만들기 (기존 것 복사)
            String newParaPrID = "custom_para_1";
            if (baseParaPrIDRef != null && refList.paraProperties() != null) {
                for (var paraPr : refList.paraProperties().items()) {
                    if (paraPr.id().equals(baseParaPrIDRef)) {
                        // 문단 모양 복제 - clone() 메서드 사용
                        var newParaPr = paraPr.clone();
                        
                        // ID 설정
                        newParaPr.id(newParaPrID);
                        
                        // 문단 정렬 수정 (중앙 정렬)
                        if (newParaPr.align() != null) {
                            newParaPr.align().horizontal(kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2.CENTER);
                        }
                        
                        // 복제한 문단 모양 추가
                        refList.paraProperties().add(newParaPr);
                        
                        System.out.println("새 문단 모양 생성: ID=" + newParaPrID);
                        break;
                    }
                }
            }
            
            // 3. 새로운 스타일 만들기
            // 기본 스타일 복제
            var newStyle = baseStyle.clone();
            newStyle.idAnd("custom_style_1")
                   .nameAnd("커스텀 스타일")
                   .engNameAnd("Custom Style")
                   .paraPrIDRefAnd(newParaPrID)
                   .charPrIDRefAnd(newCharPrID);
            
            // 복제한 스타일 추가
            styles.add(newStyle);
            
            System.out.println("새 스타일 생성 완료: " + newStyle.name());
            
            // 4. 첫 번째 섹션에 텍스트 추가하여 새 스타일 적용
            if (hwpxFile.sectionXMLFileList().count() > 0) {
                var section = hwpxFile.sectionXMLFileList().get(0);
                var para = section.addNewPara();
                
                // 새 스타일 적용
                para.styleIDRef(newStyle.id());
                
                // 텍스트 추가
                para.addNewRun().addNewT().addText("새로운 스타일이 적용된 텍스트입니다!");
                
                System.out.println("문서에 새 스타일 적용된 문단 추가 완료");
            }
        } else {
            System.out.println("RefList에 스타일이 없습니다.");
        }
    }
}