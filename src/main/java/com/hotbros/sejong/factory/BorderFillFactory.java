package com.hotbros.sejong.factory;

import com.hotbros.sejong.dto.BorderFillAttributeConfig;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.CenterLineSort;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineWidth;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.SlashType;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;

public class BorderFillFactory {

    public static BorderFill createDefaultBorderFill() {
        BorderFill borderFill = new BorderFill();

        // (1) 고정값 설정 (변경 불가)
        borderFill.threeD(false);
        borderFill.shadow(false);
        borderFill.centerLine(CenterLineSort.NONE);
        borderFill.breakCellSeparateLine(false);

        borderFill.createDiagonal();
        borderFill.diagonal()
                .typeAnd(LineType2.NONE)
                .widthAnd(LineWidth.MM_0_1)
                .colorAnd("#000000");

        borderFill.createSlash();
        borderFill.slash()
                .typeAnd(SlashType.NONE)
                .CrookedAnd(false)
                .isCounter(false);

        borderFill.createBackSlash();
        borderFill.backSlash()
                .typeAnd(SlashType.NONE)
                .CrookedAnd(false)
                .isCounter(false);

        // (2) 기본값 설정용 Config 생성
        BorderFillAttributeConfig config = new BorderFillAttributeConfig();
        config.setLeftBorderType(LineType2.SOLID);
        config.setLeftBorderWidth(LineWidth.MM_0_12);
        config.setLeftBorderColor("#000000");
        config.setRightBorderType(LineType2.SOLID);
        config.setRightBorderWidth(LineWidth.MM_0_12);
        config.setRightBorderColor("#000000");
        config.setTopBorderType(LineType2.SOLID);
        config.setTopBorderWidth(LineWidth.MM_0_12);
        config.setTopBorderColor("#000000");
        config.setBottomBorderType(LineType2.SOLID);
        config.setBottomBorderWidth(LineWidth.MM_0_12);
        config.setBottomBorderColor("#000000");
        config.setFillColor("#000000");

        // (3) Config 값 적용
        applyBorderFillAttribute(borderFill, config);

        return borderFill;
    }

    public static void applyBorderFillAttribute(BorderFill borderFill, BorderFillAttributeConfig config) {
        if (borderFill == null || config == null) {
            throw new IllegalArgumentException("BorderFill and BorderFillAttributeConfig cannot be null");
        }

        // 왼쪽
        if (borderFill.leftBorder() == null)
            borderFill.createLeftBorder();
        if (config.getLeftBorderType() != null)
            borderFill.leftBorder().typeAnd(config.getLeftBorderType());
        if (config.getLeftBorderWidth() != null)
            borderFill.leftBorder().widthAnd(config.getLeftBorderWidth());
        if (config.getLeftBorderColor() != null)
            borderFill.leftBorder().colorAnd(config.getLeftBorderColor());

        // 오른쪽
        if (borderFill.rightBorder() == null)
            borderFill.createRightBorder();
        if (config.getRightBorderType() != null)
            borderFill.rightBorder().typeAnd(config.getRightBorderType());
        if (config.getRightBorderWidth() != null)
            borderFill.rightBorder().widthAnd(config.getRightBorderWidth());
        if (config.getRightBorderColor() != null)
            borderFill.rightBorder().colorAnd(config.getRightBorderColor());

        // 위쪽
        if (borderFill.topBorder() == null)
            borderFill.createTopBorder();
        if (config.getTopBorderType() != null)
            borderFill.topBorder().typeAnd(config.getTopBorderType());
        if (config.getTopBorderWidth() != null)
            borderFill.topBorder().widthAnd(config.getTopBorderWidth());
        if (config.getTopBorderColor() != null)
            borderFill.topBorder().colorAnd(config.getTopBorderColor());

        // 아래쪽
        if (borderFill.bottomBorder() == null)
            borderFill.createBottomBorder();
        if (config.getBottomBorderType() != null)
            borderFill.bottomBorder().typeAnd(config.getBottomBorderType());
        if (config.getBottomBorderWidth() != null)
            borderFill.bottomBorder().widthAnd(config.getBottomBorderWidth());
        if (config.getBottomBorderColor() != null)
            borderFill.bottomBorder().colorAnd(config.getBottomBorderColor());

        // 채우기 색상
        if (config.getFillColor() != null) {
            if (borderFill.fillBrush() == null) {
                borderFill.createFillBrush();
                borderFill.fillBrush().createWinBrush();
            }
            borderFill.fillBrush().winBrush()
                    .faceColorAnd(config.getFillColor())
                    .alphaAnd(0.0f);
        }
    }

}
