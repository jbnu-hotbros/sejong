package com.hotbros.sejong.table;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.borderfill.FillBrush;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.CenterLineSort;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineType2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineWidth;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.SlashType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.hotbros.sejong.util.IdGenerator;

public class BorderFillRegistry {
    // BorderFill을 관리하는 Map (name → BorderFill 객체)
    private final Map<String, BorderFill> borderFillMap;
    private final RefList refList;
    private final IdGenerator idGenerator;

    public BorderFillRegistry(RefList refList, IdGenerator idGenerator) {
        this.borderFillMap = new HashMap<>();
        this.refList = refList;
        this.idGenerator = idGenerator;
        initialize();
    }

    private void initialize() {
        registerBorderFill("default", createBorderFill(false, null));
        registerBorderFill("grayFill", createBorderFill(true, "#E6E6E6"));

        // 타이틀 테이블용 커스텀 BorderFill 등록 (XML 예시와 동일하게)
        registerBorderFill("TITLE_BOX_MIDDLE_LEFT", createCustomBorderFill(
            LineType2.SOLID, "#003366", LineWidth.MM_0_1,
            LineType2.SOLID, "#003366", LineWidth.MM_0_1,
            LineType2.SOLID, "#003366", LineWidth.MM_0_1,
            LineType2.SOLID, "#003366", LineWidth.MM_0_1,
            LineType2.NONE, "#000000", LineWidth.MM_0_1,
            true, "#003366", "#000000"
        ));
        registerBorderFill("TITLE_BOX_MIDDLE_CENTER", createCustomBorderFill(
            LineType2.SOLID, "#003366", LineWidth.MM_0_1,
            LineType2.SOLID, "#999999", LineWidth.MM_0_12,
            LineType2.NONE, "#003366", LineWidth.MM_0_6,
            LineType2.NONE, "#003366", LineWidth.MM_0_6,
            LineType2.SOLID, null, LineWidth.MM_0_1,
            false, null, null
        ));
        registerBorderFill("TITLE_BOX_MIDDLE_RIGHT", createCustomBorderFill(
            LineType2.SOLID, "#999999", LineWidth.MM_0_12,
            LineType2.SOLID, "#999999", LineWidth.MM_0_12,
            LineType2.SOLID, "#999999", LineWidth.MM_0_12,
            LineType2.SOLID, "#999999", LineWidth.MM_0_12,
            LineType2.SOLID, "#999999", LineWidth.MM_0_12,
            true, "#F2F2F2", "#999999"
        ));
        registerBorderFill("TITLE_BOX_MAIN", createCustomBorderFill(
            LineType2.NONE, "#000000", LineWidth.MM_0_12, // leftBorder
            LineType2.NONE, "#000000", LineWidth.MM_0_12, // rightBorder
            LineType2.SOLID, "#3A3C84", LineWidth.MM_1_5, // topBorder
            LineType2.SOLID, "#3A3C84", LineWidth.MM_1_5, // bottomBorder
            LineType2.SOLID, "#000000", LineWidth.MM_0_1, // diagonal
            false, null, null
        ));
        // #27588b
        // Sub 타이틀박스용 BorderFill 등록 (left, center, right 순)
        registerBorderFill("TITLE_BOX_SUB_LEFT", createCustomBorderFill(
            LineType2.SOLID, "#27588b", LineWidth.MM_0_1, // left
            LineType2.SOLID, "#27588b", LineWidth.MM_0_1, // right
            LineType2.SOLID, "#27588b", LineWidth.MM_0_1, // top
            LineType2.SOLID, "#27588b", LineWidth.MM_0_1, // bottom
            LineType2.NONE, "#000000", LineWidth.MM_0_1, // diagonal
            true, "#27588b", "#000000"
        ));
        registerBorderFill("TITLE_BOX_SUB_CENTER", createCustomBorderFill(
            LineType2.SOLID, "#27588b", LineWidth.MM_0_1, // left
            LineType2.NONE, "#999999", LineWidth.MM_0_12, // right
            LineType2.NONE, "#003366", LineWidth.MM_0_6, // top
            LineType2.NONE, "#C0C0C0", LineWidth.MM_0_6, // bottom
            LineType2.SOLID, "#000000", LineWidth.MM_0_1, // diagonal
            false, null, null
        ));
        registerBorderFill("TITLE_BOX_SUB_RIGHT", createCustomBorderFill(
            LineType2.NONE, "#999999", LineWidth.MM_0_12, // left
            LineType2.NONE, "#999999", LineWidth.MM_0_12, // right
            LineType2.NONE, "#003366", LineWidth.MM_0_5, // top
            LineType2.SOLID, "#27588b", LineWidth.MM_0_5, // bottom
            LineType2.SOLID, "#000000", LineWidth.MM_0_1, // diagonal
            false, null, null
        ));
    }

    // BorderFill 등록 (name으로)
    public void registerBorderFill(String name, BorderFill borderFill) {
        if (borderFillMap.containsKey(name)) {
            throw new IllegalArgumentException("이미 존재하는 BorderFill 이름입니다: " + name);
        }
        // id는 이미 생성 시 할당됨
        borderFillMap.put(name, borderFill);
        addBorderFillToRefList(borderFill);
    }

    // name으로 BorderFill 조회
    public BorderFill getBorderFillByName(String name) {
        return borderFillMap.get(name);
    }

    // 전체 BorderFill 반환 (읽기 전용)
    public Map<String, BorderFill> getAllBorderFills() {
        return Collections.unmodifiableMap(borderFillMap);
    }

    // RefList에 BorderFill 추가
    private void addBorderFillToRefList(BorderFill borderFill) {
        if (refList == null || refList.borderFills() == null) {
            return;
        }
        refList.borderFills().add(borderFill);
    }

    // 복잡한 커스텀 BorderFill 생성 (정확한 타입 사용)
    public BorderFill createCustomBorderFill(
        LineType2 leftType, String leftColor, LineWidth leftWidth,
        LineType2 rightType, String rightColor, LineWidth rightWidth,
        LineType2 topType, String topColor, LineWidth topWidth,
        LineType2 bottomType, String bottomColor, LineWidth bottomWidth,
        LineType2 diagonalType, String diagonalColor, LineWidth diagonalWidth,
        boolean hasFill, String faceColor, String hatchColor
    ) {
        BorderFill borderFill = new BorderFill();
        String id = String.valueOf(idGenerator.nextBorderFillId());
        borderFill.id(id);
        borderFill.threeD(false);
        borderFill.shadow(false);
        borderFill.centerLine(CenterLineSort.NONE);
        borderFill.breakCellSeparateLine(false);

        // 슬래시, 백슬래시
        borderFill.createSlash();
        borderFill.slash().typeAnd(SlashType.NONE).CrookedAnd(false).isCounter(false);
        borderFill.createBackSlash();
        borderFill.backSlash().typeAnd(SlashType.NONE).CrookedAnd(false).isCounter(false);

        // 왼쪽 테두리
        borderFill.createLeftBorder();
        borderFill.leftBorder()
            .typeAnd(leftType != null ? leftType : LineType2.SOLID)
            .widthAnd(leftWidth != null ? leftWidth : LineWidth.MM_0_12)
            .colorAnd(leftColor != null ? leftColor : "#000000");

        // 오른쪽 테두리
        borderFill.createRightBorder();
        borderFill.rightBorder()
            .typeAnd(rightType != null ? rightType : LineType2.SOLID)
            .widthAnd(rightWidth != null ? rightWidth : LineWidth.MM_0_12)
            .colorAnd(rightColor != null ? rightColor : "#000000");

        // 위쪽 테두리
        borderFill.createTopBorder();
        borderFill.topBorder()
            .typeAnd(topType != null ? topType : LineType2.SOLID)
            .widthAnd(topWidth != null ? topWidth : LineWidth.MM_0_12)
            .colorAnd(topColor != null ? topColor : "#000000");

        // 아래쪽 테두리
        borderFill.createBottomBorder();
        borderFill.bottomBorder()
            .typeAnd(bottomType != null ? bottomType : LineType2.SOLID)
            .widthAnd(bottomWidth != null ? bottomWidth : LineWidth.MM_0_12)
            .colorAnd(bottomColor != null ? bottomColor : "#000000");

        // 대각선
        borderFill.createDiagonal();
        borderFill.diagonal()
            .typeAnd(diagonalType != null ? diagonalType : LineType2.SOLID)
            .widthAnd(diagonalWidth != null ? diagonalWidth : LineWidth.MM_0_1)
            .colorAnd(diagonalColor != null ? diagonalColor : "#000000");

        // 채우기 브러시 (hasFill이 true일 때만)
        if (hasFill && faceColor != null) {
            borderFill.createFillBrush();
            FillBrush fillBrush = borderFill.fillBrush();
            fillBrush.createWinBrush();
            fillBrush.winBrush()
                .faceColorAnd(faceColor)
                .hatchColorAnd(hatchColor != null ? hatchColor : "#999999")
                .alphaAnd(0.0f);
        }
        return borderFill;
    }

    // 간단한 기본 BorderFill 생성 (정확한 타입 사용)
    public BorderFill createBorderFill(boolean hasFill, String faceColor) {
        return createCustomBorderFill(
            LineType2.SOLID, "#000000", LineWidth.MM_0_12,
            LineType2.SOLID, "#000000", LineWidth.MM_0_12,
            LineType2.SOLID, "#000000", LineWidth.MM_0_12,
            LineType2.SOLID, "#000000", LineWidth.MM_0_12,
            LineType2.SOLID, "#000000", LineWidth.MM_0_1,
            hasFill, faceColor, null
        );
    }
}