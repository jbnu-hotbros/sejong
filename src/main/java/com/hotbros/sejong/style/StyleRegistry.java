package com.hotbros.sejong.style;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import com.hotbros.sejong.util.IdGenerator;


/**
 * 사전에 정의된 스타일 레지스트리 클래스.
 * 외부에서는 String ID로 스타일에 접근하며,
 * 내부적으로는 Map<String, Style>로 관리됩니다.
 */
public class StyleRegistry {

    // private final Map<String, Style> styleMap;
    private final RefList refList;
    private final IdGenerator idGenerator;

    public StyleRegistry(RefList refList, IdGenerator idGenerator, StyleBlock[] presets) {
        this.refList = refList;
        this.idGenerator = idGenerator;
        initialize(presets);
    }

    private void initialize(StyleBlock[] presets) {
        // 모든 프리셋 메서드를 호출하여 스타일 등록
        for (StyleBlock block : presets) {
            // 스타일이 이미 등록되어 있는지 확인 (이름으로 체크)
            String styleName = block.getStyle().name();
            if (styleName != null) {
                for (Style existingStyle : refList.styles().items()) {
                    if (styleName.equals(existingStyle.name())) {
                        throw new IllegalArgumentException("이미 존재하는 스타일 이름입니다: " + styleName);
                    }
                }
            }

            // CharPr, ParaPr, Style에 id를 할당 (등록 시점)
            String charPrId = String.valueOf(idGenerator.nextCharPrId());
            String paraPrId = String.valueOf(idGenerator.nextParaPrId());
            String styleId = String.valueOf(idGenerator.nextStyleId());

            block.getCharPr().id(charPrId);
            block.getParaPr().id(paraPrId);
            block.getStyle().id(styleId);
            block.getStyle().charPrIDRef(charPrId);
            block.getStyle().paraPrIDRef(paraPrId);

            // CharPr 등록 (id 기준 중복 체크)
            boolean charPrExists = false;
            for (var existingCharPr : refList.charProperties().items()) {
                if (charPrId.equals(existingCharPr.id())) {
                    charPrExists = true;
                    break;
                }
            }
            if (!charPrExists) {
                refList.charProperties().add(block.getCharPr());
            }

            // ParaPr 등록 (id 기준 중복 체크)
            boolean paraPrExists = false;
            for (var existingParaPr : refList.paraProperties().items()) {
                if (paraPrId.equals(existingParaPr.id())) {
                    paraPrExists = true;
                    break;
                }
            }
            if (!paraPrExists) {
                refList.paraProperties().add(block.getParaPr());
            }

            //등록됨
            System.out.println("등록됨: " + block.getStyle().name());

            refList.styles().add(block.getStyle());
        }
    }

    /**
     * 스타일 이름(name)으로 Style을 조회하는 메서드
     *
     * @param name 스타일 이름 (예: "제목 테이블")
     * @return Style 객체 (null 반환 가능)
     */
    public Style getStyleByName(String name) {
        if (refList.styles() == null) return null;
        for (Style style : refList.styles().items()) {
            if (name.equals(style.name())) {
                return style;
            }
        }
        return null;
    }


}
