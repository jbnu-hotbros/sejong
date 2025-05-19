// package com.hotbros.sejong.examples;

// import com.hotbros.sejong.util.HWPXObjectFinder;
// import com.hotbros.sejong.builder.StyleBuilder;

// import kr.dogfoot.hwpxlib.object.HWPXFile;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;

// import java.util.HashMap;
// import java.util.Map;

// public class MyStyleExample {
//     // 원본 객체 ID 상수
//     private static final String ORIGINAL_STYLE_ID = "0";    // 예: 바탕글 스타일 ID
    
//     private static final String NEW_STYLE_ID_TARGET = "18";
//     private static final String CLONED_PARAPR_ID_TARGET = "16";
//     private static final String CLONED_CHARPR_ID_TARGET = "7";

//     // 예제 2, 3에서 사용될 수 있는 다른 ID (필요에 따라 유지 또는 수정)
//     private static final String BUILDER_NEW_STYLE_ID_FROM_MAP_ORIGINAL_REFS = "101";
//     private static final String BUILDER_NEW_STYLE_ID_FROM_NEW_MAP_EXPLICIT_REFS = "200";


//     public static void main(String[] args) {
//         System.out.println("\n\n===== StyleBuilder 사용 예제 시작 =======");
//         demonstrateStyleBuilderUsage();
//         System.out.println("\n===== StyleBuilder 사용 예제 종료 =======");
//     }

//     private static void demonstrateStyleBuilderUsage() {
//         try {
//             HWPXFile hwpxFile = kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker.make();
//             System.out.println("새 HWPX 파일 생성 완료 (StyleBuilder 예제용).");

//             Style originalStyle = HWPXObjectFinder.findStyleById(hwpxFile, ORIGINAL_STYLE_ID);
//             if (originalStyle == null) {
//                 System.err.println("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")을 찾을 수 없습니다.");
//                 return;
//             }
//             System.out.println("StyleBuilder - 원본 스타일 로드: " + originalStyle.name() + " (ID: " + originalStyle.id() + ")");
//             System.out.println("  ㄴ 원본 CharPrIDRef: " + originalStyle.charPrIDRef() + ", ParaPrIDRef: " + originalStyle.paraPrIDRef());

//             // === 예제 1: 원본 Style의 CharPr/ParaPr을 복제하여 새 Style에 사용자 지정 ID로 연결 ===
//             System.out.println("\n--- StyleBuilder 예제 1: CharPr/ParaPr 복제 및 사용자 지정 ID 참조 --- ");
            
//             String newReferencedCharPrId = originalStyle.charPrIDRef(); // 기본값은 원본 ID
//             if (originalStyle.charPrIDRef() != null) {
//                 CharPr originalCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, originalStyle.charPrIDRef());
//                 if (originalCharPr != null) {
//                     CharPr clonedCharPr = originalCharPr.clone();
//                     clonedCharPr.id(CLONED_CHARPR_ID_TARGET); // 사용자가 지정한 새 CharPr ID
//                     hwpxFile.headerXMLFile().refList().charProperties().add(clonedCharPr);
//                     newReferencedCharPrId = clonedCharPr.id();
//                     System.out.println("  원본 CharPr(ID: " + originalCharPr.id() + ") 복제됨. 새 CharPr ID: " + newReferencedCharPrId);
//                 } else {
//                     System.out.println("  원본 CharPr(ID: " + originalStyle.charPrIDRef() + ")을 찾을 수 없어 복제하지 못함. 원본 ID를 사용합니다.");
//                 }
//             }

//             String newReferencedParaPrId = originalStyle.paraPrIDRef(); // 기본값은 원본 ID
//             if (originalStyle.paraPrIDRef() != null) {
//                 ParaPr originalParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, originalStyle.paraPrIDRef());
//                 if (originalParaPr != null) {
//                     ParaPr clonedParaPr = originalParaPr.clone();
//                     clonedParaPr.id(CLONED_PARAPR_ID_TARGET); // 사용자가 지정한 새 ParaPr ID
//                     hwpxFile.headerXMLFile().refList().paraProperties().add(clonedParaPr);
//                     newReferencedParaPrId = clonedParaPr.id();
//                     System.out.println("  원본 ParaPr(ID: " + originalParaPr.id() + ") 복제됨. 새 ParaPr ID: " + newReferencedParaPrId);
//                 } else {
//                     System.out.println("  원본 ParaPr(ID: " + originalStyle.paraPrIDRef() + ")을 찾을 수 없어 복제하지 못함. 원본 ID를 사용합니다.");
//                 }
//             }

//             Style newStyleWithClonedRefs = new StyleBuilder(originalStyle) 
//                     .id(NEW_STYLE_ID_TARGET) // 사용자가 지정한 새 Style ID
//                     .name("제목 스타일 (복제된 CharPr/ParaPr 참조 - 사용자 ID)")
//                     .engName("Title Style (Cloned CharPr/ParaPr Refs - User IDs)")
//                     .type(StyleType.PARA)
//                     .charPrIDRef(newReferencedCharPrId) // 복제된 CharPr의 새 ID (사용자 지정)
//                     .paraPrIDRef(newReferencedParaPrId) // 복제된 ParaPr의 새 ID (사용자 지정)
//                     .lockForm(false)
//                     .build();

//             System.out.println("StyleBuilder (복제된 참조, 사용자 ID) - 새 스타일 생성 성공:");
//             printStyleDetails(newStyleWithClonedRefs);
//             addStyleToRefList(hwpxFile, newStyleWithClonedRefs);

//             // === 예제 2: fromMap(originalStyle, attributes) 사용 (원본 CharPr/ParaPr ID 유지) ===
//             System.out.println("\n--- StyleBuilder 예제 2: fromMap(original, attributes) (원본 ID 참조 유지 또는 Map에서 지정) --- ");
//             Map<String, Object> styleChanges = new HashMap<>();
//             styleChanges.put("id", BUILDER_NEW_STYLE_ID_FROM_MAP_ORIGINAL_REFS);
//             styleChanges.put("name", "부제목 스타일 (원본 ID 참조)");
//             // charPrIDRef, paraPrIDRef를 맵에 설정하지 않으면 originalStyle의 것을 따름.
//             // 필요시: styleChanges.put("charPrIDRef", originalStyle.charPrIDRef()); // 또는 다른 기존 ID
//             Style newStyleFromMapOriginalRefs = StyleBuilder.fromMap(originalStyle, styleChanges).build();
//             System.out.println("StyleBuilder (맵, 원본 ID 참조) - 새 스타일 생성 성공:");
//             printStyleDetails(newStyleFromMapOriginalRefs);
//             addStyleToRefList(hwpxFile, newStyleFromMapOriginalRefs);
            
//             // === 예제 3: fromMap(attributes)를 사용한 새 스타일 생성 (명시적 ID 참조) ===
//             System.out.println("\n--- StyleBuilder 예제 3: fromMap(attributes) (명시적 ID 참조) --- ");
//             Map<String, Object> newStyleAttributes = new HashMap<>();
//             newStyleAttributes.put("id", BUILDER_NEW_STYLE_ID_FROM_NEW_MAP_EXPLICIT_REFS);
//             newStyleAttributes.put("name", "강조 문자 스타일 (명시적 ID)");
//             newStyleAttributes.put("type", "CHAR"); 
//             // 원본 스타일의 CharPr ID를 참조하거나, 알려진 기본 CharPr ID("0")를 사용합니다.
//             // 사용자님이 이전에 CLONED_CHARPR_ID_TARGET ("7")을 언급하셨으므로, 해당 ID가 유효하고 
//             // RefList에 이미 존재하거나 여기서 추가될 CharPr 객체의 ID라면 그것을 사용할 수도 있습니다.
//             // 여기서는 originalStyle (바탕글)의 charPrIDRef를 사용합니다.
//             newStyleAttributes.put("charPrIDRef", originalStyle.charPrIDRef() != null ? originalStyle.charPrIDRef() : "0"); 
//             // ParaPr은 문자 스타일이므로 설정 안 함.

//             Style brandNewStyleFromMapExplicitRefs = StyleBuilder.fromMap(newStyleAttributes).build();
//             System.out.println("StyleBuilder (맵, 명시적 ID) - 새 스타일 생성 성공:");
//             printStyleDetails(brandNewStyleFromMapExplicitRefs);
//             addStyleToRefList(hwpxFile, brandNewStyleFromMapExplicitRefs);

//             // 생성된 HWPX 파일을 저장하려면 다음 주석을 해제하세요.
//             // com.hotbros.sejong.writer.HWPXWriter.toFilepath(hwpxFile, "StyleBuilderUserIDsExample.hwpx");
//             // System.out.println("StyleBuilderUserIDsExample.hwpx 파일로 저장되었습니다.");

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     // 스타일 정보 출력 헬퍼 메서드
//     private static void printStyleDetails(Style style) {
//         if (style == null) {
//             System.out.println("  스타일 객체가 null입니다.");
//             return;
//         }
//         System.out.println("  ID: " + style.id());
//         System.out.println("  이름: " + style.name());
//         System.out.println("  영문 이름: " + style.engName());
//         System.out.println("  타입: " + style.type());
//         System.out.println("  CharPrIDRef: " + style.charPrIDRef());
//         System.out.println("  ParaPrIDRef: " + style.paraPrIDRef());
//         System.out.println("  NextStyleIDRef: " + style.nextStyleIDRef());
//         System.out.println("  LangID: " + style.langID());
//         System.out.println("  LockForm: " + style.lockForm());
//     }

//     // RefList에 스타일 추가 헬퍼 메서드
//     private static void addStyleToRefList(HWPXFile hwpxFile, Style style) {
//         if (hwpxFile != null && style != null && 
//             hwpxFile.headerXMLFile() != null && 
//             hwpxFile.headerXMLFile().refList() != null &&
//             hwpxFile.headerXMLFile().refList().styles() != null) {
//             hwpxFile.headerXMLFile().refList().styles().add(style);
//             System.out.println("  StyleBuilder - '" + style.name() + "' (ID: " + style.id() + ") 스타일이 HWPX 파일에 추가되었습니다.");
//         } else {
//             System.err.print("  스타일을 추가할 수 없습니다. 원인: ");
//             if (hwpxFile == null) System.err.print("HWPXFile null. ");
//             else if (hwpxFile.headerXMLFile() == null) System.err.print("HeaderXMLFile null. ");
//             else if (hwpxFile.headerXMLFile().refList() == null) System.err.print("RefList null. ");
//             else if (hwpxFile.headerXMLFile().refList().styles() == null) System.err.print("Styles RefList null. ");
//             if (style == null) System.err.print("Style 객체 null. ");
//             System.err.println();
//         }
//     }
// }
