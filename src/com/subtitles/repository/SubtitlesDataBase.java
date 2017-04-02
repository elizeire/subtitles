package com.subtitles.repository;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.subtitles.domain.Language;
import com.subtitles.domain.Movie;

public interface SubtitlesDataBase {
	
	Map<Movie,List<String>> searchForMovieAndLanguage(List<Path> paths, Language language);

}
