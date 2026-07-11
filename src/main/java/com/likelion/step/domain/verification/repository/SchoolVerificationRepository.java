package com.likelion.step.domain.verification.repository;

import com.likelion.step.domain.verification.entity.SchoolVerification;
import com.likelion.step.domain.verification.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolVerificationRepository extends JpaRepository<SchoolVerification, Long> {
  Optional<SchoolVerification> findByMember_MemberId(Long memberId);
  boolean existsByMember_MemberId(Long memberId);
  List<SchoolVerification> findByStatus(VerificationStatus status); // 추가
}