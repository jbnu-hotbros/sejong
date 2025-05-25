package com.hotbros.sejong.util;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;

public class HWPXObjectFinder {

    /**
     * HWPXFile에서 ID를 기준으로 Style 객체를 찾습니다.
     *
     * @param hwpxFile 검색할 HWPXFile 객체
     * @param styleId 찾을 Style의 ID
     * @return 찾아낸 Style 객체. 없으면 null.
     */
    public static Style findStyleById(HWPXFile hwpxFile, String styleId) {
        if (hwpxFile == null || styleId == null) {
            System.err.println("HWPXObjectFinder.findStyleById: hwpxFile 또는 styleId가 null입니다.");
            return null;
        }
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null || refList.styles() == null) {
            System.err.println("HWPXObjectFinder.findStyleById: RefList 또는 Styles가 null입니다. (HWPXFile: " + hwpxFile + ", StyleID: " + styleId + ")");
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
     * HWPXFile에서 ID를 기준으로 ParaPr 객체를 찾습니다.
     *
     * @param hwpxFile 검색할 HWPXFile 객체
     * @param paraPrId 찾을 ParaPr의 ID
     * @return 찾아낸 ParaPr 객체. 없으면 null.
     */
    public static ParaPr findParaPrById(HWPXFile hwpxFile, String paraPrId) {
        if (hwpxFile == null || paraPrId == null) {
            System.err.println("HWPXObjectFinder.findParaPrById: hwpxFile 또는 paraPrId가 null입니다.");
            return null;
        }
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null || refList.paraProperties() == null) {
            System.err.println("HWPXObjectFinder.findParaPrById: RefList 또는 ParaProperties가 null입니다. (HWPXFile: " + hwpxFile + ", ParaPrID: " + paraPrId + ")");
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
     * HWPXFile에서 ID를 기준으로 CharPr 객체를 찾습니다.
     *
     * @param hwpxFile 검색할 HWPXFile 객체
     * @param charPrId 찾을 CharPr의 ID
     * @return 찾아낸 CharPr 객체. 없으면 null.
     */
    public static CharPr findCharPrById(HWPXFile hwpxFile, String charPrId) {
        if (hwpxFile == null || charPrId == null) {
            System.err.println("HWPXObjectFinder.findCharPrById: hwpxFile 또는 charPrId가 null입니다.");
            return null;
        }
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null || refList.charProperties() == null) {
            System.err.println("HWPXObjectFinder.findCharPrById: RefList 또는 CharProperties가 null입니다. (HWPXFile: " + hwpxFile + ", CharPrID: " + charPrId + ")");
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
     * HWPXFile에서 ID를 기준으로 Numbering 객체를 찾습니다.
     *
     * @param hwpxFile 검색할 HWPXFile 객체
     * @param numberingId 찾을 Numbering의 ID
     * @return 찾아낸 Numbering 객체. 없으면 null.
     */
    public static Numbering findNumberingById(HWPXFile hwpxFile, String numberingId) {
        if (hwpxFile == null || numberingId == null) {
            System.err.println("HWPXObjectFinder.findNumberingById: hwpxFile 또는 numberingId가 null입니다.");
            return null;
        }
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null || refList.numberings() == null) {
            System.err.println("HWPXObjectFinder.findNumberingById: RefList 또는 Numberings가 null입니다. (HWPXFile: " + hwpxFile + ", NumberingID: " + numberingId + ")");
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