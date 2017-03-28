package com.subtitles.domain;

import java.io.File;

public class Subtitle {
	
	String fileName;
	File file;
	Language language;
	Float rank;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Float getRank() {
		return rank;
	}
	public void setRank(Float rank) {
		this.rank = rank;
	}
	
	
	
	 

}
