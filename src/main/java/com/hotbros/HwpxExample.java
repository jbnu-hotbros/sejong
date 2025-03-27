package com.hotbros;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.nio.charset.StandardCharsets;

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
            System.out.println("전달된 인자들:");
            for (int i = 0; i < args.length; i++) {
                System.out.println("args[" + i + "]: " + args[i]);
            }
            // HWPX 파일 생성
            hwpxFile = new HWPXFile();
            
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

            // spine 정보 추가
            hwpxFile.contentHPFFile().createSpine();
            hwpxFile.contentHPFFile().spine().addNew()
                    .idref("header");
            hwpxFile.contentHPFFile().spine().addNew()
                    .idref("section0");

            // 섹션 생성
            SectionXMLFile section = new SectionXMLFile();
            hwpxFile.sectionXMLFileList().add(section);
            
            // 첫 번째 단락 생성 및 텍스트 추가
            Para paragraph = section.addNewPara();
            paragraph.addNewRun().addNewT().addText("안녕하세요! 이것은 HWPX 라이브러리 테스트입니다.");
            
            // 두 번째 단락 추가
            Para paragraph2 = section.addNewPara();
            paragraph2.addNewRun().addNewT().addText("이것은 두 번째 단락입니다.");
            
            // HWPX 파일 저장
            String baseDir = "D:\\junyong\\OneDrives\\OneDrive - DEV\\Develop\\Projects\\capstone\\hwpx_converter";
            String absolutePath = Paths.get(baseDir, outputPath).toAbsolutePath().toString();
            System.out.println("파일 저장 경로: " + absolutePath);

            // 직접 ZIP 파일 생성하여 mimetype 설정
            try (FileOutputStream fos = new FileOutputStream(absolutePath);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {
                
                // mimetype 파일을 첫 번째로 저장 (압축하지 않음)
                zos.setMethod(ZipOutputStream.STORED);
                byte[] mimetypeBytes = "application/hwp+zip".getBytes(StandardCharsets.UTF_8);
                ZipEntry mimetypeEntry = new ZipEntry("mimetype");
                mimetypeEntry.setSize(mimetypeBytes.length);
                mimetypeEntry.setCompressedSize(mimetypeBytes.length);
                
                // CRC 계산
                CRC32 crc = new CRC32();
                crc.update(mimetypeBytes);
                mimetypeEntry.setCrc(crc.getValue());
                
                zos.putNextEntry(mimetypeEntry);
                zos.write(mimetypeBytes);
                zos.closeEntry();
                
                // 나머지 파일들은 HWPXWriter를 사용하여 임시 파일에 저장 후 복사
                File tempFile = File.createTempFile("hwpx", ".tmp");
                tempFile.deleteOnExit();
                
                HWPXWriter.toFilepath(hwpxFile, tempFile.getAbsolutePath());
                
                // 임시 파일에서 mimetype을 제외한 나머지 항목들을 복사
                try (ZipFile zipFile = new ZipFile(tempFile)) {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        if (!entry.getName().equals("mimetype")) {
                            // 일반 압축 모드로 전환
                            zos.setMethod(ZipOutputStream.DEFLATED);
                            
                            // 원본 항목 데이터 읽기
                            try (InputStream is = zipFile.getInputStream(entry)) {
                                // 새 ZipEntry 생성
                                ZipEntry newEntry = new ZipEntry(entry.getName());
                                zos.putNextEntry(newEntry);
                                
                                // 데이터 복사
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = is.read(buffer)) > 0) {
                                    zos.write(buffer, 0, len);
                                }
                                zos.closeEntry();
                            }
                        }
                    }
                }
                
                // 임시 파일 삭제
                tempFile.delete();
            }

            System.out.println("HWPX 파일이 성공적으로 생성되었습니다: " + absolutePath);
            
        } catch (IOException e) {
            System.err.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("HWPX 문서 생성 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (hwpxFile != null) {
                // 만약 HWPXFile에 close나 dispose 같은 메서드가 있다면 여기서 호출
            }
        }
    }
} 