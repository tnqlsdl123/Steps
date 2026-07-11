package com.likelion.step.profilecard.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CertificatesResponse {

    private List<String> recentThree;
    private int othersCount;

}
