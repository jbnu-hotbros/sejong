package com.hotbros;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;

// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.OutputStream;
// import java.nio.file.Paths;
// import java.util.Enumeration;
// import java.util.zip.CRC32;
// import java.util.zip.ZipEntry;
// import java.util.zip.ZipFile;
// import java.util.zip.ZipOutputStream;
// import java.nio.charset.StandardCharsets;

public class HwpxExample {
    public static HWPXFile createEmptyHwpx() {
        HWPXFile hwpxFile = new HWPXFile();
        
        // 버전 정보 설정
        hwpxFile.versionXMLFile()
                .applicationAnd("hwpxlib_test")
                .appVersion("1.0");
        hwpxFile.versionXMLFile().version()
                .majorAnd(5)
                .minorAnd(0)
                .microAnd(5)
                .buildNumber(0);

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
                .contentAnd("hwpxlib 테스트");

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
        hwpxFile = createSection(hwpxFile);

        return hwpxFile;
    }

    public static HWPXFile createSection(HWPXFile hwpxFile) {
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

        // 섹션 생성
        SectionXMLFile section = new SectionXMLFile();
        hwpxFile.sectionXMLFileList().add(section);
        
        // spine 정보 추가
        hwpxFile.contentHPFFile().createSpine();
        hwpxFile.contentHPFFile().spine().addNew()
                .idref("header");
        hwpxFile.contentHPFFile().spine().addNew()
                .idref("section0");
        
        return hwpxFile;
    }

    // test용 임시 함수
    public static HWPXFile addParagraph(HWPXFile hwpxFile, int sid, String text) {
        SectionXMLFile section = hwpxFile.sectionXMLFileList().get(sid);
        
        // 첫 번째 단락 생성 및 텍스트 추가
        Para paragraph = section.addNewPara();
        paragraph.addNewRun().addNewT().addText(text);

        return hwpxFile;
    }

    public static void main(String[] args) {
        String outputPath = args.length > 0 ? args[0] : "example.hwpx";
        HWPXFile hwpxFile = createEmptyHwpx();
        
        hwpxFile = addParagraph(hwpxFile, 0, "안녕하세요! 이것은 HWPX 라이브러리 테스트입니다.");
        hwpxFile = addParagraph(hwpxFile, 0, "이것은 두 번째 단락입니다.");

        try {
            HWPXWriter.toFilepath(hwpxFile, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 