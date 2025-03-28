package com.hotbros.sejong;

public class SejongExample {

    public static void main(String[] args) throws Exception {
        Sejong sejong = new Sejong();
        String outputPath = args.length > 0 ? args[0] : "example.hwpx";
        
        sejong.createEmptyHwpx();
        sejong.addParagraph(0, "안녕하세요! 이것은 HWPX 라이브러리 테스트입니다.");
        sejong.addParagraph(0, "이것은 두 번째 단락입니다.");

        sejong.saveToFile(outputPath);
    }
}