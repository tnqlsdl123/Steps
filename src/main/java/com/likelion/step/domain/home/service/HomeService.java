package com.likelion.step.domain.home.service;

import com.likelion.step.domain.home.dto.HomePostResponse;
import com.likelion.step.domain.home.dto.HomeResponse;
import com.likelion.step.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final PostRepository postRepository;

  // 홈화면에 보여줄 최신 글 개수
  private static final int RECENT_POST_LIMIT = 6;

  // 카테고리 목록 (고정값)
  private static final List<String> CATEGORIES = List.of(
      "기획/아이디어", "개발", "디자인", "마케팅", "기타"
  );

  @Transactional(readOnly = true)
  public HomeResponse getHome() {
    List<HomePostResponse> recentPosts = postRepository
        .findAllByOrderByCreatedAtDesc(PageRequest.of(0, RECENT_POST_LIMIT))
        .stream()
        .map(post -> new HomePostResponse(
            post.getPostId(),
            post.getTitle(),
            toCategoryLabel(post.getCategory()),
            post.getAuthor().getName(),
            makePreview(post.getContent())
        ))
        .toList();

    return new HomeResponse(CATEGORIES, recentPosts);
  }

  // 내용 미리보기: 50자까지만 자르고 나머지는 "..." 처리
  private String makePreview(String content) {
    if (content == null) return "";
    if (content.length() <= 50) return content;
    return content.substring(0, 50) + "...";
  }

  private String toCategoryLabel(com.likelion.step.domain.post.entity.PostCategory category) {
    return switch (category) {
      case PLANNING_IDEA -> "기획/아이디어";
      case DEVELOPMENT -> "개발";
      case DESIGN -> "디자인";
      case MARKETING -> "마케팅";
      case ETC -> "기타";
    };
  }
}