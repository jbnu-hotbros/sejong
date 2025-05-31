package com.hotbros.sejong.examples;

import java.io.File;

import com.hotbros.sejong.global.HWPXBuilder;
import com.hotbros.sejong.theme.Theme;
import com.hotbros.sejong.theme.ThemeConfig;
import com.hotbros.sejong.util.HWPXWriter;

import kr.dogfoot.hwpxlib.object.HWPXFile;
public class HwpxRefactorExample {
    public static void main(String[] args) {
        Theme theme = ThemeConfig.createDefaultTheme();
        HWPXBuilder builder = new HWPXBuilder(theme);
        HWPXFile hwpxFile = builder.build();


        String outputDir = "output";
        new File(outputDir).mkdirs();
        File outputFile = new File(outputDir, "refactor.hwpx");

        try {
            HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
            System.out.println("보고서 HWPX 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
