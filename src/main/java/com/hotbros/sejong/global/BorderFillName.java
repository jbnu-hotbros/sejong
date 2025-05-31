
package com.hotbros.sejong.global;

/**
 * 보더필 이름(Key) 정의
 * HWPX 문서에서 테이블 및 레이아웃 요소의 테두리/배경 스타일 참조에 사용
 */
public enum BorderFillName {
    기본,         // DEFAULT: 기본 테두리
    회색_채우기,   // GRAY_FILL: 회색 배경
    제목_박스,     // TITLE_BOX_MAIN: 보고서 제목 박스
    중제목_왼쪽,   // TITLE_BOX_MIDDLE_LEFT: 중제목 박스 (왼쪽 정렬)
    중제목_가운데, // TITLE_BOX_MIDDLE_CENTER: 중제목 박스 (가운데 정렬)
    중제목_오른쪽, // TITLE_BOX_MIDDLE_RIGHT: 중제목 박스 (오른쪽 정렬)
    소제목_왼쪽,   // TITLE_BOX_SUB_LEFT: 소제목 박스 (왼쪽 정렬)
    소제목_가운데, // TITLE_BOX_SUB_CENTER: 소제목 박스 (가운데 정렬)
    소제목_오른쪽; // TITLE_BOX_SUB_RIGHT: 소제목 박스 (오른쪽 정렬)
}