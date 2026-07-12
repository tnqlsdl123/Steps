package com.likelion.step.domain.auth.service;

import com.likelion.step.domain.auth.dto.SignupRequest;
import com.likelion.step.domain.auth.dto.SignupResponse;
import com.likelion.step.domain.auth.entity.GeneralLogin;
import com.likelion.step.domain.auth.exception.DuplicateEmailException;
import com.likelion.step.domain.auth.exception.InvalidSignupException;
import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.domain.member.entity.Member;
import com.likelion.step.domain.member.repository.MemberRepository;
import com.likelion.step.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignupService {

  private final MemberRepository memberRepository;
  private final GeneralLoginRepository generalLoginRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider; // 추가

  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]+$");
  private static final Pattern BIRTH_DATE_PATTERN = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
  private static final Pattern SCHOOL_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣\\s]+$");
  private static final Pattern PASSWORD_PATTERN = Pattern.compile("^\\S{8,16}$");

  @Transactional
  public SignupResponse signup(SignupRequest request) {
    validate(request);

    if (generalLoginRepository.findByEmail(request.email()).isPresent()) {
      throw new DuplicateEmailException("이미 가입된 이메일입니다.");
    }

    Member member = new Member(
        request.name(),
        request.birthDate(),
        request.gender(),
        request.school(),
        request.major(),
        request.grade()
    );
    Member savedMember = memberRepository.save(member);

    String encodedPassword = passwordEncoder.encode(request.password());
    GeneralLogin generalLogin = new GeneralLogin(request.email(), encodedPassword, savedMember);
    generalLoginRepository.save(generalLogin);

    // 회원가입 직후 토큰 발급 (학교 인증 페이지에서 사용)
    String accessToken = jwtProvider.createToken(savedMember.getMemberId(), false);

    return new SignupResponse(savedMember.getMemberId(), accessToken);
  }

  private void validate(SignupRequest request) {
    if (request.name() == null || !NAME_PATTERN.matcher(request.name()).matches()) {
      throw new InvalidSignupException("이름은 한글 또는 영문만 입력 가능하며 공백・특수문자를 포함할 수 없습니다.");
    }
    if (request.birthDate() == null || !BIRTH_DATE_PATTERN.matcher(request.birthDate()).matches()) {
      throw new InvalidSignupException("생년월일은 YYYY/MM/DD 형식으로 입력해주세요.");
    }
    if (request.gender() == null || (!request.gender().equals("male") && !request.gender().equals("female"))) {
      throw new InvalidSignupException("성별을 선택해주세요.");
    }
    if (request.school() == null || !SCHOOL_PATTERN.matcher(request.school()).matches()) {
      throw new InvalidSignupException("소속 학교명에 특수문자를 포함할 수 없습니다.");
    }
    if (request.major() == null || request.major().isBlank()) {
      throw new InvalidSignupException("전공을 입력해주세요.");
    }
    if (request.grade() == null) {
      throw new InvalidSignupException("학년을 입력해주세요.");
    }
    if (request.email() == null || request.email().isBlank()) {
      throw new InvalidSignupException("이메일을 입력해주세요.");
    }
    if (request.password() == null || !PASSWORD_PATTERN.matcher(request.password()).matches()) {
      throw new InvalidSignupException("비밀번호는 공백 없이 8~16자로 입력해주세요.");
    }
    if (!request.password().equals(request.passwordCheck())) {
      throw new InvalidSignupException("비밀번호가 일치하지 않습니다.");
    }
  }
}