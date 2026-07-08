package com.likelion.step.profilecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor

public class ProfileCardCreateRequest {

    private Long userId;
    private List<String> collaborationTags;
    private String selfIntroduce;
    private List<String> certificates;
}
