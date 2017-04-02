package com.subtitles.domain;

import java.nio.file.Path;
import java.util.List;

public class Movie {
	
	private String title;
	private String filename;
	private String imdbId;
	private Path path;
	private List<Subtitle> subtitles;
	
	
	
	public Movie() {
		super();
	}
	public Movie(Path path) {
		this.path = path;
	}

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public List<Subtitle> getSubtitles() {
		return subtitles;
	}
	public void setSubtitles(List<Subtitle> subtitles) {
		this.subtitles = subtitles;
	}

}
