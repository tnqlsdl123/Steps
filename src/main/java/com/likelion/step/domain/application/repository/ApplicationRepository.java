package com.likelion.step.domain.application.repository;

import com.likelion.step.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
  boolean existsByPost_PostIdAndApplicant_MemberId(Long postId, Long memberId);
}