package com.hotbros.sejong.numbering;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.numbering.ParaHead;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;

import java.util.Map;
import java.util.HashMap;

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint NO_EXTERNAL_NUMBERING
 * @Description 외부에서 생성된 넘버링 객체를 주입받지 않음
 * @Reason 
 *   - 모든 넘버링은 기본 템플릿에서 시작
 *   - 외부 넘버링의 상태나 구조를 신뢰할 수 없음
 *   - 일관된 넘버링 생성 방식 보장
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint NO_RECORD_TYPES
 * @Description record 타입 사용하지 않음
 * @Reason 
 *   - 일반 클래스로도 충분한 기능 구현 가능
 *   - 코드 일관성 유지
 *   - Java 14 이전 버전과의 호환성
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint MINIMIZE_CLASS_CREATION
 * @Description 역할이 작은 경우 새로운 클래스나 파일을 생성하지 않음
 * @Reason 
 *   - 불필요한 코드 복잡도 증가 방지
 *   - 파일 구조의 단순성 유지
 *   - 유지보수 비용 최소화
 *   - 작은 역할의 클래스는 내부 클래스로 구현 권장
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint FIXED_PARAHEAD_COUNT
 * @Description 넘버링은 항상 7개의 ParaHead 보유
 * @Reason HWPX 표준 구조 준수 및 YAGNI 원칙 적용
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint MULTI_LEVEL_SUPPORT
 * @Description 하나의 넘버링에서 1-7 레벨의 모든 ParaHead를 독립적으로 설정 가능
 * @Reason 
 *   - 문서 구조의 일관성 유지
 *   - 복잡한 문서 구조 지원 필요
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint VALUE_REUSE
 * @Description 값이 제시되지 않은 속성은 clone한 대상의 기존 값 유지
 * @Reason 
 *   - 불필요한 값 재설정 방지
 *   - 기존 설정 보존 필요
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint SELECTIVE_LEVEL_MODIFICATION
 * @Description 사용자가 원하는 레벨만 선택적으로 수정 가능 (2^7가지 조합)
 * @Reason 
 *   - 문서 구조의 유연성 확보
 *   - 불필요한 레벨 수정 방지
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint FIXED_ID_AND_STRUCTURE
 * @Description 
 *   - Numbering의 ID는 "1"로 고정
 *   - 7개의 ParaHead 구조 유지
 *   - 레벨은 1-7 범위로 제한
 * @Reason 
 *   - HWPX 표준 호환성 보장
 *   - 문서 구조 일관성 유지
 *   - YAGNI 원칙 준수
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint FIXED_NUMBERING_ID
 * @Description 모든 넘버링은 ID "1"을 사용해야 함
 * @Reason HWPX 표준 호환성 보장
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint VALID_LEVEL_RANGE
 * @Description 레벨은 1-7 범위만 허용
 * @Reason HWPX 표준 구조 준수
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint BASE_NUMBERING_CLONE
 * @Description 모든 넘버링은 BlankFileMaker가 생성한 기본 넘버링을 클론해서 사용
 * @Reason 
 *   - 직접 생성 금지
 *   - 표준 호환성 보장
 * @Status ACTIVE
 */

/**
 * !!! HWPX 제약사항 !!!
 * @Constraint METHOD_NAMING
 * @Description 메서드 이름은 의도를 명확히 표현
 * @Reason 
 *   - 사용자 실수 방지
 *   - 코드 가독성 향상
 * @Status ACTIVE
 */

public class NumberingFactory {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 7;

    private static final HWPXFile defaultHwpxFile;
    private static final Numbering defaultNumbering;

    static {
        try {
            defaultHwpxFile = BlankFileMaker.make();
            defaultNumbering = defaultHwpxFile.headerXMLFile().refList().numberings().get(0);
        } catch (Exception e) {
            throw new RuntimeException("기본 HWPX 파일 생성 실패", e);
        }
    }

    private final Numbering numbering;
    private final Map<Integer, LevelData> settings;

    private NumberingFactory() {
        this.numbering = defaultNumbering.clone();
        this.settings = new HashMap<>();
    }

    public static NumberingFactory create() {
        return new NumberingFactory();
    }

    public LevelDirector level(int level) {
        if (level < MIN_LEVEL || level > MAX_LEVEL) {
            throw new IllegalArgumentException(
                String.format("레벨은 %d-%d 범위여야 합니다. (입력값: %d)", 
                MIN_LEVEL, MAX_LEVEL, level)
            );
        }
        return new LevelDirector(this, level);
    }

    public Numbering build() {
        settings.forEach((level, setting) -> {
            ParaHead paraHead = numbering.getParaHead(level - 1);
            if (setting.getFormat() != null) paraHead.numFormat(setting.getFormat());
            if (setting.getText() != null) paraHead.text(setting.getText());
            if (setting.getStart() != null) paraHead.start(setting.getStart());
        });
        return numbering;
    }

    void addSetting(int level, LevelData setting) {
        settings.put(level, setting);
    }

    /**
     * 레벨별 설정 데이터를 담는 불변 클래스
     */
    private static class LevelData {
        private final NumberType1 format;
        private final String text;
        private final Integer start;

        private LevelData(NumberType1 format, String text, Integer start) {
            this.format = format;
            this.text = text;
            this.start = start;
        }

        public NumberType1 getFormat() {
            return format;
        }

        public String getText() {
            return text;
        }

        public Integer getStart() {
            return start;
        }
    }

    /**
     * 레벨별 설정을 담당하는 Director 클래스
     */
    public class LevelDirector {
        private final int level;
        private NumberType1 format;
        private String text;
        private Integer start;

        private LevelDirector(NumberingFactory parent, int level) {
            this.level = level;
        }

        public LevelDirector format(NumberType1 format) {
            this.format = format;
            return this;
        }

        public LevelDirector text(String text) {
            this.text = text;
            return this;
        }

        public LevelDirector start(int start) {
            this.start = start;
            return this;
        }

        public NumberingFactory done() {
            addSetting(level, new LevelData(format, text, start));
            return NumberingFactory.this;
        }
    }
} 