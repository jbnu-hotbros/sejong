package com.hotbros.sejong.global;

import com.hotbros.sejong.dto.FontAttributeConfig;
import com.hotbros.sejong.dto.StyleAttributeConfig;
import com.hotbros.sejong.factory.BorderFillFactory;
import com.hotbros.sejong.factory.BulletFactory;
import com.hotbros.sejong.factory.FontFactory;
import com.hotbros.sejong.factory.StyleFactory;

import com.hotbros.sejong.style.StyleBlock;
import com.hotbros.sejong.theme.Theme;

import com.hotbros.sejong.util.IdGenerator;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BorderFill;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.fontface.Font;

import java.util.HashMap;
import java.util.Map;

/**
 * HWPX Header Manager
 * - Header XML 섹션 (Styles, Fonts, BorderFills, Bullets)에 필요한 객체 생성/등록 및 ID 관리
 * - Theme + Override 값 적용
 */
public class HeaderManager {
    private final RefList refList;
    private final IdGenerator idGenerator;
    private final Map<StyleName, StyleBlock> styleMap = new HashMap<>();
    private final Map<BulletName, Bullet> bulletMap = new HashMap<>();
    private final Map<BorderFillName, BorderFill> borderFillMap = new HashMap<>();
    private final Map<FontName, Font> fontMap = new HashMap<>();

    // 기본적인 틀만 등록
    public HeaderManager(RefList refList, IdGenerator idGenerator) {
        this.refList = refList;
        this.idGenerator = idGenerator;

        initialize();
    }

    /**
     * 전체 Header 초기화
     */
    private void initialize() {
        // 스타일
        for (StyleName key : StyleName.values()) {
            StyleBlock styleBlock = StyleFactory.createDefaultStyle(refList);

            String charPrId = String.valueOf(idGenerator.nextCharPrId());
            String paraPrId = String.valueOf(idGenerator.nextParaPrId());
            String styleId = String.valueOf(idGenerator.nextStyleId());

            styleBlock.getCharPr().id(charPrId);
            styleBlock.getParaPr().id(paraPrId);
            styleBlock.getStyle().id(styleId);
            styleBlock.getStyle().charPrIDRef(charPrId);
            styleBlock.getStyle().paraPrIDRef(paraPrId);

            StyleFactory.addStyleToRefList(refList, styleBlock);

            styleMap.put(key, styleBlock);
        }

        // 불릿
        if (refList.bullets() == null) {
            refList.createBullets();
        }
        for (BulletName key : BulletName.values()) {
            Bullet bullet = BulletFactory.createDefaultBullet();

            String bulletId = String.valueOf(idGenerator.nextBulletId());
            bullet.id(bulletId);

            refList.bullets().add(bullet);
            bulletMap.put(key, bullet);
        }

        // 보더
        for (BorderFillName key : BorderFillName.values()) {
            BorderFill borderFill = BorderFillFactory.createDefaultBorderFill();
            String borderFillId = String.valueOf(idGenerator.nextBorderFillId());
            borderFill.id(borderFillId);
            refList.borderFills().add(borderFill);

            borderFillMap.put(key, borderFill);
        }

        // 폰트 등록
        for (FontName key : FontName.values()) {
            String fontName = key.name(); // FontName Enum → String 이름
            Font font = null;

            // 1️⃣ RefList에서 이름(face) 검색
            if (refList.fontfaces() != null) {
                for (var fontface : refList.fontfaces().fontfaces()) {
                    if (fontface.fonts() != null) {
                        for (var f : fontface.fonts()) {
                            if (f.face().equals(fontName)) {
                                font = f;
                                break;
                            }
                        }
                    }
                    if (font != null)
                        break;
                }
            }

            if (font == null) {
                // 2️⃣ 존재하지 않으면 새로 생성
                font = FontFactory.createDefaultFont(fontName);
                String fontId = String.valueOf(idGenerator.nextFontId());
                font.id(fontId);

                // RefList에 추가
                if (refList.fontfaces() == null) {
                    refList.createFontfaces();
                }
                if (!refList.fontfaces().fontfaces().iterator().hasNext()) {
                    refList.fontfaces().addNewFontface();
                }
                for (var fontface : refList.fontfaces().fontfaces()) {
                    fontface.addFont(font);
                }
            }

            // 3️⃣ map에 등록
            fontMap.put(key, font);
        }

    }

    public void override(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("Theme cannot be null");
        }

        // 1️⃣ Bullet
        for (var entry : theme.getBulletConfigMap().entrySet()) {
            var key = entry.getKey();
            var config = entry.getValue();
            var bullet = bulletMap.get(key);
            if (bullet != null) {
                BulletFactory.applyBulletAttribute(bullet, config);
            }
        }

        // 2️⃣ BorderFill
        for (var entry : theme.getBorderFillConfigMap().entrySet()) {
            var key = entry.getKey();
            var config = entry.getValue();
            var borderFill = borderFillMap.get(key);
            if (borderFill != null) {
                BorderFillFactory.applyBorderFillAttribute(borderFill, config);
            }
        }

        // 3️⃣ Style
        for (var entry : theme.getStyleConfigMap().entrySet()) {
            var key = entry.getKey();
            var config = entry.getValue();
            var styleBlock = styleMap.get(key);
            if (styleBlock != null) {
                StyleFactory.applyStyleAttribute(styleBlock, config);

                // 이름 → id 매핑 처리
                resolveStyleRefs(styleBlock, config);
            }
        }
    }

    private void resolveStyleRefs(StyleBlock styleBlock, StyleAttributeConfig config) {

        // Font name → id 매핑
        if (config.getFontName() != null) {
            FontName fontKey = FontName.valueOf(config.getFontName());
            Font font = fontMap.get(fontKey);
            if (font != null) {
                styleBlock.getCharPr().fontRef().setAll(font.id());
            }
        }

        // Bullet name → id 매핑
        if (config.getBulletName() != null) {
            BulletName bulletKey = BulletName.valueOf(config.getBulletName());
            Bullet bullet = bulletMap.get(bulletKey);
            if (bullet != null) {
                styleBlock.getParaPr().heading().idRef(bullet.id());
            }
        }

    }

}
