package com.likelion.step.domain.profilecard.service;

import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.domain.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralException;
import com.likelion.step.domain.profilecard.entity.Certificates;
import com.likelion.step.domain.profilecard.entity.ProfileCard;
import com.likelion.step.domain.profilecard.repository.CertificatesRepository;
import com.likelion.step.domain.profilecard.repository.ProfileCardRepository;
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

    @Transactional
    public void createProfileCard(
            Long memberId,
            ProfileCardCreateRequest request
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(
                        GlobalErrorcode.MEMBER_NOT_FOUND
                ));

        if (request.getCollaborationTags() == null
                || request.getCollaborationTags().size() != 2) {
            throw new GeneralException(
                    GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR
            );
        }

        String collaborationTags =
                String.join(",", request.getCollaborationTags());

        ProfileCard profileCard = new ProfileCard(
                collaborationTags,
                request.getSelfIntroduce(),
                member.getMemberId()
        );

        ProfileCard savedProfileCard =
                profileCardRepository.save(profileCard);

        for (String certificateName : request.getCertificates()) {
            Certificates certificates = new Certificates(
                    certificateName,
                    savedProfileCard.getProfileCardId()
            );

            certificatesRepository.save(certificates);
        }
    }
}
