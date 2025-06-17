# Sejong (세종)
한글 문서(.hwpx) 생성을 위한 고급 문서 작성 라이브러리

## 개요
Sejong은 Java 기반의 HWPX (한글 문서) 생성 전용 라이브러리입니다. 복잡한 HWPX 구조를 추상화하여 간단한 API로 전문적인 한글 문서를 생성할 수 있습니다.

## 핵심 기능

### 🎨 **스타일 시스템**
- 미리 정의된 전문적인 스타일 세트
- 테마 지원 (GRAY, BLUE)
- 제목, 본문, 표 등 다양한 스타일 타입

### 📊 **표 생성**
- 헤더와 본문이 다른 스타일을 가진 표
- 캡션 지원
- 자동 테두리 및 배경색 설정
- 다양한 표 템플릿 (메인, 미들, 서브 타이틀 박스)

### 🖼️ **이미지 삽입**
- PNG/JPG 이미지 삽입
- 자동 크기 조정 및 비율 유지
- 캡션 지원
- 위치 및 정렬 설정

### 📝 **텍스트 관리**
- 다양한 레벨의 제목
- 본문 텍스트
- 페이지 브레이크 지원
- 자동 넘버링

### 🎯 **특별 요소**
- 타이틀 박스 (메인, 미들, 서브)
- 테마별 색상 체계
- 글머리 기호 관리

## 아키텍처

### 설계 철학

Sejong은 **복잡한 HWPX 스펙을 추상화**하여 개발자가 **문서의 논리적 구조에만 집중**할 수 있도록 설계되었습니다. 

핵심 설계 원칙:
- **관심사의 분리**: 각 Registry가 독립적인 도메인을 담당
- **의존성 역전**: 고수준 모듈(HWPXBuilder)이 저수준 모듈(Registry들)에 의존하지 않음
- **Factory & Registry 패턴**: 객체 생성과 관리의 중앙화
- **Preset 기반 설정**: 사전 정의된 스타일로 일관성 보장

### 계층형 아키텍처

```
┌─────────────────────────────────────────────────────┐
│                Application Layer                    │
│                  HWPXBuilder                       │
└─────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────┐
│                Domain Layer                         │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐   │
│  │TableBuilder │ │ImageBuilder │ │TitleBuilder │   │
│  └─────────────┘ └─────────────┘ └─────────────┘   │
└─────────────────────────────────────────────────────┘
                          │
┌─────────────────────────────────────────────────────┐
│              Infrastructure Layer                   │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐│
│  │StyleRegistry │ │FontRegistry  │ │BorderFillReg ││
│  └──────────────┘ └──────────────┘ └──────────────┘│
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐│
│  │BulletRegistry│ │IdGenerator   │ │StylePreset   ││
│  └──────────────┘ └──────────────┘ └──────────────┘│
└─────────────────────────────────────────────────────┘
```

### 핵심 컴포넌트 상세

#### 1. **HWPXBuilder (Facade)**
```
역할: 전체 시스템의 진입점이자 오케스트레이터
책임:
  - 하위 컴포넌트들의 생명주기 관리
  - 테마 기반 초기화 프로세스 실행
  - 문서 요소 생성을 위한 통합 API 제공
  - HWPX 파일 최종 조립 및 출력
```

#### 2. **Registry 패턴 (Infrastructure)**
각 Registry는 특정 도메인의 **메타데이터를 중앙 관리**하는 저장소 역할:

```
StyleRegistry    → 문서 스타일 (CharPr, ParaPr, Style 트리플렛)
FontRegistry     → 폰트 정보 및 참조 관계
BorderFillRegistry → 테두리와 배경 스타일
BulletRegistry   → 글머리 기호 및 넘버링
```

**Registry의 핵심 메커니즘:**
- **지연 초기화**: 실제 사용 시점에 HWPX 객체 생성
- **ID 중앙 관리**: IdGenerator를 통한 고유 식별자 할당
- **참조 무결성**: 스타일 간 의존성 자동 해결

#### 3. **Builder 패턴 (Domain)**
복잡한 HWPX 객체 생성을 **단계별로 분해**하여 관리:

```
TableBuilder  → 표 구조, 셀 병합, 캡션 처리
ImageBuilder  → 이미지 삽입, 크기 조정, 위치 설정
TitleBuilder  → 테마별 타이틀 박스 레이아웃
```

### 데이터 흐름 및 생명주기

#### 초기화 단계
```
1. Theme 선택
   ↓
2. BlankFileMaker로 기본 HWPX 구조 생성
   ↓
3. IdGenerator 초기화 (시작 ID 설정)
   ↓
4. Registry들 순차 초기화
   FontRegistry → BulletRegistry → BorderFillRegistry → StyleRegistry
   ↓
5. StylePreset에서 테마별 사전 정의 스타일 로드
   ↓
6. Builder들 초기화 (Registry 참조 주입)
```

#### 문서 생성 단계
```
1. 문서 요소 생성 요청 (예: addTable())
   ↓
2. 해당 Builder가 Registry에서 필요한 스타일 조회
   ↓
3. HWPX 객체 생성 및 속성 설정
   ↓
4. Section에 추가 (첫 번째 문단은 교체, 이후는 추가)
   ↓
5. 참조 관계 업데이트
```

### 스타일 시스템 아키텍처

#### StyleBlock: 스타일의 최소 단위
```
StyleBlock = CharPr + ParaPr + Style
             (문자)   (문단)   (스타일 메타)
```

#### Preset 시스템
```
StylePreset (테마별 구현)
├── titlePreset()     → 제목 스타일
├── bodyPreset()      → 본문 스타일  
├── heading1Preset()  → 개요1 스타일
├── tableHeaderPreset() → 표 헤더 스타일
└── ...
```

**테마별 차별화:**
- **GRAY 테마**: 차분한 회색 계열, 보수적 레이아웃
- **BLUE 테마**: 역동적 파란색 계열, 모던 레이아웃

### ID 관리 전략

```java
IdGenerator: 전역 고유 식별자 관리
├── charPrId: 7부터 시작    (문자 속성)
├── paraPrId: 16부터 시작   (문단 속성)  
├── styleId: 18부터 시작    (스타일)
├── fontId: 2부터 시작      (폰트)
├── borderFillId: 3부터 시작 (테두리/배경)
└── bulletId: 1부터 시작    (글머리기호)
```

**충돌 방지 메커니즘:**
- HWPX 기본 객체들과 겹치지 않는 시작 ID 사용
- 각 도메인별 독립적인 ID 네임스페이스 유지

### 확장성 설계

#### 새로운 스타일 추가
```
1. StylePreset에 새로운 xxxPreset() 메서드 추가
2. getAllPresets()에 등록
3. StyleRegistry가 자동으로 로드 및 관리
```

#### 새로운 문서 요소 추가  
```
1. 전용 Builder 클래스 생성
2. HWPXBuilder에서 해당 Builder 초기화
3. 필요한 Registry 의존성 주입
4. 공개 API 메서드 추가
```

이러한 아키텍처를 통해 Sejong은 **높은 유지보수성**과 **확장 가능성**을 동시에 달성하면서도, **HWPX의 복잡성을 완전히 추상화**하여 개발자 친화적인 API를 제공합니다.

## 사용법

### 기본 설정
```java
// 테마를 선택하여 빌더 생성
HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);
```

### 타이틀 박스 추가
```java
// 메인 타이틀
builder.addTitleBoxMain("프로젝트 진행 현황 보고서");

// 중간 타이틀
builder.addTitleBoxMiddle("Ⅰ", "프로젝트 개요");
```

### 제목과 본문 추가
```java
// 다양한 레벨의 제목
builder.addThemedHeading(1, "1", "프로젝트 목적");
builder.addThemedHeading(2, "세부 내용입니다.");

// 본문 텍스트
builder.addBodyText("본문 내용을 입력합니다.");
```

### 표 추가
```java
List<List<String>> tableData = Arrays.asList(
    Arrays.asList("헤더1", "헤더2", "헤더3"),
    Arrays.asList("데이터1", "데이터2", "데이터3"),
    Arrays.asList("데이터4", "데이터5", "데이터6")
);

// 캡션이 있는 표
builder.addTableWithHeader(tableData, "프로젝트 진행 현황");
```

### 이미지 추가
```java
byte[] imageData = Files.readAllBytes(Paths.get("image.png"));

// 캡션이 있는 이미지
builder.addImage(imageData, 800, 600, "차트 이미지");

// 자동 크기 조정
builder.addImage(imageData);
```

### 파일 저장
```java
HWPXFile hwpxFile = builder.build();
HWPXWriter.toFilepath(hwpxFile, "output/document.hwpx");
```

## 전체 예제
```java
public class DocumentExample {
    public static void main(String[] args) {
        HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);
        
        // 문서 구조 생성
        builder.addTitleBoxMain("2024년 프로젝트 보고서");
        builder.addTitleBoxMiddle("Ⅰ", "개요");
        builder.addThemedHeading(1, "1", "목적");
        builder.addBodyText("이 프로젝트의 목적은...");
        
        // 표 추가
        List<List<String>> data = Arrays.asList(
            Arrays.asList("항목", "진행률", "상태"),
            Arrays.asList("분석", "100%", "완료"),
            Arrays.asList("개발", "80%", "진행중")
        );
        builder.addTableWithHeader(data, "진행 현황");
        
        // 이미지 추가
        byte[] chart = loadChartImage();
        builder.addImage(chart, 600, 400, "진행률 차트");
        
        // 파일 저장
        HWPXFile hwpxFile = builder.build();
        HWPXWriter.toFilepath(hwpxFile, "report.hwpx");
    }
}
```

## 사용 가능한 스타일

### 제목 스타일
- `제목` - 메인 제목
- `내용` - 기본 본문
- `내용 가운데정렬` - 가운데 정렬 본문

### 표 스타일
- `표 헤더` - 표 헤더 셀
- `표 내용` - 표 본문 셀

### 테마별 색상
- **GRAY 테마**: 회색 계열의 전문적인 색상
- **BLUE 테마**: 파란색 계열의 색상

## 예제 파일
- `HWPXBuilderExample.java` - 완전한 문서 생성 예제
- `TableExample.java` - 표 생성 예제
- `TitleTableExample.java` - 타이틀과 표 조합 예제

## 요구사항
- Java 11 이상
- hwpxlib 라이브러리

## 빌드
```bash
./gradlew build
```

## 라이선스
이 프로젝트는 hwpxlib를 기반으로 구축되었습니다.