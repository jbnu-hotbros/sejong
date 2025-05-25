package com.hotbros.sejong.examples;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;

import com.hotbros.sejong.builder.TableBuilder;
import com.hotbros.sejong.util.HWPXWriter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TableExample {
    public static void main(String[] args) {
        try {
            // 1. 빈 HWPX 파일 생성
            HWPXFile hwpxFile = BlankFileMaker.make();

            // 2. TableBuilder 생성 (삭제)
            // TableBuilder builder = new TableBuilder(hwpxFile);

            // 3. BorderFill 정의 및 등록
            BorderFill normalBorderFill = TableBuilder.createBorderFill(false, null);  // 일반 셀용 보더 (배경색 없음)
            BorderFill headerBorderFill = TableBuilder.createBorderFill(true, "#E6E6E6");  // 헤더 셀용 보더 (회색 배경)
            TableBuilder.addBorderFill(hwpxFile, "3", normalBorderFill);         // 일반 셀용 보더 등록
            TableBuilder.addBorderFill(hwpxFile, "4", headerBorderFill);    // 헤더 셀용 보더 등록

            // 4. 내용 준비 (10행 10열)
            List<List<String>> contents = Arrays.asList(
                Arrays.asList("헤더1", "헤더2", "헤더3", "헤더4", "헤더5", "헤더6", "헤더7", "헤더8", "헤더9", "헤더10"),  // 헤더 행
                Arrays.asList("값1-1", "값1-2", "값1-3", "값1-4", "값1-5", "값1-6", "값1-7", "값1-8", "값1-9", "값1-1"),
                Arrays.asList("값2-1", "값2-2", "값2-3", "값2-4", "값2-5", "값2-6", "값2-7", "값2-8", "값2-9", "값2-1"),
                Arrays.asList("값3-1", "값3-2", "값3-3", "값3-4", "값3-5", "값3-6", "값3-7", "값3-8", "값3-9", "값3-1"),
                Arrays.asList("값4-1", "값4-2", "값4-3", "값4-4", "값4-5", "값4-6", "값4-7", "값4-8", "값4-9", "값4-1"),
                Arrays.asList("값5-1", "값5-2", "값5-3", "값5-4", "값5-5", "값5-6", "값5-7", "값5-8", "값5-9", "값5-1"),
                Arrays.asList("값6-1", "값6-2", "값6-3", "값6-4", "값6-5", "값6-6", "값6-7", "값6-8", "값6-9", "값6-1"),
                Arrays.asList("값7-1", "값7-2", "값7-3", "값7-4", "값7-5", "값7-6", "값7-7", "값7-8", "값7-9", "값7-1"),
                Arrays.asList("값8-1", "값8-2", "값8-3", "값8-4", "값8-5", "값8-6", "값8-7", "값8-8", "값8-9", "값8-1"),
                Arrays.asList("값9-1", "값9-2", "값9-3", "값9-4", "값9-5", "값9-6", "값9-7", "값9-8", "값9-9", "값9-1")
            );

            // 5. 표 생성 (헤더는 "4", 일반 셀은 "3" 사용)
            Table table = TableBuilder.buildTable(10, 10, contents, "3", "4");

            // 6. Para 객체 생성 및 표 삽입
            Para para = new Para();
            para.id("0");
            para.paraPrIDRef("0");
            para.styleIDRef("0");
            para.pageBreak(false);
            para.columnBreak(false);
            para.merged(false);
            para.addNewRun().addNewTable().copyFrom(table);

            // 7. 문단 삽입
            SectionXMLFile section = hwpxFile.sectionXMLFileList().get(0);
            section.addPara(para);

            // 표 아래에 일반 텍스트 문단 추가
            Para plainPara = new Para();
            plainPara.id("1");
            plainPara.paraPrIDRef("0");
            plainPara.styleIDRef("0");
            plainPara.pageBreak(false);
            plainPara.columnBreak(false);
            plainPara.merged(false);
            plainPara.addNewRun().addNewT().addText("이것은 표 아래에 들어가는 일반 텍스트입니다.");
            section.addPara(plainPara);

            // 8. 파일로 저장
            String outputDir = "output";
            new File(outputDir).mkdirs();
            File outputFile = new File(outputDir, "TableWithHeaderBorder.hwpx");
            HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());

            System.out.println("✅ 파일 생성 완료: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
