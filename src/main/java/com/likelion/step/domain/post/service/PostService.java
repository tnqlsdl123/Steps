package com.likelion.step.domain.post.service;

import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.domain.post.dto.PostCreateRequest;
import com.likelion.step.domain.post.dto.PostCreateResponse;
import com.likelion.step.domain.post.entity.ActivityType;
import com.likelion.step.domain.post.entity.Post;
import com.likelion.step.domain.post.entity.PostCategory;
import com.likelion.step.domain.post.exception.PostErrorCode;
import com.likelion.step.domain.post.repository.PostRepository;
import com.likelion.step.domain.team.entity.Team;
import com.likelion.step.domain.team.entity.TeamMember;
import com.likelion.step.domain.team.entity.TeamRole;
import com.likelion.step.domain.team.repository.TeamMemberRepository;
import com.likelion.step.domain.team.repository.TeamRepository;
import com.likelion.step.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;

  @Transactional
  public PostCreateResponse create(Long memberId, PostCreateRequest request) {
    Member author = memberRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(PostErrorCode.AUTHOR_NOT_FOUND));

    validate(request);

    Post post = new Post(
        author,
        request.title(),
        request.applicationUrl(),
        parseCategory(request.category()),
        parseDeadline(request.recruitDeadline()),
        request.recruitCount(),
        parseActivityType(request.activityType()),
        request.activityPurpose(),
        request.content()
    );
    Post savedPost = postRepository.save(post);

    // 모집글 등록과 동시에 팀(1:1) 생성 + 작성자를 리더로 등록
    Team team = teamRepository.save(new Team(savedPost));
    teamMemberRepository.save(new TeamMember(team, author, TeamRole.LEADER));

    return new PostCreateResponse(savedPost.getPostId());
  }

  private void validate(PostCreateRequest request) {
    if (request.title() == null || request.title().isBlank() || request.title().length() > 30) {
      throw new GeneralException(PostErrorCode.INVALID_TITLE);
    }
    if (request.content() == null || request.content().isBlank() || request.content().length() > 200) {
      throw new GeneralException(PostErrorCode.INVALID_CONTENT);
    }
    if (request.recruitCount() == null || request.recruitCount() < 1) {
      throw new GeneralException(PostErrorCode.INVALID_RECRUIT_COUNT);
    }
    if (request.applicationUrl() == null || request.applicationUrl().isBlank()
        || request.activityPurpose() == null || request.activityPurpose().isBlank()) {
      throw new GeneralException(PostErrorCode.INVALID_INPUT);
    }
  }

  private LocalDate parseDeadline(String value) {
    if (value == null || value.isBlank()) {
      throw new GeneralException(PostErrorCode.INVALID_DEADLINE);
    }
    try {
      return LocalDate.parse(value); // ISO-8601 (YYYY-MM-DD)
    } catch (DateTimeParseException e) {
      throw new GeneralException(PostErrorCode.INVALID_DEADLINE);
    }
  }

  private PostCategory parseCategory(String value) {
    if (value == null) throw new GeneralException(PostErrorCode.INVALID_CATEGORY);
    return switch (value.trim()) {
      case "기획/아이디어", "PLANNING_IDEA" -> PostCategory.PLANNING_IDEA;
      case "개발", "DEVELOPMENT" -> PostCategory.DEVELOPMENT;
      case "디자인", "DESIGN" -> PostCategory.DESIGN;
      case "마케팅", "MARKETING" -> PostCategory.MARKETING;
      case "기타", "ETC" -> PostCategory.ETC;
      default -> throw new GeneralException(PostErrorCode.INVALID_CATEGORY);
    };
  }

  private ActivityType parseActivityType(String value) {
    if (value == null) throw new GeneralException(PostErrorCode.INVALID_ACTIVITY_TYPE);
    return switch (value.trim()) {
      case "비대면", "ONLINE" -> ActivityType.ONLINE;
      case "대면", "OFFLINE" -> ActivityType.OFFLINE;
      case "혼합", "HYBRID" -> ActivityType.HYBRID;
      default -> throw new GeneralException(PostErrorCode.INVALID_ACTIVITY_TYPE);
    };
  }
}
