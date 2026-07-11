package com.likelion.step.domain.verification.entity;

import com.likelion.step.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "school_verification")
public class SchoolVerification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long schoolVerificationId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false, unique = true)
  private Member member;

  @Column(nullable = false)
  private String fileUrl;          // 저장된 인증 서류 경로/URL

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VerificationStatus status;

  private LocalDateTime submittedAt;
  private LocalDateTime processedAt;   // 승인/거절 처리 시각

  public SchoolVerification(Member member, String fileUrl) {
    this.member = member;
    this.fileUrl = fileUrl;
    this.status = VerificationStatus.PENDING;
    this.submittedAt = LocalDateTime.now();
  }

  // 재제출(거절 후 다시 올릴 때) 사용
  public void resubmit(String fileUrl) {
    this.fileUrl = fileUrl;
    this.status = VerificationStatus.PENDING;
    this.submittedAt = LocalDateTime.now();
    this.processedAt = null;
  }

  public void approve() {
    this.status = VerificationStatus.APPROVED;
    this.processedAt = LocalDateTime.now();
  }

  public void reject() {
    this.status = VerificationStatus.REJECTED;
    this.processedAt = LocalDateTime.now();
  }
}
