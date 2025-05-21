package com.hotbros.sejong;

import java.util.List;

import com.hotbros.sejong.dto.CharPrAttributes;
import com.hotbros.sejong.dto.ParaHeadAttributes;
import com.hotbros.sejong.dto.ParaPrAttributes;
import com.hotbros.sejong.dto.StyleAttributes;
import com.hotbros.sejong.dto.StyleBlock;
import com.hotbros.sejong.util.HWPXObjectFinder;
import com.hotbros.sejong.builder.NumberingBuilder;
import com.hotbros.sejong.util.StyleIdAllocator;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;


public class HWPXBuilder {
    private static final String ORIGINAL_STYLE_ID = "0"; // 예: 바탕글 스타일 ID

    private HWPXFile hwpxFile;
    private RefList refList;
    private StyleIdAllocator allocator;

    public HWPXBuilder() {
        this.hwpxFile = BlankFileMaker.make();
        this.refList = hwpxFile.headerXMLFile().refList();
        this.allocator = new StyleIdAllocator();
    }

    private void addStyle() {
        // 원본 스타일 및 참조 객체 찾기
        Style originalStyle = HWPXObjectFinder.findStyleById(hwpxFile, ORIGINAL_STYLE_ID);
        if (originalStyle == null) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")을 찾을 수 없습니다. 프로그램 실행을 중단합니다.");
        }

        String originalCharPrId = originalStyle.charPrIDRef();
        if (originalCharPrId == null || originalCharPrId.trim().isEmpty()) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 유효한 charPrIDRef를 가지고 있지 않습니다.");
        }
        CharPr sourceCharPr = HWPXObjectFinder.findCharPrById(hwpxFile, originalCharPrId);
        if (sourceCharPr == null) {
            throw new RuntimeException(
                    "원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 참조하는 CharPr(ID: " + originalCharPrId + ")을 찾을 수 없습니다.");
        }

        String originalParaPrId = originalStyle.paraPrIDRef();
        if (originalParaPrId == null || originalParaPrId.trim().isEmpty()) {
            throw new RuntimeException("원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 유효한 paraPrIDRef를 가지고 있지 않습니다.");
        }
        ParaPr sourceParaPr = HWPXObjectFinder.findParaPrById(hwpxFile, originalParaPrId);
        if (sourceParaPr == null) {
            throw new RuntimeException(
                    "원본 스타일(ID: " + ORIGINAL_STYLE_ID + ")이 참조하는 ParaPr(ID: " + originalParaPrId + ")을 찾을 수 없습니다.");
        }

        // 1. Attributes DTO 정의
        CharPrAttributes charPrDto = new CharPrAttributes();
        charPrDto.setTextColor("#FF0000");
        charPrDto.setBold(true);
        charPrDto.setFontSizePt(12.0);

        ParaPrAttributes paraPrDto = new ParaPrAttributes();
        paraPrDto.setAlignHorizontal(HorizontalAlign2.CENTER); // 사용자가 수정한 방식 반영
        paraPrDto.setLineSpacing(160); // 사용자가 수정한 방식 반영 (Integer)

        StyleAttributes styleDto = new StyleAttributes();
        styleDto.setName("나의커스텀스타일");
        styleDto.setEngName("MyCustomStyle");

        // 2. StyleBlock 생성
        StyleBlock customBlock = StyleBlock.fromAttributes(
                sourceCharPr,
                sourceParaPr,
                originalStyle,
                charPrDto,
                paraPrDto,
                styleDto,
                allocator);

        // 3. RefList에 등록 (StyleBlock에서 객체 가져오기)
        CharPr charPr = customBlock.getCharPr();
        ParaPr paraPr = customBlock.getParaPr();
        Style customStyle = customBlock.getStyle();

        if (refList.charProperties() == null) {
            refList.createCharProperties();
        }
        refList.charProperties().add(charPr);

        if (refList.paraProperties() == null) {
            refList.createParaProperties();
        }
        refList.paraProperties().add(paraPr);

        if (refList.styles() == null) {
            refList.createStyles();
        }
        refList.styles().add(customStyle);

        System.out.println("새로운 스타일 '" + customStyle.name() + "' (ID: " + customStyle.id() + ") 추가됨");
        System.out.println("  - 참조 CharPr ID: " + customStyle.charPrIDRef());
        System.out.println("  - 참조 ParaPr ID: " + customStyle.paraPrIDRef());
    }

    private void addNumbering() {
        Numbering numbering = refList.numberings().get(0);

        ParaHeadAttributes attr= new ParaHeadAttributes();
        attr.setLevel((byte) 1);
        attr.setStart(1);
        attr.setNumFormat(NumberType1.DIGIT);
        attr.setText("hhh");

        NumberingBuilder numberingBuilder = NumberingBuilder.fromAttributes(numbering, List.of(attr));
        Numbering updated = numberingBuilder.build();


        // 넘버링이 '대체' 되어야 함
        // 기존 넘버링을 삭제
        refList.numberings().remove(numbering);
        // 새로운 넘버링 추가
        refList.numberings().add(updated);


        System.out.println("넘버링 추가 완료");
        System.out.println("넘버링 ID: " + updated.id());
        System.out.println("넘버링 시작: " + updated.start());
    }


    public HWPXFile build() throws Exception {
        addStyle();
        // addNumbering();
        return hwpxFile;
    }
}
