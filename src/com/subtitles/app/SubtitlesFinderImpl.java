package com.subtitles.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.subtitles.domain.Language;
import com.subtitles.domain.Movie;
import com.subtitles.domain.UserConfig;
import com.subtitles.repository.SubtitlesDataBase;
import com.subtitles.repository.opensubtitles.OpenSubtitlesDataBaseImpl;

public class SubtitlesFinderImpl implements SubtitlesFinder {

	private static Logger LOGGER = Logger.getLogger(SubtitlesFinderImpl.class.getName());
	
	private static SubtitlesFinderImpl instance;

	private static String CONFIG_FILE_PATH = "resources/config.properties";
	
	private UserConfig userConfig;
	
	private SubtitlesDataBase subtitlesDataBase = OpenSubtitlesDataBaseImpl.getInstance();
	

	private SubtitlesFinderImpl() {
	}

	public static SubtitlesFinderImpl getInstance() {
		if (instance == null) {
			instance = new SubtitlesFinderImpl();
		}
		return instance;
	}

	@Override
	public List<File> searchForFiles() {

		List<File> files = new ArrayList<File>();
		
		if (userConfig == null) {
			loadUserConfig();
		}
		
		try(Stream<Path> paths = Files.walk(Paths.get(userConfig.getMoviesFolder()))) {
		    paths.forEach(filePath -> {
		    	//TODO add extension validation for movies files
		        if (Files.isRegularFile(filePath)) {
		        	files.add(filePath.toFile());
		        }
		    });
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} 
		
		return files;
	}

	@Override
	public UserConfig loadUserConfig() {
		
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(CONFIG_FILE_PATH));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		userConfig = new UserConfig(Language.valueOf(prop.getProperty("LANGUAGE")), prop.getProperty("MOVIES_FOLDER"));
		
		return userConfig;
	}

	/**
	 * Method to search for subtitles ids
	 *@return Map Key: Movie, value List of subtitles id
	 */
	@Override
	public Map<Movie,List<String>>  searchForSubtitles(List<Path> paths) {
		return subtitlesDataBase.searchForMovieAndLanguage(paths, Language.PT_BR);

	}

}
 