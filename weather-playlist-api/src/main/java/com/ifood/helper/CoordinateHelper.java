package com.ifood.helper;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CoordinateHelper {
	protected static final String LATITUDE_PATTERN = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
	protected static final String LONGITUDE_PATTERN = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

	private static final String CITY_COORDINATE_KEY_PATTERN = "%f:%f";

	public static String formatCoordinate(Double latitude, Double longitude) {
		return String.format(CITY_COORDINATE_KEY_PATTERN, latitude, longitude);
	}

	public static Boolean isValid(Double latitude, Double longitude) {
		return validateLatitude(latitude) && validateLongitude(longitude);
	}

	private static Boolean validateLatitude(Double latitude) {
		DecimalFormat df = new DecimalFormat("#.######");
		df.setRoundingMode(RoundingMode.UP);
		return df.format(latitude).matches(LATITUDE_PATTERN);
	}

	private static Boolean validateLongitude(Double longitude) {
		DecimalFormat df = new DecimalFormat("#.######");
		df.setRoundingMode(RoundingMode.UP);
		return df.format(longitude).matches(LONGITUDE_PATTERN);
	}

}
