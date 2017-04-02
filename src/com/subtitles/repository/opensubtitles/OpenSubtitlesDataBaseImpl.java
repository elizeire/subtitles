package com.subtitles.repository.opensubtitles;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public Map<Movie, List<String>> searchForMovieAndLanguage(List<Path> paths, Language language) {

		LanguageCodes openSubtitlesLanguage = LanguageCodes.valueOf(language.name());
		List<Movie> movies = new ArrayList<Movie>();

		paths.forEach(path->movies.add(new Movie(path)));
		
		XmlRcpClient client = XmlRcpClient.getInstance();
		Map<Movie,List<String>> mapSubs  = client.searchSubtitles(movies, openSubtitlesLanguage);
		
		return mapSubs;
	}

}
