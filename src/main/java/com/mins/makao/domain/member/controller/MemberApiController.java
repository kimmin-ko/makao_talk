package com.mins.makao.domain.member.controller;

import com.mins.makao.common.template.ResultResponse;
import com.mins.makao.common.validator.BirthDate;
import com.mins.makao.common.validator.PhoneNumber;
import com.mins.makao.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberRepository memberRepository;

    @GetMapping("/exists/email/{email}")
    public ResultResponse<EmailExistsResponse> existsEmail(@PathVariable("email") String email) {
        boolean exists = memberRepository.existsByEmail(email);

        return ResultResponse.<EmailExistsResponse>builder()
                .status(HttpStatus.OK.value())
                .message(exists ? "이미 존재하는 이메일입니다." : "사용 가능한 이메일입니다.")
                .data(new EmailExistsResponse(exists))
                .build();
    }

    @GetMapping("/exists/account/{account}")
    public ResultResponse<AccountExistsResponse> existsAccount(@PathVariable("account") String account) {
        boolean exists = memberRepository.existsByAccount(account);

        return ResultResponse.<AccountExistsResponse>builder()
                .status(HttpStatus.OK.value())
                .message(exists ? "이미 존재하는 계정입니다." : "사용 가능한 계정입니다.")
                .data(new AccountExistsResponse(exists))
                .build();
    }

    /* nested class */
    @Data
    public static class JoinMemberRequest {
        @NotBlank(message = "계정 아이디는 필수값 입니다.")
        private String account;

        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수값 입니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수값 입니다.")
        private String password;

        @NotBlank(message = "이름은 필수값 입니다.")
        private String name;

        @PhoneNumber
        private String phoneNumber;

        @BirthDate
        private LocalDate birthDate;
    }

    @Data
    @AllArgsConstructor
    static class EmailExistsResponse {
        private boolean exists;
    }

    @Data
    @AllArgsConstructor
    static class AccountExistsResponse {
        private boolean exists;
    }

}
