package com.likelion.step.domain.application.service;

import com.likelion.step.domain.application.dto.ApplicationDecisionRequest;
import com.likelion.step.domain.application.dto.ApplicationDecisionResponse;
import com.likelion.step.domain.application.entity.Application;
import com.likelion.step.domain.application.exception.ApplicationErrorCode;
import com.likelion.step.domain.application.repository.ApplicationRepository;
import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.post.entity.Post;
import com.likelion.step.domain.team.entity.Team;
import com.likelion.step.domain.team.entity.TeamMember;
import com.likelion.step.domain.team.entity.TeamRole;
import com.likelion.step.domain.team.repository.TeamMemberRepository;
import com.likelion.step.domain.team.repository.TeamRepository;
import com.likelion.step.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final GeneralLoginRepository generalLoginRepository;

  @Transactional
  public ApplicationDecisionResponse decide(
      Long memberId, Long applicationId, ApplicationDecisionRequest request) {

    // 1. 지원 정보 조회
    Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new GeneralException(ApplicationErrorCode.APPLICATION_NOT_FOUND));

    Post post = application.getPost();

    // 2. 팀장(모집글 작성자)만 수락/거절 가능
    if (!post.getAuthor().getMemberId().equals(memberId)) {
      throw new GeneralException(ApplicationErrorCode.NOT_APPLICATION_OWNER);
    }

    // 3. decision 값 검증
    String decision = request.decision();
    if (decision == null
        || (!decision.equals("ACCEPT") && !decision.equals("REJECT"))) {
      throw new GeneralException(ApplicationErrorCode.INVALID_DECISION);
    }

    // 4. 팀 조회
    Team team = teamRepository.findByPost_PostId(post.getPostId())
        .orElseThrow(() -> new GeneralException(ApplicationErrorCode.APPLICATION_NOT_FOUND));

    // 5. 수락 or 거절 처리
    boolean isPostClosed = false;

    if (decision.equals("ACCEPT")) {
      application.accept();

      // 팀원으로 추가
      teamMemberRepository.save(
          new TeamMember(team, application.getApplicant(), TeamRole.MEMBER)
      );

      // 모집인원 충족 여부 확인 → 자동 마감
      List<TeamMember> currentMembers = teamMemberRepository.findByTeam_TeamId(team.getTeamId());
      if (currentMembers.size() >= post.getRecruitCount()) {
        post.close();
        isPostClosed = true;
      }

    } else {
      application.reject();
    }

    // 6. 지원자 이메일 조회
    String contactEmail = generalLoginRepository
        .findByMember_MemberId(application.getApplicant().getMemberId())
        .map(gl -> gl.getEmail())
        .orElse("");

    return new ApplicationDecisionResponse(team.getTeamId(), contactEmail, isPostClosed);
  }
}