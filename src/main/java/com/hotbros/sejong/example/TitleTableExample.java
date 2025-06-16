package com.hotbros.sejong.example;

import com.hotbros.sejong.HWPXBuilder;
import com.hotbros.sejong.util.HWPXWriter;
import kr.dogfoot.hwpxlib.object.HWPXFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TitleTableExample {
    public static void main(String[] args) {
        try {
            // HWPXBuilder 인스턴스 생성
            HWPXBuilder builder = new HWPXBuilder();

            // 제목 테이블만 추가
            builder.addTitleBoxMain("제목 테이블 예제");
            builder.addTitleBoxMiddle("Ⅰ", "테이블 제목");
            builder.addTitleBoxSub("1", "테이블 부제목");

            // 캡션이 있는 테이블 추가
            List<List<String>> tableContents = Arrays.asList(
                Arrays.asList("항목", "내용", "상태"),
                Arrays.asList("테스트1", "테이블 캡션 테스트", "완료"),
                Arrays.asList("테스트2", "자동 번호 확인", "진행중"),
                Arrays.asList("테스트3", "가운데 정렬 확인", "대기")
            );
            builder.addTableWithHeader(tableContents, "이걸봣줴숑 ㅎㅎ");

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