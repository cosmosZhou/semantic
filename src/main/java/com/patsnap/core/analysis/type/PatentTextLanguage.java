package com.patsnap.core.analysis.type;

/**
 * Enum for Patent Text Language
 * @author zhangyan
 */
public enum PatentTextLanguage {
    /**
     * Simplified Chinese
     */
    CN("CN"),
    /**
     *  English
     */
    EN("EN");


    private String value;

    PatentTextLanguage(String value) {
        this.value = value;
    }

    public static PatentTextLanguage fromString(String value) {
        if (value != null) {
            for (PatentTextLanguage lang : PatentTextLanguage.values()) {
                if (lang.value.equalsIgnoreCase(value)) {
                    return lang;
                }
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
