// package com.hotbros.sejong.numbering.request;

// import java.util.HashMap;
// import java.util.Map;

// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;


// public class ParaHeadAttributes {
//     private String numberingId;       // 넘버링 그룹 ID
//     private Byte level;               // 넘버링 레벨
//     private Integer start;            // 시작 숫자
//     private NumberType1 numFormat;    // 번호 형식 (enum)
//     private String text;              // 실제 표시 텍스트 (예: ^1.)

//     public ParaHeadAttributes() {
//     }

//     public ParaHeadAttributes(String numberingId, Byte level, Integer start, NumberType1 numFormat, String text) {
//         this.numberingId = numberingId;
//         this.level = level;
//         this.start = start;
//         this.numFormat = numFormat;
//         this.text = text;
//     }

//     public String getNumberingId() {
//         return numberingId;
//     }

//     public void setNumberingId(String numberingId) {
//         this.numberingId = numberingId;
//     }

//     public Byte getLevel() {
//         return level;
//     }

//     public void setLevel(Byte level) {
//         this.level = level;
//     }

//     public Integer getStart() {
//         return start;
//     }

//     public void setStart(Integer start) {
//         this.start = start;
//     }

//     public NumberType1 getNumFormat() {
//         return numFormat;
//     }

//     public void setNumFormat(NumberType1 numFormat) {
//         this.numFormat = numFormat;
//     }

//     public String getText() {
//         return text;
//     }

//     public void setText(String text) {
//         this.text = text;
//     }

//     public Map<String, String> toMap() {
//         Map<String, String> map = new HashMap<>();
//         if (numberingId != null) map.put("numberingId", numberingId);
//         if (level != null) map.put("level", level.toString());
//         if (start != null) map.put("start", start.toString());
//         if (numFormat != null) map.put("numFormat", numFormat.name());
//         if (text != null) map.put("text", text);
//         return map;
//     }

//     public static ParaHeadAttributes fromMap(Map<String, String> map) {
//         if (map == null) return null;

//         ParaHeadAttributes attr = new ParaHeadAttributes();
//         attr.setNumberingId(map.get("numberingId"));

//         String levelStr = map.get("level");
//         if (levelStr != null) {
//             try {
//                 attr.setLevel(Byte.parseByte(levelStr));
//             } catch (NumberFormatException e) {
//                 System.err.println("NumberingAttributes.fromMap: 잘못된 level 값 - " + levelStr);
//             }
//         }

//         String startStr = map.get("start");
//         if (startStr != null) {
//             try {
//                 attr.setStart(Integer.parseInt(startStr));
//             } catch (NumberFormatException e) {
//                 System.err.println("NumberingAttributes.fromMap: 잘못된 start 값 - " + startStr);
//             }
//         }

//         String numFormatStr = map.get("numFormat");
//         if (numFormatStr != null) {
//             try {
//                 attr.setNumFormat(NumberType1.valueOf(numFormatStr));
//             } catch (IllegalArgumentException e) {
//                 System.err.println("NumberingAttributes.fromMap: 잘못된 numFormat 값 - " + numFormatStr);
//             }
//         }

//         attr.setText(map.get("text"));
//         return attr;
//     }
// }
