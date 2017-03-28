package com.subtitles.domain;

public class UserConfig {
	
	private Language language;
	private String moviesFolder;
	

	public UserConfig(Language language, String moviesFolder) {
		super();
		this.language = language;
		this.moviesFolder = moviesFolder;
	}

	public String getMoviesFolder() {
		return moviesFolder;
	}

	public Language getLanguage() {
		return language;
	}

}
