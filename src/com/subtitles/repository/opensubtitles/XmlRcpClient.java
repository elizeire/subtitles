package com.subtitles.repository.opensubtitles;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.subtitles.domain.Movie;

public class XmlRcpClient {

	private static String USERNAME = "elizeire@gmail.com";
	private static String PASSWORD = "Sbt#2017";
	private static String LANGUAGE = "pob";
	private static String USERAGENT = "forcecodeSubs v1.00";
	private static String URL_SERVER = "http://api.opensubtitles.org/xml-rpc";
	private String token;

	public static void main(String[] args) {
		XmlRcpClient client = new XmlRcpClient();
		// client.testClient();
		// client.login(USERNAME, PASSWORD, LANGUAGE, USERAGENT);
		List<Movie> movies = new ArrayList<Movie>();
		Movie movie = new Movie();
		Path path = new File("/home/elizeire/Videos/videos.avi").toPath();
		movie.setPath(path);
		movies.add(movie);
		client.searchSubtitles(movies);
	}

	public void testClient() {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://api.opensubtitles.org/xml-rpc"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] {};
			HashMap resultMap = (HashMap) client.execute("ServerInfo", params);
			for (Object key : resultMap.keySet()) {
				System.out.printf("%s ::", key);
				System.out.println(resultMap.get(key));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String login(String username, String password, String language, String userAgent) {

		String token = "";

		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(URL_SERVER));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] { username, password, language, userAgent };
			Map<String, Object> resultMap = (HashMap<String, Object>) client.execute("LogIn", params);
			for (Object key : resultMap.keySet()) {
				if ("token".equalsIgnoreCase(key.toString())) {
					token = resultMap.get(key).toString();
					System.out.println("token::" + token);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return token;
	}

	// array SearchSubtitles( $token, array(array('sublanguageid' =>
	// $sublanguageid, 'moviehash' => $moviehash, 'moviebytesize' => $moviesize,
	// imdbid => $imdbid, query => 'movie name', "season" => 'season number',
	// "episode" => 'episode number', 'tag' => tag ),array(...)), array('limit'
	// => 500))
	public String searchSubtitles(List<Movie> movies) {


		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://api.opensubtitles.org/xml-rpc"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Map<String, String> mapParams = new HashMap<String, String>();
			for (Movie movie : movies) {
				
				
				mapParams.put("sublanguageid", this.LANGUAGE);
//				mapParams.put("moviehash", movie.getMoviehash());
//				mapParams.put("moviebytesize", ""); // TODO
//				mapParams.put("imdbid", "");
				mapParams.put("query", "Star Wars Return of Jedi");
//				mapParams.put("season", "");
//				mapParams.put("episode", "");
//				mapParams.put("tag", "");
//
			}
			Object[] videoParams = {mapParams};
			Object[] params = new Object[] { getToken(), videoParams };

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) client.execute("SearchSubtitles", params);
			
			for (String key : map.keySet()) {
				
				if("data".equalsIgnoreCase(key)){
					
					Object[] data = 
							(Object[]) map.get(key);
					
					for (int x = 0 ; x < data.length; x++) {
						Map subtitleInfo = (HashMap<String,String>) data[x];
						
						System.out.printf("%s :: ",subtitleInfo.get("MovieReleaseName"));
						//System.out.println(subtitleInfo.get("ZipDownloadLink"));
						System.out.println(subtitleInfo.get("idsubtitlefile"));
					}
				}
			}
			
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return token;
	}

	public String getToken() {

		if (this.token == null) {
			this.token = login(USERNAME, PASSWORD, LANGUAGE, USERAGENT);
		}

		return token;
	}

}
