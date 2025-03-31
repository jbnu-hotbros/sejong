package com.hotbros.sejong.example;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import com.hotbros.sejong.util.IdManager;
import com.hotbros.sejong.styles.CharPrBuilder;

public class CharPrExample {
    public static void main(String[] args) throws Exception {
        // HWPX 파일 생성
        HWPXFile hwpxFile = new HWPXFile();
        
        // 기본 초기화 코드
        initializeHWPXFile(hwpxFile);
        
        // ID 관리자 생성
        IdManager idManager = new IdManager();
        
        // 글자 모양 생성 예제
        CharPrBuilder charPrBuilder = new CharPrBuilder(hwpxFile, idManager);
        
        // 1. 기본 글자 모양
        String normalCharPrId = charPrBuilder
                .withFontName("한글", "맑은 고딕")
                .withFontName("영문", "맑은 고딕")
                .withFontSize(10.0)
                .build();
        System.out.println("기본 글자 모양 ID: " + normalCharPrId);
        
        // 2. 굵은 글자 모양
        String boldCharPrId = charPrBuilder
                .withFontName("한글", "맑은 고딕")
                .withFontName("영문", "맑은 고딕")
                .withFontSize(10.0)
                .withBold(true)
                .build();
        System.out.println("굵은 글자 모양 ID: " + boldCharPrId);
        
        // 3. 기울임 글자 모양
        String italicCharPrId = charPrBuilder
                .withFontName("한글", "맑은 고딕")
                .withFontName("영문", "맑은 고딕")
                .withFontSize(10.0)
                .withItalic(true)
                .build();
        System.out.println("기울임 글자 모양 ID: " + italicCharPrId);
        
        // 4. 굵은 기울임 글자 모양
        String boldItalicCharPrId = charPrBuilder
                .withFontName("한글", "맑은 고딕")
                .withFontName("영문", "맑은 고딕")
                .withFontSize(10.0)
                .withBold(true)
                .withItalic(true)
                .build();
        System.out.println("굵은 기울임 글자 모양 ID: " + boldItalicCharPrId);
        
        // 5. 색상이 있는 글자 모양
        String redCharPrId = charPrBuilder
                .withFontName("한글", "맑은 고딕")
                .withFontName("영문", "맑은 고딕")
                .withFontSize(10.0)
                .withTextColor("#FF0000")  // 빨간색
                .build();
        System.out.println("빨간색 글자 모양 ID: " + redCharPrId);
        
        // 6. 큰 글자 모양
        String largeCharPrId = charPrBuilder
                .withFontName("한글", "맑은 고딕")
                .withFontName("영문", "맑은 고딕")
                .withFontSize(16.0)
                .build();
        System.out.println("큰 글자 모양 ID: " + largeCharPrId);
        
        // 파일 저장
        HWPXWriter.toFilepath(hwpxFile, "example_charpr.hwpx");
        
        System.out.println("글자 모양 예제 파일이 생성되었습니다: example_charpr.hwpx");
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
                .contentAnd("CharPrBuilder 예제");

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