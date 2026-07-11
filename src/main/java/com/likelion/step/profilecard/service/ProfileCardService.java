package com.likelion.step.profilecard.service;

import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralException;
import com.likelion.step.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.profilecard.entity.Certificates;
import com.likelion.step.profilecard.entity.ProfileCard;
import com.likelion.step.profilecard.repository.CertificatesRepository;
import com.likelion.step.profilecard.repository.ProfileCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class ProfileCardService {

    private final ProfileCardRepository profileCardRepository;
    private final CertificatesRepository certificationRepository;
    private final MemberRepository memberRepository;
    private final GeneralLoginRepository generalLoginRepository;

    @Transactional
    public void createProfileCard(
            Long memberId,
            ProfileCardCreateRequest request) {

        if (request.getCollaborationTags() == null
                || request.getCollaborationTags().size() != 2) {
            throw new GeneralException(
                    GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(
                        GlobalErrorcode.MEMBER_NOT_FOUND
                ));

        String collaborationTags =
                String.join(",", request.getCollaborationTags());

        ProfileCard profileCard = new ProfileCard(
                collaborationTags,
                request.getSelfIntroduce(),
                member.getMemberId()
        );

        ProfileCard saveProfileCard = profileCardRepository.save(profileCard);

        for (String certificationName : request.getCertificates()) {
            Certificates certificates = new Certificates(
                    certificationName,
                    saveProfileCard.getProfileCardId()
            );

            certificationRepository.save(certificates);
        }
    }
}
