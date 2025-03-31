package com.hotbros.sejong.styles;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.charpr.ValuesByLanguage;
import kr.dogfoot.hwpxlib.object.common.ObjectType;
import com.hotbros.sejong.util.IdManager;

import java.util.Map;
import java.util.HashMap;

public class CharPrBuilder {
    private HWPXFile hwpxFile;
    private IdManager idManager;
    private Map<String, Object> props = new HashMap<>();
    
    public CharPrBuilder(HWPXFile hwpxFile, IdManager idManager) {
        this.hwpxFile = hwpxFile;
        this.idManager = idManager;
    }
    
    public CharPrBuilder withFontSize(double fontSize) {
        props.put("fontSize", fontSize);
        return this;
    }
    
    public CharPrBuilder withBold(boolean bold) {
        props.put("bold", bold);
        return this;
    }
    
    public CharPrBuilder withItalic(boolean italic) {
        props.put("italic", italic);
        return this;
    }
    
    public CharPrBuilder withTextColor(String textColor) {
        props.put("textColor", textColor);
        return this;
    }
    
    public CharPrBuilder withFontName(String langId, String fontName) {
        Map<String, String> fonts = (Map<String, String>) props.getOrDefault("fonts", new HashMap<String, String>());
        fonts.put(langId, fontName);
        props.put("fonts", fonts);
        return this;
    }
    
    public String build() {
        String charPrId = idManager.generateCharPrId();
        
        CharPr charPr = hwpxFile.headerXMLFile().refList().charProperties().addNew()
                .idAnd(charPrId);
        
        // 글꼴 설정
        if (props.containsKey("fonts")) {
            Map<String, String> fonts = (Map<String, String>) props.get("fonts");
            if (!fonts.isEmpty()) {
                charPr.createFontRef();
                
                for (Map.Entry<String, String> entry : fonts.entrySet()) {
                    String langId = entry.getKey();
                    String fontName = entry.getValue();
                    
                    switch (langId) {
                        case "한글":
                            charPr.fontRef().hangul(fontName);
                            break;
                        case "영문":
                            charPr.fontRef().latin(fontName);
                            break;
                        case "한자":
                            charPr.fontRef().hanja(fontName);
                            break;
                        case "일본어":
                            charPr.fontRef().japanese(fontName);
                            break;
                        case "기타":
                            charPr.fontRef().other(fontName);
                            break;
                        case "기호":
                            charPr.fontRef().symbol(fontName);
                            break;
                        case "사용자":
                            charPr.fontRef().user(fontName);
                            break;
                    }
                }
            }
        }
        
        // 크기 설정 (포인트 * 100)
        if (props.containsKey("fontSize")) {
            double fontSize = (Double) props.get("fontSize");
            charPr.heightAnd((int)(fontSize * 100));
        }
        
        // 굵게 설정
        if (props.containsKey("bold") && (Boolean) props.get("bold")) {
            charPr.createBold();
        }
        
        // 기울임 설정
        if (props.containsKey("italic") && (Boolean) props.get("italic")) {
            charPr.createItalic();
        }
        
        // 색상 설정
        if (props.containsKey("textColor")) {
            charPr.textColorAnd((String) props.get("textColor"));
        }
        
        return charPrId;
    }
} 