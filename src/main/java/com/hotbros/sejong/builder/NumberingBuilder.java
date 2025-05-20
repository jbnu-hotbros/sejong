package com.hotbros.sejong.builder;

import com.hotbros.sejong.dto.ParaHeadAttributes;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;

import java.util.ArrayList;
import java.util.List;

public class NumberingBuilder {
    private final Numbering workingNumbering;

    public NumberingBuilder(Numbering original) {
        if (original == null) {
            throw new IllegalArgumentException("Original Numbering cannot be null.");
        }
        this.workingNumbering = original.clone();
    }

    public NumberingBuilder overrideParaHeads(List<ParaHeadAttributes> overrides) {
        if (overrides == null || overrides.isEmpty()) {
            return this;
        }

        for (ParaHeadAttributes attr : overrides) {
            if (attr.getLevel() == null)
                continue;

            ParaHead existing = findParaHeadByLevel(attr.getLevel());
            if (existing != null) {
                ParaHead updated = ParaHeadBuilder.fromAttributes(existing, attr).build();
                replaceParaHead(attr.getLevel(), updated);
            }
        }
        return this;
    }

    private ParaHead findParaHeadByLevel(Byte level) {
        for (ParaHead paraHead : workingNumbering.paraHeads()) {
            if (paraHead.level() != null && paraHead.level().equals(level)) {
                return paraHead;
            }
        }
        return null;
    }

    private void replaceParaHead(Byte level, ParaHead newHead) {
        List<ParaHead> updatedList = new ArrayList<>();
        for (ParaHead head : workingNumbering.paraHeads()) {
            if (head.level() != null && head.level().equals(level)) {
                updatedList.add(newHead); // 교체
            } else {
                updatedList.add(head); // 유지
            }
        }
        workingNumbering.removeAllParaHeads();
        for (ParaHead head : updatedList) {
            workingNumbering.addParaHead(head);
        }
    }

    public Numbering build() {
        return workingNumbering;
    }

    public static NumberingBuilder fromAttributes(Numbering original, List<ParaHeadAttributes> overrideAttrs) {
        NumberingBuilder builder = new NumberingBuilder(original)
                .overrideParaHeads(overrideAttrs);

        return builder;
    }
}
