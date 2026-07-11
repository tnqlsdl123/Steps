package com.likelion.step.domain.profilecard.service;

import com.likelion.step.domain.auth.entity.GeneralLogin;
import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.domain.profilecard.dto.CertificatesResponse;
import com.likelion.step.domain.profilecard.dto.ProfileCardResponse;
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
@Transactional(readOnly = true)
public class ProfileCardQueryService {

  private final ProfileCardRepository profileCardRepository;
  private final CertificatesRepository certificatesRepository;
  private final MemberRepository memberRepository;
  private final GeneralLoginRepository generalLoginRepository;

  public ProfileCardResponse getProfileCard(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.MEMBER_NOT_FOUND));

    ProfileCard profileCard = profileCardRepository.findByMemberId(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.INVALID_INPUT_VALUE));

    GeneralLogin generalLogin = generalLoginRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(GlobalErrorcode.MEMBER_NOT_FOUND));

    List<String> collaborationTags = (profileCard.getCollaborationTags() != null
        && !profileCard.getCollaborationTags().isBlank())
        ? List.of(profileCard.getCollaborationTags().split(","))
        : List.of();

    List<Certificates> certificates =
        certificatesRepository.findAllByProfileCardId(profileCard.getProfileCardId());

    List<String> recentThree = certificates.stream()
        .limit(3)
        .map(Certificates::getCertificates)
        .toList();

    int othersCount = Math.max(certificates.size() - 3, 0);

    return new ProfileCardResponse(
        member.getName(),
        member.getMajor(),
        member.getSchool(),
        member.getGrade(),
        member.getGender(),
        collaborationTags,
        new CertificatesResponse(recentThree, othersCount),
        profileCard.getSelfIntroduce(),
        generalLogin.getEmail()
    );
  }
}