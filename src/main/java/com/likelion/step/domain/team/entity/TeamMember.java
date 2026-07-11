package com.likelion.step.domain.team.entity;

import com.likelion.step.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "team_member")
public class TeamMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long teamMemberId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TeamRole role;

  private LocalDateTime joinedAt;

  public TeamMember(Team team, Member member, TeamRole role) {
    this.team = team;
    this.member = member;
    this.role = role;
    this.joinedAt = LocalDateTime.now();
  }
}
