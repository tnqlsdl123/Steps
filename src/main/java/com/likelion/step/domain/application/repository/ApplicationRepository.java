package com.likelion.step.domain.application.repository;

import com.likelion.step.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
  boolean existsByPost_PostIdAndApplicant_MemberId(Long postId, Long memberId);
  List<Application> findByApplicant_MemberIdOrderByAppliedAtDesc(Long memberId); // 추가
  List<Application> findByPost_PostIdOrderByAppliedAtAsc(Long postId); // 추가
}