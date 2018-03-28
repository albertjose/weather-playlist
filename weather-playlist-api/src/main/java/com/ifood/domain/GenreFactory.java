package com.ifood.domain;

public class GenreFactory {

	private static final double TEMPERATURE_10 = 10.0;
	private static final double TEMPERATURE_15 = 15.0;
	private static final double TEMPERATURE_30 = 30.0;

	/**
	 * Get genre by temperature param
	 * 
	 * @param temperature
	 * @return
	 */
	public static Genre getGenreByTemperature(Double temperature) {
		if (temperature >= TEMPERATURE_30) {
			return new GenreParty();
		} else if (temperature >= TEMPERATURE_15) {
			return new GenrePop();
		} else if (temperature >= TEMPERATURE_10) {
			return new GenreRock();
		} else {
			return new GenreClassical();
		}
	}
}