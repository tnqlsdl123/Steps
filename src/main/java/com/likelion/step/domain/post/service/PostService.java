package com.likelion.step.domain.post.service;

import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.post.dto.PostListResponse;
import com.likelion.step.domain.application.entity.Application;
import com.likelion.step.domain.application.exception.ApplicationErrorCode;
import com.likelion.step.domain.application.repository.ApplicationRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.domain.post.dto.*;
import com.likelion.step.domain.post.entity.ActivityType;
import com.likelion.step.domain.post.entity.Post;
import com.likelion.step.domain.post.entity.PostCategory;
import com.likelion.step.domain.post.exception.PostErrorCode;
import com.likelion.step.domain.post.repository.PostRepository;
import com.likelion.step.domain.profilecard.repository.CertificatesRepository;
import com.likelion.step.domain.profilecard.repository.ProfileCardRepository;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final ApplicationRepository applicationRepository; // 추가
  private final ProfileCardRepository profileCardRepository;
  private final CertificatesRepository certificatesRepository;
  private final GeneralLoginRepository generalLoginRepository;

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

  // ===== 모집글 상세 조회 =====
  @Transactional(readOnly = true)
  public PostDetailResponse getDetail(Long memberId, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

    boolean hasApplied = applicationRepository
        .existsByPost_PostIdAndApplicant_MemberId(postId, memberId);

    String postStatus = post.getRecruitDeadline().isBefore(LocalDate.now())
        ? "모집마감" : "모집중";

    LocalDateTime deadlineWithTime = LocalDateTime.of(post.getRecruitDeadline(), LocalTime.of(23, 59, 0));

    return new PostDetailResponse(
        post.getPostId(),
        post.getTitle(),
        post.getAuthor().getName(),
        post.getCreatedAt().toString(),
        toCategoryLabel(post.getCategory()),
        post.getApplicationUrl(),
        deadlineWithTime.toString(),
        post.getRecruitCount() + "명",
        toActivityTypeLabel(post.getActivityType()),
        post.getActivityPurpose(),
        post.getContent(),
        postStatus,
        hasApplied
    );
  }

  // ===== 모집글 지원하기 =====
  @Transactional
  public PostApplyResponse apply(Long memberId, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

    Member applicant = memberRepository.findById(memberId)
        .orElseThrow(() -> new GeneralException(PostErrorCode.AUTHOR_NOT_FOUND));

    if (post.getAuthor().getMemberId().equals(memberId)) {
      throw new GeneralException(ApplicationErrorCode.SELF_APPLY_NOT_ALLOWED);
    }

    if (applicationRepository.existsByPost_PostIdAndApplicant_MemberId(postId, memberId)) {
      throw new GeneralException(ApplicationErrorCode.ALREADY_APPLIED);
    }

    Application application = applicationRepository.save(new Application(post, applicant));

    return new PostApplyResponse(application.getApplicationId());
  }

  // ===== 전체 글 목록 조회 =====
  @Transactional(readOnly = true)
  public List<PostListResponse> getList() {
    return postRepository.findAllByOrderByCreatedAtDesc()
        .stream()
        .map(post -> new PostListResponse(
            post.getPostId(),
            post.getTitle(),
            toCategoryLabel(post.getCategory()),
            post.getAuthor().getName(),
            post.getCreatedAt().toLocalDate().toString(),
            post.getRecruitDeadline().isBefore(LocalDate.now()) ? "모집마감" : "모집중"
        ))
        .toList();
  }

  // ===== 지원자 프로필 목록 조회 (팀장 전용) =====
  @Transactional(readOnly = true)
  public List<ApplicantProfileResponse> getApplicants(Long memberId, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

    // 팀장(작성자) 본인만 조회 가능
    if (!post.getAuthor().getMemberId().equals(memberId)) {
      throw new GeneralException(PostErrorCode.NOT_POST_AUTHOR);
    }

    return applicationRepository.findByPost_PostIdOrderByAppliedAtAsc(postId)
        .stream()
        .map(application -> {
          Member applicant = application.getApplicant();
          Long applicantId = applicant.getMemberId();

          List<String> collaborationTags = List.of();
          List<String> certificates = List.of();
          String selfIntroduction = "";

          var profileCardOpt = profileCardRepository.findByMemberId(applicantId);
          if (profileCardOpt.isPresent()) {
            var profileCard = profileCardOpt.get();
            String tagsStr = profileCard.getCollaborationTags();
            collaborationTags = (tagsStr != null && !tagsStr.isBlank())
                ? List.of(tagsStr.split(","))
                : List.of();
            selfIntroduction = profileCard.getSelfIntroduce() != null
                ? profileCard.getSelfIntroduce() : "";
            certificates = certificatesRepository
                .findAllByProfileCardId(profileCard.getProfileCardId())
                .stream()
                .map(c -> c.getCertificates())
                .toList();
          }

          String contactEmail = generalLoginRepository.findById(applicantId)
              .map(gl -> gl.getEmail())
              .orElse("");

          return new ApplicantProfileResponse(
              application.getApplicationId(),
              applicantId,
              applicant.getName(),
              applicant.getMajor(),
              applicant.getSchool(),
              applicant.getGrade(),
              applicant.getGender(),
              collaborationTags,
              certificates,
              selfIntroduction,
              contactEmail
          );
        })
        .toList();
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
      return LocalDate.parse(value);
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

  private String toCategoryLabel(PostCategory category) {
    return switch (category) {
      case PLANNING_IDEA -> "기획/아이디어";
      case DEVELOPMENT -> "개발";
      case DESIGN -> "디자인";
      case MARKETING -> "마케팅";
      case ETC -> "기타";
    };
  }

  private String toActivityTypeLabel(ActivityType activityType) {
    return switch (activityType) {
      case ONLINE -> "비대면";
      case OFFLINE -> "대면";
      case HYBRID -> "혼합";
    };
  }
}
