package com.ifood.helper;

public class CoordinateHelper {

	private static final String CITY_COORDINATE_KEY_PATTERN = "%f:%f";

	public static String formatCoordinate(Double latitude, Double longitude) {
		return String.format(CITY_COORDINATE_KEY_PATTERN, latitude, longitude);
	}

}
