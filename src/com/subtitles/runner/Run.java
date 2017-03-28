package com.subtitles.runner;

import com.subtitles.app.SubtitlesFinder;
import com.subtitles.app.SubtitlesFinderImpl;

/**
 * 
 */

/**
 * @author elizeire
 *
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SubtitlesFinder finder = SubtitlesFinderImpl.getInstance();
		finder.searchForFiles();
	}

}
