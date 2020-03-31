package org.mamba.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String subreddit;
	
	@NotBlank
	@Column(nullable=false)
	private String submittedBy;
	
	@NotBlank 
	@Column(nullable=false, columnDefinition = "TEXT")
	private String title;
	
	@NotBlank
	@Column(nullable=false, columnDefinition = "TEXT")
	private String commentsLink;
	
	@Column(nullable=false, unique=true)
	@JsonIgnore
	private String commentsLinkHash;
	
	@Column(nullable=false, columnDefinition = "TEXT")
	private String submittedLink;
	
	@Column(columnDefinition = "TEXT")		
	private String content;

	public Post() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommentsLink() {
		return commentsLink;
	}

	public void setCommentsLink(String commentsLink) {
		this.commentsLink = commentsLink;
	}

	public String getCommentsLinkHash() {
		return commentsLinkHash;
	}

	public void setCommentsLinkHash(String commentsLinkHash) {
		this.commentsLinkHash = commentsLinkHash;
	}

	public String getSubmittedLink() {
		return submittedLink;
	}

	public void setSubmittedLink(String submittedLink) {
		this.submittedLink = submittedLink;
	}


}
