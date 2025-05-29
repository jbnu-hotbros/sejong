package com.hotbros.sejong.examples;

import com.hotbros.sejong.HWPXBuilder;
import com.hotbros.sejong.util.HWPXWriter;
import kr.dogfoot.hwpxlib.object.HWPXFile;

import java.io.File;

public class TitleTableExample {
    public static void main(String[] args) {
        try {
            // HWPXBuilder 인스턴스 생성
            HWPXBuilder builder = new HWPXBuilder();

            // 제목 테이블만 추가
            builder.addTitleBoxMain("제목 테이블 예제");
            builder.addTitleBoxMiddle("Ⅰ", "테이블 제목");
            builder.addTitleBoxSub("1", "테이블 부제목");

            // HWPX 파일 빌드 및 저장
            HWPXFile hwpxFile = builder.build();

            String outputDir = "output";
            new File(outputDir).mkdirs();
            File outputFile = new File(outputDir, "title_table_example.hwpx");
            HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());

            System.out.println("제목 테이블 예제 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 