package com.feuji.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlterPort {
	
	@Value("${enviroment}")
	private String environmentState;
	
	@Value("${prod}")
	private String prodUrl;
	
	public String getUrl(String url) {
		
		if(environmentState.equals("prod"))
		{
			return url.replace("localhost", prodUrl);
		}
		return url;
	
	}

}
