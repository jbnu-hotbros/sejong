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

    // BorderFill 생성 유틸 (id는 생성 시 할당)
    public BorderFill createBorderFill(boolean hasFill, String faceColor) {
        BorderFill borderFill = new BorderFill();
        String id = String.valueOf(idGenerator.nextBorderFillId());
        borderFill.id(id);
        borderFill.threeD(false);
        borderFill.shadow(false);
        borderFill.centerLine(CenterLineSort.NONE);
        borderFill.breakCellSeparateLine(false);

        // 슬래시 설정
        borderFill.createSlash();
        borderFill.slash()
                .typeAnd(SlashType.NONE)
                .CrookedAnd(false)
                .isCounter(false);

        // 백슬래시 설정
        borderFill.createBackSlash();
        borderFill.backSlash()
                .typeAnd(SlashType.NONE)
                .CrookedAnd(false)
                .isCounter(false);

        // 왼쪽 테두리 설정
        borderFill.createLeftBorder();
        borderFill.leftBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12)
                .colorAnd("#000000");

        // 오른쪽 테두리 설정
        borderFill.createRightBorder();
        borderFill.rightBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12)
                .colorAnd("#000000");

        // 위쪽 테두리 설정
        borderFill.createTopBorder();
        borderFill.topBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12)
                .colorAnd("#000000");

        // 아래쪽 테두리 설정
        borderFill.createBottomBorder();
        borderFill.bottomBorder()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_12)
                .colorAnd("#000000");

        // 대각선 설정
        borderFill.createDiagonal();
        borderFill.diagonal()
                .typeAnd(LineType2.SOLID)
                .widthAnd(LineWidth.MM_0_1)
                .colorAnd("#000000");

        // 채우기 브러시 설정 (hasFill이 true일 때만)
        if (hasFill) {
            borderFill.createFillBrush();
            FillBrush fillBrush = borderFill.fillBrush();
            fillBrush.createWinBrush();
            fillBrush.winBrush()
                    .faceColorAnd(faceColor)
                    .hatchColorAnd("#999999")  // 기본 해치 색상
                    .alphaAnd(0.0f);           // 기본 투명도
        }
        return borderFill;
    }
}