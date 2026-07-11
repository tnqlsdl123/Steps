package com.likelion.step.domain.application.entity;

public enum ApplicationStatus {
  PENDING,   // 지원 대기 (팀장 검토 중)
  ACCEPTED,  // 수락됨 (팀원으로 편입됨)
  REJECTED   // 거절됨
}