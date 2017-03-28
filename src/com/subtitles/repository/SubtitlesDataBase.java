package com.subtitles.repository;

import java.io.File;

import com.subtitles.domain.Language;
import com.subtitles.domain.Movie;

public interface SubtitlesDataBase {
	
	File searchForMovieAndLanguage(Movie movie, Language language);

}
