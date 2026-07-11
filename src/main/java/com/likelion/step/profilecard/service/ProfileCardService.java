package com.likelion.step.profilecard.service;

import com.likelion.step.domain.auth.entity.GeneralLogin;
import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralException;
import com.likelion.step.profilecard.dto.CertificatesResponse;
import com.likelion.step.profilecard.dto.ProfileCardResponse;
import com.likelion.step.profilecard.dto.ProfileCardUpdateRequest;
import com.likelion.step.profilecard.entity.Certificates;
import com.likelion.step.profilecard.entity.ProfileCard;
import com.likelion.step.profilecard.repository.CertificatesRepository;
import com.likelion.step.profilecard.repository.ProfileCardRepository;
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
    public  ProfileCardResponse updateProfileCard(Long memberId, ProfileCardUpdateRequest request){

        if (request.getCollaborationTags() == null
                ||request.getCollaborationTags().size() != 2){
            throw new GeneralException(GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(
                        GlobalErrorcode.MEMBER_NOT_FOUND
                ));

        GeneralLogin generalLogin = generalLoginRepository
                .findById(memberId)
                .orElseThrow(() -> new GeneralException(
                        GlobalErrorcode.MEMBER_NOT_FOUND
                ));


        ProfileCard profileCard = profileCardRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(GlobalErrorcode.INVALID_INPUT_VALUE));

        String collaborationtags = String.join(",", request.getCollaborationTags());

        profileCard.updateProfileCard(
                collaborationtags,
                request.getSelfIntroduce()
        );

        certificatesRepository.deleteByProfileCardId(profileCard.getProfileCardId());

        for (String certificateName : request.getCertificates()) {
            Certificates certificate = new Certificates(
                    certificateName,
                    profileCard.getProfileCardId()
            );

            certificatesRepository.save(certificate);
        }

        List<String> recentThree = request.getCertificates()
                .stream()
                .limit(3)
                .toList();

        int othersCount = Math.max(request.getCertificates().size() -3,0);

        CertificatesResponse certificatesResponse = new CertificatesResponse(
                recentThree,
                othersCount
        );

        return new ProfileCardResponse(
                member.getName(), // user 엔티티 연결 후 name
                member.getMajor(), // major
                member.getSchool(),// school
                member.getGrade(), // grade
                member.getGender(),//gender
                request.getCollaborationTags(),
                certificatesResponse,
                request.getSelfIntroduce(),
                generalLogin.getEmail() // contactEmail
        );
    }
}
