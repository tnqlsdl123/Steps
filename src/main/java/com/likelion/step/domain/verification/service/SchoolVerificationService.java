package com.likelion.step.domain.verification.service;

import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.domain.verification.dto.VerificationStatusResponse;
import com.likelion.step.domain.verification.dto.VerificationSubmitResponse;
import com.likelion.step.domain.verification.entity.SchoolVerification;
import com.likelion.step.domain.verification.exception.VerificationErrorCode;
import com.likelion.step.domain.verification.repository.SchoolVerificationRepository;
import com.likelion.step.global.error.exception.GeneralExeption;
import com.likelion.step.global.file.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SchoolVerificationService {

  private final SchoolVerificationRepository verificationRepository;
  private final MemberRepository memberRepository;
  private final FileStorage fileStorage;

  @Transactional
  public VerificationSubmitResponse submit(Long memberId, MultipartFile file) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new GeneralExeption(VerificationErrorCode.UNAUTHORIZED));

    String fileUrl = fileStorage.store(file);

    SchoolVerification verification = verificationRepository
        .findByMember_MemberId(memberId)
        .map(existing -> {
          existing.resubmit(fileUrl); // 거절 후 재제출 등
          return existing;
        })
        .orElseGet(() -> verificationRepository.save(
            new SchoolVerification(member, fileUrl)));

    return new VerificationSubmitResponse(verification.getStatus());
  }

  @Transactional(readOnly = true)
  public VerificationStatusResponse getStatus(Long memberId) {
    SchoolVerification verification = verificationRepository
        .findByMember_MemberId(memberId)
        .orElseThrow(() -> new GeneralExeption(VerificationErrorCode.VERIFICATION_NOT_FOUND));

    return new VerificationStatusResponse(verification.getStatus());
  }
}
