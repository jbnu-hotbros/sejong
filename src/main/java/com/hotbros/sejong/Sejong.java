package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.writer.HWPXWriter;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Sejong {
        private static Set<String> styleNames = new HashSet<>(Arrays.asList("Diploma Style 1", "Diploma Style 2", "Billing Invoice Style 1"));

        private static final Set<String> subStyles = new HashSet<>(Arrays.asList("제목", "바탕글", "본문", "개요 1", "개요 2", "개요 3", "개요 4", "개요 5", "개요 6",
                        "개요 7", "개요 8", "개요 9", "개요 10", "쪽 번호", "각주", "미주", "메모", "차례 제목", "차례 1", "차례 2", "차례 3",
                        "캡션"));
        private static final Set<String> engSubStyles = new HashSet<>(Arrays.asList("Title", "Normal", "Body", "Outline 1", "Outline 2", "Outline 3",
                        "Outline 4", "Outline 5", "Outline 6", "Outline 7", "Outline 8", "Outline 9", "Outline 10",
                        "Page Number", "Footnote", "Endnote", "Memo", "TOC Heading", "TOC 1", "TOC 2", "TOC 3",
                        "Caption"));

        private HWPXFile hwpxFile;

        /* 선택한 style_name에 따라 header에 style를 생성해주는 함수 */
        public void createStyles(String styleName) throws Exception {
                if (!styleNames.contains(styleName)) {
                        throw new IllegalArgumentException("Invalid style name");
                }

                for (int i=0; i < subStyles.size(); i++) {
                        continue;
                        // 추후 구현
                        /*
                        제목 ~ 캡션까지 생성
                         */
                }
        }

        public void createEmptyHwpx() {
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
                createSection();
        }

        private void createSection() {
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
        }

        public void addParagraph(int sectionIndex, String text) throws Exception {
                SectionXMLFile section = hwpxFile.sectionXMLFileList().get(sectionIndex);

                if (section == null) {
                        throw new Exception("Section not found");
                }

                // 단락 생성 및 텍스트 추가
                Para paragraph = section.addNewPara();
                paragraph.addNewRun().addNewT().addText(text);
        }

        public void saveToFile(String outputPath) throws Exception {
                HWPXWriter.toFilepath(hwpxFile, outputPath);
        }

        public HWPXFile getHwpxFile() {
                return hwpxFile;
        }
}