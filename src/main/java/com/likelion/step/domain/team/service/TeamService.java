package com.likelion.step.domain.team.service;

import com.likelion.step.domain.profilecard.entity.ProfileCard;
import com.likelion.step.domain.profilecard.repository.ProfileCardRepository;
import com.likelion.step.domain.team.dto.MyTeamResponse;
import com.likelion.step.domain.team.dto.TeamMemberResponse;
import com.likelion.step.domain.team.entity.Team;
import com.likelion.step.domain.team.entity.TeamMember;
import com.likelion.step.domain.team.exception.TeamErrorCode;
import com.likelion.step.domain.team.repository.TeamMemberRepository;
import com.likelion.step.domain.team.repository.TeamRepository;
import com.likelion.step.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamMemberRepository teamMemberRepository;
  private final TeamRepository teamRepository;
  private final ProfileCardRepository profileCardRepository;

  // ===== My팀 목록 조회 =====
  @Transactional(readOnly = true)
  public List<MyTeamResponse> getMyTeams(Long memberId) {
    return teamMemberRepository.findByMember_MemberId(memberId)
        .stream()
        .map(teamMember -> {
          Team team = teamMember.getTeam();
          var post = team.getPost();

          String postStatus = (post.isClosed() || post.getRecruitDeadline().isBefore(LocalDate.now()))
              ? "모집마감" : "모집중";

          return new MyTeamResponse(
              team.getTeamId(),
              post.getPostId(),
              postStatus,
              post.getTitle(),
              post.getAuthor().getName(),
              post.getCreatedAt().toLocalDate().toString()
          );
        })
        .toList();
  }

  // ===== 팀원 목록 조회 =====
  @Transactional(readOnly = true)
  public List<TeamMemberResponse> getTeamMembers(Long memberId, Long teamId) {
    // 팀 존재 여부 확인
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new GeneralException(TeamErrorCode.TEAM_NOT_FOUND));

    // 해당 팀의 멤버인지 확인
    List<TeamMember> members = teamMemberRepository.findByTeam_TeamId(teamId);
    boolean isMember = members.stream()
        .anyMatch(m -> m.getMember().getMemberId().equals(memberId));
    if (!isMember) {
      throw new GeneralException(TeamErrorCode.NOT_TEAM_MEMBER);
    }

    return members.stream()
        .map(m -> {
          Long mId = m.getMember().getMemberId();
          List<String> collaborationTags = profileCardRepository
              .findByMemberId(mId)
              .map(ProfileCard::getCollaborationTags)
              .filter(tags -> tags != null && !tags.isBlank())
              .map(tags -> List.of(tags.split(",")))
              .orElse(List.of());

          return new TeamMemberResponse(
              mId,
              m.getMember().getName(),
              collaborationTags
          );
        })
        .toList();
  }
}