package com.ifood.helper;

public class CoordinateHelper {

	protected static final String CITY_COORDINATE_KEY_PATTERN = "%f:%f";

	public static String formatCoordinate(Double latitude, Double longitude) {
		return String.format(CITY_COORDINATE_KEY_PATTERN, latitude, longitude);
	}

	public static boolean inRange(double lat, double lon) {
		if (Double.compare(lat, 90) <= 0 && Double.compare(lat, -90) >= 0 && Double.compare(lon, 180) <= 0
				&& Double.compare(lon, -180) >= 0) {
			return true;
		}
		return false;
	}

}
