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
            // 1. 빈 HWPX 파일 생성
            HWPXFile hwpxFile = BlankFileMaker.make();

            // 2. TableBuilder 생성
            TableBuilder builder = new TableBuilder(hwpxFile);

            // 3. BorderFill 정의 및 등록
            builder.addBorderFill("3");         // 일반 셀용 보더
            builder.addBorderFill("4");    // 헤더 셀용 보더 (속성 다르게 만들고 싶다면 createBorderFill 수정)

            // 4. 내용 준비 (2행 2열)
            List<List<String>> contents = Arrays.asList(
                Arrays.asList("헤더1", "헤더2"),  // 헤더 행
                Arrays.asList("값1", "값2")       // 일반 데이터 행
            );

            // 5. 표 생성 (헤더는 "header", 일반 셀은 "3" 사용)
            Table table = builder.buildTable(2, 2, contents, "3", "4");

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
