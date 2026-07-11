package com.likelion.step.domain.application.entity;

import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "application")
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long applicationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "applicant_id", nullable = false)
  private Member applicant;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ApplicationStatus status;

  private LocalDateTime appliedAt;

  public Application(Post post, Member applicant) {
    this.post = post;
    this.applicant = applicant;
    this.status = ApplicationStatus.PENDING;
    this.appliedAt = LocalDateTime.now();
  }
}