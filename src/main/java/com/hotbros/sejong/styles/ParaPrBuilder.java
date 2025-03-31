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
    private StringBuilder hashBuilder = new StringBuilder();
    
    public ParaPrBuilder(HWPXFile hwpxFile, IdManager idManager) {
        this.hwpxFile = hwpxFile;
        this.idManager = idManager;
    }
    
    public ParaPrBuilder withAlignment(HorizontalAlign2 align) {
        props.put("align", align);
        hashBuilder.append("a:").append(align).append(";");
        return this;
    }
    
    public ParaPrBuilder withIndent(double indent) {
        props.put("indent", indent);
        int indentValue = (int)(indent * 7200); // 1mm = 7200 hwpx 단위
        hashBuilder.append("i:").append(indentValue).append(";");
        return this;
    }
    
    public ParaPrBuilder withLineSpacing(int percent) {
        props.put("lineSpacing", percent);
        hashBuilder.append("lst:PERCENT;lsv:").append(percent).append(";");
        return this;
    }
    
    public ParaPrBuilder withMarginLeft(double margin) {
        props.put("marginLeft", margin);
        int marginValue = (int)(margin * 7200); // 1mm = 7200 hwpx 단위
        hashBuilder.append("ml:").append(marginValue).append(";");
        return this;
    }
    
    public ParaPrBuilder withMarginRight(double margin) {
        props.put("marginRight", margin);
        int marginValue = (int)(margin * 7200); // 1mm = 7200 hwpx 단위
        hashBuilder.append("mr:").append(marginValue).append(";");
        return this;
    }
    
    public String build() {
        // 속성 해시 기반으로 ID 생성 시도 (캐싱)
        String propHash = hashBuilder.toString();
        if (!propHash.isEmpty()) {
            String existingId = idManager.generateParaPrIdFromHash(propHash);
            
            // 이미 존재하는 ID라면 해당 ID 반환
            if (existingId != null && !existingId.equals(String.valueOf(0))) {
                return existingId;
            }
            
            // 기존 ID가 없으면 새로 생성
            String paraPrId = existingId;
            
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
            }
            
            // 왼쪽 여백 설정
            if (props.containsKey("marginLeft")) {
                double marginLeft = (Double) props.get("marginLeft");
                int marginValue = (int)(marginLeft * 7200);
                
                if (paraPr.margin() == null) {
                    paraPr.createMargin();
                }
                
                if (paraPr.margin().left() == null) {
                    paraPr.margin().createLeft();
                }
                paraPr.margin().left().value(marginValue);
            } else if (props.containsKey("indent")) {
                // 들여쓰기만 설정된 경우 왼쪽 여백은 0으로
                if (paraPr.margin() == null) {
                    paraPr.createMargin();
                }
                
                if (paraPr.margin().left() == null) {
                    paraPr.margin().createLeft();
                }
                paraPr.margin().left().value(0);
            }
            
            // 오른쪽 여백 설정
            if (props.containsKey("marginRight")) {
                double marginRight = (Double) props.get("marginRight");
                int marginValue = (int)(marginRight * 7200);
                
                if (paraPr.margin() == null) {
                    paraPr.createMargin();
                }
                
                if (paraPr.margin().right() == null) {
                    paraPr.margin().createRight();
                }
                paraPr.margin().right().value(marginValue);
            } else if (props.containsKey("indent")) {
                // 들여쓰기만 설정된 경우 오른쪽 여백은 0으로
                if (paraPr.margin() == null) {
                    paraPr.createMargin();
                }
                
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
        
        // 빌더에 속성이 설정되지 않은 경우 기본 ID 생성
        ParaPr paraPr = hwpxFile.headerXMLFile().refList().paraProperties().addNew();
        String paraPrId = idManager.generateParaPrId();
        paraPr.idAnd(paraPrId);
        
        return paraPrId;
    }
    
    /**
     * 빌더 초기화 - 새로운 문단 모양 생성 시 호출
     */
    public ParaPrBuilder reset() {
        props.clear();
        hashBuilder.setLength(0);
        return this;
    }
} 