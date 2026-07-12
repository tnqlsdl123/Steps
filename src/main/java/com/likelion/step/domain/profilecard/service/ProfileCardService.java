package com.likelion.step.domain.profilecard.service;

import com.likelion.step.domain.auth.entity.GeneralLogin;
import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.domain.profilecard.dto.CertificatesResponse;
import com.likelion.step.domain.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.domain.profilecard.dto.ProfileCardCreateResponse;
import com.likelion.step.domain.profilecard.dto.ProfileCardResponse;
import com.likelion.step.domain.profilecard.dto.ProfileCardUpdateRequest;
import com.likelion.step.domain.profilecard.entity.Certificates;
import com.likelion.step.domain.profilecard.entity.ProfileCard;
import com.likelion.step.domain.profilecard.repository.CertificatesRepository;
import com.likelion.step.domain.profilecard.repository.ProfileCardRepository;
import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileCardService {

  private final ProfileCardRepository profileCardRepository;
  private final CertificatesRepository certificatesRepository;
  private final GeneralLoginRepository generalLoginRepository;
  private final MemberRepository memberRepository;

  // 프로필 카드 생성
  @Transactional
  public ProfileCardCreateResponse createProfileCard(Long memberId, ProfileCardCreateRequest request) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.MEMBER_NOT_FOUND));

    if (request.getCollaborationTags() == null
        || request.getCollaborationTags().size() != 2) {
      throw new GeneralException(GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR);
    }

    String collaborationTags = String.join(",", request.getCollaborationTags());

    ProfileCard profileCard = new ProfileCard(
        collaborationTags,
        request.getSelfIntroduce(),
        member.getMemberId()
    );
    ProfileCard savedProfileCard = profileCardRepository.save(profileCard);

    for (String certificateName : request.getCertificates()) {
      certificatesRepository.save(
          new Certificates(certificateName, savedProfileCard.getProfileCardId())
      );
    }

    return new ProfileCardCreateResponse(savedProfileCard.getProfileCardId());
  }

  // 프로필 카드 수정
  @Transactional
  public ProfileCardResponse updateProfileCard(Long memberId, ProfileCardUpdateRequest request) {
    if (request.getCollaborationTags() == null
        || request.getCollaborationTags().size() != 2) {
      throw new GeneralException(GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR);
    }
    if (profileCardRepository.findByMemberId(memberId).isPresent()) {
      throw new GeneralException(GlobalErrorcode.PROFILE_ALREADY_EXISTS); // 에러코드 추가 필요
    }

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.MEMBER_NOT_FOUND));

    GeneralLogin generalLogin = generalLoginRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.MEMBER_NOT_FOUND));

    ProfileCard profileCard = profileCardRepository.findByMemberId(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.INVALID_INPUT_VALUE));

    String collaborationTags = String.join(",", request.getCollaborationTags());
    profileCard.updateProfileCard(collaborationTags, request.getSelfIntroduce());

    certificatesRepository.deleteByProfileCardId(profileCard.getProfileCardId());

    for (String certificateName : request.getCertificates()) {
      certificatesRepository.save(
          new Certificates(certificateName, profileCard.getProfileCardId())
      );
    }

    List<String> recentThree = request.getCertificates().stream().limit(3).toList();
    int othersCount = Math.max(request.getCertificates().size() - 3, 0);

    return new ProfileCardResponse(
        member.getName(),
        member.getMajor(),
        member.getSchool(),
        member.getGrade(),
        member.getGender(),
        request.getCollaborationTags(),
        new CertificatesResponse(recentThree, othersCount),
        request.getSelfIntroduce(),
        generalLogin.getEmail()
    );
  }
}