package com.hotbros.sejong.util;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.BulletList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;

import java.util.HashMap;
import java.util.Map;

/**
 * HWPX 문서의 ID를 관리하는 클래스입니다.
 * 글자 모양, 문단 모양, 글머리 기호, 번호 매기기 ID를 생성하고 관리합니다.
 * 캐싱 메커니즘을 통해 동일한 속성을 가진 객체의 ID를 재사용합니다.
 */
public class IdManager {
    // ID 생성용 카운터
    private int nextCharPrId = 1;
    private int nextParaPrId = 1;
    private int nextBulletId = 1;
    private int nextNumberingId = 1;
    
    // 속성 해시 기반 캐싱 맵
    private final Map<String, String> charPrHashToIdMap = new HashMap<>();
    private final Map<String, String> paraPrHashToIdMap = new HashMap<>();
    private final Map<String, String> bulletHashToIdMap = new HashMap<>();
    private final Map<String, String> numberingHashToIdMap = new HashMap<>();
    
    /**
     * 새로운 IdManager 인스턴스를 생성합니다.
     */
    public IdManager() {
        // 기본 생성자
    }
    
    /**
     * 기존 HWPX 파일에서 ID를 스캔하여 초기화합니다.
     * 
     * @param hwpxFile HWPX 파일 객체
     * @return 이 IdManager 인스턴스 (체이닝 지원)
     */
    public IdManager scanIdsFromDocument(HWPXFile hwpxFile) {
        if (hwpxFile == null || hwpxFile.headerXMLFile() == null || 
            hwpxFile.headerXMLFile().refList() == null) {
            return this;
        }
        
        // 글자 모양 ID 스캔
        if (hwpxFile.headerXMLFile().refList().charProperties() != null) {
            for (CharPr charPr : hwpxFile.headerXMLFile().refList().charProperties().items()) {
                if (charPr.id() != null) {
                    try {
                        int idValue = Integer.parseInt(charPr.id());
                        updateMaxCharPrId(idValue);
                        
                        // 속성 해시 계산 및 캐싱
                        String hash = computeCharPrHash(charPr);
                        charPrHashToIdMap.put(hash, charPr.id());
                    } catch (NumberFormatException e) {
                        // ID가 숫자가 아닌 경우 무시
                    }
                }
            }
        }
        
        // 문단 모양 ID 스캔
        if (hwpxFile.headerXMLFile().refList().paraProperties() != null) {
            for (ParaPr paraPr : hwpxFile.headerXMLFile().refList().paraProperties().items()) {
                if (paraPr.id() != null) {
                    try {
                        int idValue = Integer.parseInt(paraPr.id());
                        updateMaxParaPrId(idValue);
                        
                        // 속성 해시 계산 및 캐싱
                        String hash = computeParaPrHash(paraPr);
                        paraPrHashToIdMap.put(hash, paraPr.id());
                    } catch (NumberFormatException e) {
                        // ID가 숫자가 아닌 경우 무시
                    }
                }
            }
        }
        
        // 글머리 기호 ID 스캔
        if (hwpxFile.headerXMLFile().refList().bulletList() != null) {
            for (BulletList bullet : hwpxFile.headerXMLFile().refList().bulletList().items()) {
                if (bullet.id() != null) {
                    try {
                        int idValue = Integer.parseInt(bullet.id());
                        updateMaxBulletId(idValue);
                        
                        // 속성 해시 계산 및 캐싱
                        String hash = computeBulletHash(bullet);
                        bulletHashToIdMap.put(hash, bullet.id());
                    } catch (NumberFormatException e) {
                        // ID가 숫자가 아닌 경우 무시
                    }
                }
            }
        }
        
        // 번호 매기기 ID 스캔
        if (hwpxFile.headerXMLFile().refList().numberingList() != null) {
            for (Numbering numbering : hwpxFile.headerXMLFile().refList().numberingList().items()) {
                if (numbering.id() != null) {
                    try {
                        int idValue = Integer.parseInt(numbering.id());
                        updateMaxNumberingId(idValue);
                        
                        // 속성 해시 계산 및 캐싱
                        String hash = computeNumberingHash(numbering);
                        numberingHashToIdMap.put(hash, numbering.id());
                    } catch (NumberFormatException e) {
                        // ID가 숫자가 아닌 경우 무시
                    }
                }
            }
        }
        
        return this;
    }
    
    /**
     * 글자 모양 속성의 해시값을 계산합니다.
     * @param charPr 글자 모양 객체
     * @return 속성 해시
     */
    public String computeCharPrHash(CharPr charPr) {
        if (charPr == null) return "";
        
        StringBuilder sb = new StringBuilder();
        
        // 주요 속성들 해시에 추가
        if (charPr.height() != null) sb.append("h:").append(charPr.height()).append(";");
        if (charPr.textColor() != null) sb.append("c:").append(charPr.textColor()).append(";");
        if (charPr.bold() != null) sb.append("b:1;");
        if (charPr.italic() != null) sb.append("i:1;");
        if (charPr.underline() != null) sb.append("u:1;");
        
        // 글꼴 정보 추가
        if (charPr.fontRef() != null) {
            if (charPr.fontRef().hangul() != null) sb.append("fh:").append(charPr.fontRef().hangul()).append(";");
            if (charPr.fontRef().latin() != null) sb.append("fl:").append(charPr.fontRef().latin()).append(";");
            if (charPr.fontRef().hanja() != null) sb.append("fj:").append(charPr.fontRef().hanja()).append(";");
        }
        
        return sb.toString();
    }
    
    /**
     * 문단 모양 속성의 해시값을 계산합니다.
     * @param paraPr 문단 모양 객체
     * @return 속성 해시
     */
    public String computeParaPrHash(ParaPr paraPr) {
        if (paraPr == null) return "";
        
        StringBuilder sb = new StringBuilder();
        
        // 주요 속성들 해시에 추가
        if (paraPr.align() != null) sb.append("a:").append(paraPr.align()).append(";");
        if (paraPr.margin() != null) {
            if (paraPr.margin().left() != null) sb.append("ml:").append(paraPr.margin().left()).append(";");
            if (paraPr.margin().right() != null) sb.append("mr:").append(paraPr.margin().right()).append(";");
            if (paraPr.margin().top() != null) sb.append("mt:").append(paraPr.margin().top()).append(";");
            if (paraPr.margin().bottom() != null) sb.append("mb:").append(paraPr.margin().bottom()).append(";");
        }
        if (paraPr.lineSpacing() != null) {
            if (paraPr.lineSpacing().type() != null) sb.append("lst:").append(paraPr.lineSpacing().type()).append(";");
            if (paraPr.lineSpacing().value() != null) sb.append("lsv:").append(paraPr.lineSpacing().value()).append(";");
        }
        
        return sb.toString();
    }
    
    /**
     * 글머리 기호 속성의 해시값을 계산합니다.
     * @param bullet 글머리 기호 객체
     * @return 속성 해시
     */
    public String computeBulletHash(BulletList bullet) {
        if (bullet == null) return "";
        
        StringBuilder sb = new StringBuilder();
        
        // 주요 속성들 해시에 추가
        if (bullet.bulletChar() != null) sb.append("bc:").append(bullet.bulletChar()).append(";");
        if (bullet.bulletImage() != null) sb.append("bi:").append(bullet.bulletImage()).append(";");
        
        return sb.toString();
    }
    
    /**
     * 번호 매기기 속성의 해시값을 계산합니다.
     * @param numbering 번호 매기기 객체
     * @return 속성 해시
     */
    public String computeNumberingHash(Numbering numbering) {
        if (numbering == null) return "";
        
        StringBuilder sb = new StringBuilder();
        
        // 번호 매기기 속성 추가
        // 실제 속성에 맞게 구현 필요
        sb.append("n:").append(numbering.toString().hashCode()).append(";");
        
        return sb.toString();
    }
    
    /**
     * 글자 모양 ID를 생성합니다.
     * 동일한 속성을 가진 글자 모양이 이미 있으면 해당 ID를 반환합니다.
     * 
     * @param charPr 글자 모양 객체 (캐싱 목적)
     * @return 생성된 글자 모양 ID
     */
    public String generateCharPrId(CharPr charPr) {
        if (charPr != null) {
            String hash = computeCharPrHash(charPr);
            String existingId = charPrHashToIdMap.get(hash);
            
            if (existingId != null) {
                return existingId;
            }
            
            String newId = String.valueOf(nextCharPrId++);
            charPrHashToIdMap.put(hash, newId);
            return newId;
        }
        
        return String.valueOf(nextCharPrId++);
    }
    
    /**
     * 글자 모양 ID를 생성합니다. (해시 기반)
     * 
     * @param charPrHash 글자 모양 속성 해시
     * @return 생성된 글자 모양 ID
     */
    public String generateCharPrIdFromHash(String charPrHash) {
        if (charPrHash != null && !charPrHash.isEmpty()) {
            String existingId = charPrHashToIdMap.get(charPrHash);
            
            if (existingId != null) {
                return existingId;
            }
            
            String newId = String.valueOf(nextCharPrId++);
            charPrHashToIdMap.put(charPrHash, newId);
            return newId;
        }
        
        return String.valueOf(nextCharPrId++);
    }
    
    /**
     * 글자 모양 ID를 생성합니다. (캐싱 없음)
     * 
     * @return 생성된 글자 모양 ID
     */
    public String generateCharPrId() {
        return String.valueOf(nextCharPrId++);
    }
    
    /**
     * 문단 모양 ID를 생성합니다.
     * 동일한 속성을 가진 문단 모양이 이미 있으면 해당 ID를 반환합니다.
     * 
     * @param paraPr 문단 모양 객체 (캐싱 목적)
     * @return 생성된 문단 모양 ID
     */
    public String generateParaPrId(ParaPr paraPr) {
        if (paraPr != null) {
            String hash = computeParaPrHash(paraPr);
            String existingId = paraPrHashToIdMap.get(hash);
            
            if (existingId != null) {
                return existingId;
            }
            
            String newId = String.valueOf(nextParaPrId++);
            paraPrHashToIdMap.put(hash, newId);
            return newId;
        }
        
        return String.valueOf(nextParaPrId++);
    }
    
    /**
     * 문단 모양 ID를 생성합니다. (해시 기반)
     * 
     * @param paraPrHash 문단 모양 속성 해시
     * @return 생성된 문단 모양 ID
     */
    public String generateParaPrIdFromHash(String paraPrHash) {
        if (paraPrHash != null && !paraPrHash.isEmpty()) {
            String existingId = paraPrHashToIdMap.get(paraPrHash);
            
            if (existingId != null) {
                return existingId;
            }
            
            String newId = String.valueOf(nextParaPrId++);
            paraPrHashToIdMap.put(paraPrHash, newId);
            return newId;
        }
        
        return String.valueOf(nextParaPrId++);
    }
    
    /**
     * 문단 모양 ID를 생성합니다. (캐싱 없음)
     * 
     * @return 생성된 문단 모양 ID
     */
    public String generateParaPrId() {
        return String.valueOf(nextParaPrId++);
    }
    
    /**
     * 글머리 기호 ID를 생성합니다.
     * 
     * @return 생성된 글머리 기호 ID
     */
    public String generateBulletId() {
        return String.valueOf(nextBulletId++);
    }
    
    /**
     * 번호 매기기 ID를 생성합니다.
     * 
     * @return 생성된 번호 매기기 ID
     */
    public String generateNumberingId() {
        return String.valueOf(nextNumberingId++);
    }
    
    /**
     * 최대 글자 모양 ID를 업데이트합니다.
     * 
     * @param maxId 최대 ID 값
     */
    public void updateMaxCharPrId(int maxId) {
        nextCharPrId = Math.max(nextCharPrId, maxId + 1);
    }
    
    /**
     * 최대 문단 모양 ID를 업데이트합니다.
     * 
     * @param maxId 최대 ID 값
     */
    public void updateMaxParaPrId(int maxId) {
        nextParaPrId = Math.max(nextParaPrId, maxId + 1);
    }
    
    /**
     * 최대 글머리 기호 ID를 업데이트합니다.
     * 
     * @param maxId 최대 ID 값
     */
    public void updateMaxBulletId(int maxId) {
        nextBulletId = Math.max(nextBulletId, maxId + 1);
    }
    
    /**
     * 최대 번호 매기기 ID를 업데이트합니다.
     * 
     * @param maxId 최대 ID 값
     */
    public void updateMaxNumberingId(int maxId) {
        nextNumberingId = Math.max(nextNumberingId, maxId + 1);
    }
    
    /**
     * 캐시된 ID 맵을 초기화합니다.
     */
    public void clearCache() {
        charPrHashToIdMap.clear();
        paraPrHashToIdMap.clear();
        bulletHashToIdMap.clear();
        numberingHashToIdMap.clear();
    }
} 