package org.mamba.server.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.mamba.server.domain.Post;
import org.mamba.server.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostService {
	private static final Logger LOGGER =
		    LoggerFactory.getLogger(PostService.class);
	
	@Autowired
	private PostRepository postRepository;
	
	public Iterable<Post> findAllPosts() {
		return postRepository.findAll();
	}
	
	public Post save(Post newPost) {
		try {
			if(postRepository.countByCommentsLinkHash(newPost.getCommentsLinkHash())==0)
				return postRepository.save(newPost);
		}catch(Exception e) {
			PostService.LOGGER.info("Unable to save the post to the database.");
		}
		return null;
	}
	
	public Iterable<Post> keywordSearch(String[] keywords){
		Set<Post> allPosts = postRepository.findAll();
		HashMap<Post,Integer> foundPosts = new HashMap<>();
		
		for(Post post : allPosts) {
			int sumOfOccurances=0;
			for(String keyword: keywords) {
				
				//match all non-ASCII words
				String[] wordList = post.getTitle().split("\\P{L}+");
				for(String word : wordList) {
					if(word.toLowerCase().equals(keyword.toLowerCase()))
						sumOfOccurances++;
				}
			}
			if(sumOfOccurances>0) {
				foundPosts.put(post, sumOfOccurances);
			}
		}
		
		Map<Post,Integer> topTen =
				foundPosts.entrySet().stream()
			       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			       .limit(10)
			       .collect(Collectors.toMap(
			          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		List<Post> topTenArray = new ArrayList<Post>(topTen.keySet());
		return topTenArray;
	}

}
