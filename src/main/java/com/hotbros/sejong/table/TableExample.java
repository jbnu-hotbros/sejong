package com.hotbros.sejong.table;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Table;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import com.hotbros.sejong.util.HWPXWriter;

import java.io.File;

public class TableExample {
    public static void main(String[] args) {
        try {
            // 1. 블랭크 HWPX 파일 생성
            HWPXFile hwpxFile = BlankFileMaker.make();

            // 2. TableBuilder로 표 생성 (HWPXFile 전달)
            TableBuilder builder = new TableBuilder(hwpxFile);
            Table table = builder.build();

            // 3. 표를 담을 문단 생성 및 추가
            Para para = new Para();
            para.paraPrIDRef("1");
            para.styleIDRef("0");
            para.pageBreak(false);
            para.columnBreak(false);
            para.merged(false);

            para.addNewRun().addNewTable().copyFrom(table);
            SectionXMLFile section = hwpxFile.sectionXMLFileList().get(0);
            section.addPara(para);

            // 4. 파일로 저장
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
