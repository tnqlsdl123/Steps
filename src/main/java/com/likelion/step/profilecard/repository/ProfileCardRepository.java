package com.likelion.step.profilecard.repository;

import com.likelion.step.profilecard.entity.ProfileCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileCardRepository extends JpaRepository<ProfileCard, Long> {

    Optional<ProfileCard> findByMemberId(Long memberId);


}
