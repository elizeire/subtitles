package com.subtitles.repository.opensubtitles;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.subtitles.domain.Movie;

public class XmlRcpClient {

	private Logger LOGGER = Logger.getLogger(XmlRcpClient.class.getName());
	private static XmlRcpClient client;
	
	private static String USERNAME = "elizeire@gmail.com";
	private static String PASSWORD = "Sbt#2017";
	private static String LANGUAGE = "pob";
	private static String USERAGENT = "forcecodeSubs v1.00";
	private static String URL_SERVER = "http://api.opensubtitles.org/xml-rpc";
	private String token;

	
	private XmlRcpClient(){
	}
	
	public static XmlRcpClient getInstance(){
		if(client == null){
			client = new XmlRcpClient();
		}
		return client;
	}

	@SuppressWarnings("rawtypes")
	public void testClient() {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://api.opensubtitles.org/xml-rpc"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] {};
			Map resultMap = (HashMap) client.execute("ServerInfo", params);
			for (Object key : resultMap.keySet()) {
				System.out.printf("%s ::", key);
				System.out.println(resultMap.get(key));
			}
		} catch (XmlRpcException  | MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
			@SuppressWarnings("unchecked")
			Map<String, Object> resultMap = (HashMap<String, Object>) client.execute("LogIn", params);
			for (Object key : resultMap.keySet()) {
				if ("token".equalsIgnoreCase(key.toString())) {
					token = resultMap.get(key).toString();
					System.out.println("token::" + token);
				}
			}
		} catch (XmlRpcException  | MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		return token;
	}

	//TODO get subtitles id and dowload them, needs refactor
	@SuppressWarnings("unchecked")
	public Map<Movie,List<String>> searchSubtitles(List<Movie> movies, LanguageCodes lang) {
		
		Map<Movie,List<String>> mapSubs = new HashMap<Movie,List<String>>();

		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://api.opensubtitles.org/xml-rpc"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Map<String, String> mapParams = new HashMap<String, String>();
			for (Movie movie : movies) {
				mapParams.put("sublanguageid", lang.getCodISO639());
				mapParams.put("moviehash", OpenSubtitlesHasher.computeHash(movie.getPath().toFile()));
			}
			Object[] videoParams = {mapParams};
			Object[] params = new Object[] { getToken(), videoParams };

			Map<String, Object> map = (Map<String, Object>) client.execute("SearchSubtitles", params);
			
			for (String key : map.keySet()) {
				
				if("data".equalsIgnoreCase(key)){
					
					Object[] data = 
							(Object[]) map.get(key);
					
					for (int x = 0 ; x < data.length; x++) {
						Map<String,String> subtitleInfo = (HashMap<String,String>) data[x];
						
						LOGGER.log(Level.INFO, subtitleInfo.get("MovieReleaseName").toString());
						LOGGER.log(Level.INFO, subtitleInfo.get("idsubtitlefile").toString());
						//mapSubs.put(movie, subtitleInfo.get("ZipDownloadLink").toString());
					}
				}
			}
			
		} catch (XmlRpcException  | IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return mapSubs;
	}

	public String getToken() {

		if (this.token == null) {
			this.token = login(USERNAME, PASSWORD, LANGUAGE, USERAGENT);
		}

		return token;
	}

}
