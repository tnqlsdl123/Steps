package com.likelion.step.profilecard.service;

import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralExeption;
import com.likelion.step.profilecard.dto.CertificatesResponse;
import com.likelion.step.profilecard.dto.ProfileCardResponse;
import com.likelion.step.profilecard.dto.ProfileCardUpdateRequest;
import com.likelion.step.profilecard.entity.Certificates;
import com.likelion.step.profilecard.entity.ProfileCard;
import com.likelion.step.profilecard.repository.CertificatesRepository;
import com.likelion.step.profilecard.repository.ProfileCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileCardService {

    private final ProfileCardRepository profileCardRepository;

    private final CertificatesRepository certificatesRepository;

    @Transactional
    public  ProfileCardResponse updateProfileCard(Long userId, ProfileCardUpdateRequest request){

        if (request.getCollaborationTags() == null
                ||request.getCollaborationTags().size() != 2){
            throw new GeneralExeption((GlobalErrorcode.PROFILE_TAGS_COUNT_ERROR)
            );
        }

        ProfileCard profileCard = profileCardRepository.findByUserId(userId)
                .orElseThrow(() -> new GeneralExeption(GlobalErrorcode.INVALID_INPUT_VALUE));

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
                null, // user 엔티티 연결 후 name
                null, // major
                null, // school
                0, // grade
                null, //gender
                request.getCollaborationTags(),
                certificatesResponse,
                request.getSelfIntroduce(),
                null // contactEmail
        );
    }
}
