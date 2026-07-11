package com.likelion.step.domain.team.service;

import com.likelion.step.domain.post.entity.Post;
import com.likelion.step.domain.team.dto.MyTeamResponse;
import com.likelion.step.domain.team.entity.Team;
import com.likelion.step.domain.team.entity.TeamMember;
import com.likelion.step.domain.team.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamQueryService {

    private final TeamMemberRepository teamMemberRepository;

    //로그인한 회원이 속한 팀 목록 조회
    public List<MyTeamResponse> getMyTeams(Long memberId) {

        List<TeamMember> teamMembers = teamMemberRepository.findAll()
                .stream()
                .filter(teamMember ->
                        teamMember.getMember() != null
                                && teamMember.getMember().getMemberId().equals(memberId)
                )
                .toList();

        return teamMembers.stream()
                .map(this::toMyTeamResponse)
                .toList();
    }

    //TeamMember 엔티티를 MyTeamResponse로 변환
    private MyTeamResponse toMyTeamResponse(TeamMember teamMember) {

        Team team = teamMember.getTeam();
        Post post = team.getPost();

        String postStatus =
                post.getRecruitDeadline().isBefore(LocalDate.now())
                        ? "모집마감"
                        : "모집중";

        String createdAt = post.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        return new MyTeamResponse(
                team.getTeamId(),
                post.getPostId(),
                postStatus,
                post.getTitle(),
                post.getAuthor().getName(),
                createdAt
        );
    }
}