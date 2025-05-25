package com.hotbros.sejong.numbering;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import com.hotbros.sejong.util.HWPXObjectFinder;

/**
 * 넘버링 레지스트리: 넘버링은 항상 1개만 존재하며, 등록 시 기존 넘버링을 무조건 제거 후 새로 등록한다.
 * id는 항상 "1"로 고정된다.
 */
public class NumberingRegistry {
    private final RefList refList;

    public NumberingRegistry(RefList refList) {
        this.refList = refList;
        initialize();
    }

    private void initialize() {
        registerNumbering(defaultNumberingPreset());
    }

    // 넘버링 등록 (기존 넘버링이 있으면 무조건 제거 후 등록, id는 항상 "1")
    public void registerNumbering(Numbering numbering) {
        if (refList != null && refList.numberings() != null) {
            refList.numberings().removeAll();
            refList.numberings().add(numbering);
        }
    }

    // 현재 넘버링 반환 (없으면 null)
    public Numbering getNumbering() {
        return HWPXObjectFinder.findFirstNumbering(refList);
    }

    /**
     * 넘버링에서 특정 레벨의 ParaHead를 찾아 반환합니다. 없으면 null 반환.
     */
    public static ParaHead findParaHeadByLevel(Numbering numbering, int level) {
        for (ParaHead ph : numbering.paraHeads()) {
            if (ph.level() != null && ph.level() == level) {
                return ph;
            }
        }
        return null;
    }

    // ===== 프리셋 메서드 예시 =====
    public Numbering defaultNumberingPreset() {
        Numbering base = HWPXObjectFinder.findFirstNumbering(refList).clone();
        base.id("1");

        // 기존 ParaHead를 제거하지 않고, 레벨별로 수정 또는 추가
        ParaHead head1 = findParaHeadByLevel(base, 1);
        if (head1 != null) {
            head1.numFormat(NumberType1.DIGIT);
            head1.text("^1.");
        }

        ParaHead head2 = findParaHeadByLevel(base, 2);
        if (head2 != null) {
            head2.numFormat(NumberType1.LATIN_SMALL);
            head2.text("^1.^2)");
        }

        ParaHead head3 = findParaHeadByLevel(base, 3);
        if (head3 != null) {
            head3.numFormat(NumberType1.ROMAN_SMALL);
            head3.text("^1.^2)^3.");
        }

        ParaHead head4 = findParaHeadByLevel(base, 4);
        if (head4 != null) {
            head4.numFormat(NumberType1.LATIN_SMALL);
            head4.text("^1.^2)^3)^4.");
        }

        return base;
    }
} 