// package com.hotbros.sejong.example;

// import kr.dogfoot.hwpxlib.object.HWPXFile;
// import kr.dogfoot.hwpxlib.writer.HWPXWriter;
// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
// import com.hotbros.sejong.util.IdManager;
// import com.hotbros.sejong.styles.*;

// public class BulletNumberingExample {
//     public static void main(String[] args) throws Exception {
//         // HWPX 파일 생성
//         HWPXFile hwpxFile = new HWPXFile();
        
//         // 기본 초기화 코드
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
        
//         // 문단 속성, 글자 속성, 글머리표 목록 생성
//         hwpxFile.headerXMLFile().refList().createParaProperties();
//         hwpxFile.headerXMLFile().refList().createCharProperties();
//         hwpxFile.headerXMLFile().refList().createBullets();
//         hwpxFile.headerXMLFile().refList().createNumberings();
        
//         // 섹션 생성
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

//         // 섹션 객체 생성
//         hwpxFile.sectionXMLFileList().add(new kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile());

//         // spine 정보 추가
//         hwpxFile.contentHPFFile().createSpine();
//         hwpxFile.contentHPFFile().spine().addNew().idref("header");
//         hwpxFile.contentHPFFile().spine().addNew().idref("section0");
        
//         // ID 관리자 생성
//         IdManager idManager = new IdManager();
        
//         // 글자 모양 생성
//         CharPrBuilder charPrBuilder = new CharPrBuilder(hwpxFile, idManager);
//         String normalCharPrId = charPrBuilder
//                 .withFontName("한글", "맑은 고딕")
//                 .withFontName("영문", "맑은 고딕")
//                 .withFontSize(10.0)
//                 .build();
                
//         String boldCharPrId = charPrBuilder
//                 .withFontName("한글", "맑은 고딕")
//                 .withFontName("영문", "맑은 고딕")
//                 .withFontSize(10.0)
//                 .withBold(true)
//                 .build();
        
//         // 문단 모양 생성
//         ParaPrBuilder paraPrBuilder = new ParaPrBuilder(hwpxFile, idManager);
//         String normalParaPrId = paraPrBuilder
//                 .withAlignment(HorizontalAlign2.JUSTIFY)
//                 .withLineSpacing(160)
//                 .build();
        
//         // 글머리표 생성
//         BulletBuilder bulletBuilder = new BulletBuilder(hwpxFile, idManager);
//         String bulletId = bulletBuilder
//                 .withChar("•")
//                 .withCheckedChar("√")
//                 .build();
        
//         // 문단 생성 및 글머리표 적용
//         ParagraphBuilder paraBulletBuilder = new ParagraphBuilder(hwpxFile, idManager, 0);
//         paraBulletBuilder
//                 .withParaPrId(normalParaPrId)
//                 .withBullet(bulletId, 1)
//                 .createRun("첫 번째 글머리표 항목")
//                     .withCharPrId(normalCharPrId)
//                     .add()
//                 .build();
                
//         // 두 번째 문단 (강조 텍스트 포함)
//         ParagraphBuilder paraBulletBuilder2 = new ParagraphBuilder(hwpxFile, idManager, 0);
//         paraBulletBuilder2
//                 .withParaPrId(normalParaPrId)
//                 .withBullet(bulletId, 1)
//                 .createRun("두 번째 글머리표 항목: ")
//                     .withCharPrId(normalCharPrId)
//                     .add()
//                 .createRun("강조 텍스트")
//                     .withCharPrId(boldCharPrId)
//                     .add()
//                 .build();
        
//         // 파일 저장
//         HWPXWriter.toFilepath(hwpxFile, "example_bullet.hwpx");
        
//         System.out.println("예제 파일이 생성되었습니다.");
//     }
// } 