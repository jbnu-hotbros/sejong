package com.hotbros.sejong.styles;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.LineSpacingType;
import com.hotbros.sejong.util.IdManager;

import java.util.Map;
import java.util.HashMap;

public class ParaPrBuilder {
    private HWPXFile hwpxFile;
    private IdManager idManager;
    private Map<String, Object> props = new HashMap<>();
    
    public ParaPrBuilder(HWPXFile hwpxFile, IdManager idManager) {
        this.hwpxFile = hwpxFile;
        this.idManager = idManager;
    }
    
    public ParaPrBuilder withAlignment(HorizontalAlign2 align) {
        props.put("align", align);
        return this;
    }
    
    public ParaPrBuilder withIndent(double indent) {
        props.put("indent", indent);
        return this;
    }
    
    public ParaPrBuilder withLineSpacing(int percent) {
        props.put("lineSpacing", percent);
        return this;
    }
    
    public String build() {
        String paraPrId = idManager.generateParaPrId();
        
        ParaPr paraPr = hwpxFile.headerXMLFile().refList().paraProperties().addNew()
                .idAnd(paraPrId);
        
        // 정렬 설정
        if (props.containsKey("align")) {
            HorizontalAlign2 align = (HorizontalAlign2) props.get("align");
            if (paraPr.align() == null) {
                paraPr.createAlign();
            }
            paraPr.align().horizontalAnd(align);
        }
        
        // 여백 설정
        if (props.containsKey("indent")) {
            double indent = (Double) props.get("indent");
            int indentValue = (int)(indent * 7200); // 1mm = 7200 hwpx 단위
            
            if (paraPr.margin() == null) {
                paraPr.createMargin();
            }
            
            if (paraPr.margin().intent() == null) {
                paraPr.margin().createIntent();
            }
            paraPr.margin().intent().value(indentValue);
            
            if (paraPr.margin().left() == null) {
                paraPr.margin().createLeft();
            }
            paraPr.margin().left().value(0);
            
            if (paraPr.margin().right() == null) {
                paraPr.margin().createRight();
            }
            paraPr.margin().right().value(0);
        }
        
        // 줄 간격 설정
        if (props.containsKey("lineSpacing")) {
            int spacing = (Integer) props.get("lineSpacing");
            
            if (paraPr.lineSpacing() == null) {
                paraPr.createLineSpacing();
            }
            
            paraPr.lineSpacing()
                  .typeAnd(LineSpacingType.PERCENT)
                  .valueAnd(spacing);
        }
        
        return paraPrId;
    }
} 