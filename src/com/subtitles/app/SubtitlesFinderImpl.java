package com.subtitles.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import com.subtitles.domain.Language;
import com.subtitles.domain.Movie;
import com.subtitles.domain.UserConfig;
import com.subtitles.repository.SubtitlesDataBase;
import com.subtitles.repository.opensubtitles.OpenSubtitlesDataBaseImpl;

public class SubtitlesFinderImpl implements SubtitlesFinder {

	private static SubtitlesFinderImpl instance;

	private static String CONFIG_FILE_PATH = "resources/config.properties";
	
	private UserConfig userConfig;
	

	private SubtitlesFinderImpl() {
	}

	public static SubtitlesFinderImpl getInstance() {
		if (instance == null) {
			instance = new SubtitlesFinderImpl();
		}
		return instance;
	}

	@Override
	public void searchForFiles() {

		System.out.println("SubtitlesFinderImpl :: searchForFiles");

		if (userConfig == null) {
			loadUserConfig();
		}
		
		try(Stream<Path> paths = Files.walk(Paths.get(userConfig.getMoviesFolder()))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		    		search(filePath);
		        }
		    });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	@Override
	public void loadUserConfig() {
		System.out.println("SubtitlesFinderImpl :: loadUserConfig");

		
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(CONFIG_FILE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		userConfig = new UserConfig(Language.valueOf(prop.getProperty("LANGUAGE")), prop.getProperty("MOVIES_FOLDER"));
	}

	@Override
	public void search(Path filePath) {
		System.out.println("SubtitlesFinderImpl :: search");
		
		Movie movie = new Movie();
		movie.setPath(filePath);
	

		SubtitlesDataBase subtitlesDataBase = OpenSubtitlesDataBaseImpl.getInstance();
		subtitlesDataBase.searchForMovieAndLanguage(movie, Language.PT_BR);

	}

}
 