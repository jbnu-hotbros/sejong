package com.hotbros.sejong.examples;

import com.hotbros.sejong.builder.HWPXBuilder;
import com.hotbros.sejong.util.HWPXWriter;

import kr.dogfoot.hwpxlib.object.HWPXFile;

import java.io.File;

public class HWPXBuilderExample {

    public static void main(String[] args) {
        try {
            HWPXBuilder builder = new HWPXBuilder();
            HWPXFile hwpxFile = builder.build();


            String outputDir = "output";
            new File(outputDir).mkdirs(); // output 디렉토리 생성
            File outputFile = new File(outputDir, "StyledBlankFile.hwpx");

            try {
                HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
                System.out.println("스타일이 추가된 HWPX 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
