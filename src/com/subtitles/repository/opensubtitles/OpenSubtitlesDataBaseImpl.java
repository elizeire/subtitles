package com.subtitles.repository.opensubtitles;

import java.io.File;
import java.io.IOException;

import com.subtitles.domain.Language;
import com.subtitles.domain.Movie;
import com.subtitles.repository.SubtitlesDataBase;

public class OpenSubtitlesDataBaseImpl implements SubtitlesDataBase {

	private static OpenSubtitlesDataBaseImpl singleton;

	private OpenSubtitlesDataBaseImpl() {

	}

	public static OpenSubtitlesDataBaseImpl getInstance() {
		if (singleton == null) {
			singleton = new OpenSubtitlesDataBaseImpl();
		}
		return singleton;
	}

	@Override
	public File searchForMovieAndLanguage(Movie movie, Language language) {

		LanguageCodes openSubtitlesLanguage = LanguageCodes.valueOf(language.name());
		System.out.printf("OpenSubtitlesDataBaseImpl :: searchForMovieAndLanguage %s %s \n", movie.getPath().getFileName(),
				openSubtitlesLanguage.getCodISO639());
		
		try {
			String hash = OpenSubtitlesHasher.computeHash(movie.getPath().toFile());
			System.out.println(hash);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
