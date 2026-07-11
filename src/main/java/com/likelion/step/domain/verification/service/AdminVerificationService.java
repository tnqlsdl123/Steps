package com.likelion.step.domain.verification.service;

import com.likelion.step.domain.verification.dto.PendingVerificationResponse;
import com.likelion.step.domain.verification.entity.SchoolVerification;
import com.likelion.step.domain.verification.entity.VerificationStatus;
import com.likelion.step.domain.verification.exception.VerificationErrorCode;
import com.likelion.step.domain.verification.repository.SchoolVerificationRepository;
import com.likelion.step.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVerificationService {

  private final SchoolVerificationRepository verificationRepository;

  @Transactional(readOnly = true)
  public List<PendingVerificationResponse> getPendingList() {
    return verificationRepository.findByStatus(VerificationStatus.PENDING).stream()
        .map(v -> new PendingVerificationResponse(
            v.getSchoolVerificationId(),
            v.getMember().getMemberId(),
            v.getMember().getName(),
            v.getMember().getSchool(),
            v.getFileUrl(),
            v.getSubmittedAt()
        ))
        .toList();
  }

  @Transactional
  public void approve(Long verificationId) {
    SchoolVerification verification = verificationRepository.findById(verificationId)
        .orElseThrow(() -> new GeneralException(VerificationErrorCode.VERIFICATION_NOT_FOUND));
    verification.approve();
  }

  @Transactional
  public void reject(Long verificationId) {
    SchoolVerification verification = verificationRepository.findById(verificationId)
        .orElseThrow(() -> new GeneralException(VerificationErrorCode.VERIFICATION_NOT_FOUND));
    verification.reject();
  }
}