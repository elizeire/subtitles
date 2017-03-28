package com.subtitles.repository.opensubtitles;

public enum LanguageCodes {

	PT_BR("pob");

	private String codISO639;
	 
	private LanguageCodes(String codISO639) {
		this.codISO639 = codISO639;
	}

	public String getCodISO639() {
		
		return this.codISO639;
	}

	

}
