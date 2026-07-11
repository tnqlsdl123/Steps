// domain/team/repository/TeamMemberRepository.java
package com.likelion.step.domain.team.repository;

import com.likelion.step.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository
        extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByMemberMemberId(Long memberId);


}
