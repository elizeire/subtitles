package com.subtitles.app;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.subtitles.domain.Movie;
import com.subtitles.domain.UserConfig;

public interface SubtitlesFinder {


	UserConfig loadUserConfig();
	
	Map<Movie,List<String>> searchForSubtitles(List<Path> paths);

	List<File> searchForFiles();
	
}
