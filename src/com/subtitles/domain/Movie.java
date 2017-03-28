package com.subtitles.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.subtitles.repository.opensubtitles.OpenSubtitlesHasher;

public class Movie {
	
	String title;
	String filename;
	String imdbId;
	Path path;
	String moviehash;
	
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
	public String getMoviehash() {
		if(moviehash == null && path != null){
			try {
				this.moviehash = OpenSubtitlesHasher.computeHash(path.toFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return moviehash;
	}


}
