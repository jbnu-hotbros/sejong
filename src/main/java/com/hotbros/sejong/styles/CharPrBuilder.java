// package com.hotbros.sejong.styles;

// import kr.dogfoot.hwpxlib.object.HWPXFile;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.charpr.ValuesByLanguage;
// import kr.dogfoot.hwpxlib.object.common.ObjectType;
// import com.hotbros.sejong.util.IdManager;

// import java.util.Map;
// import java.util.HashMap;

// public class CharPrBuilder {
//     private HWPXFile hwpxFile;
//     private IdManager idManager;
//     private Map<String, Object> props = new HashMap<>();
//     private StringBuilder hashBuilder = new StringBuilder();
    
//     public CharPrBuilder(HWPXFile hwpxFile, IdManager idManager) {
//         this.hwpxFile = hwpxFile;
//         this.idManager = idManager;
//     }
    
//     public CharPrBuilder withFontSize(double fontSize) {
//         props.put("fontSize", fontSize);
//         hashBuilder.append("h:").append((int)(fontSize * 100)).append(";");
//         return this;
//     }
    
//     public CharPrBuilder withBold(boolean bold) {
//         props.put("bold", bold);
//         if (bold) {
//             hashBuilder.append("b:1;");
//         }
//         return this;
//     }
    
//     public CharPrBuilder withItalic(boolean italic) {
//         props.put("italic", italic);
//         if (italic) {
//             hashBuilder.append("i:1;");
//         }
//         return this;
//     }
    
//     public CharPrBuilder withTextColor(String textColor) {
//         props.put("textColor", textColor);
//         hashBuilder.append("c:").append(textColor).append(";");
//         return this;
//     }
    
//     public CharPrBuilder withFontName(String langId, String fontName) {
//         Map<String, String> fonts = (Map<String, String>) props.getOrDefault("fonts", new HashMap<String, String>());
//         fonts.put(langId, fontName);
//         props.put("fonts", fonts);
        
//         // 해시에 글꼴 정보 추가
//         switch (langId) {
//             case "한글":
//                 hashBuilder.append("fh:").append(fontName).append(";");
//                 break;
//             case "영문":
//                 hashBuilder.append("fl:").append(fontName).append(";");
//                 break;
//             case "한자":
//                 hashBuilder.append("fj:").append(fontName).append(";");
//                 break;
//             case "일본어":
//                 hashBuilder.append("fja:").append(fontName).append(";");
//                 break;
//             case "기타":
//                 hashBuilder.append("fo:").append(fontName).append(";");
//                 break;
//             case "기호":
//                 hashBuilder.append("fs:").append(fontName).append(";");
//                 break;
//             case "사용자":
//                 hashBuilder.append("fu:").append(fontName).append(";");
//                 break;
//         }
        
//         return this;
//     }
    
//     public String build() {
//         // 속성 해시 기반으로 ID 생성 시도 (캐싱)
//         String propHash = hashBuilder.toString();
//         if (!propHash.isEmpty()) {
//             String existingId = idManager.generateCharPrIdFromHash(propHash);
            
//             // 이미 존재하는 ID라면 해당 ID 반환
//             if (existingId != null && !existingId.equals(String.valueOf(0))) {
//                 return existingId;
//             }
            
//             // 기존 ID가 없으면 새로 생성
//             String charPrId = existingId;
            
//             CharPr charPr = hwpxFile.headerXMLFile().refList().charProperties().addNew()
//                     .idAnd(charPrId);
            
//             // 글꼴 설정
//             if (props.containsKey("fonts")) {
//                 Map<String, String> fonts = (Map<String, String>) props.get("fonts");
//                 if (!fonts.isEmpty()) {
//                     charPr.createFontRef();
                    
//                     for (Map.Entry<String, String> entry : fonts.entrySet()) {
//                         String langId = entry.getKey();
//                         String fontName = entry.getValue();
                        
//                         switch (langId) {
//                             case "한글":
//                                 charPr.fontRef().hangul(fontName);
//                                 break;
//                             case "영문":
//                                 charPr.fontRef().latin(fontName);
//                                 break;
//                             case "한자":
//                                 charPr.fontRef().hanja(fontName);
//                                 break;
//                             case "일본어":
//                                 charPr.fontRef().japanese(fontName);
//                                 break;
//                             case "기타":
//                                 charPr.fontRef().other(fontName);
//                                 break;
//                             case "기호":
//                                 charPr.fontRef().symbol(fontName);
//                                 break;
//                             case "사용자":
//                                 charPr.fontRef().user(fontName);
//                                 break;
//                         }
//                     }
//                 }
//             }
            
//             // 크기 설정 (포인트 * 100)
//             if (props.containsKey("fontSize")) {
//                 double fontSize = (Double) props.get("fontSize");
//                 charPr.heightAnd((int)(fontSize * 100));
//             }
            
//             // 굵게 설정
//             if (props.containsKey("bold") && (Boolean) props.get("bold")) {
//                 charPr.createBold();
//             }
            
//             // 기울임 설정
//             if (props.containsKey("italic") && (Boolean) props.get("italic")) {
//                 charPr.createItalic();
//             }
            
//             // 색상 설정
//             if (props.containsKey("textColor")) {
//                 charPr.textColorAnd((String) props.get("textColor"));
//             }
            
//             return charPrId;
//         }
        
//         // 빌더에 속성이 설정되지 않은 경우 기본 ID 생성
//         CharPr charPr = hwpxFile.headerXMLFile().refList().charProperties().addNew();
//         String charPrId = idManager.generateCharPrId();
//         charPr.idAnd(charPrId);
        
//         return charPrId;
//     }
    
//     /**
//      * 빌더 초기화 - 새로운 글자 모양 생성 시 호출
//      */
//     public CharPrBuilder reset() {
//         props.clear();
//         hashBuilder.setLength(0);
//         return this;
//     }
// } 