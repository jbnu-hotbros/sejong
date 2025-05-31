package com.hotbros.sejong.factory;

import com.hotbros.sejong.dto.StyleAttributeConfig;
import com.hotbros.sejong.style.StyleBlock;
import com.hotbros.sejong.util.HWPXObjectFinder;

import kr.dogfoot.hwpxlib.object.common.HWPXObject;
import kr.dogfoot.hwpxlib.object.common.compatibility.Case;
import kr.dogfoot.hwpxlib.object.common.compatibility.Default;
import kr.dogfoot.hwpxlib.object.common.compatibility.Switch;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.LineSpacing;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.ParaMargin;

public class StyleFactory {
    private static final String DEFAULT_STYLE_ID = "0";

    public static StyleBlock createDefaultStyle(RefList refList) {
        Style styleTag = HWPXObjectFinder.findStyleById(refList, DEFAULT_STYLE_ID).clone();
        CharPr charPr = HWPXObjectFinder.findCharPrById(refList, styleTag.charPrIDRef()).clone();
        ParaPr paraPr = HWPXObjectFinder.findParaPrById(refList, styleTag.paraPrIDRef()).clone();

        return new StyleBlock(charPr, paraPr, styleTag);
    }

    public static void addStyleToRefList(RefList refList, StyleBlock styleBlock) {
        String charPrId = styleBlock.getCharPr().id();
        String paraPrId = styleBlock.getParaPr().id();

        // CharPr 등록 (id 기준 중복 체크)
        boolean charPrExists = false;
        for (var existingCharPr : refList.charProperties().items()) {
            if (charPrId.equals(existingCharPr.id())) {
                charPrExists = true;
                break;
            }
        }
        if (!charPrExists) {
            refList.charProperties().add(styleBlock.getCharPr());
        }

        // ParaPr 등록 (id 기준 중복 체크)
        boolean paraPrExists = false;
        for (var existingParaPr : refList.paraProperties().items()) {
            if (paraPrId.equals(existingParaPr.id())) {
                paraPrExists = true;
                break;
            }
        }
        if (!paraPrExists) {
            refList.paraProperties().add(styleBlock.getParaPr());
        }

        refList.styles().add(styleBlock.getStyle());
    }

    public static void applyStyleAttribute(StyleBlock block, StyleAttributeConfig config) {
        CharPr charPr = block.getCharPr();
        ParaPr paraPr = block.getParaPr();
        Style style = block.getStyle();
    
 
        if (config.getFontSize() != null) {
            charPr.height(config.getFontSize());
        }
        if (config.getBold() != null) {
            if (config.getBold()) {
                charPr.createBold();
            } else {
                charPr.removeBold();
            }
        }
        if (config.getTextColor() != null) {
            charPr.textColor(config.getTextColor());
        }
        if (config.getSpacing() != null) {
            charPr.spacing().setAll(config.getSpacing());
        }
        if (config.getRatio() != null) {
            charPr.relSz().setAll(config.getRatio());
        }
        if (config.getAlign() != null) {
            paraPr.align().horizontal(config.getAlign());
        }
        if (config.getLineSpacing() != null) {
            setLineSpacingBoth(paraPr, config.getLineSpacing());
        }
        if (config.getMarginLeft() != null) {
            setMarginLeftBoth(paraPr, config.getMarginLeft());
        }
        if (config.getStyleName() != null) {
            style.name(config.getStyleName());
        }
        if (config.getStyleEngName() != null) {
            style.engName(config.getStyleEngName());
        }
    }
    

    private static void setLineSpacingBoth(ParaPr paraPr, int value) {
        if (paraPr == null || paraPr.switchList() == null || paraPr.switchList().isEmpty()) return;
        Switch sw = (Switch) paraPr.switchList().get(paraPr.switchList().size() - 1);
        if (sw == null) return;
        // case
        for (Case c : sw.caseObjects()) {
            if ("http://www.hancom.co.kr/hwpml/2016/HwpUnitChar".equals(c.requiredNamespace())) {
                for (HWPXObject child : c.children()) {
                    if (child instanceof LineSpacing) {
                        ((LineSpacing) child).value(value);
                    }
                }
            }
        }
        // default
        Default def = sw.defaultObject();
        if (def != null) {
            for (HWPXObject child : def.children()) {
                if (child instanceof LineSpacing) {
                    ((LineSpacing) child).value(value);
                }
            }
        }
    }

    /**
     * case/default 모두에 들여쓰기(intent) 값을 일괄 적용
     */
    private static void setMarginLeftBoth(ParaPr paraPr, int value) {
        if (paraPr == null || paraPr.switchList() == null || paraPr.switchList().isEmpty()) return;
        Switch sw = (Switch) paraPr.switchList().get(paraPr.switchList().size() - 1);
        if (sw == null) return;
        // case
        for (Case c : sw.caseObjects()) {
            if ("http://www.hancom.co.kr/hwpml/2016/HwpUnitChar".equals(c.requiredNamespace())) {
                for (HWPXObject child : c.children()) {
                    if (child instanceof ParaMargin) {
                        ParaMargin margin = (ParaMargin) child;
                        if (margin.left() != null) {
                            margin.left().value(value);
                        }
                    }
                }
            }
        }
        // default
        Default def = sw.defaultObject();
        if (def != null) {
            for (HWPXObject child : def.children()) {
                if (child instanceof ParaMargin) {
                    ParaMargin margin = (ParaMargin) child;
                    if (margin.left() != null) {
                        margin.left().value(value*2);
                    }
                }
            }
        }
    }
}
