package com.hotbros.sejong.examples;

import com.hotbros.sejong.SimpleHwpxWriter;

/**
 * SimpleHwpxWriter 사용 예제
 */
public class SimpleWriterExample {
    public static void main(String[] args) throws Exception {
        // SimpleHwpxWriter 인스턴스 생성
        SimpleHwpxWriter writer = new SimpleHwpxWriter();
        
        // 문단 추가
        writer.addParagraph("간단한 HWPX 문서 만들기", "제목");
        writer.addParagraph("1. 첫 번째 개요입니다.", "개요1");
        writer.addParagraph("a. 두 번째 수준 개요입니다.", "개요2");
        writer.addParagraph("b. 또 다른 두 번째 수준 개요입니다.", "개요2");
        writer.addParagraph("2. 다시 첫 번째 개요입니다.", "개요1");
        
        // 파일로 저장
        writer.saveFile("simple_example.hwpx");
        System.out.println("파일이 생성되었습니다: simple_example.hwpx");
        
        // 바이트 배열로 변환 (예: 웹 응답으로 전송하는 경우)
        byte[] hwpxBytes = writer.toByteArray();
        System.out.println("문서 크기: " + hwpxBytes.length + " bytes");
    }
} 