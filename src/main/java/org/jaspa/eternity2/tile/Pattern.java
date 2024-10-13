/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

/**
 * Enum encapsulates the Pattern of the tile sides
 */
public enum Pattern {
    EDGE("00"), MALTES_CROSS("01"), SANDWICH("02"), NUT("03"), FLOWER("04"), CLUB("05"), SWARD_CROSS("06"), EHBO("07"), PICTURE_FRAME(
            "08"), CASTLE("09"), SQUARE("10"), STAR("11");

    private final String code;

    Pattern(String code) {
        this.code = code;
    }

    /**
     * Finds the Pattern enum from the given code
     *
     * @param code The code of the enum to find
     * @return The found Enum
     */
    public static Pattern getFromCode(String code) {
        Pattern result = null;
        for (Pattern pattern : values()) {
            if (pattern.code().equals(code)) {
                result = pattern;
                break;
            }
        }
        assert result != null : "Unknown Pattern code: " + code;
        return result;
    }

    public String code() {
        return code;
    }

}
