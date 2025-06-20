package com.hotbros.sejong.util;

import kr.dogfoot.hwpxlib.commonstrings.FileIDs;
import kr.dogfoot.hwpxlib.commonstrings.MineTypes;
import kr.dogfoot.hwpxlib.commonstrings.ZipEntryName;
import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.chart.ChartXMLFile;
import kr.dogfoot.hwpxlib.object.common.HWPXObject;
import kr.dogfoot.hwpxlib.object.content.context_hpf.ManifestItem;
import kr.dogfoot.hwpxlib.object.etc.UnparsedXMLFile;
import kr.dogfoot.hwpxlib.object.metainf.RootFile;
import kr.dogfoot.hwpxlib.writer.common.ElementWriterManager;
import kr.dogfoot.hwpxlib.writer.common.ElementWriterSort;
import kr.dogfoot.hwpxlib.writer.util.XMLStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.CRC32;

public class HWPXWriter {
    public static void toFilepath(HWPXFile hwpxFile, String filepath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filepath);
        toStream(hwpxFile, fos);
    }

    public static void toStream(HWPXFile hwpxFile, OutputStream os) throws Exception {
        HWPXWriter writer = new HWPXWriter(hwpxFile);
        writer.createZIPFile(os);
        writer.write();
        writer.close();
        os.close();
    }

    public static byte[] toBytes(HWPXFile hwpxFile) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toStream(hwpxFile, baos);
        return baos.toByteArray();
    }

    private final HWPXFile hwpxFile;
    private ElementWriterManager elementWriterManager;
    private ZipOutputStream zos;

    public HWPXWriter(HWPXFile hwpxFile) {
        this.hwpxFile = hwpxFile;
        elementWriterManager = new ElementWriterManager();
    }

    public void createZIPFile(OutputStream outputStream) {
        zos = new ZipOutputStream(outputStream);
    }

    private void write() throws Exception {
        mineType();
        version_xml();
        META_INF_manifest_xml();
        META_INF_container_xml();
        content_hpf();
        contentFiles();
        chartFiles();
        etcContainedFile();
        unparsedXMLFiles();
    }

    private void mineType() throws IOException {
        putIntoZip(ZipEntryName.MineType,
                MineTypes.HWPX,
                StandardCharsets.UTF_8);
    }

    private void version_xml() throws Exception {
        writeChild(ElementWriterSort.Version, hwpxFile.versionXMLFile());
        putIntoZip(ZipEntryName.Version, xsb().toString(), StandardCharsets.UTF_8);
    }

    private void writeChild(ElementWriterSort sort, HWPXObject child) {
        elementWriterManager.get(sort).write(child);
    }

    private XMLStringBuilder xsb() {
        return elementWriterManager.xsb();
    }

    /**
     * 이 메서드는 문자열을 ZipEntry에 바로 쓰는 대신,
     * 로컬 헤더에 크기, CRC 정보를 먼저 세팅(STORED)하는 방식으로 수정되었습니다.
     */
    private void putIntoZip(String entryName, String data, Charset charset) throws IOException {
        if (data == null) {
            return;
        }

        // 원본 데이터 전부 확보
        byte[] bytes = data.getBytes(charset);

        // CRC 계산
        CRC32 crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        long crcValue = crc32.getValue();

        // 로컬 헤더 정보 세팅 (STORED 모드)
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipEntry.setMethod(ZipEntry.STORED);
        zipEntry.setSize(bytes.length);
        zipEntry.setCrc(crcValue);

        // 실제 기록
        zos.putNextEntry(zipEntry);
        zos.write(bytes, 0, bytes.length);
        zos.closeEntry();
    }

    /**
     * 이 메서드는 바이트 배열을 ZipEntry에 바로 쓰는 대신,
     * 로컬 헤더에 크기, CRC 정보를 먼저 세팅(STORED)하는 방식으로 수정되었습니다.
     */
    private void putIntoZip(String entryName, byte[] binary) throws IOException {
        if (binary == null) {
            return;
        }

        // CRC 계산
        CRC32 crc32 = new CRC32();
        crc32.update(binary, 0, binary.length);
        long crcValue = crc32.getValue();

        // 로컬 헤더 정보 세팅 (STORED 모드)
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipEntry.setMethod(ZipEntry.STORED);
        zipEntry.setSize(binary.length);
        zipEntry.setCrc(crcValue);

        // 실제 기록
        zos.putNextEntry(zipEntry);
        zos.write(binary, 0, binary.length);
        zos.closeEntry();
    }

    public void META_INF_manifest_xml() throws IOException {
        writeChild(ElementWriterSort.Manifest, hwpxFile.manifestXMLFile());
        putIntoZip(ZipEntryName.Manifest, xsb().toString(), StandardCharsets.UTF_8);
    }

    public void META_INF_container_xml() throws Exception {
        writeChild(ElementWriterSort.Container, hwpxFile.containerXMLFile());
        putIntoZip(ZipEntryName.Container, xsb().toString(), StandardCharsets.UTF_8);
    }

    private void content_hpf() throws Exception {
        String packageXMLFilePath = hwpxFile.containerXMLFile().packageXMLFilePath();
        if (packageXMLFilePath != null) {
            writeChild(ElementWriterSort.Content, hwpxFile.contentHPFFile());
            putIntoZip(packageXMLFilePath, xsb().toString(), StandardCharsets.UTF_8);
        }
    }

    private void contentFiles() throws IOException {
        if (hwpxFile.contentHPFFile().manifest() == null) {
            return;
        }

        for (ManifestItem item : hwpxFile.contentHPFFile().manifest().items()) {
            if (item.id().equals(FileIDs.Settings)) {
                writeChild(ElementWriterSort.Settings, hwpxFile.settingsXMLFile());
                putIntoZip(item.href(), xsb().toString(), StandardCharsets.UTF_8);
            } else if (item.id().equals(FileIDs.Header)) {
                writeChild(ElementWriterSort.Header, hwpxFile.headerXMLFile());
                putIntoZip(item.href(), xsb().toString(), StandardCharsets.UTF_8);
            } else if (item.id().startsWith(FileIDs.Section_Prefix)) {
                int sectionIndex = Integer.parseInt(item.id().substring(FileIDs.Section_Prefix.length()));
                writeChild(ElementWriterSort.Section, hwpxFile.sectionXMLFileList().get(sectionIndex));
                putIntoZip(item.href(), xsb().toString(), StandardCharsets.UTF_8);
            } else if (item.id().startsWith(FileIDs.MasterPage_PreFix)) {
                int masterPageIndex = Integer.parseInt(item.id().substring(FileIDs.MasterPage_PreFix.length()));
                writeChild(ElementWriterSort.MasterPage, hwpxFile.masterPageXMLFileList().get(masterPageIndex));
                putIntoZip(item.href(), xsb().toString(), StandardCharsets.UTF_8);
            } else if (item.hasAttachedFile() && item.attachedFile() != null) {
                putIntoZip(item.href(), item.attachedFile().data());
            }
        }
    }

    private void chartFiles() throws IOException {
        for (ChartXMLFile chartXMLFile : hwpxFile.chartXMLFileList().items()) {
            putIntoZip(chartXMLFile.path(), chartXMLFile.data());
        }
    }

    private void etcContainedFile() throws Exception {
        if (hwpxFile.containerXMLFile().rootFiles() == null) {
            return;
        }

        for (RootFile rootFile : hwpxFile.containerXMLFile().rootFiles().items()) {
            if (!MineTypes.HWPML_Package.equals(rootFile.mediaType())
                    && rootFile.attachedFile() != null) {
                putIntoZip(rootFile.fullPath(), rootFile.attachedFile().data());
            }
        }
    }

    private void unparsedXMLFiles() throws IOException {
        for (UnparsedXMLFile unparsedXMLFile : hwpxFile.unparsedXMLFiles()) {
            putIntoZip(unparsedXMLFile.href(), unparsedXMLFile.xml(), StandardCharsets.UTF_8);
        }
    }

    private void close() throws IOException {
        zos.close();
    }
}