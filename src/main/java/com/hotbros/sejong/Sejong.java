// package com.hotbros.sejong;

// import kr.dogfoot.hwpxlib.object.HWPXFile;
// import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
// import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
// import kr.dogfoot.hwpxlib.writer.HWPXWriter;

// public class Sejong {
//     private HWPXFile hwpxFile;
    
//     public void createEmptyHwpx() {
//         hwpxFile = new HWPXFile();

//         // 컨테이너 정보 초기화
//         hwpxFile.containerXMLFile().createRootFiles();
//         hwpxFile.containerXMLFile().rootFiles().addNew()
//                 .fullPathAnd("Contents/content.hpf")
//                 .mediaType("application/hwpml-package+xml");

//         // 컨텐츠 정보 초기화
//         hwpxFile.contentHPFFile().createManifest();
//         hwpxFile.contentHPFFile().createMetaData();
//         hwpxFile.contentHPFFile().metaData().createTitle();
//         hwpxFile.contentHPFFile().metaData().createLanguage();
//         hwpxFile.contentHPFFile().metaData().language().addText("ko");
//         hwpxFile.contentHPFFile().metaData().addNewMeta()
//                 .nameAnd("creator")
//                 .contentAnd("hwpxlib 테스트");

//         // header.xml 설정
//         hwpxFile.headerXMLFile()
//                 .versionAnd("1.4")
//                 .secCnt((short) 1);

//         // header 필수 요소 생성
//         hwpxFile.headerXMLFile().createBeginNum();
//         hwpxFile.headerXMLFile().beginNum()
//                 .pageAnd(1)
//                 .footnoteAnd(1)
//                 .endnoteAnd(1)
//                 .picAnd(1)
//                 .tblAnd(1)
//                 .equation(1);

//         // 참조 목록 생성 (글꼴, 테두리, 스타일 등)
//         hwpxFile.headerXMLFile().createRefList();
        
//         // 참조 목록을 위한 컨테이너 초기화
//         hwpxFile.headerXMLFile().refList().createParaProperties();
//         hwpxFile.headerXMLFile().refList().createCharProperties();
//         hwpxFile.headerXMLFile().refList().createStyles();

//         // 섹션 생성
//         createSection();
//     }
    
//     private void createSection() {
//         hwpxFile.contentHPFFile().manifest().addNew()
//                 .idAnd("header")
//                 .hrefAnd("Contents/header.xml")
//                 .mediaType("application/xml");

//         // 섹션 추가 (section0.xml 파일 생성 설정)
//         hwpxFile.contentHPFFile().manifest().addNew()
//                 .idAnd("section0")
//                 .hrefAnd("Contents/section0.xml")
//                 .mediaType("application/xml");

//         // 설정 파일 추가
//         hwpxFile.contentHPFFile().manifest().addNew()
//                 .idAnd("settings")
//                 .hrefAnd("settings.xml")
//                 .mediaType("application/xml");

//         // 섹션 생성
//         SectionXMLFile section = new SectionXMLFile();
//         hwpxFile.sectionXMLFileList().add(section);

//         // spine 정보 추가
//         hwpxFile.contentHPFFile().createSpine();
//         hwpxFile.contentHPFFile().spine().addNew()
//                 .idref("header");
//         hwpxFile.contentHPFFile().spine().addNew()
//                 .idref("section0");
//     }

//     public void addParagraph(int sectionIndex, String text) {
//         SectionXMLFile section = hwpxFile.sectionXMLFileList().get(sectionIndex);

//         // 단락 생성 및 텍스트 추가
//         Para paragraph = section.addNewPara();
//         paragraph.addNewRun().addNewT().addText(text);
//     }
    
//     public void saveToFile(String outputPath) throws Exception {
//         HWPXWriter.toFilepath(hwpxFile, outputPath);
//     }
    
//     public HWPXFile getHwpxFile() {
//         return hwpxFile;
//     }
// } 