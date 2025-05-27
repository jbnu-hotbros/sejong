// package com.hotbros.sejong.numbering.request;

// import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
// import com.hotbros.sejong.dto.ParaHeadAttributes;
// import com.hotbros.sejong.builder.NumberingBuilder;
// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
// import java.util.List;

// public class NumberingPresets {
//     /**
//      * 기본 넘버링 프리셋을 생성하여 반환합니다.
//      * @param refList 넘버링이 추가될 RefList
//      * @return 새로 생성된 Numbering 객체
//      */
//     public static Numbering createDefaultNumbering(RefList refList) {
//         Numbering numbering = refList.numberings().get(0);

//         ParaHeadAttributes paraHeadAttributes1 = new ParaHeadAttributes();
//         paraHeadAttributes1.setLevel((byte) 1);
//         paraHeadAttributes1.setNumFormat(NumberType1.DIGIT);
//         paraHeadAttributes1.setText("§^1.");

//         ParaHeadAttributes paraHeadAttributes2 = new ParaHeadAttributes();
//         paraHeadAttributes2.setLevel((byte) 2);
//         paraHeadAttributes2.setNumFormat(NumberType1.LATIN_SMALL);
//         paraHeadAttributes2.setText("§^1.^2)");

//         ParaHeadAttributes paraHeadAttributes3 = new ParaHeadAttributes();
//         paraHeadAttributes3.setLevel((byte) 3);
//         paraHeadAttributes3.setStart(1);
//         paraHeadAttributes3.setNumFormat(NumberType1.ROMAN_SMALL);
//         paraHeadAttributes3.setText("§^1.^2).^3.");

//         ParaHeadAttributes paraHeadAttributes4 = new ParaHeadAttributes();
//         paraHeadAttributes4.setLevel((byte) 4);
//         paraHeadAttributes4.setNumFormat(NumberType1.LATIN_SMALL);
//         paraHeadAttributes4.setText("§^1.^2).^3).^4.");

//         NumberingBuilder numberingBuilder = NumberingBuilder.fromAttributes(
//             numbering, List.of(paraHeadAttributes1, paraHeadAttributes2, paraHeadAttributes3, paraHeadAttributes4)
//         );
//         return numberingBuilder.build();
//     }

//     // 필요하다면 추가 프리셋/유틸 static 메서드도 여기에 작성
// } 