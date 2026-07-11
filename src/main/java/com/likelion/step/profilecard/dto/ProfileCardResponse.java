package com.likelion.step.profilecard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProfileCardResponse {

    private String name;
    private String major;
    private String school;
    private int grade;
    private String gender;

    private List<String> collaborationTags;

    private CertificatesResponse certificates;

    private String selfIntroduce;
    private String email;
}
