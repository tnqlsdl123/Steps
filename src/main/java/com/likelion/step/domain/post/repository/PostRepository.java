package com.likelion.step.domain.post.repository;

import com.likelion.step.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByAuthor_MemberIdOrderByCreatedAtDesc(Long memberId); // 추가
  List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
  List<Post> findAllByOrderByCreatedAtDesc();
}
