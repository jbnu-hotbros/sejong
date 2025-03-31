package com.hotbros.sejong.styles;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.Heading;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
import com.hotbros.sejong.util.IdManager;

public class ParagraphBuilder {
    private HWPXFile hwpxFile;
    private IdManager idManager;
    private SectionXMLFile section;
    private Para para;
    private String paraPrId;
    
    public ParagraphBuilder(HWPXFile hwpxFile, IdManager idManager, int sectionIndex) {
        this.hwpxFile = hwpxFile;
        this.idManager = idManager;
        this.section = hwpxFile.sectionXMLFileList().get(sectionIndex);
        this.para = section.addNewPara();
    }
    
    public ParagraphBuilder withParaPrId(String paraPrId) {
        this.paraPrId = paraPrId;
        para.paraPrIDRef(paraPrId);
        return this;
    }
    
    public ParagraphBuilder withBullet(String bulletId, int level) {
        // 문단 속성이 없으면 생성
        if (paraPrId == null) {
            ParaPrBuilder paraPrBuilder = new ParaPrBuilder(hwpxFile, idManager);
            paraPrId = paraPrBuilder.build();
            para.paraPrIDRef(paraPrId);
        }
        
        // 글머리표 설정을 위한 ParaPr 찾기
        for (ParaPr paraPr : hwpxFile.headerXMLFile().refList().paraProperties().items()) {
            if (paraPr.id().equals(paraPrId)) {
                Heading heading = paraPr.createHeading();
                heading.typeAnd(ParaHeadingType.BULLET);
                heading.idRefAnd(bulletId);
                heading.level((byte) level);
                break;
            }
        }
        
        return this;
    }
    
    public ParagraphBuilder withNumbering(String numberingId, int level) {
        // 문단 속성이 없으면 생성
        if (paraPrId == null) {
            ParaPrBuilder paraPrBuilder = new ParaPrBuilder(hwpxFile, idManager);
            paraPrId = paraPrBuilder.build();
            para.paraPrIDRef(paraPrId);
        }
        
        // 번호매기기 설정을 위한 ParaPr 찾기
        for (ParaPr paraPr : hwpxFile.headerXMLFile().refList().paraProperties().items()) {
            if (paraPr.id().equals(paraPrId)) {
                Heading heading = paraPr.createHeading();
                heading.typeAnd(ParaHeadingType.NUMBER);
                heading.idRefAnd(numberingId);
                heading.level((byte) level);
                break;
            }
        }
        
        return this;
    }
    
    public RunBuilder createRun(String text) {
        return new RunBuilder(this, text);
    }
    
    public Para build() {
        return para;
    }
    
    public class RunBuilder {
        private ParagraphBuilder paragraphBuilder;
        private String text;
        private String charPrId;
        
        public RunBuilder(ParagraphBuilder paragraphBuilder, String text) {
            this.paragraphBuilder = paragraphBuilder;
            this.text = text;
        }
        
        public RunBuilder withCharPrId(String charPrId) {
            this.charPrId = charPrId;
            return this;
        }
        
        public ParagraphBuilder add() {
            Run run = para.addNewRun();
            
            if (charPrId != null) {
                run.charPrIDRef(charPrId);
            }
            
            run.addNewT().addText(text);
            
            return paragraphBuilder;
        }
    }
} 