# Sejong 리팩토링 계획

## 목표

HWPX 문서 생성을 위한 일관된 고수준 API 제공

## 현재 문제점

1. **과도한 책임**: 일부 클래스가 너무 많은 책임을 가짐
2. **낮은 추상화**: hwpxlib에 직접 의존하는 코드 다수
3. **일관성 부족**: 빌더 패턴이 일관되게 적용되지 않음
4. **문서화 부족**: API 문서화 미흡
5. **사용자 관점 부족**: 라이브러리 사용자 경험이 고려되지 않음

## 리팩토링 접근 방식

### 1. 핵심 인터페이스 설계

```
SejongDocument (인터페이스)
└── 문서 생성/조작의 최상위 인터페이스
    └── build(): HWPXFile
    └── save(String path): void
```

### 2. 컴포넌트 구조화

```
com.hotbros.sejong
├── core
│   ├── SejongDocument (인터페이스)
│   ├── SejongBuilder (인터페이스)
│   └── DefaultSejongDocument (구현체)
├── component
│   ├── style
│   │   ├── Style (인터페이스)
│   │   ├── StyleBuilder (인터페이스)
│   │   └── DefaultStyleBuilder (구현체)
│   ├── paragraph
│   │   ├── Paragraph (인터페이스)
│   │   ├── ParagraphBuilder (인터페이스)
│   │   └── DefaultParagraphBuilder (구현체)
│   ├── numbering
│   │   ├── Numbering (인터페이스)
│   │   ├── NumberingBuilder (인터페이스)
│   │   └── DefaultNumberingBuilder (구현체)
│   └── table
│       ├── Table (인터페이스)
│       ├── TableBuilder (인터페이스)
│       └── DefaultTableBuilder (구현체)
└── util
    ├── HWPXWriter
    ├── IdGenerator
    └── HWPXAdapter (hwpxlib 연동)
```

### 3. 일관된 빌더 패턴 적용

모든 빌더는 다음 인터페이스를 따름:

```java
public interface Builder<T> {
    T build();
}
```

구체적인 빌더 클래스:

```java
// 예: 테이블 빌더
public interface TableBuilder extends Builder<Table> {
    TableBuilder rows(int rows);
    TableBuilder columns(int columns);
    TableBuilder withHeader(boolean hasHeader);
    TableBuilder withData(List<List<String>> data);
    TableBuilder withBorder(BorderStyle style);
    // ...
    
    @Override
    Table build();
}
```

### 4. 파사드 패턴을 통한 단순화

사용자가 쉽게 접근할 수 있는 진입점:

```java
public class Sejong {
    public static SejongDocument createDocument() {
        return new DefaultSejongDocument();
    }
    
    public static StyleBuilder createStyle() {
        return new DefaultStyleBuilder();
    }
    
    public static TableBuilder createTable() {
        return new DefaultTableBuilder();
    }
    
    // ...
}
```

### 5. 구현 계획

1. **인터페이스 계층 먼저 설계**
   - 모든 컴포넌트의 인터페이스 정의
   - 서로 간의 관계 정립

2. **핵심 컴포넌트 구현**
   - 문서 생성을 위한 기본 컴포넌트 구현
   - 테스트 케이스 작성

3. **어댑터 계층 구현**
   - hwpxlib 객체와의 변환 로직
   - 의존성 격리

4. **기능별 빌더 구현**
   - 각 컴포넌트별 빌더 구현
   - 일관된 API 유지

5. **예제 및 문서화**
   - 사용 예제 작성
   - JavaDoc 문서화 완성

## 예상 사용 코드

```java
// 문서 생성
SejongDocument document = Sejong.createDocument();

// 스타일 생성
Style headingStyle = Sejong.createStyle()
    .withName("제목")
    .withFontSize(16)
    .withBold(true)
    .withAlignment(Alignment.CENTER)
    .build();

// 문단 생성
Paragraph heading = Sejong.createParagraph()
    .withText("문서 제목")
    .withStyle(headingStyle)
    .build();

// 테이블 생성
Table table = Sejong.createTable()
    .rows(3)
    .columns(3)
    .withHeader(true)
    .withData(Arrays.asList(
        Arrays.asList("헤더1", "헤더2", "헤더3"),
        Arrays.asList("데이터1", "데이터2", "데이터3"),
        Arrays.asList("데이터4", "데이터5", "데이터6")
    ))
    .build();

// 문서에 컴포넌트 추가
document.addParagraph(heading);
document.addTable(table);

// 저장
document.save("output/example.hwpx");
```

## 다음 단계

1. 코어 인터페이스 설계 및 구현
2. 테이블 컴포넌트 리팩토링 (현재 개발 중인 부분)
3. 스타일 및 넘버링 시스템 통합
4. 단위 테스트 구현
5. 예제 코드 및 문서화 작성