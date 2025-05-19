package com.hotbros.sejong.style;

import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.ParaPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.CharPr;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.parapr.Align;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.StyleType;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.SymMarkSort;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign2;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.VerticalAlign1;

import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * HWPX 파일 내의 스타일을 간단하게 복사하고 수정하는 클래스.
 */
public class MyStyle {
    // Style 속성 핸들러 맵
    private static final Map<String, BiConsumer<Style, Object>> STYLE_ATTRIBUTE_HANDLERS = new HashMap<>();
    static {
        STYLE_ATTRIBUTE_HANDLERS.put("name", (style, value) -> style.nameAnd(ensureString(value, "name")));
        STYLE_ATTRIBUTE_HANDLERS.put("engname", (style, value) -> style.engNameAnd(ensureString(value, "engName")));
        STYLE_ATTRIBUTE_HANDLERS.put("type", (style, value) -> style.typeAnd(ensureEnum(value, StyleType.class, "type")));
        STYLE_ATTRIBUTE_HANDLERS.put("parapridref", (style, value) -> style.paraPrIDRefAnd(ensureString(value, "paraPrIDRef")));
        STYLE_ATTRIBUTE_HANDLERS.put("charpridref", (style, value) -> style.charPrIDRefAnd(ensureString(value, "charPrIDRef")));
        STYLE_ATTRIBUTE_HANDLERS.put("nextstyleidref", (style, value) -> style.nextStyleIDRefAnd(ensureString(value, "nextStyleIDRef")));
        STYLE_ATTRIBUTE_HANDLERS.put("langid", (style, value) -> style.langIDAnd(ensureString(value, "langID")));
        STYLE_ATTRIBUTE_HANDLERS.put("lockform", (style, value) -> style.lockFormAnd(ensureBoolean(value, "lockForm")));
    }

    // Align 속성 핸들러 맵
    private static final Map<String, BiConsumer<Align, Object>> ALIGN_ATTRIBUTE_HANDLERS = new HashMap<>();
    static {
        ALIGN_ATTRIBUTE_HANDLERS.put("horizontal", (align, value) -> align.horizontalAnd(ensureEnum(value, HorizontalAlign2.class, "horizontal")));
        ALIGN_ATTRIBUTE_HANDLERS.put("vertical", (align, value) -> align.verticalAnd(ensureEnum(value, VerticalAlign1.class, "vertical")));
    }

    // ParaPr 속성 핸들러 맵
    private static final Map<String, BiConsumer<ParaPr, Object>> PARAPR_ATTRIBUTE_HANDLERS = new HashMap<>();
    static {
        PARAPR_ATTRIBUTE_HANDLERS.put("tabpridref", (paraPr, value) -> paraPr.tabPrIDRefAnd(ensureString(value, "tabPrIDRef")));
        PARAPR_ATTRIBUTE_HANDLERS.put("condense", (paraPr, value) -> paraPr.condenseAnd(ensureByte(value, "condense")));
        PARAPR_ATTRIBUTE_HANDLERS.put("fontlineheight", (paraPr, value) -> paraPr.fontLineHeightAnd(ensureBoolean(value, "fontLineHeight")));
        PARAPR_ATTRIBUTE_HANDLERS.put("snaptogrid", (paraPr, value) -> paraPr.snapToGridAnd(ensureBoolean(value, "snapToGrid")));
        PARAPR_ATTRIBUTE_HANDLERS.put("suppresslinenumbers", (paraPr, value) -> paraPr.suppressLineNumbersAnd(ensureBoolean(value, "suppressLineNumbers")));
        PARAPR_ATTRIBUTE_HANDLERS.put("checked", (paraPr, value) -> paraPr.checkedAnd(ensureBoolean(value, "checked")));
        PARAPR_ATTRIBUTE_HANDLERS.put("align", (paraPr, value) -> {
            if (!(value instanceof Map)) {
                throw new IllegalArgumentException("Attribute 'align' must be a Map of align attributes.");
            }
            Align alignObj = paraPr.align();
            if (alignObj == null) {
                paraPr.createAlign();
                alignObj = paraPr.align();
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> alignAttributes = (Map<String, Object>) value;
            applyAttributes(alignObj, alignAttributes, ALIGN_ATTRIBUTE_HANDLERS, "Align");
        });
        // TODO: heading, margin 등 다른 복합 객체 핸들러 추가 필요
    }

    // CharPr 속성 핸들러 맵
    private static final Map<String, BiConsumer<CharPr, Object>> CHARPR_ATTRIBUTE_HANDLERS = new HashMap<>();
    static {
        CHARPR_ATTRIBUTE_HANDLERS.put("height", (charPr, value) -> charPr.heightAnd(ensureInteger(value, "height")));
        CHARPR_ATTRIBUTE_HANDLERS.put("textcolor", (charPr, value) -> charPr.textColorAnd(ensureString(value, "textColor")));
        CHARPR_ATTRIBUTE_HANDLERS.put("shadecolor", (charPr, value) -> charPr.shadeColorAnd(ensureString(value, "shadeColor")));
        CHARPR_ATTRIBUTE_HANDLERS.put("usefontspace", (charPr, value) -> charPr.useFontSpaceAnd(ensureBoolean(value, "useFontSpace")));
        CHARPR_ATTRIBUTE_HANDLERS.put("usekerning", (charPr, value) -> charPr.useKerningAnd(ensureBoolean(value, "useKerning")));
        CHARPR_ATTRIBUTE_HANDLERS.put("symmark", (charPr, value) -> charPr.symMarkAnd(ensureEnum(value, SymMarkSort.class, "symMark")));
        CHARPR_ATTRIBUTE_HANDLERS.put("borderfillidref", (charPr, value) -> charPr.borderFillIDRefAnd(ensureString(value, "borderFillIDRef")));
        CHARPR_ATTRIBUTE_HANDLERS.put("italic", (charPr, value) -> {
            if (ensureBoolean(value, "italic")) charPr.createItalic(); else charPr.removeItalic();
        });
        CHARPR_ATTRIBUTE_HANDLERS.put("bold", (charPr, value) -> {
            if (ensureBoolean(value, "bold")) charPr.createBold(); else charPr.removeBold();
        });
    }

    /**
     * 원본 스타일 객체를 복사하고, 주어진 속성 맵으로 메타데이터를 업데이트하여 반환합니다.
     * 속성 맵에는 'id' 키로 새로운 스타일 ID가 반드시 포함되어야 합니다.
     * RefList에 자동으로 추가하지 않습니다.
     *
     * @param originalStyle 복사할 원본 Style 객체
     * @param attributesToUpdate 업데이트할 속성 맵 (키: 속성명, 값: 속성값). 'id' 포함 필수.
     * @return 복사되고 수정된 새로운 Style 객체
     */
    public static Style copyStyleAndUpdate(Style originalStyle, Map<String, Object> attributesToUpdate) {
        if (originalStyle == null) {
            throw new IllegalArgumentException("원본 Style 객체(originalStyle)는 null일 수 없습니다.");
        }
        if (attributesToUpdate == null) {
            throw new IllegalArgumentException("속성 맵(attributesToUpdate)은 null일 수 없습니다.");
        }
        String newStyleId = ensureString(attributesToUpdate.get("id"), "id", "속성 맵에 유효한 'id'가 포함되어야 합니다.");
        if (newStyleId.isEmpty()) {
            throw new IllegalArgumentException("새로운 스타일 ID는 비어 있을 수 없습니다.");
        }

        Style newStyle = originalStyle.clone();
        newStyle.id(newStyleId);

        applyAttributes(newStyle, attributesToUpdate, STYLE_ATTRIBUTE_HANDLERS, "Style");
        return newStyle;
    }

    /**
     * 원본 ParaPr 객체를 복사하고, 주어진 속성 맵으로 업데이트하여 반환합니다.
     * 속성 맵에는 'id' 키로 새로운 ParaPr ID가 반드시 포함되어야 합니다.
     * RefList에 자동으로 추가하지 않습니다.
     *
     * @param originalParaPr 복사할 원본 ParaPr 객체
     * @param attributesToUpdate 업데이트할 속성 맵. 'id' 포함 필수.
     * @return 복사되고 수정된 새로운 ParaPr 객체
     */
    public static ParaPr copyParaPrAndUpdate(ParaPr originalParaPr, Map<String, Object> attributesToUpdate) {
        if (originalParaPr == null) {
            throw new IllegalArgumentException("원본 ParaPr 객체(originalParaPr)는 null일 수 없습니다.");
        }
        if (attributesToUpdate == null) {
            throw new IllegalArgumentException("속성 맵(attributesToUpdate)은 null일 수 없습니다.");
        }
        String newParaPrId = ensureString(attributesToUpdate.get("id"), "id", "속성 맵에 유효한 'id'가 포함되어야 합니다.");
        if (newParaPrId.isEmpty()) {
            throw new IllegalArgumentException("새로운 ParaPr ID는 비어 있을 수 없습니다.");
        }

        ParaPr newParaPr = originalParaPr.clone();
        newParaPr.id(newParaPrId);
        
        applyAttributes(newParaPr, attributesToUpdate, PARAPR_ATTRIBUTE_HANDLERS, "ParaPr");
        return newParaPr;
    }

    /**
     * 원본 CharPr 객체를 복사하고, 주어진 속성 맵으로 업데이트하여 반환합니다.
     * 속성 맵에는 'id' 키로 새로운 CharPr ID가 반드시 포함되어야 합니다.
     * RefList에 자동으로 추가하지 않습니다.
     *
     * @param originalCharPr 복사할 원본 CharPr 객체
     * @param attributesToUpdate 업데이트할 속성 맵. 'id' 포함 필수.
     * @return 복사되고 수정된 새로운 CharPr 객체
     */
    public static CharPr copyCharPrAndUpdate(CharPr originalCharPr, Map<String, Object> attributesToUpdate) {
        if (originalCharPr == null) {
            throw new IllegalArgumentException("원본 CharPr 객체(originalCharPr)는 null일 수 없습니다.");
        }
        if (attributesToUpdate == null) {
            throw new IllegalArgumentException("속성 맵(attributesToUpdate)은 null일 수 없습니다.");
        }
        String newCharPrId = ensureString(attributesToUpdate.get("id"), "id", "속성 맵에 유효한 'id'가 포함되어야 합니다.");
        if (newCharPrId.isEmpty()) {
            throw new IllegalArgumentException("새로운 CharPr ID는 비어 있을 수 없습니다.");
        }

        CharPr newCharPr = originalCharPr.clone();
        newCharPr.id(newCharPrId);

        applyAttributes(newCharPr, attributesToUpdate, CHARPR_ATTRIBUTE_HANDLERS, "CharPr");
        return newCharPr;
    }

    // 공통 속성 적용 로직
    private static <T> void applyAttributes(T targetObject, Map<String, Object> attributesToUpdate, Map<String, BiConsumer<T, Object>> handlers, String objectTypeName) {
        for (Map.Entry<String, Object> entry : attributesToUpdate.entrySet()) {
            String key = entry.getKey().toLowerCase(); // 키는 소문자로 일괄 처리
            Object value = entry.getValue();

            if (key.equals("id")) continue; 
            if (value == null) {
                System.out.println("Info: Attribute '" + entry.getKey() + "' for " + objectTypeName + " has null value, skipping update.");
                continue;
            }

            BiConsumer<T, Object> handler = handlers.get(key);
            if (handler != null) {
                try {
                    handler.accept(targetObject, value);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Error processing " + objectTypeName + " attribute '" + entry.getKey() + "' with value '" + value + "': " + e.getMessage(), e);
                } catch (Exception e) {
                    throw new RuntimeException("Unexpected error processing " + objectTypeName + " attribute '" + entry.getKey() + "': " + e.getMessage(), e);
                }
            } else {
                System.err.println("경고: 알 수 없거나 처리할 수 없는 " + objectTypeName + " 속성 키입니다: " + entry.getKey());
            }
        }
    }

    // --- 타입 변환 및 검증 유틸리티 메서드 ---
    private static String ensureString(Object value, String attrName) {
        return ensureString(value, attrName, null);
    }
    private static String ensureString(Object value, String attrName, String customErrorMessage) {
        if (value == null) {
             if (customErrorMessage != null) throw new IllegalArgumentException(customErrorMessage + " (attribute: " + attrName + " is null)");
             throw new IllegalArgumentException("Attribute '" + attrName + "' value cannot be null for String type.");
        }
        if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalArgumentException("Attribute '" + attrName + "' must be a String. Found: " + value.getClass().getSimpleName());
    }

    private static Boolean ensureBoolean(Object value, String attrName) {
        if (value == null) throw new IllegalArgumentException("Attribute '" + attrName + "' value cannot be null for Boolean type.");
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            String sValue = ((String) value).toLowerCase();
            if (sValue.equals("true")) return true;
            if (sValue.equals("false")) return false;
        }
        throw new IllegalArgumentException("Attribute '" + attrName + "' must be a Boolean or a 'true'/'false' String. Found: " + value + " (type: " + value.getClass().getSimpleName() + ")");
    }

    private static Byte ensureByte(Object value, String attrName) {
        if (value == null) throw new IllegalArgumentException("Attribute '" + attrName + "' value cannot be null for Byte type.");
        if (value instanceof Byte) {
            return (Byte) value;
        } else if (value instanceof Number) { 
            return ((Number)value).byteValue();
        } else if (value instanceof String) {
            try {
                return Byte.parseByte((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Attribute '" + attrName + "' String value '"+ value +"' cannot be parsed to Byte.", e);
            }
        }
        throw new IllegalArgumentException("Attribute '" + attrName + "' must be a Byte, Number, or a parsable String. Found: " + value.getClass().getSimpleName());
    }

    private static Integer ensureInteger(Object value, String attrName) {
        if (value == null) throw new IllegalArgumentException("Attribute '" + attrName + "' value cannot be null for Integer type.");
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
             return ((Number)value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Attribute '" + attrName + "' String value '"+ value +"' cannot be parsed to Integer.", e);
            }
        }
        throw new IllegalArgumentException("Attribute '" + attrName + "' must be an Integer, Number, or a parsable String. Found: " + value.getClass().getSimpleName());
    }
    
    @SuppressWarnings("unchecked")
    private static <E extends Enum<E>> E ensureEnum(Object value, Class<E> enumClass, String attrName) {
        if (value == null) throw new IllegalArgumentException("Attribute '" + attrName + "' value cannot be null for Enum type " + enumClass.getSimpleName());
        if (enumClass.isInstance(value)) {
            return (E) value;
        } else if (value instanceof String) {
            try {
                return Enum.valueOf(enumClass, ((String) value).toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Attribute '" + attrName + "' String value '"+ value +"' is not a valid constant for Enum " + enumClass.getSimpleName(), e);
            }
        }
        throw new IllegalArgumentException("Attribute '" + attrName + "' must be an instance of " + enumClass.getSimpleName() + " or its String representation. Found: " + value.getClass().getSimpleName());
    }
}

