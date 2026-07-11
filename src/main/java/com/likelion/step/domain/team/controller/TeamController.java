package com.likelion.step.domain.team.controller;

import com.likelion.step.domain.team.dto.MyTeamResponse;
import com.likelion.step.domain.team.dto.TeamMemberResponse;
import com.likelion.step.domain.team.service.TeamService;
import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

  private final TeamService teamService;

  @GetMapping("/me")
  public ApiResponse<List<MyTeamResponse>> getMyTeams(
      @LoginMember Long memberId) {

    return ApiResponse.success(teamService.getMyTeams(memberId));
  }

  @GetMapping("/{teamId}/members")
  public ApiResponse<List<TeamMemberResponse>> getTeamMembers(
      @LoginMember Long memberId,
      @PathVariable Long teamId) {

    return ApiResponse.success(teamService.getTeamMembers(memberId, teamId));
  }
}