package com.hotbros.sejong.section;

import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.secpr.SecPr;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.secpr.pagepr.PageMargin;

public class SectionPreset {

    /**
     * section0에서 첫 번째 p(문단)의 첫 번째 run의 secPr/pagePr/margin에 바로 접근하여 margin 값을 XML 예시대로 설정
     */
    public static void setFirstRunSecPrDefault(SectionXMLFile section0) {
        Para para = section0.getPara(0);
        Run run = para.getRun(0);
        SecPr secPr = run.secPr();

        // pagePr와 margin에 바로 접근하여 값 설정
        PageMargin margin = secPr.pagePr().margin();
        margin.header(2834);
        margin.footer(2834);
        margin.gutter(0);
        margin.left(5669);
        margin.right(5669);
        margin.top(4251);
        margin.bottom(4252);
    }
}
