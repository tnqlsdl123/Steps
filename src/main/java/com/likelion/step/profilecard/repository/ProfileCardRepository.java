package com.likelion.step.profilecard.repository;


import com.likelion.step.profilecard.entity.ProfileCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileCardRepository extends JpaRepository<ProfileCard, Long> {
}
