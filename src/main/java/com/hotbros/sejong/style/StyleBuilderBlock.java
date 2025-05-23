package com.hotbros.sejong.style;

import com.hotbros.sejong.builder.CharPrBuilder;
import com.hotbros.sejong.builder.ParaPrBuilder;
import com.hotbros.sejong.builder.StyleBuilder;
import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;

/**
 * CharPr, ParaPr, Style의 빌더를 통합 관리하며, build() 시 참조 무결성을 자동으로 보장하는 집합체.
 */
public class StyleBuilderBlock {
    private final CharPrBuilder charPrBuilder;
    private final ParaPrBuilder paraPrBuilder;
    private final StyleBuilder styleBuilder;
    private String charPrId;
    private String paraPrId;
    private String styleId;

    public StyleBuilderBlock(CharPrBuilder charPrBuilder, ParaPrBuilder paraPrBuilder, StyleBuilder styleBuilder) {
        this.charPrBuilder = charPrBuilder;
        this.paraPrBuilder = paraPrBuilder;
        this.styleBuilder = styleBuilder;
    }

    public CharPrBuilder getCharPrBuilder() {
        return charPrBuilder;
    }
    public ParaPrBuilder getParaPrBuilder() {
        return paraPrBuilder;
    }
    public StyleBuilder getStyleBuilder() {
        return styleBuilder;
    }

    /**
     * build()를 호출하면 CharPr, ParaPr, Style의 id/참조가 자동으로 맞춰지고, HWPXFile에 추가됩니다.
     * @param hwpxFile 스타일이 추가될 HWPXFile
     * @return 생성된 Style 객체
     */
    public Style build(HWPXFile hwpxFile) {
        // id 자동 생성 (간단히 System.nanoTime() 사용, 실제로는 더 정교한 id 생성 필요)
        this.charPrId = "charpr-" + System.nanoTime();
        this.paraPrId = "parapr-" + System.nanoTime();
        this.styleId = "style-" + System.nanoTime();

        charPrBuilder.id(this.charPrId);
        paraPrBuilder.id(this.paraPrId);
        styleBuilder.id(this.styleId)
                .charPrIDRef(this.charPrId)
                .paraPrIDRef(this.paraPrId);

        CharPr charPr = charPrBuilder.build();
        ParaPr paraPr = paraPrBuilder.build();
        Style style = styleBuilder.build();

        CharPr charPrTarget = hwpxFile.headerXMLFile().refList().charProperties().addNew();
        charPrTarget.copyFrom(charPr);
        ParaPr paraPrTarget = hwpxFile.headerXMLFile().refList().paraProperties().addNew();
        paraPrTarget.copyFrom(paraPr);
        Style styleTarget = hwpxFile.headerXMLFile().refList().styles().addNew();
        styleTarget.copyFrom(style);

        return styleTarget;
    }

    public String getCharPrId() { return charPrId; }
    public String getParaPrId() { return paraPrId; }
    public String getStyleId() { return styleId; }
} 