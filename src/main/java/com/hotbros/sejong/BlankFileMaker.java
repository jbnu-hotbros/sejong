package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;

/**
 * 빈 HWPX 파일을 생성하는 유틸리티 클래스
 * HWPX 파일의 기본 구조와 초기 설정을 생성합니다.
 */
public class BlankFileMaker {
    
    /**
     * 새로운 빈 HWPX 파일을 생성합니다.
     * 기본 컨테이너, 메타데이터, 스타일 참조를 포함합니다.
     * 
     * @param creator 생성자 이름
     * @return 초기화된 HWPXFile 객체
     */
    public static HWPXFile createBlankHwpx(String creator) {
        HWPXFile hwpxFile = new HWPXFile();
        
        // 컨테이너 정보 초기화
        initializeContainer(hwpxFile);
        
        // 메타데이터 초기화
        initializeMetaData(hwpxFile, creator);
        
        // 헤더 초기화
        initializeHeader(hwpxFile);
        
        // 섹션 생성
        createSection(hwpxFile);
        
        return hwpxFile;
    }
    
    /**
     * 기본 생성자 정보로 빈 HWPX 파일을 생성합니다.
     * 
     * @return 초기화된 HWPXFile 객체
     */
    public static HWPXFile createBlankHwpx() {
        return createBlankHwpx("Sejong 라이브러리");
    }
    
    /**
     * HWPX 컨테이너 구조를 초기화합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     */
    private static void initializeContainer(HWPXFile hwpxFile) {
        hwpxFile.containerXMLFile().createRootFiles();
        hwpxFile.containerXMLFile().rootFiles().addNew()
                .fullPathAnd("Contents/content.hpf")
                .mediaType("application/hwpml-package+xml");
    }
    
    /**
     * HWPX 메타데이터를 초기화합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     * @param creator 생성자 이름
     */
    private static void initializeMetaData(HWPXFile hwpxFile, String creator) {
        hwpxFile.contentHPFFile().createManifest();
        hwpxFile.contentHPFFile().createMetaData();
        hwpxFile.contentHPFFile().metaData().createTitle();
        hwpxFile.contentHPFFile().metaData().createLanguage();
        hwpxFile.contentHPFFile().metaData().language().addText("ko");
        hwpxFile.contentHPFFile().metaData().addNewMeta()
                .nameAnd("creator")
                .contentAnd(creator);
    }
    
    /**
     * HWPX 헤더 정보를 초기화합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     */
    private static void initializeHeader(HWPXFile hwpxFile) {
        // 버전 및 섹션 수 설정
        hwpxFile.headerXMLFile()
                .versionAnd("1.4")
                .secCnt((short) 1);

        // 시작 번호 초기화
        hwpxFile.headerXMLFile().createBeginNum();
        hwpxFile.headerXMLFile().beginNum()
                .pageAnd(1)
                .footnoteAnd(1)
                .endnoteAnd(1)
                .picAnd(1)
                .tblAnd(1)
                .equation(1);

        // 참조 목록 초기화
        hwpxFile.headerXMLFile().createRefList();
        initializeRefList(hwpxFile);
    }
    
    /**
     * HWPX 참조 목록을 초기화합니다.
     * 글꼴, 테두리/배경, 글자 모양, 탭, 번호 매기기, 문단 모양, 스타일 등을 정의합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     */
    private static void initializeRefList(HWPXFile hwpxFile) {
        // RefListManager를 사용하여 참조 목록 초기화
        RefListManager refListManager = new RefListManager(hwpxFile);
        refListManager.initialize();
    }
    
    /**
     * HWPX 섹션을 생성합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     */
    private static void createSection(HWPXFile hwpxFile) {
        // 헤더 매니페스트 추가
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd("header")
                .hrefAnd("Contents/header.xml")
                .mediaType("application/xml");

        // 섹션 매니페스트 추가
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd("section0")
                .hrefAnd("Contents/section0.xml")
                .mediaType("application/xml");

        // 설정 파일 추가
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd("settings")
                .hrefAnd("settings.xml")
                .mediaType("application/xml");

        // 섹션 생성
        SectionXMLFile section = new SectionXMLFile();
        hwpxFile.sectionXMLFileList().add(section);

        // spine 정보 추가
        hwpxFile.contentHPFFile().createSpine();
        hwpxFile.contentHPFFile().spine().addNew()
                .idref("header");
        hwpxFile.contentHPFFile().spine().addNew()
                .idref("section0");
    }
    
    /**
     * 지정된 수의 섹션을 가진 빈 HWPX 파일을 생성합니다.
     * 
     * @param creator 생성자 이름
     * @param sectionCount 생성할 섹션 수 (1 이상)
     * @return 초기화된 HWPXFile 객체
     */
    public static HWPXFile createBlankHwpxWithSections(String creator, int sectionCount) {
        if (sectionCount < 1) {
            throw new IllegalArgumentException("섹션 수는 1 이상이어야 합니다.");
        }
        
        HWPXFile hwpxFile = createBlankHwpx(creator);
        
        // 섹션이 1개보다 많을 경우 추가 섹션 생성
        if (sectionCount > 1) {
            // 헤더의 섹션 수 업데이트
            hwpxFile.headerXMLFile().secCnt((short) sectionCount);
            
            // 추가 섹션 생성
            for (int i = 1; i < sectionCount; i++) {
                // 매니페스트에 추가
                hwpxFile.contentHPFFile().manifest().addNew()
                        .idAnd("section" + i)
                        .hrefAnd("Contents/section" + i + ".xml")
                        .mediaType("application/xml");
                
                // 섹션 생성 및 추가
                SectionXMLFile section = new SectionXMLFile();
                hwpxFile.sectionXMLFileList().add(section);
                
                // spine에 추가
                hwpxFile.contentHPFFile().spine().addNew()
                        .idref("section" + i);
            }
        }
        
        return hwpxFile;
    }
} 