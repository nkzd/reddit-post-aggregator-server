package org.mamba.server.service;

import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mamba.server.config.ServerConfig;
import org.mamba.server.domain.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RssService {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ServerConfig serverConfig;
	
	private static final Logger LOGGER =
		    LoggerFactory.getLogger(RssService.class);
	
	@Scheduled(fixedRate = 1800000)
	public boolean updatePosts() {
		for(String subreddit : serverConfig.getSubreddits()) {
			try {
	            String url = "https://www.reddit.com/r/"+subreddit+"/.rss";
	 
	            try (XmlReader reader = new XmlReader(new URL(url))) {
	                SyndFeed feed = new SyndFeedInput().build(reader);
	                for (SyndEntry entry : feed.getEntries()) {
	                    
	                    String contentHtml = entry.getContents().get(0).getValue();
	                    
	                    Post newPost = new Post();
	                    newPost.setTitle(entry.getTitle());
	                    newPost.setSubmittedBy(entry.getAuthor());
	                    newPost.setSubreddit(subreddit);
	                    newPost.setCommentsLink(entry.getLink());
	                    newPost.setCommentsLinkHash(sha1Hash(entry.getLink()));
	                    newPost.setSubmittedLink(scrapeSubmittedLink(contentHtml));
	                    newPost.setContent(scrapeContent(contentHtml));
	                    
	                    postService.save(newPost);
	                }
	            }
	        }  catch (Exception e) {
	        	RssService.LOGGER.info("Error fetching a rss feed");
	        	return false;
	        }
		}
		return true;
	}

	
	public String scrapeSubmittedLink(String contentHtml) {
		Document document = Jsoup.parse(contentHtml);
		Element submittedLinkElement = document.select("a:contains([link])").first();
		return submittedLinkElement.attr("href");
		
	}
	public String scrapeCommentLink(String contentHtml) {
		Document document = Jsoup.parse(contentHtml);
		Element commentsLinkElement = document.select("a:contains([comments])").first();
		return commentsLinkElement.attr("href");
		
	}
	public String scrapeContent(String contentHtml) {
		
		/* There are two categories of reddit posts: Link and Self-Post
		 * Links have two types of post content: with thumbnail picture (/r/pics for example) and without thumbnail (/r/news for example)
		 * Self-Posts have two types of post content: with text (/r/Entrepreneur for example) or without text (/r/ASkReddit for example)
		 */
		Document document = Jsoup.parse(contentHtml);
		Element selectedElement;
		//check for thumbnail image
		if((selectedElement = document.select("td > a > img").first()) != null) {
			return selectedElement.attr("src");
		}//check for self-post preview text
		else if((selectedElement = document.select("div.md").first()) != null) {
			return selectedElement.text();
		}else {
			return null;
		}
	}
	public String sha1Hash(String url) {
		return DigestUtils.sha1Hex(url);
	}
}
