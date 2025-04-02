package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ParaHeadingType;
import java.util.HashMap;
import java.util.Map;

/**
 * 스타일 서비스 - 스타일 등록 및 관리 기능을 제공합니다.
 * StyleBuilder로 생성된 스타일을 HWPX 파일에 등록합니다.
 * 
 * 중요: 객체 등록 순서는 다음과 같아야 합니다:
 * 1. Bullet (불렛)
 * 2. CharPr (글자 모양)
 * 3. ParaPr (문단 모양)
 * 4. Style (스타일)
 * 
 * 이 순서는 객체 간의 참조 관계 때문에 중요합니다.
 */
public class StyleService {
    
    /**
     * 스타일셋에 포함된 모든 스타일을 HWPX 파일에 등록합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param styleSet 등록할 스타일셋
     * @return 등록된 스타일 ID와 이름 쌍의 매핑
     */
    public static void registerStyleSet(HWPXFile hwpxFile, StyleSet styleSet) {
        if (hwpxFile == null || styleSet == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 스타일셋이 null입니다");
        }
        
        // 모든 스타일 등록
        Style[] styles = styleSet.getAllStyles();
        for (Style style : styles) {
            if (style != null) {
                registerStyle(hwpxFile, style);
            }
        }
    }
    
    /**
     * StyleResult에 포함된 모든 객체를 올바른 순서로 HWPX 파일에 등록합니다.
     * 이 메서드는 스타일 객체와 관련된 모든 구성 요소를 올바른 순서로 등록하는 
     * 표준 방법입니다.
     * 
     * 각 객체에 ID를 생성하고 할당한 후, 참조 관계를 설정하여 등록합니다.
     * 등록 순서는 다음과 같습니다:
     * 1. Bullet (불렛)
     * 2. CharPr (글자 모양)
     * 3. ParaPr (문단 모양) - 불렛 참조 설정
     * 4. Style (스타일) - 글자 모양 및 문단 모양 참조 설정
     * 
     * @param hwpxFile HWPX 파일
     * @param result StyleBuilder에서 생성한 StyleResult 인스턴스
     * @return 등록된 스타일
     */
    public static Style registerResult(HWPXFile hwpxFile, StyleResult result) {
        if (hwpxFile == null || result == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 StyleResult가 null입니다");
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 1. 불렛 등록 (있는 경우) - ID 생성 및 할당
        String bulletId = null;
        if (result.getBullet() != null) {
            Bullet bullet = result.getBullet().clone();
            
            // 불렛 ID 생성
            int maxBulletId = 0;
            if (refList.bullets() != null) {
                maxBulletId = IdUtils.getMaxID(refList.bullets().items(), item -> item.id());
            } else {
                refList.createBullets();
            }
            bulletId = String.valueOf(maxBulletId + 1);
            
            // ID 설정
            bullet.id(bulletId);
            
            // 불렛 등록
            registerBullet(hwpxFile, bullet);
        }
        
        // 2. 글자 모양 등록 (있는 경우) - ID 생성 및 할당
        String charPrId = null;
        if (result.getCharPr() != null) {
            CharPr charPr = result.getCharPr().clone();
            
            // 글자 모양 ID 생성
            int maxCharPrId = 0;
            if (refList.charProperties() != null) {
                maxCharPrId = IdUtils.getMaxID(refList.charProperties().items(), item -> item.id());
            } else {
                refList.createCharProperties();
            }
            charPrId = String.valueOf(maxCharPrId + 1);
            
            // ID 설정
            charPr.id(charPrId);
            
            // 글자 모양 등록
            registerCharPr(hwpxFile, charPr);
        }
        
        // 3. 문단 모양 등록 (있는 경우) - ID 생성 및 할당, 불렛 참조 업데이트
        String paraPrId = null;
        if (result.getParaPr() != null) {
            ParaPr paraPr = result.getParaPr().clone();
            
            // 문단 모양 ID 생성
            int maxParaPrId = 0;
            if (refList.paraProperties() != null) {
                maxParaPrId = IdUtils.getMaxID(refList.paraProperties().items(), item -> item.id());
            } else {
                refList.createParaProperties();
            }
            paraPrId = String.valueOf(maxParaPrId + 1);
            
            // ID 설정
            paraPr.id(paraPrId);
            
            // 불렛 참조 업데이트
            if (result.hasBulletReference() && bulletId != null) {
                // 불변 조건: 불렛이 있으면 반드시 문단 모양은 해당 불렛을 참조해야 함
                if (paraPr.heading() == null) {
                    // heading이 없으면 생성
                    paraPr.createHeading();
                    paraPr.heading().typeAnd(ParaHeadingType.BULLET);
                } else if (paraPr.heading().type() != ParaHeadingType.BULLET) {
                    // heading이 있지만 불렛 타입이 아니면 불렛 타입으로 설정
                    paraPr.heading().type(ParaHeadingType.BULLET);
                }
                
                // 항상 불렛 ID 참조 설정
                paraPr.heading().idRef(bulletId);
                
                // 설정 후 확인 (로깅이나 디버깅 목적으로 남겨둘 수 있음)
                if (!bulletId.equals(paraPr.heading().idRef())) {
                    throw new IllegalStateException("불렛 참조 ID를 설정할 수 없습니다. expected: " + bulletId + ", actual: " + paraPr.heading().idRef());
                }
            }
            
            // 문단 모양 등록
            registerParaPr(hwpxFile, paraPr);
        }
        
        // 4. 스타일 등록 - ID 생성 및 할당, 글자 모양 및 문단 모양 참조 업데이트
        Style style = result.getStyle().clone();
        
        // 스타일 ID 생성
        int maxStyleId = 0;
        if (refList.styles() != null) {
            maxStyleId = IdUtils.getMaxID(refList.styles().items(), item -> item.id());
        }
        String styleId = String.valueOf(maxStyleId + 1);
        
        // ID 설정
        style.id(styleId);
        
        // 참조 업데이트 - 중요: 여기서 생성된 실제 ID로 참조 업데이트
        if (result.hasCharPrReference() && charPrId != null) {
            style.charPrIDRef(charPrId);
        }
        if (result.hasParaPrReference() && paraPrId != null) {
            style.paraPrIDRef(paraPrId);
        }
        
        // 스타일 등록 - 참조가 이미 업데이트된 상태이므로 단순히 등록만 하고 반환
        refList.styles().add(style);
        
        return style;
    }
    
    /**
     * 스타일 객체를 HWPX 파일에 등록합니다.
     * 임시 ID를 가진 스타일 객체의 경우 고유한 실제 ID로 변경하여 등록합니다.
     * 참조하는 글자 모양과 문단 모양 ID도 함께 업데이트합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param style 등록할 스타일
     * @return 등록된 스타일
     */
    public static Style registerStyle(HWPXFile hwpxFile, Style style) {
        if (hwpxFile == null || style == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 스타일이 null입니다");
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 스타일이 이미 등록되어 있는지 확인 (이름으로 체크)
        String styleName = style.name();
        if (styleName != null) {
            for (Style existingStyle : refList.styles().items()) {
                if (styleName.equals(existingStyle.name())) {
                    return existingStyle; // 이미 등록된 스타일이면 기존 스타일 반환
                }
            }
        }
        
        // 임시 ID인지 확인하고 실제 ID로 변경
        String styleId = style.id();
        if (styleId == null || styleId.startsWith("temp_")) {
            // 스타일 ID 생성
            int maxStyleId = 0;
            if (refList.styles() != null) {
                maxStyleId = IdUtils.getMaxID(refList.styles().items(), item -> item.id());
            }
            styleId = String.valueOf(maxStyleId + 1);
            
            // ID 설정
            style.id(styleId);
        }
        
        // 임시 글자 모양 ID 참조를 업데이트
        if (style.charPrIDRef() != null && style.charPrIDRef().startsWith("temp_")) {
            String tempCharPrId = style.charPrIDRef();
            
            // 임시 ID에 해당하는 글자 모양 검색
            CharPr tempCharPr = null;
            for (CharPr charPr : refList.charProperties().items()) {
                if (tempCharPrId.equals(charPr.id())) {
                    tempCharPr = charPr;
                    break;
                }
            }
            
            if (tempCharPr != null) {
                // 임시 글자 모양을 등록하여 실제 ID 할당
                CharPr charPrCopy = tempCharPr.clone();
                String newCharPrId = registerCharPr(hwpxFile, charPrCopy);
                style.charPrIDRef(newCharPrId);
            }
        }
        
        // 임시 문단 모양 ID 참조를 업데이트
        if (style.paraPrIDRef() != null && style.paraPrIDRef().startsWith("temp_")) {
            String tempParaPrId = style.paraPrIDRef();
            
            // 임시 ID에 해당하는 문단 모양 검색
            ParaPr tempParaPr = null;
            for (ParaPr paraPr : refList.paraProperties().items()) {
                if (tempParaPrId.equals(paraPr.id())) {
                    tempParaPr = paraPr;
                    break;
                }
            }
            
            if (tempParaPr != null) {
                // 임시 문단 모양을 등록하여 실제 ID 할당
                ParaPr paraPrCopy = tempParaPr.clone();
                String newParaPrId = registerParaPr(hwpxFile, paraPrCopy);
                style.paraPrIDRef(newParaPrId);
            }
        }
        
        // 스타일 객체 추가
        refList.styles().add(style);
        
        return style;
    }
    
    /**
     * 글자 모양 객체를 HWPX 파일에 등록합니다.
     * 임시 ID를 가진 경우 고유한 실제 ID로 변경하여 등록합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param charPr 등록할 글자 모양
     * @return 등록된 글자 모양 ID
     */
    public static String registerCharPr(HWPXFile hwpxFile, CharPr charPr) {
        if (hwpxFile == null || charPr == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 글자 모양이 null입니다");
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 글자 모양 목록이 없으면 생성
        if (refList.charProperties() == null) {
            refList.createCharProperties();
        }
        
        // 임시 ID인지 확인하고 실제 ID로 변경
        String charPrId = charPr.id();
        if (charPrId == null || charPrId.startsWith("temp_")) {
            // 글자 모양 ID 생성
            int maxCharPrId = 0;
            maxCharPrId = IdUtils.getMaxID(refList.charProperties().items(), item -> item.id());
            charPrId = String.valueOf(maxCharPrId + 1);
            
            // ID 설정
            charPr.id(charPrId);
        }
        
        // 글자 모양 객체 추가
        refList.charProperties().add(charPr);
        
        return charPr.id();
    }
    
    /**
     * 문단 모양 객체를 HWPX 파일에 등록합니다.
     * 임시 ID를 가진 경우 고유한 실제 ID로 변경하여 등록합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param paraPr 등록할 문단 모양
     * @return 등록된 문단 모양 ID
     */
    public static String registerParaPr(HWPXFile hwpxFile, ParaPr paraPr) {
        if (hwpxFile == null || paraPr == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 문단 모양이 null입니다");
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 문단 모양 목록이 없으면 생성
        if (refList.paraProperties() == null) {
            refList.createParaProperties();
        }
        
        // 임시 ID인지 확인하고 실제 ID로 변경
        String paraPrId = paraPr.id();
        if (paraPrId == null || paraPrId.startsWith("temp_")) {
            // 문단 모양 ID 생성
            int maxParaPrId = 0;
            maxParaPrId = IdUtils.getMaxID(refList.paraProperties().items(), item -> item.id());
            paraPrId = String.valueOf(maxParaPrId + 1);
            
            // ID 설정
            paraPr.id(paraPrId);
        }
        
        // 문단 모양 객체 추가
        refList.paraProperties().add(paraPr);
        
        return paraPr.id();
    }
    
    /**
     * 불렛 객체를 HWPX 파일에 등록합니다.
     * 임시 ID를 가진 경우 고유한 실제 ID로 변경하여 등록합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param bullet 등록할 불렛
     * @return 등록된 불렛 ID
     */
    public static String registerBullet(HWPXFile hwpxFile, Bullet bullet) {
        if (hwpxFile == null || bullet == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 불렛이 null입니다");
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        
        // 불렛 목록이 없으면 생성
        if (refList.bullets() == null) {
            refList.createBullets();
        }
        
        // 임시 ID인지 확인하고 실제 ID로 변경
        String bulletId = bullet.id();
        if (bulletId == null || bulletId.startsWith("temp_")) {
            // 불렛 ID 생성
            int maxBulletId = 0;
            maxBulletId = IdUtils.getMaxID(refList.bullets().items(), item -> item.id());
            bulletId = String.valueOf(maxBulletId + 1);
            
            // ID 설정
            bullet.id(bulletId);
        }
        
        // 불렛 객체 추가
        refList.bullets().add(bullet);
        
        return bullet.id();
    }
    
    /**
     * ID로 스타일을 찾습니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param styleId 스타일 ID
     * @return 찾은 스타일 객체, 없으면 null
     */
    public static Style findStyleById(HWPXFile hwpxFile, String styleId) {
        if (hwpxFile == null || styleId == null) {
            return null;
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null || refList.styles() == null) {
            return null;
        }
        
        for (Style style : refList.styles().items()) {
            if (styleId.equals(style.id())) {
                return style;
            }
        }
        
        return null;
    }
    
    /**
     * 스타일 이름으로 HWPX 파일에서 스타일을 찾습니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param styleName 찾을 스타일 이름
     * @return 찾은 스타일 객체, 없으면 null 반환
     */
    public static Style findStyleByName(HWPXFile hwpxFile, String styleName) {
        if (hwpxFile == null || styleName == null) {
            return null;
        }
        
        RefList refList = hwpxFile.headerXMLFile().refList();
        if (refList == null || refList.styles() == null) {
            return null;
        }
        
        for (Style style : refList.styles().items()) {
            if (styleName.equals(style.name())) {
                return style;
            }
        }
        
        return null;
    }

    /**
     * 스타일셋 템플릿을 HWPX 파일에 등록하고 등록된 스타일셋을 반환합니다.
     * 
     * @param hwpxFile HWPX 파일
     * @param template 등록할 스타일셋 템플릿
     * @return 등록된 스타일셋
     */
    public static StyleSet registerStyleSet(HWPXFile hwpxFile, StyleSetTemplate template) {
        if (hwpxFile == null || template == null) {
            throw new IllegalArgumentException("HWPX 파일 또는 스타일셋 템플릿이 null입니다");
        }
        
        // 등록된 스타일 맵 생성
        Map<String, Style> registeredStyles = new HashMap<>();
        
        // 모든 스타일 결과 등록
        StyleResult[] results = template.getAllResults();
        for (StyleResult result : results) {
            Style style = registerResult(hwpxFile, result);
            registeredStyles.put(style.name(), style);
        }
        
        // 스타일셋 생성 및 반환
        return new StyleSet(template.getName(), registeredStyles);
    }
} 