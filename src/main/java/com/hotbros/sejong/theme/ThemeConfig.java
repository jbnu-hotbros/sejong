package com.hotbros.sejong.theme;

import com.hotbros.sejong.dto.*;
import com.hotbros.sejong.global.*;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineWidth;

public class ThemeConfig {

    /**
     * 기본 테마 생성 메서드
     * - 실무에서 사용하는 보고서 스타일 기반
     */
    public static Theme createDefaultTheme() {
        Theme theme = new Theme();

        // ================================
        // 스타일(Style) 설정
        // ================================

        // 제목
        StyleAttributeConfig title = new StyleAttributeConfig();
        title.setFontName("HY헤드라인M");
        title.setFontSize(2000);
        title.setBold(true);
        title.setTextColor("#000000");
        title.setAlign(HorizontalAlign2.CENTER);
        title.setLineSpacing(130);
        title.setStyleName(StyleName.제목.name());
        title.setStyleEngName(StyleName.제목.getEngName());
        theme.getStyleConfigMap().put(StyleName.제목, title);

        // 중제목_숫자
        StyleAttributeConfig middleNum = new StyleAttributeConfig();
        middleNum.setFontName("함초롬바탕");
        middleNum.setFontSize(1000);
        middleNum.setBold(true);
        middleNum.setTextColor("#FFFFFF");
        middleNum.setAlign(HorizontalAlign2.LEFT);
        middleNum.setLineSpacing(150);
        middleNum.setMarginLeft(1500);
        middleNum.setBulletName("개요1");
        middleNum.setStyleName(StyleName.중제목_숫자.name());
        middleNum.setStyleEngName(StyleName.중제목_숫자.getEngName());
        theme.getStyleConfigMap().put(StyleName.중제목_숫자, middleNum);

        // 중제목_내용
        StyleAttributeConfig middleContent = new StyleAttributeConfig();
        middleContent.setFontName("HY헤드라인M");
        middleContent.setFontSize(1600);
        middleContent.setTextColor("#000000");
        middleContent.setAlign(HorizontalAlign2.LEFT);

        middleContent.setLineSpacing(150);
        middleContent.setMarginLeft(1500);
        middleContent.setStyleName(StyleName.중제목_내용.name());
        middleContent.setStyleEngName(StyleName.중제목_내용.getEngName());
        theme.getStyleConfigMap().put(StyleName.중제목_내용, middleContent);

        // 소제목_숫자
        StyleAttributeConfig subNum = new StyleAttributeConfig();
        subNum.setFontName("함초롬바탕");
        subNum.setFontSize(1600);
        subNum.setTextColor("#FFFFFF");
        subNum.setAlign(HorizontalAlign2.LEFT);
        subNum.setLineSpacing(140);
        subNum.setBold(true);
        subNum.setBulletName("개요2");
        subNum.setStyleName(StyleName.소제목_숫자.name());
        subNum.setStyleEngName(StyleName.소제목_숫자.getEngName());
        theme.getStyleConfigMap().put(StyleName.소제목_숫자, subNum);

        // 소제목_내용
        StyleAttributeConfig subContent = new StyleAttributeConfig();
        subContent.setFontName("HY헤드라인M");
        subContent.setFontSize(1600);
        subContent.setTextColor("#000000");
        subContent.setAlign(HorizontalAlign2.LEFT);
        subContent.setLineSpacing(140);
        subContent.setStyleName(StyleName.소제목_내용.name());
        subContent.setStyleEngName(StyleName.소제목_내용.getEngName());
        theme.getStyleConfigMap().put(StyleName.소제목_내용, subContent);

        // 개요1 (Heading1)
        StyleAttributeConfig heading1 = new StyleAttributeConfig();
        heading1.setFontName("함초롬바탕");
        heading1.setFontSize(1400);
        heading1.setTextColor("#000000");
        heading1.setAlign(HorizontalAlign2.LEFT);
        heading1.setLineSpacing(150);
        heading1.setMarginLeft(1500);
        heading1.setBulletName(StyleName.개요1.name());
        heading1.setStyleName(StyleName.개요1.name());
        heading1.setStyleEngName(StyleName.개요1.getEngName());
        theme.getStyleConfigMap().put(StyleName.개요1, heading1);

        // 개요2 (Heading2)
        StyleAttributeConfig heading2 = new StyleAttributeConfig();
        heading2.setFontName("함초롬바탕");
        heading2.setFontSize(1400);
        heading2.setTextColor("#000000");
        heading2.setAlign(HorizontalAlign2.LEFT);
        heading2.setLineSpacing(150);
        heading2.setMarginLeft(3000);
        heading2.setBulletName(StyleName.개요2.name());
        heading2.setStyleName(StyleName.개요2.name());
        heading2.setStyleEngName(StyleName.개요2.getEngName());
        theme.getStyleConfigMap().put(StyleName.개요2, heading2);

        // 테이블_헤더
        StyleAttributeConfig tableHeader = new StyleAttributeConfig();
        tableHeader.setFontName("맑은 고딕");
        tableHeader.setFontSize(1000);
        tableHeader.setBold(true);
        tableHeader.setTextColor("#000000");
        tableHeader.setAlign(HorizontalAlign2.CENTER);
        tableHeader.setLineSpacing(160);
        tableHeader.setStyleName(StyleName.테이블_헤더.name());
        tableHeader.setStyleEngName(StyleName.테이블_헤더.getEngName());
        theme.getStyleConfigMap().put(StyleName.테이블_헤더, tableHeader);

        // 테이블_내용
        StyleAttributeConfig tableBody = new StyleAttributeConfig();
        tableBody.setFontName("맑은 고딕");
        tableBody.setFontSize(1000);
        tableBody.setTextColor("#000000");
        tableBody.setAlign(HorizontalAlign2.JUSTIFY);
        tableBody.setLineSpacing(160);
        tableBody.setStyleName(StyleName.테이블_내용.name());
        tableBody.setStyleEngName(StyleName.테이블_내용.getEngName());
        theme.getStyleConfigMap().put(StyleName.테이블_내용, tableBody);

        // 왼쪽정렬_스타일
        StyleAttributeConfig leftAlign = new StyleAttributeConfig();
        leftAlign.setAlign(HorizontalAlign2.LEFT);
        leftAlign.setStyleName(StyleName.왼쪽정렬_스타일.name());
        leftAlign.setStyleEngName(StyleName.왼쪽정렬_스타일.getEngName());
        theme.getStyleConfigMap().put(StyleName.왼쪽정렬_스타일, leftAlign);

        // 가운데정렬_스타일
        StyleAttributeConfig centerAlign = new StyleAttributeConfig();
        centerAlign.setAlign(HorizontalAlign2.CENTER);
        centerAlign.setStyleName(StyleName.가운데정렬_스타일.name());
        centerAlign.setStyleEngName(StyleName.가운데정렬_스타일.getEngName());
        theme.getStyleConfigMap().put(StyleName.가운데정렬_스타일, centerAlign);

        // ================================
        // 2️⃣ 테두리(BorderFill) 설정
        // ================================

        // BorderFill 등록
        theme.getBorderFillConfigMap().put(BorderFillName.기본, buildCustomBorderFill(
                LineType2.SOLID, "#000000", LineWidth.MM_0_12,
                LineType2.SOLID, "#000000", LineWidth.MM_0_12,
                LineType2.SOLID, "#000000", LineWidth.MM_0_12,
                LineType2.SOLID, "#000000", LineWidth.MM_0_12,
                LineType2.NONE, "#000000", LineWidth.MM_0_1,
                true, "#FFFFFF"));

        theme.getBorderFillConfigMap().put(BorderFillName.회색_채우기, buildCustomBorderFill(
                LineType2.SOLID, "#E0E0E0", LineWidth.MM_0_12,
                LineType2.SOLID, "#E0E0E0", LineWidth.MM_0_12,
                LineType2.SOLID, "#E0E0E0", LineWidth.MM_0_12,
                LineType2.SOLID, "#E0E0E0", LineWidth.MM_0_12,
                LineType2.NONE, "#000000", LineWidth.MM_0_1,
                true, "#E0E0E0"));

        theme.getBorderFillConfigMap().put(BorderFillName.제목_박스, buildCustomBorderFill(
                LineType2.NONE, "#000000", LineWidth.MM_0_12,
                LineType2.NONE, "#000000", LineWidth.MM_0_12,
                LineType2.SOLID, "#3A3C84", LineWidth.MM_1_5,
                LineType2.SOLID, "#3A3C84", LineWidth.MM_1_5,
                LineType2.SOLID, "#000000", LineWidth.MM_0_1,
                false, null));

        theme.getBorderFillConfigMap().put(BorderFillName.중제목_왼쪽, buildCustomBorderFill(
                LineType2.SOLID, "#003366", LineWidth.MM_0_1,
                LineType2.SOLID, "#003366", LineWidth.MM_0_1,
                LineType2.SOLID, "#003366", LineWidth.MM_0_1,
                LineType2.SOLID, "#003366", LineWidth.MM_0_1,
                LineType2.NONE, "#000000", LineWidth.MM_0_1,
                true, "#003366"));

        theme.getBorderFillConfigMap().put(BorderFillName.중제목_가운데, buildCustomBorderFill(
                LineType2.SOLID, "#003366", LineWidth.MM_0_1,
                LineType2.SOLID, "#999999", LineWidth.MM_0_12,
                LineType2.NONE, "#003366", LineWidth.MM_0_6,
                LineType2.NONE, "#003366", LineWidth.MM_0_6,
                LineType2.SOLID, null, LineWidth.MM_0_1,
                false, null));

        theme.getBorderFillConfigMap().put(BorderFillName.중제목_오른쪽, buildCustomBorderFill(
                LineType2.SOLID, "#999999", LineWidth.MM_0_12,
                LineType2.SOLID, "#999999", LineWidth.MM_0_12,
                LineType2.SOLID, "#999999", LineWidth.MM_0_12,
                LineType2.SOLID, "#999999", LineWidth.MM_0_12,
                LineType2.SOLID, "#999999", LineWidth.MM_0_12,
                true, "#F2F2F2"));

        theme.getBorderFillConfigMap().put(BorderFillName.소제목_왼쪽, buildCustomBorderFill(
                LineType2.SOLID, "#27588b", LineWidth.MM_0_1,
                LineType2.SOLID, "#27588b", LineWidth.MM_0_1,
                LineType2.SOLID, "#27588b", LineWidth.MM_0_1,
                LineType2.SOLID, "#27588b", LineWidth.MM_0_1,
                LineType2.NONE, "#000000", LineWidth.MM_0_1,
                true, "#27588b"));

        theme.getBorderFillConfigMap().put(BorderFillName.소제목_가운데, buildCustomBorderFill(
                LineType2.SOLID, "#27588b", LineWidth.MM_0_1,
                LineType2.NONE, "#999999", LineWidth.MM_0_12,
                LineType2.NONE, "#003366", LineWidth.MM_0_6,
                LineType2.NONE, "#C0C0C0", LineWidth.MM_0_6,
                LineType2.SOLID, "#000000", LineWidth.MM_0_1,
                false, null));

        theme.getBorderFillConfigMap().put(BorderFillName.소제목_오른쪽, buildCustomBorderFill(
                LineType2.NONE, "#999999", LineWidth.MM_0_12,
                LineType2.NONE, "#999999", LineWidth.MM_0_12,
                LineType2.NONE, "#003366", LineWidth.MM_0_5,
                LineType2.SOLID, "#27588b", LineWidth.MM_0_5,
                LineType2.SOLID, "#000000", LineWidth.MM_0_1,
                false, null));

        // ================================
        // 3️⃣ 불릿(Bullet) 설정
        // ================================

        BulletAttributeConfig bullet1 = new BulletAttributeConfig();
        bullet1.setBulletChar("❍");
        theme.getBulletConfigMap().put(BulletName.개요1, bullet1);

        BulletAttributeConfig bullet2 = new BulletAttributeConfig();
        bullet2.setBulletChar("-");
        theme.getBulletConfigMap().put(BulletName.개요2, bullet2);

        return theme;
    }

    private static BorderFillAttributeConfig buildCustomBorderFill(
            LineType2 leftType, String leftColor, LineWidth leftWidth,
            LineType2 rightType, String rightColor, LineWidth rightWidth,
            LineType2 topType, String topColor, LineWidth topWidth,
            LineType2 bottomType, String bottomColor, LineWidth bottomWidth,
            LineType2 diagonalType, String diagonalColor, LineWidth diagonalWidth,
            boolean fill, String fillColor) {
        BorderFillAttributeConfig config = new BorderFillAttributeConfig();

        config.setLeftBorderType(leftType);
        config.setLeftBorderColor(leftColor);
        config.setLeftBorderWidth(leftWidth);

        config.setRightBorderType(rightType);
        config.setRightBorderColor(rightColor);
        config.setRightBorderWidth(rightWidth);

        config.setTopBorderType(topType);
        config.setTopBorderColor(topColor);
        config.setTopBorderWidth(topWidth);

        config.setBottomBorderType(bottomType);
        config.setBottomBorderColor(bottomColor);
        config.setBottomBorderWidth(bottomWidth);

        if (fill && fillColor != null) {
            config.setFillColor(fillColor);
        }

        return config;
    }
}
