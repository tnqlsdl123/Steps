package com.likelion.step.domain.profilecard.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@NoArgsConstructor

public class ProfileCardUpdateRequest {

    private List<String> certificates;
    private List<String> collaborationTags;
    private String selfIntroduce;

}
