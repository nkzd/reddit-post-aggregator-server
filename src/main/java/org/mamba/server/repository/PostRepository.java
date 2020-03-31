package org.mamba.server.repository;

import java.util.Set;

import org.mamba.server.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{
	Set<Post> findByCommentsLinkHash(String commentsLinkHash);
	Long countByCommentsLinkHash(String commentsLinkHash);
	Set<Post> findAll();
}
