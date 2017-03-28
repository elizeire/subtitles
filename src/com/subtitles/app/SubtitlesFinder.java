package com.subtitles.app;

import java.nio.file.Path;

public interface SubtitlesFinder {


	void loadUserConfig();
	
	void search(Path filePath);

	void searchForFiles();
	
}
