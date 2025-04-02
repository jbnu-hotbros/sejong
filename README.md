# Sejong (세종)
한글 문서(.hwpx) 생성을 위한 스타일 시스템 라이브러리

## 핵심 아키텍처

### 데이터 흐름
```
StyleBuilder → StyleTemplate → StyleSetTemplate → StyleSet → 문서 적용
```

### 구조 다이어그램
```
+------------------+    생성    +------------------+
|   StyleBuilder   | --------> |   StyleTemplate   |
+------------------+           +------------------+
        ^                              |
        |                              v
+------------------+    수집    +------------------+    등록    +------------------+    사용    +----------+
| BasicStyleSet-   | --------> |  StyleService    | --------> |    StyleSet     | --------> |  문서    |
| Template         |           +------------------+           +------------------+           +----------+
+------------------+
```

### 인터페이스 관계
```
┌─ StyleSetTemplate ─┐      ┌─ StyleSet ─┐
│                    │      │            │
│ titleTemplate()    │  →   │ title()    │  → 문서 적용
│ outline1Template() │ 등록  │ outline1() │
│ getAllTemplates()  │      │ getStyle() │
└────────────────────┘      └────────────┘
```

## 기본 사용법
```java
// 1. 스타일셋 템플릿 생성
StyleSetTemplate template = new BasicStyleSetTemplate();

// 2. 스타일셋 등록 및 사용
StyleSet styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, template);

// 3. 문서에 스타일 적용
addParagraph(hwpxFile, styleSet.title(), "제목");
addParagraphWithStyleName(hwpxFile, styleSet, "개요1", "내용");
```

## 제공 템플릿
- **BasicStyleSetTemplate**: 기본 스타일셋 (제목, 개요1-5)
- 커스텀 템플릿: `StyleSetTemplate` 인터페이스 구현