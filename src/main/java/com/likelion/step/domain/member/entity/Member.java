package com.likelion.step.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  private String name;
  private String birthDate;
  private String gender;
  private String school;
  private String major;
  private Integer grade;   // 추가

  public Member(String name, String birthDate, String gender, String school, String major, Integer grade) {
    this.name = name;
    this.birthDate = birthDate;
    this.gender = gender;
    this.school = school;
    this.major = major;
    this.grade = grade;
  }
}