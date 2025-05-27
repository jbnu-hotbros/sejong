package com.hotbros.sejong.style;
// package com.hotbros.sejong.style.request;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;


public class StyleBlock {
    private final CharPr charPr;
    private final ParaPr paraPr;
    private final Style style;

    public StyleBlock(CharPr charPr, ParaPr paraPr, Style style) {
        this.charPr = charPr;
        this.paraPr = paraPr;
        this.style = style;
    }

    public CharPr getCharPr() {
        return charPr;
    }

    public ParaPr getParaPr() {
        return paraPr;
    }

    public Style getStyle() {
        return style;
    }

    public String getStyleId() {
        return style != null ? style.id() : null;
    }

    public String getName() {
        return style != null ? style.name() : null;
    }

    // public static StyleBlock fromAttributes(
    //     CharPr baseCharPr,
    //     ParaPr baseParaPr,
    //     Style baseStyle,
    //     CharPrAttributes charAttrs,
    //     ParaPrAttributes paraAttrs,
    //     StyleAttributes styleAttrs,
    //     IdGenerator allocator
    // ) {
    //     String styleId = String.valueOf(allocator.nextStyleId());
    //     String charPrId = String.valueOf(allocator.nextCharPrId());
    //     String paraPrId = String.valueOf(allocator.nextParaPrId());

    //     charAttrs.setId(charPrId);
    //     paraAttrs.setId(paraPrId);
    //     styleAttrs.setId(styleId);

    //     styleAttrs.setCharPrIDRef(charPrId);
    //     styleAttrs.setParaPrIDRef(paraPrId);

    //     CharPr charPr = CharPrBuilder.fromAttributes(baseCharPr, charAttrs).build();
    //     ParaPr paraPr = ParaPrBuilder.fromAttributes(baseParaPr, paraAttrs).build();
    //     Style style = StyleTagBuilder.fromAttributes(baseStyle, styleAttrs).build();


    //     return new StyleBlock(charPr, paraPr, style);   
    // }
}
