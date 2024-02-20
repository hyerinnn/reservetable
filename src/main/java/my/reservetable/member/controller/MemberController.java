package my.reservetable.member.controller;

import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.TempMemberRequest;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.repository.TempMemberRepository;
import my.reservetable.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {


}
