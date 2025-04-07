package com.hotbros.sejong.examples;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
// import kr.dogfoot.hwpxlib.writer.HWPXWriter;
import com.hotbros.sejong.writer.HWPXWriter;
import java.io.File;

/**
 * 빈 HWPX 파일을 생성하는 간단한 예제
 */
public class SimpleBlankFileExample {
    public static void main(String[] args) throws Exception {
        // 빈 HWPX 파일 생성
        HWPXFile hwpxFile = BlankFileMaker.make();
        
        // 파일 저장
        File outputFile = new File("BlankFile.hwpx");
        HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
        
        System.out.println("빈 HWPX 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
    }
} 