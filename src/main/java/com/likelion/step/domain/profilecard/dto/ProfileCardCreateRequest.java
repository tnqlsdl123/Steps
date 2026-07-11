package com.likelion.step.domain.profilecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor

public class ProfileCardCreateRequest {

    private Long memberId;
    private List<String> collaborationTags;
    private String selfIntroduce;
    private List<String> certificates;
}
