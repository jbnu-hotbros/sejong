package com.hotbros.sejong.example;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineSpacingType;
import com.hotbros.sejong.util.IdManager;
import com.hotbros.sejong.styles.ParaPrBuilder;

public class ParaPrExample {
    public static void main(String[] args) throws Exception {
        // HWPX 파일 생성
        HWPXFile hwpxFile = new HWPXFile();
        
        // 기본 초기화 코드
        initializeHWPXFile(hwpxFile);
        
        // ID 관리자 생성
        IdManager idManager = new IdManager();
        
        // 문단 모양 생성 예제
        ParaPrBuilder paraPrBuilder = new ParaPrBuilder(hwpxFile, idManager);
        
        // 1. 왼쪽 정렬 문단
        String leftAlignParaPrId = paraPrBuilder
                .withAlignment(HorizontalAlign2.LEFT)
                .withLineSpacing(160)  // 160% 줄간격
                .build();
        System.out.println("왼쪽 정렬 문단 ID: " + leftAlignParaPrId);
        
        // 2. 가운데 정렬 문단
        String centerAlignParaPrId = paraPrBuilder
                .withAlignment(HorizontalAlign2.CENTER)
                .withLineSpacing(160)
                .build();
        System.out.println("가운데 정렬 문단 ID: " + centerAlignParaPrId);
        
        // 3. 오른쪽 정렬 문단
        String rightAlignParaPrId = paraPrBuilder
                .withAlignment(HorizontalAlign2.RIGHT)
                .withLineSpacing(160)
                .build();
        System.out.println("오른쪽 정렬 문단 ID: " + rightAlignParaPrId);
        
        // 4. 양쪽 정렬 문단
        String justifyAlignParaPrId = paraPrBuilder
                .withAlignment(HorizontalAlign2.JUSTIFY)
                .withLineSpacing(160)
                .build();
        System.out.println("양쪽 정렬 문단 ID: " + justifyAlignParaPrId);
        
        // 5. 들여쓰기가 있는 문단
        String indentParaPrId = paraPrBuilder
                .withAlignment(HorizontalAlign2.JUSTIFY)
                .withLineSpacing(160)
                .withIndent(10.0)  // 10mm 들여쓰기
                .build();
        System.out.println("들여쓰기 문단 ID: " + indentParaPrId);
        
        // 6. 줄간격이 넓은 문단
        String wideSpacingParaPrId = paraPrBuilder
                .withAlignment(HorizontalAlign2.JUSTIFY)
                .withLineSpacing(200)  // 200% 줄간격
                .build();
        System.out.println("넓은 줄간격 문단 ID: " + wideSpacingParaPrId);
        
        // 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "example_parapr.hwpx");
        
        System.out.println("문단 모양 예제 파일이 생성되었습니다: example_parapr.hwpx");
    }
    
    // HWPX 파일 초기화 메서드
    private static void initializeHWPXFile(HWPXFile hwpxFile) {
        // 컨테이너 정보 초기화
        hwpxFile.containerXMLFile().createRootFiles();
        hwpxFile.containerXMLFile().rootFiles().addNew()
                .fullPathAnd("Contents/content.hpf")
                .mediaType("application/hwpml-package+xml");

        // 컨텐츠 정보 초기화
        hwpxFile.contentHPFFile().createManifest();
        hwpxFile.contentHPFFile().createMetaData();
        hwpxFile.contentHPFFile().metaData().createTitle();
        hwpxFile.contentHPFFile().metaData().createLanguage();
        hwpxFile.contentHPFFile().metaData().language().addText("ko");
        hwpxFile.contentHPFFile().metaData().addNewMeta()
                .nameAnd("creator")
                .contentAnd("ParaPrBuilder 예제");

        // header.xml 설정
        hwpxFile.headerXMLFile()
                .versionAnd("1.4")
                .secCnt((short) 1);

        // header 필수 요소 생성
        hwpxFile.headerXMLFile().createBeginNum();
        hwpxFile.headerXMLFile().beginNum()
                .pageAnd(1)
                .footnoteAnd(1)
                .endnoteAnd(1)
                .picAnd(1)
                .tblAnd(1)
                .equation(1);

        // 참조 목록 생성 (글꼴, 테두리, 스타일 등)
        hwpxFile.headerXMLFile().createRefList();
        
        // 문단 속성, 글자 속성, 글머리표 목록 생성
        hwpxFile.headerXMLFile().refList().createParaProperties();
        hwpxFile.headerXMLFile().refList().createCharProperties();
        hwpxFile.headerXMLFile().refList().createBullets();
        hwpxFile.headerXMLFile().refList().createNumberings();
        
        // 섹션 생성
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd("header")
                .hrefAnd("Contents/header.xml")
                .mediaType("application/xml");

        // 섹션 추가 (section0.xml 파일 생성 설정)
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd("section0")
                .hrefAnd("Contents/section0.xml")
                .mediaType("application/xml");

        // 설정 파일 추가
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd("settings")
                .hrefAnd("settings.xml")
                .mediaType("application/xml");

        // 섹션 객체 생성
        hwpxFile.sectionXMLFileList().add(new kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile());

        // spine 정보 추가
        hwpxFile.contentHPFFile().createSpine();
        hwpxFile.contentHPFFile().spine().addNew().idref("header");
        hwpxFile.contentHPFFile().spine().addNew().idref("section0");
    }
} 