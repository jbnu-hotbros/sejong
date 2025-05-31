package com.hotbros.sejong.global;


import com.hotbros.sejong.factory.PageFactory;
import com.hotbros.sejong.theme.Theme;
import com.hotbros.sejong.util.IdGenerator;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;

/**
 * HWPXBuilder: HWPX 문서 생성의 메인 클래스
 * - Global Preset Key 기반 초기화
 * - ThemeConfig + UserOverride 값 적용
 * - HeaderManager를 통해 XML Header에 등록 및 ID 참조 관리
 */
public class HWPXBuilder {
    private final HWPXFile hwpxFile;
    private final HeaderManager headerManager;
    private final IdGenerator idGenerator;
    private final RefList refList;
    private final SectionXMLFile section;

    public HWPXBuilder(Theme theme) {
        this.hwpxFile = BlankFileMaker.make();
        this.idGenerator = new IdGenerator();
        this.refList = hwpxFile.headerXMLFile().refList();
        this.section = hwpxFile.sectionXMLFileList().get(0);
        this.headerManager = new HeaderManager(refList, idGenerator);

        PageFactory.applyPageAttribute(section);
        headerManager.override(theme);
    }

    // /**
    // * 문단 생성 API (styleID 참조)
    // */
    // public void addParagraph(StyleName styleKey, String text) {
    // String styleId = headerManager.getStyleId(styleKey);

    // Para para = new Para();
    // para.styleIDRef(styleId);
    // var run = para.addNewRun();
    // run.addNewT().addText(text);

    // hwpxFile.body().addNewSection().addNewParagraph().addPara(para);
    // }

    public HWPXFile build() {
        return hwpxFile;
    }

}
