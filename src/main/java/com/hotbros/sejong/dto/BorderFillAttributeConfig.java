package com.hotbros.sejong.dto;


import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineWidth;

/**
 * BorderFill의 모든 속성을 Config 형태로 추출
 * - null 값은 기본 유지
 * - Theme/Override에서 필요한 값만 선택적 덮어쓰기
 */

public class BorderFillAttributeConfig {
    // 4면 테두리
    private LineType2 leftBorderType;
    private LineWidth leftBorderWidth;
    private String leftBorderColor;

    private LineType2 rightBorderType;
    private LineWidth rightBorderWidth;
    private String rightBorderColor;

    private LineType2 topBorderType;
    private LineWidth topBorderWidth;
    private String topBorderColor;

    private LineType2 bottomBorderType;
    private LineWidth bottomBorderWidth;
    private String bottomBorderColor;

    // 채우기 색상
    private String fillColor;

    // Getter & Setter
    public LineType2 getLeftBorderType() {
        return leftBorderType;
    }

    public void setLeftBorderType(LineType2 leftBorderType) {
        this.leftBorderType = leftBorderType;
    }

    public LineWidth getLeftBorderWidth() {
        return leftBorderWidth;
    }

    public void setLeftBorderWidth(LineWidth leftBorderWidth) {
        this.leftBorderWidth = leftBorderWidth;
    }

    public String getLeftBorderColor() {
        return leftBorderColor;
    }

    public void setLeftBorderColor(String leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
    }

    public LineType2 getRightBorderType() {
        return rightBorderType;
    }

    public void setRightBorderType(LineType2 rightBorderType) {
        this.rightBorderType = rightBorderType;
    }

    public LineWidth getRightBorderWidth() {
        return rightBorderWidth;
    }

    public void setRightBorderWidth(LineWidth rightBorderWidth) {
        this.rightBorderWidth = rightBorderWidth;
    }

    public String getRightBorderColor() {
        return rightBorderColor;
    }

    public void setRightBorderColor(String rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
    }

    public LineType2 getTopBorderType() {
        return topBorderType;
    }

    public void setTopBorderType(LineType2 topBorderType) {
        this.topBorderType = topBorderType;
    }

    public LineWidth getTopBorderWidth() {
        return topBorderWidth;
    }

    public void setTopBorderWidth(LineWidth topBorderWidth) {
        this.topBorderWidth = topBorderWidth;
    }

    public String getTopBorderColor() {
        return topBorderColor;
    }

    public void setTopBorderColor(String topBorderColor) {
        this.topBorderColor = topBorderColor;
    }

    public LineType2 getBottomBorderType() {
        return bottomBorderType;
    }

    public void setBottomBorderType(LineType2 bottomBorderType) {
        this.bottomBorderType = bottomBorderType;
    }

    public LineWidth getBottomBorderWidth() {
        return bottomBorderWidth;
    }

    public void setBottomBorderWidth(LineWidth bottomBorderWidth) {
        this.bottomBorderWidth = bottomBorderWidth;
    }

    public String getBottomBorderColor() {
        return bottomBorderColor;
    }

    public void setBottomBorderColor(String bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }
}

