package com.likelion.step.domain.profilecard.repository;

import com.likelion.step.domain.profilecard.entity.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificatesRepository
        extends JpaRepository<Certificates, Long> {

    List<Certificates> findAllByProfileCardId(Long profileCardId);

    void deleteByProfileCardId(Long profileCardId);

}
