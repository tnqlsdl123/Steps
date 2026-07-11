package com.likelion.step.domain.profilecard.repository;

import com.likelion.step.domain.profilecard.entity.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificatesRepository extends JpaRepository<Certificates, Long> {
    void deleteByProfileCardId(Long profileCardId);
    List<Certificates> findAllByProfileCardId(Long profileCardId);
    List<Certificates> findByProfileCardId(Long profileCardId); // PostService에서 사용
}