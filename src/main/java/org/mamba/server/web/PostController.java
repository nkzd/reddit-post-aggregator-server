package org.mamba.server.web;

import java.util.List;

import javax.validation.Valid;

import org.mamba.server.domain.Post;
import org.mamba.server.service.PostService;
import org.mamba.server.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private RssService rssService;
	
	@GetMapping("/post/update")
	public ResponseEntity<?> updatePosts() {
		if(rssService.updatePosts())
			return new ResponseEntity<String>("Database updated",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Database failed to update",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/post/search")
	public ResponseEntity<?> searchPosts(@RequestBody String[] keywords){
		
		if(keywords.length >=3 && keywords.length <= 5) {
			Iterable<Post> result= postService.keywordSearch(keywords);
			return new ResponseEntity<Iterable<Post>>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Invalid number of keywords",HttpStatus.BAD_REQUEST);
		}

	}
	
}
