# Sejong 개발 자바 코드북 (개선판)

## 1. 개요 & 사용 기술 스택

### 1.1 프로젝트 개요

- **HWPXBuilder**는 보고서 생성에 필요한 제목 박스, 표, 이미지 등을 손쉽게 삽입할 수 있도록 도와주는 고수준 Builder 클래스입니다.
- 내부적으로는 `BlankFileMaker`를 통해 HWPX 문서의 기본 구조를 생성하고, 각 요소는 XML 기반의 `hwpxlib` 구조체로 구성됩니다.
- 테마 설정은 `ThemeConfig`와 `ThemeContract`를 기반으로 하며, 스타일(Style), 테두리(BorderFill), 글꼴(Font) 등을 일괄적으로 등록하고 관리합니다.

### 1.2 사용 기술 스택

| 분류 | 기술 | 비고 |
| --- | --- | --- |
| JVM | Java 11 | `java.sourceCompatibility = JavaVersion.VERSION_11` 명시됨 |
| XML 처리 | hwpxlib 1.0.5 | `kr.dogfoot:hwpxlib:1.0.5` 명시 |
| 빌드 도구 | Gradle (Kotlin DSL) | `build.gradle.kts` 사용 |
| 패키징 도구 | Maven Publish | `maven-publish` 플러그인 사용, `mavenLocal()` 등록 |

## 2. 전체 아키텍처 및 데이터 흐름

### 2.1 Component Diagram (구성 요소 다이어그램)

- **`HWPXBuilderExample`**
    
    → 엔트리 포인트. `HWPXBuilder`를 사용하여 보고서 생성 테스트 수행
    
    → `main()` 메서드에서 특정 테마 및 내용이 적용된 샘플 보고서를 생성하여 `.hwpx` 파일로 출력.
    
- **`HWPXBuilder`**
    
    → HWPX 문서 생성을 위한 핵심 빌더 클래스.
    
    → 문서 요소 구성, 테마 스타일 적용, 문서 최종 구조화 기능 포함.
    
    → 내부적으로 `BlankFileMaker.make()` 호출로 시작하며, 이후 다양한 `add` 계열 메서드로 문단, 표, 이미지 삽입 .
    
- **`HWPXWriter`**
    
    → `HWPXFile` 객체를 `.hwpx` 확장자의 ZIP 구조로 저장.
    
    → `toFilepath()` 메서드는 최종 HWPX 구조를 물리 파일로 직렬화하여 생성 .
    

---

### 2.2 데이터 흐름 (Data Flow)

1. `new HWPXBuilder(theme)`
    
    → 내부에서 `BlankFileMaker.make()` 호출하여 새 문서 구조 및 빈 Header/Body 생성.
    
    → `HWPXBuilder.headerManager` 및 `bodyManager` 초기화.
    
2. Registry 구조 초기화
    
    → `StyleRegistry`, `BorderFillRegistry`, `BulletRegistry`, `FontRegistry` 등이 `HeaderManager` 내에서 초기화됨.
    
    → 각 프리셋별 고정 ID 매핑 구조로 초기값 등록 및 ID 부여 수행.
    
3. 테마 Preset 적용
    
    → `ThemeConfig`에 정의된 테마 프리셋이 `StylePresetApplier` 등을 통해 `styleRegistry` 등에 일괄 등록됨.
    
    → 이때 테마에 맞는 `StyleAttributeConfig`, `BorderFillAttributeConfig` 등의 값이 적용됨 .
    
4. 문서 요소 추가
    
    → `builder.addTitleBoxMain()`, `addImage()`, `addTableWithHeader()` 등으로 보고서 구성 요소 추가.
    
    → 각 요소는 `bodyManager`를 통해 `section` 내 적절한 위치에 삽입됨.
    
5. 파일 출력
    
    → `builder.build()` 호출 시 내부적으로 `HWPXWriter.toFilepath(filepath)` 실행.
    
    → 결과적으로 ZIP 구조의 `.hwpx` 파일 생성 완료.
    

---

### 2.3 Theme 구조 및 확장

- **Theme Enum 구성**
    
    → 실제 지원 테마: `GRAY`, `BLUE`
    
    → 기본값: `GRAY` (생성자에서 테마 미지정 시)
    
- **Builder 구조 변화**
    
    → 테마에 따라 UI Box 빌더(`TitleBoxMainBuilder`, `TitleBoxMiddleBuilder`)가 달라짐
    
    → 예: `TitleBoxMiddleBuilder` ↔ `TitleBoxMiddleGrayBuilder`
    
- **StylePreset 적용 범위 조절**
    
    → 테마에 따라 적용할 스타일 ID의 범위 및 속성이 달라짐
    
    → 내부 `ThemeContract`를 구현한 `ThemeConfig.createDefaultTheme()`에서 설정됨.
    
- **확장 가능 구조**
    
    → 개발자는 새로운 `ThemeConfig`를 만들어 새로운 테마를 정의할 수 있음.
    
    → `ThemeContract`를 구현하면 `Style`, `BorderFill`, `Font`, `Bullet` 등을 커스터마이징 가능함 .
    

---

## 3. 주요 구성 클래스 정리

### 3.1 Builder Layer

### 3.1.1 `HWPXBuilder` (`com.hotbros.sejong`)

- **역할**: 보고서 전체를 구성하는 핵심 빌더 클래스 (Facade).

- **생성자**:
    - `HWPXBuilder()`: 기본값으로 `Theme.GRAY` 사용
    - `HWPXBuilder(Theme theme)`: 지정된 테마로 초기화

- **주요 Public 메서드**:
    
    **타이틀 박스 관련**:
    - `addTitleBoxMain(String title)`: 메인 타이틀 박스 추가
    - `addTitleBoxMiddle(String number, String title)`: 중간 타이틀 박스 추가
    - `addTitleBoxSub(String number, String title)`: 부제목 타이틀 박스 추가
    
    **문단 및 제목 관련**:
    - `addParagraph(String styleName, String text, boolean pageBreak)`: 지정된 스타일로 문단 추가
    - `addBodyText(String text)`: 본문 스타일로 텍스트 추가
    - `addThemedHeading(int level, String number, String title)`: 테마별 제목 추가 (번호 포함)
    - `addThemedHeading(int level, String title)`: 테마별 제목 추가 (번호 없음)
    
    **표 관련**:
    - `addTableWithHeader(List<List<String>> contents, String captionText)`: 캡션이 있는 표 추가
    - `addTableWithHeader(List<List<String>> contents)`: 기본 표 추가 (캡션 없음)
    
    **이미지 관련**:
    - `addImage(byte[] imageData, int width, int height, String captionText)`: 캡션이 있는 이미지 추가
    - `addImage(byte[] imageData, int width, int height)`: 이미지 추가 (캡션 없음)
    - `addImage(byte[] imageData)`: 기본 크기(400x300)로 이미지 추가
    
    **문서 완성**:
    - `build()`: 최종 `HWPXFile` 객체 반환

- **에러 처리**:
    - `IllegalArgumentException`: 유효하지 않은 스타일명이나 빈 표 데이터 전달 시 발생
    - `RuntimeException`: 이미지 추가 중 내부 오류 발생 시

- **특징**:
    - 사용자 요청에 따라 구성 순서를 유연하게 조합
    - 첫 번째 문단은 교체(`addParaSmart` 내부 로직), 이후 문단은 추가하는 방식
    - 보안 문서 처리를 위한 내부 속성 설정 지원 (예: 임베디드 여부, 객체 ID 고정)

---

### 3.1.2 `TableBuilder`

- **역할**: 표 구조 생성 및 셀, 단락, 텍스트 Run 구성 담당.
- **주요 흐름**:
    1. `buildTable()`: 기본 표(Table) 객체 생성
    2. 내부적으로 셀 객체 생성 및 스타일 적용
    3. 셀 내부 텍스트 작성을 위한 Para/Run 구성
- **기능 예시**:
    - 데이터프레임 기반 표 생성 (`addTableFromDataFrame` 등)
    - 머리글, 본문 구분 구성
    - Cell의 Border/Style 지정

---

### 3.1.3 `ImageBuilder`

- **역할**: HWPX 문서에 이미지 요소 삽입 및 배치 제어
- **주요 기능**:
    - `configurePicture()`: Picture 객체 설정
    - 바이너리 등록 및 `manifest.xml`에 `embedded` 속성 설정
    - `Matrix` 요소 설정 (이동/스케일/회전)
    - 이미지 크기 변환 (`px × 75 = HWPX 단위`)
    - 자동 비율 조정 (가로 400px 고정, 높이 자동 계산)
- **기술적 특징**:
    - 이미지 ID 및 zOrder 제어
    - 좌표계 및 회전 중심점 수동 설정 가능
    - ManifestItem을 통한 이미지 데이터 저장

---

### 3.1.4 `TitleBox Builders` (테마별 상속 구조)

- **대표 클래스**:
    - `TitleBoxMainBuilder`: 메인 타이틀용 (1행 1열)
    - `TitleBoxMiddleBuilder`: 중간 타이틀용
    - `TitleBoxMiddleGrayBuilder`: GRAY 테마용 중간 타이틀
    - `TitleBoxSubBuilder`: 부제목용
- **역할**:
    - 타이틀 박스를 테마별 스타일로 구성
    - 테마에 따라 배경색, 테두리, 정렬, 위치가 달라짐
- **구조 특징**:
    - 공통 기반 클래스를 상속
    - 테마 변경 시 `ThemeContract`에 따라 알맞은 Builder 선택

---

### 3.2 Registry Layer

| Registry 클래스 | 주요 역할 | 특징 |
| --- | --- | --- |
| `StyleRegistry` | 스타일 ID 발급 및 `<refList>` 등록 | `getStyleByName()` 메서드로 스타일 조회 |
| `FontRegistry` | 폰트 등록 및 `getFontByName()` 지원 | 테마별 폰트 설정 |
| `BorderFillRegistry` | 테두리 스타일 (실선, 점선 등) 등록 | `getBorderFillByName()` 메서드로 조회 |
| `BulletRegistry` | 글머리 기호 등록 및 번호 매김 제어 | 자동 번호 생성 지원 |

- **공통 특징**:
    - 내부적으로 `IdGenerator`와 연결되어 고유 ID 부여
    - Header XML의 각 요소(`styles.xml`, `fonts.xml`, etc.)에 자동 연계
    - 스타일 이름(ID)을 기반으로 선언부와 사용처 자동 매핑 가능

---

### 3.3 Preset & Config Layer

### `StylePreset`

- **역할**: 보고서 전체에 사용할 텍스트 스타일 Preset 제공
- **예시 메서드**:
    - `title()`, `subtitle()`, `paragraph()`, `caption()` 등
- **테마별 차이**:
    - 같은 함수라도 `Theme.BLUE`와 `Theme.GRAY`는 `fontSize`, `margin`, `textColor` 등이 다름

---

### `ThemeConfig` / `ThemeContract`

- **ThemeContract**:
    - 모든 테마는 이 인터페이스를 구현해야 함
    - 스타일, 테두리, 글머리 기호, 폰트에 대한 ID 지정 함수 필수
- **ThemeConfig**:
    - 테마 값에 따라 Preset을 구현한 클래스
    - 예: `ThemeConfig.createDefaultTheme()`는 BLUE 테마 반환
- **관련 Config 클래스**:
    - `StyleAttributeConfig`, `BorderFillAttributeConfig`, `BulletAttributeConfig`
    - 각 요소의 색상, 두께, 간격, 정렬 등 속성 지정

---

### 3.4 Utility

### `IdGenerator`

- **역할**: 모든 객체에 고유 ID를 부여하는 시퀀스 관리자
- **특징**:
    - 동일 이름 요청 시 중복 방지
    - 각 Registry에서 공유하여 ID 충돌 없는 구조 생성 가능

### `HWPXObjectFinder`

- **역할**: RefList에서 ID 기반으로 객체 검색
- **주요 메서드**:
    - `findStyleById()`: Style 객체 검색
    - `findParaPrById()`: ParaPr 객체 검색
    - `findCharPrById()`: CharPr 객체 검색

---

### 3.5 Writer

### `HWPXWriter`

- **역할**: `HWPXFile` 객체를 실제 `.hwpx` ZIP 포맷으로 저장

- **주요 메서드**:
    - `toFilepath(HWPXFile hwpxFile, String filepath)`: 파일 경로에 직접 저장
    - `toStream(HWPXFile hwpxFile, OutputStream os)`: OutputStream에 저장
    - `toBytes(HWPXFile hwpxFile)`: 바이트 배열로 변환

- **내부 동작**:
    - `ZipOutputStream` 기반 구현
    - `STORED` 모드 (압축 안 함)로 저장
    - 각 항목(`header.xml`, `body.xml`, `manifest.xml`)의 CRC, size 수동 계산
    - `putIntoZip()` 메서드로 ZIP 엔트리 생성

- **ZIP 구조 생성 순서**:
    1. `mineType()` - MIME 타입 설정
    2. `version_xml()` - 버전 정보
    3. `META_INF_manifest_xml()` - 매니페스트
    4. `META_INF_container_xml()` - 컨테이너 정보
    5. `content_hpf()` - 콘텐츠 정보
    6. `contentFiles()` - 실제 콘텐츠 파일들
    7. `chartFiles()` - 차트 파일들
    8. `etcContainedFile()` - 기타 포함 파일들

---

## 4. 실제 사용 예제

### 4.1 기본 문서 생성

```java
// 1. 빌더 생성 (GRAY 테마)
HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);

// 2. 메인 타이틀 추가
builder.addTitleBoxMain("2024년 Q1 프로젝트 진행 현황 보고서");

// 3. 섹션 제목 추가
builder.addTitleBoxMiddle("Ⅰ", "프로젝트 개요");

// 4. 소제목 및 본문 추가
builder.addThemedHeading(0, "1", "프로젝트 목적");
builder.addThemedHeading(1, "디지털 전환 전략의 핵심 과제입니다.");
builder.addBodyText("세부 내용을 여기에 작성합니다.");

// 5. 문서 완성 및 저장
HWPXFile hwpxFile = builder.build();
HWPXWriter.toFilepath(hwpxFile, "output/report.hwpx");
```

### 4.2 표 추가 예제

```java
// 표 데이터 준비
List<List<String>> tableData = Arrays.asList(
    Arrays.asList("단계", "작업 내용", "담당자", "진행률", "상태"),
    Arrays.asList("1단계", "요구사항 분석", "김기획", "100%", "완료"),
    Arrays.asList("2단계", "시스템 설계", "이설계", "95%", "진행중"),
    Arrays.asList("3단계", "UI/UX 디자인", "박디자인", "85%", "진행중")
);

// 캡션이 있는 표 추가
builder.addTableWithHeader(tableData, "프로젝트 단계별 진행 현황");
```

### 4.3 이미지 추가 예제

```java
// 파일에서 이미지 로드
byte[] imageData = Files.readAllBytes(Paths.get("chart.png"));

// 이미지 추가 (다양한 방법)
builder.addImage(imageData, 675, 311, "프로젝트 진행률 차트");  // 지정 크기 + 캡션
builder.addImage(imageData, 800, 600);                          // 지정 크기만
builder.addImage(imageData);                                    // 기본 크기(400x300)
```

### 4.4 복합 문서 예제

```java
HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);

// 제목 구조
builder.addTitleBoxMain("종합 보고서");
builder.addTitleBoxMiddle("Ⅰ", "현황 분석");
builder.addTitleBoxSub("1", "세부 현황");

// 내용 구성
builder.addThemedHeading(0, "1", "개요");
builder.addThemedHeading(1, "프로젝트 진행 상황입니다.");

// 표 추가
List<List<String>> budgetData = Arrays.asList(
    Arrays.asList("분류", "예산", "집행액", "집행률"),
    Arrays.asList("인건비", "2,400만원", "2,200만원", "91.7%"),
    Arrays.asList("장비비", "500만원", "480만원", "96.0%")
);
builder.addTableWithHeader(budgetData, "예산 집행 현황");

// 이미지 추가
byte[] chartData = loadImageFromFile("budget_chart.png");
builder.addImage(chartData, 600, 400, "예산 집행률 차트");

// 결론
builder.addThemedHeading(0, "2", "결론");
builder.addBodyText("전반적으로 순조로운 진행을 보이고 있습니다.");

// 저장
HWPXFile hwpxFile = builder.build();
HWPXWriter.toFilepath(hwpxFile, "output/comprehensive_report.hwpx");
```

---

## 5. 에러 처리 및 예외 상황

### 5.1 일반적인 예외 상황

```java
try {
    HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);
    
    // 잘못된 스타일명 사용 시
    builder.addParagraph("존재하지않는스타일", "텍스트", false);
    
} catch (IllegalArgumentException e) {
    System.err.println("스타일 오류: " + e.getMessage());
    // 출력: "해당 이름의 스타일을 찾을 수 없습니다: 존재하지않는스타일"
}

try {
    // 빈 표 데이터 전달 시
    builder.addTableWithHeader(Arrays.asList(), "빈 표");
    
} catch (IllegalArgumentException e) {
    System.err.println("표 데이터 오류: " + e.getMessage());
    // 출력: "contents가 비어있거나 올바르지 않습니다."
}

try {
    // 이미지 추가 시 오류
    builder.addImage(null, 400, 300, "캡션");
    
} catch (RuntimeException e) {
    System.err.println("이미지 오류: " + e.getMessage());
    // 출력: "이미지 추가 중 오류가 발생했습니다."
}
```

### 5.2 파일 저장 시 예외 처리

```java
try {
    HWPXFile hwpxFile = builder.build();
    HWPXWriter.toFilepath(hwpxFile, "/invalid/path/report.hwpx");
    
} catch (Exception e) {
    System.err.println("파일 저장 실패: " + e.getMessage());
    e.printStackTrace();
}
```

---

## 6. 빌드 및 실행 방법

### 6.1 Gradle 빌드

```bash
# 프로젝트 빌드
./gradlew build

# 의존성 다운로드
./gradlew dependencies

# 테스트 실행
./gradlew test

# 애플리케이션 실행 (HWPXBuilderExample)
./gradlew run

# JAR 파일 생성
./gradlew jar
```

### 6.2 IDE에서 실행

- **메인 클래스**: `com.hotbros.sejong.example.HWPXBuilderExample`
- **출력 디렉토리**: `output/` (자동 생성됨)
- **생성 파일**: `2024_Q1_디지털플랫폼_진행현황보고.hwpx`

### 6.3 의존성 관리

```kotlin
// build.gradle.kts
dependencies {
    implementation("kr.dogfoot:hwpxlib:1.0.5")  // 핵심 HWPX 처리 라이브러리
    testImplementation("junit:junit:4.11")      // 단위 테스트
}
```

### 6.4 Maven Local 설치

```bash
# 로컬 Maven 저장소에 설치
./gradlew publishToMavenLocal

# 다른 프로젝트에서 사용 시
implementation("com.hotbros:sejong:0.1.1")
```

---

## 7. 목적에 따른 기능 확장 가이드

### 7.1 새로운 테마(Theme) 추가

현재 지원되는 테마는 `GRAY`와 `BLUE`입니다. 새로운 테마를 추가하려면:

1. `Theme` Enum에 새 항목 추가
```java
public enum Theme {
    GRAY,
    BLUE,
    GREEN  // 새 테마 추가
}
```

2. `StylePreset`에서 테마별 스타일 정의
```java
public Style createTitleStyle(Theme theme) {
    switch (theme) {
        case GREEN:
            return createGreenTitleStyle();
        // 기존 케이스들...
    }
}
```

3. 테마별 TitleBox Builder 구현
```java
public class TitleBoxMiddleGreenBuilder extends TitleBoxMiddleBuilder {
    // GREEN 테마 전용 구현
}
```

---

### 7.2 새로운 BorderFill 스타일 추가

```java
// BorderFillRegistry에 새 스타일 등록
BorderFill customBorder = createCustomBorderFill();
borderFillRegistry.addBorderFill("CUSTOM_BORDER", customBorder);

// 사용 시
String customBorderId = borderFillRegistry.getBorderFillByName("CUSTOM_BORDER").id();
```

---

### 7.3 새로운 스타일 추가

```java
// StylePreset에서 새 스타일 정의
public Style createCustomStyle() {
    Style style = new Style();
    style.id("CUSTOM_STYLE");
    // 스타일 속성 설정...
    return style;
}

// 사용 시
builder.addParagraph("CUSTOM_STYLE", "커스텀 스타일 텍스트", false);
```

---

## 8. 실행 방식 (완전한 예제)

```java
public class ComprehensiveExample {
    public static void main(String[] args) {
        try {
            // 1. 빌더 생성
            HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);
            
            // 2. 문서 구조 생성
            builder.addTitleBoxMain("종합 프로젝트 보고서");
            builder.addTitleBoxMiddle("Ⅰ", "프로젝트 현황");
            
            // 3. 내용 추가
            builder.addThemedHeading(0, "1", "개요");
            builder.addThemedHeading(1, "프로젝트 진행 상황입니다.");
            builder.addBodyText("세부 내용을 작성합니다.");
            
            // 4. 표 추가
            List<List<String>> data = Arrays.asList(
                Arrays.asList("항목", "내용", "상태"),
                Arrays.asList("개발", "80% 완료", "진행중"),
                Arrays.asList("테스트", "60% 완료", "진행중")
            );
            builder.addTableWithHeader(data, "진행 현황표");
            
            // 5. 이미지 추가 (파일에서 로드)
            byte[] imageData = loadImageFromFile("chart.png");
            builder.addImage(imageData, 600, 400, "진행률 차트");
            
            // 6. 문서 완성
            HWPXFile hwpxFile = builder.build();
            
            // 7. 파일 저장
            String outputDir = "output";
            new File(outputDir).mkdirs();
            File outputFile = new File(outputDir, "comprehensive_report.hwpx");
            
            HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
            System.out.println("보고서 생성 완료: " + outputFile.getAbsolutePath());
            
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static byte[] loadImageFromFile(String fileName) throws IOException {
        Path imagePath = Paths.get("images/" + fileName);
        if (!Files.exists(imagePath)) {
            throw new IOException("이미지 파일을 찾을 수 없습니다: " + imagePath);
        }
        return Files.readAllBytes(imagePath);
    }
}
```
