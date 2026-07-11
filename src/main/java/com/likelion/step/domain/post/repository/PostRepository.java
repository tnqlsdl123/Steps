// domain/post/repository/PostRepository.java
package com.likelion.step.domain.post.repository;

import com.likelion.step.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
