// package com.hotbros.sejong.numbering.request;

// import com.hotbros.sejong.dto.ParaHeadAttributes;

// import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;

// class ParaHeadBuilder {

//     private final ParaHead workingParaHead;

//     /**
//      * 기존 ParaHead 객체를 복제하여 빌더를 초기화합니다.
//      * @param originalParaHead 원본 ParaHead (null이면 예외)
//      */
//     public ParaHeadBuilder(ParaHead originalParaHead) {
//         if (originalParaHead == null) {
//             throw new IllegalArgumentException("Original ParaHead cannot be null.");
//         }
//         this.workingParaHead = originalParaHead.clone();
//     }

//     public ParaHeadBuilder level(Byte level) {
//         if (level != null) {
//             this.workingParaHead.level(level);
//         }
//         return this;
//     }

//     public ParaHeadBuilder start(Integer start) {
//         if (start != null) {
//             this.workingParaHead.start(start);
//         }
//         return this;
//     }

//     public ParaHeadBuilder numFormat(NumberType1 numFormat) {
//         if (numFormat != null) {
//             this.workingParaHead.numFormat(numFormat);
//         }
//         return this;
//     }

//     public ParaHeadBuilder text(String text) {
//         if (text != null) {
//             this.workingParaHead.text(text);
//         }
//         return this;
//     }

//     public ParaHead build() {
//         if (this.workingParaHead.level() == null) {
//             throw new IllegalStateException("ParaHead must have a level.");
//         }
//         return this.workingParaHead;
//     }

//     /**
//      * 원본 ParaHead 객체를 복제하고, 사용자 속성으로 선택적 덮어씌움
//      */
//     public static ParaHeadBuilder fromAttributes(ParaHead originalParaHead, ParaHeadAttributes attributesToApply) {
//         ParaHeadBuilder builder = new ParaHeadBuilder(originalParaHead);
//         if (attributesToApply == null) {
//             return builder;
//         }

//         builder
//             .level(attributesToApply.getLevel())
//             .start(attributesToApply.getStart())
//             .numFormat(attributesToApply.getNumFormat())
//             .text(attributesToApply.getText());

//         return builder;
//     }
// }
