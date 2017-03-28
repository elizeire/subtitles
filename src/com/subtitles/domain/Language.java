package com.subtitles.domain;

public enum Language {
PT_BR("pt_br"),EN_US("en_us");
	
	
	private String cod;

	private Language(String cod) {
		this.cod = cod;
	}
	
	public String getCod(){
		return this.cod;
	}
	
	
	
}
