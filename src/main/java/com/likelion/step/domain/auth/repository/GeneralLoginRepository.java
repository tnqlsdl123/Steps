package com.likelion.step.domain.auth.repository;

import com.likelion.step.domain.auth.entity.GeneralLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralLoginRepository extends JpaRepository<GeneralLogin, Long> {
  Optional<GeneralLogin> findByEmail(String email);
  Optional<GeneralLogin> findByMember_MemberId(Long memberId); // 추가
}