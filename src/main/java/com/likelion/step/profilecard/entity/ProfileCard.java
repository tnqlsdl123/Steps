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

    public ProfileCard(String tag, String introduce, Long memberId){
        this.collaborationTags = getCollaborationTags();
        this.selfIntroduce = getSelfIntroduce();
        this.memberId = getMemberId();
    }
}
