# Sejong 
한글 파일 생성 및 관리를 위한 패키지


## 스타일 시스템 아키텍처

### 데이터 흐름

```
[1] StyleBuilder ---> StyleResult (임시 ID 포함)
    |
    v
[2] StyleSetTemplate (여러 StyleResult 보유)
    |
    v
[3] StyleService.registerStyleSet(template) ---> HWPX 파일에 등록
    |                                            (실제 ID 할당)
    v
[4] StyleSet (등록된 실제 스타일 객체 보유)
    |
    v
[5] 문서 생성 로직에서 StyleSet 사용
    - styleSet.title()                [정적 호출]
    - styleSet.getStyle("개요1")       [동적 호출]
```


### 다이어그램

```
+------------------+    buildResult()    +------------------+
|   StyleBuilder   | -----------------> |   StyleResult    |
+------------------+                     +------------------+
        ^                                        |
        |                                        |
        | 사용                                    | 포함
        |                                        v
+------------------+    getAllResults()   +------------------+
| BasicStyleSet-   | -----------------> |  StyleService    |
| Template         |                     +------------------+
+------------------+                            |
                                                | registerStyleSet()
                                                v
                               +------------------+    getStyle("개요1")    +-----------+
                               |    StyleSet      | --------------------> | Style     |
                               | (RegisteredSet)  |                        | (개요1)    |
                               +------------------+                        +-----------+
                                        |
                                        | 사용
                                        v
                               +------------------+
                               |  문서 생성 로직    |
                               | (SejongExample)  |
                               +------------------+
```


### 인터페이스 관계
```
┌─ StyleSetTemplate ─┐      ┌─ StyleSet ─┐
│                    │      │            │
│ titleResult()      │      │ title()    │
│ outline1Result()   │  →   │ outline1() │  → 문서 생성에 사용
│ outline2Result()   │등록   │ outline2() │
│ ...                │      │ ...        │
│ getAllResults()    │      │ getStyle() │
└────────────────────┘      └────────────┘
```

### 사용패턴
```
[문서 생성 패턴]
1. HWPXFile 생성 → 2. 템플릿 선택 → 3. 스타일셋 등록 → 4. 스타일 사용 → 5. 저장

// 코드 패턴
HWPXFile hwpxFile = BlankFileMaker.make();
StyleSetTemplate template = new BasicStyleSetTemplate(hwpxFile);
StyleSet styleSet = StyleService.registerStyleSet(hwpxFile, template);
addParagraphWithStyleName(hwpxFile, styleSet, "개요1", "내용");
```