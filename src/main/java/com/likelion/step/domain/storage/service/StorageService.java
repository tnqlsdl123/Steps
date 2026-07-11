package com.likelion.step.domain.storage.service;

import com.likelion.step.domain.application.entity.Application;
import com.likelion.step.domain.application.repository.ApplicationRepository;
import com.likelion.step.domain.post.entity.Post;
import com.likelion.step.domain.post.repository.PostRepository;
import com.likelion.step.domain.storage.dto.StorageItemResponse;
import com.likelion.step.domain.storage.exception.StorageErrorCode;
import com.likelion.step.global.error.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

  private final PostRepository postRepository;
  private final ApplicationRepository applicationRepository;

  @Transactional(readOnly = true)
  public List<StorageItemResponse> getList(Long memberId, String type, String status) {
    validateType(type);
    validateStatus(status);

    List<Post> posts = "posted".equals(type)
        ? postRepository.findByAuthor_MemberIdOrderByCreatedAtDesc(memberId)
        : applicationRepository.findByApplicant_MemberIdOrderByAppliedAtDesc(memberId)
        .stream()
        .map(Application::getPost)
        .toList();

    return posts.stream()
        .map(this::toResponse)
        .filter(item -> matchesStatus(item, status))
        .toList();
  }

  private StorageItemResponse toResponse(Post post) {
    String postStatus = post.getRecruitDeadline().isBefore(LocalDate.now())
        ? "모집마감" : "모집중";

    return new StorageItemResponse(
        post.getPostId(),
        postStatus,
        post.getTitle(),
        post.getAuthor().getName(),
        post.getCreatedAt().toLocalDate().toString()
    );
  }

  private boolean matchesStatus(StorageItemResponse item, String status) {
    return switch (status) {
      case "all" -> true;
      case "recruiting" -> "모집중".equals(item.status());
      case "closed" -> "모집마감".equals(item.status());
      default -> true; // validateStatus에서 이미 걸러짐
    };
  }

  private void validateType(String type) {
    if (type == null || (!type.equals("applied") && !type.equals("posted"))) {
      throw new GeneralException(StorageErrorCode.INVALID_TYPE);
    }
  }

  private void validateStatus(String status) {
    if (status == null
        || (!status.equals("all") && !status.equals("recruiting") && !status.equals("closed"))) {
      throw new GeneralException(StorageErrorCode.INVALID_STATUS);
    }
  }
}