package com.likelion.step.domain.profilecard.repository;

import com.likelion.step.domain.profilecard.entity.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificatesRepository extends JpaRepository<Certificates, Long> {

    void deleteByProfileCardId(Long profileCardId);

}
