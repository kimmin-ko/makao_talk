package com.mins.makao.domain.member.controller;

import com.mins.makao.common.template.ResultResponse;
import com.mins.makao.common.validator.BirthDate;
import com.mins.makao.common.validator.PhoneNumber;
import com.mins.makao.domain.member.repository.MemberRepository;
import com.mins.makao.domain.member.service.MemberService;
import com.mins.makao.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {





}
