package com.hotbros.sejong.global;

/**
 * 스타일 이름(Key) 정의
 */
public enum StyleName {
    제목("Title"),
    중제목_숫자("MiddleTitleNumber"),
    중제목_내용("MiddleTitleContent"),
    소제목_숫자("SubTitleNumber"),
    소제목_내용("SubTitleContent"),
    개요1("Heading1"),
    개요2("Heading2"),
    테이블_헤더("TableHeader"),
    테이블_내용("TableBody"),
    왼쪽정렬_스타일("AlignLeft"),
    가운데정렬_스타일("AlignCenter");

    private final String engName;

    StyleName(String engName) {
        this.engName = engName;
    }

    public String getEngName() {
        return engName;
    }
}