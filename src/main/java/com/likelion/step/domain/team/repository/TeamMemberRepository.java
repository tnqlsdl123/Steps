// domain/team/repository/TeamMemberRepository.java
package com.likelion.step.domain.team.repository;

import com.likelion.step.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
