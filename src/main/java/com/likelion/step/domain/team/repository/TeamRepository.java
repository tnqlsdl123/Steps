// domain/team/repository/TeamRepository.java
package com.likelion.step.domain.team.repository;

import com.likelion.step.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
