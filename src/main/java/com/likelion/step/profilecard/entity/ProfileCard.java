package com.likelion.step.profilecard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ProfileCard")
public class ProfileCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileCardId;

    private String collaborationTags;

    private String selfIntroduce;

    private Long memberId;

    public ProfileCard(String collaborationTags, String selfIntroduce, Long userId) {
        this.collaborationTags = collaborationTags;
        this.selfIntroduce = selfIntroduce;
        this.memberId = userId;
    }

    public void updateProfileCard(String collaborationTags, String selfIntroduce) {
        this.collaborationTags = collaborationTags;
        this.selfIntroduce = selfIntroduce;
    }

}
