package com.ifood.domain.enums;

public enum GenreEnum {
	PARTY("party", 30.0), POP("pop", 15.0), ROCK("rock", 10.0), CLASSICAL("classical", Double.MIN_VALUE);

	private Double temperature;
	private String name;

	private GenreEnum(String name, Double temperature) {
		this.name = name;
		this.temperature = temperature;
	}

	public static GenreEnum selectCategory(Double temp) {
		if (temp >= PARTY.getTemperature()) {
			return PARTY;
		} else if (temp >= POP.getTemperature()) {
			return POP;
		} else if (temp >= ROCK.getTemperature()) {
			return ROCK;
		} else {
			return CLASSICAL;
		}
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getGenreName() {
		return name;
	}

	public void setGenreName(String categotyId) {
		this.name = categotyId;
	}
}
