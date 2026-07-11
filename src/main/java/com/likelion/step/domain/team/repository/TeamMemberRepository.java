package com.likelion.step.domain.team.repository;

import com.likelion.step.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
  List<TeamMember> findByTeam_TeamId(Long teamId); // 추가
  List<TeamMember> findByMember_MemberId(Long memberId);
}