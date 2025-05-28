package com.hotbros.sejong.util;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;

public class HWPXObjectFinder {

    /**
     * RefList에서 ID를 기준으로 Style 객체를 찾습니다.
     *
     * @param refList 검색할 RefList 객체
     * @param styleId 찾을 Style의 ID
     * @return 찾아낸 Style 객체. 없으면 null.
     */
    public static Style findStyleById(RefList refList, String styleId) {
        if (refList == null || styleId == null) {
            System.err.println("HWPXObjectFinder.findStyleById: refList 또는 styleId가 null입니다.");
            return null;
        }
        if (refList.styles() == null) {
            System.err.println("HWPXObjectFinder.findStyleById: Styles가 null입니다. (RefList: " + refList + ", StyleID: " + styleId + ")");
            return null;
        }
        for (Style style : refList.styles().items()) {
            if (styleId.equals(style.id())) {
                return style;
            }
        }
        System.err.println("HWPXObjectFinder.findStyleById: ID '" + styleId + "'에 해당하는 Style을 찾지 못했습니다.");
        return null;
    }

    /**
     * RefList에서 ID를 기준으로 ParaPr 객체를 찾습니다.
     *
     * @param refList 검색할 RefList 객체
     * @param paraPrId 찾을 ParaPr의 ID
     * @return 찾아낸 ParaPr 객체. 없으면 null.
     */
    public static ParaPr findParaPrById(RefList refList, String paraPrId) {
        if (refList == null || paraPrId == null) {
            System.err.println("HWPXObjectFinder.findParaPrById: refList 또는 paraPrId가 null입니다.");
            return null;
        }
        if (refList.paraProperties() == null) {
            System.err.println("HWPXObjectFinder.findParaPrById: ParaProperties가 null입니다. (RefList: " + refList + ", ParaPrID: " + paraPrId + ")");
            return null;
        }
        for (ParaPr paraPr : refList.paraProperties().items()) {
            if (paraPrId.equals(paraPr.id())) {
                return paraPr;
            }
        }
        System.err.println("HWPXObjectFinder.findParaPrById: ID '" + paraPrId + "'에 해당하는 ParaPr을 찾지 못했습니다.");
        return null;
    }

    /**
     * RefList에서 ID를 기준으로 CharPr 객체를 찾습니다.
     *
     * @param refList 검색할 RefList 객체
     * @param charPrId 찾을 CharPr의 ID
     * @return 찾아낸 CharPr 객체. 없으면 null.
     */
    public static CharPr findCharPrById(RefList refList, String charPrId) {
        if (refList == null || charPrId == null) {
            System.err.println("HWPXObjectFinder.findCharPrById: refList 또는 charPrId가 null입니다.");
            return null;
        }
        if (refList.charProperties() == null) {
            System.err.println("HWPXObjectFinder.findCharPrById: CharProperties가 null입니다. (RefList: " + refList + ", CharPrID: " + charPrId + ")");
            return null;
        }
        for (CharPr charPr : refList.charProperties().items()) {
            if (charPrId.equals(charPr.id())) {
                return charPr;
            }
        }
        System.err.println("HWPXObjectFinder.findCharPrById: ID '" + charPrId + "'에 해당하는 CharPr을 찾지 못했습니다.");
        return null;
    }

    /**
     * RefList에서 ID를 기준으로 Numbering 객체를 찾습니다.
     *
     * @param refList 검색할 RefList 객체
     * @param numberingId 찾을 Numbering의 ID
     * @return 찾아낸 Numbering 객체. 없으면 null.
     */
    public static Numbering findNumberingById(RefList refList, String numberingId) {
        if (refList == null || numberingId == null) {
            System.err.println("HWPXObjectFinder.findNumberingById: refList 또는 numberingId가 null입니다.");
            return null;
        }
        if (refList.numberings() == null) {
            System.err.println("HWPXObjectFinder.findNumberingById: Numberings가 null입니다. (RefList: " + refList + ", NumberingID: " + numberingId + ")");
            return null;
        }
        for (Numbering numbering : refList.numberings().items()) {
            if (numberingId.equals(numbering.id())) {
                return numbering;
            }
        }
        System.err.println("HWPXObjectFinder.findNumberingById: ID '" + numberingId + "'에 해당하는 Numbering을 찾지 못했습니다.");
        return null;
    }

    /**
     * RefList에서 첫 번째 Numbering 객체를 반환합니다.
     * 없으면 null 반환.
     */
    public static Numbering findFirstNumbering(RefList refList) {
        if (refList == null || refList.numberings() == null || refList.numberings().empty()) {
            System.err.println("HWPXObjectFinder.findFirstNumbering: RefList 또는 Numberings가 null이거나 비어있습니다.");
            return null;
        }
        return refList.numberings().get(0);
    }
} 