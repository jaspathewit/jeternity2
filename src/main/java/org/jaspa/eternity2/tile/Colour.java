/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

/**
 * Enum encapsulates the colours of the tile sides
 */
public enum Colour {
	EDGE("00"), LIGHT_BLUE("01"), PINK("02"), ORANGE("03"), GREEN("04"), DARK_BLUE("05"), YELLOW("06"), PURPLE("07"), BROWN(
			"08");

	private final String code;

	Colour(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	/**
	 * Finds the Colour enum from the given code
	 * 
	 * @param code
	 *            The code of the enum to find
	 * @return The found Enum
	 */
	public static Colour getFromCode(String code) {
		Colour result = null;
		for (Colour colour : values()) {
			if (colour.code().equals(code)) {
				result = colour;
				break;
			}
		}
		assert result != null : "Unknown Colour code: " + code;
		return result;
	}
}
