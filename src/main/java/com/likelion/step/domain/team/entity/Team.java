package com.likelion.step.domain.team.entity;

import com.likelion.step.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "team")
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long teamId;

  // 팀과 모집글은 1:1
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, unique = true)
  private Post post;

  private LocalDateTime createdAt;

  public Team(Post post) {
    this.post = post;
    this.createdAt = LocalDateTime.now();
  }
}
