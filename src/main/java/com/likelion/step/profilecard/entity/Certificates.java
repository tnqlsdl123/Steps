package com.likelion.step.profilecard.entity;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name ="certificates")
public class Certificates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    private String certificates;

    private Long profileCardId;

    public Certificates(String certificates, Long profilecardId){
        this.certificates = certificates;
        this.profileCardId = profilecardId;
    }
}
