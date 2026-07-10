package com.likelion.step.profilecard.entity;

import lombok.NoArgsConstructor;
import lombok.Getter;
import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name ="certificates")
public class Certificates {

    @Id
    @GeneratedValue
    private Long certificatesId;

    private String certificates;

    private Long profileCardId;

    public Certificates(String certificates, Long profileCardId) {
        this.certificates = certificates;
        this.profileCardId = profileCardId;
    }
}
