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

public class HwpxFileManager {
    private HWPXFile hwpxFile;
    private String baseDir;

    public HwpxFileManager(String baseDir) {
        this.baseDir = baseDir;
        this.hwpxFile = createEmptyHwpx();
    }

    /**
     * 비어있는 HWPX 파일을 생성합니다.
     */
    private HWPXFile createEmptyHwpx() {
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

        return hwpxFile;
    }

    /**
     * 새로운 섹션을 생성합니다.
     * @return 생성된 섹션의 인덱스
     */
    public int createNewSection() {
        // 섹션 추가 (sectionX.xml 파일 생성 설정)
        String sectionId = "section" + hwpxFile.sectionXMLFileList().size();
        hwpxFile.contentHPFFile().manifest().addNew()
                .idAnd(sectionId)
                .hrefAnd("Contents/" + sectionId + ".xml")
                .mediaType("application/xml");

        // spine 정보 추가
        hwpxFile.contentHPFFile().spine().addNew()
                .idref(sectionId);

        // 섹션 생성
        SectionXMLFile section = new SectionXMLFile();
        hwpxFile.sectionXMLFileList().add(section);
        
        return hwpxFile.sectionXMLFileList().size() - 1;
    }

    /**
     * 지정된 섹션에 문단을 추가합니다.
     * @param sectionIndex 섹션 인덱스
     * @param text 문단 텍스트
     */
    public void addParagraph(int sectionIndex, String text) {
        if (sectionIndex < 0 || sectionIndex >= hwpxFile.sectionXMLFileList().size()) {
            throw new IllegalArgumentException("Invalid section index");
        }

        SectionXMLFile section = hwpxFile.sectionXMLFileList().get(sectionIndex);
        Para paragraph = section.addNewPara();
        paragraph.addNewRun().addNewT().addText(text);
    }

    /**
     * HWPX 파일을 저장합니다.
     * @param filename 저장할 파일 이름
     * @throws IOException 파일 저장 중 오류 발생 시
     */
    public void saveFile(String filename) throws IOException {
        String absolutePath = Paths.get(baseDir, filename).toAbsolutePath().toString();
        
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
    }
} 