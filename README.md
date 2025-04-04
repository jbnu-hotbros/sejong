# Sejong (세종)
한글 문서(.hwpx) 생성을 위한 스타일과 넘버링 시스템 라이브러리

## 핵심 아키텍처

### 데이터 흐름
```
스타일: StyleSetTemplate → StyleSet → 문서 적용
넘버링: NumberingFactory → NumberingRegistry → 문서 적용
```

### 구조 다이어그램
```
+------------------+    등록    +------------------+
| BasicStyleSet-   | --------> |  StyleService    |
| Template         |           +------------------+
+------------------+                    |
                                       v
+------------------+    등록    +------------------+    사용    +----------+
|  NumberingFactory| --------> | NumberingService | --------> |  문서    |
+------------------+           +------------------+           +----------+
```

### 인터페이스 관계
```
┌─ StyleSetTemplate ─┐      ┌─ StyleSet ─┐
│                    │      │            │
│ titleTemplate()    │  →   │ getStyle() │  → 문서 적용
│ outline1Template() │ 등록  │            │
│ getAllTemplates()  │      │            │
└────────────────────┘      └────────────┘
```

## 기본 사용법
```java
// 1. 스타일셋 설정
StyleSetTemplate template = new BasicStyleSetTemplate();
StyleSet styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, template);

// 2. 넘버링 설정
Numbering outline = NumberingFactory.create()
    .level(1)
        .format(NumberType1.DIGIT)
        .text("^1.")
        .start(1)
        .done()
    .build();
    
NumberingService.apply(outline);

// 3. 문서에 스타일과 넘버링 적용
addParagraph(hwpxFile, styleSet.getStyle("제목"), "제목");
addParagraph(hwpxFile, styleSet.getStyle("개요1"), "내용");
```

## 제공 기능
- **스타일**: 기본 스타일셋 (제목, 개요1-5)
- **넘버링**: 다중 레벨 자동 넘버링 지원