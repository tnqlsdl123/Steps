package com.likelion.step.domain.auth.entity;

import com.likelion.step.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "general_login")
public class GeneralLogin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long generalLoginId;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;   // 암호화된 값 저장

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public GeneralLogin(String email, String password, Member member) {
    this.email = email;
    this.password = password;
    this.member = member;
  }
}