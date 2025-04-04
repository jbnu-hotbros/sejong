package com.hotbros.sejong.numbering;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint FIXED_NUMBERING_ID
 * @Description 모든 넘버링의 ID는 "1"로 고정
 * @Reason 
 *   - 단일 넘버링 정책 유지
 *   - 일관된 문서 구조 보장
 *   - 복잡성 감소
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint NO_MULTIPLE_NUMBERINGS
 * @Description ID가 "1"이 아닌 넘버링은 허용하지 않음
 * @Reason 
 *   - 문서 일관성 유지
 *   - 잘못된 넘버링 적용 방지
 *   - 단일 넘버링 원칙 준수
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint FIXED_REMOVAL_ID
 * @Description 제거 대상 넘버링의 ID도 항상 "1"
 * @Reason 
 *   - 단일 넘버링 정책과의 일관성
 *   - 잘못된 넘버링 제거 방지
 * @Status ACTIVE
 */

/**
 * 넘버링을 레퍼런스 목록에 등록하기 위한 서비스 클래스
 * 특정 RefList에 대한 넘버링 작업을 전담하여 처리합니다.
 */
public class NumberingService {
    private static final String FIXED_NUMBERING_ID = "1";
    private final RefList refList;
    
    /**
     * 생성자
     * 
     * @param refList 넘버링을 관리할 레퍼런스 목록
     * @throws IllegalArgumentException refList가 null인 경우
     */
    public NumberingService(RefList refList) {
        if (refList == null) {
            throw new IllegalArgumentException("레퍼런스 목록이 null입니다");
        }
        this.refList = refList;
    }
    
    /**
     * 지정된 넘버링을 레퍼런스 목록에 적용합니다.
     * ID가 "1"인 넘버링만 허용되며, 기존 넘버링은 대체됩니다.
     * 
     * @param numbering 적용할 넘버링
     * @throws IllegalArgumentException numbering이 null이거나 ID가 "1"이 아닌 경우
     */
    public void apply(Numbering numbering) {
        validateNumbering(numbering);
        
        // 기존 넘버링 제거 (ID가 항상 "1"이므로 첫 번째 항목만 확인)
        if (refList.numberings().count() > 0) {
            refList.numberings().remove(0);
        }
        
        // 새 넘버링 추가 (복제본 추가)
        refList.numberings().add(numbering.clone());
    }

    private void validateNumbering(Numbering numbering) {
        if (numbering == null) {
            throw new IllegalArgumentException("넘버링이 null입니다");
        }
        if (!FIXED_NUMBERING_ID.equals(numbering.id())) {
            throw new IllegalArgumentException(
                String.format("넘버링 ID는 반드시 '%s'여야 합니다. (입력값: '%s')", 
                FIXED_NUMBERING_ID, numbering.id())
            );
        }
    }
} 