package com.likelion.step.profilecard.service;

import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralExeption;
import com.likelion.step.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.profilecard.entity.Certificates;
import com.likelion.step.profilecard.entity.ProfileCard;
import com.likelion.step.profilecard.repository.CertificatesRepository;
import com.likelion.step.profilecard.repository.ProfileCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ProfileCardService {

    private final ProfileCardRepository profileCardRepository;
    private final CertificatesRepository certificationRepository;

    public void createProfileCard(ProfileCardCreateRequest request) {

        if (request.getCollaborationTags() == null
                || request.getCollaborationTags().size() != 2) {
            throw new GeneralExeption(GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR);
        }

        String collaborationTags = String.join(",", request.getCollaborationTags());

        ProfileCard profileCard = new ProfileCard(
                collaborationTags,
                request.getSelfIntroduce(),
                request.getUserId()
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
