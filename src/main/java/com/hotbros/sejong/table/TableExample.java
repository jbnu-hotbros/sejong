package com.hotbros.sejong.table;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import com.hotbros.sejong.util.HWPXWriter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TableExample {
    public static void main(String[] args) {
        try {
            // 1. 블랭크 HWPX 파일 생성
            HWPXFile hwpxFile = BlankFileMaker.make();

            // 2. TableBuilder 생성 및 BorderFill 추가
            TableBuilder builder = new TableBuilder(hwpxFile);
            builder.addBorderFill();

            // 3. 표 내용 준비 (2x2 표)
            List<List<String>> contents = Arrays.asList(
                Arrays.asList("11", "12"),
                Arrays.asList("21", "22")
            );

            // 4. 표 생성
            Table table = builder.buildTable(2, 2, contents);

            // 5. 표를 담을 문단 생성 및 추가
            Para para = new Para();
            para.id("0");
            para.paraPrIDRef("0");
            para.styleIDRef("0");
            para.pageBreak(false);
            para.columnBreak(false);
            para.merged(false);

            para.addNewRun().addNewTable().copyFrom(table);
            SectionXMLFile section = hwpxFile.sectionXMLFileList().get(0);
            section.addPara(para);

            // 6. 파일로 저장
            String outputDir = "output";
            new File(outputDir).mkdirs();
            File outputFile = new File(outputDir, "TableWithBorderExample.hwpx");
            HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
            System.out.println("테두리가 적용된 표가 포함된 HWPX 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
