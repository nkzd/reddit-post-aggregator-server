package org.mamba.server.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "serverconfig")
public class ServerConfig {
	private int refresh;
	private List<String> subreddits;
	
	public ServerConfig() {
		super();
	}
	public int getRefresh() {
		return refresh;
	}
	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}
	public List<String> getSubreddits() {
		return subreddits;
	}
	public void setSubreddits(List<String> subreddits) {
		this.subreddits = subreddits;
	}
	
	
}
