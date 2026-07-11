package com.likelion.step.domain.post.entity;

import com.likelion.step.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private Member author;              // 작성자

  @Column(nullable = false, length = 30)
  private String title;               // 30자 이내

  @Column(nullable = false)
  private String applicationUrl;      // 지원 공고 링크

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PostCategory category;      // 모집 분야

  @Column(nullable = false)
  private LocalDate recruitDeadline;  // 모집 마감일

  @Column(nullable = false)
  private int recruitCount;           // 모집 인원

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ActivityType activityType;  // 활동 방식(대면/비대면)

  @Column(nullable = false)
  private String activityPurpose;     // 활동 목적

  @Column(nullable = false, length = 200)
  private String content;             // 200자 이내

  private LocalDateTime createdAt;

  public Post(Member author, String title, String applicationUrl, PostCategory category,
              LocalDate recruitDeadline, int recruitCount, ActivityType activityType,
              String activityPurpose, String content) {
    this.author = author;
    this.title = title;
    this.applicationUrl = applicationUrl;
    this.category = category;
    this.recruitDeadline = recruitDeadline;
    this.recruitCount = recruitCount;
    this.activityType = activityType;
    this.activityPurpose = activityPurpose;
    this.content = content;
    this.createdAt = LocalDateTime.now();
  }

  @Column(nullable = false)
  private boolean isClosed = false; // 모집 인원 충족 시 true로 변경

  public void close() {
    this.isClosed = true;
  }

  public boolean isClosed() {
    return isClosed;
  }
}
