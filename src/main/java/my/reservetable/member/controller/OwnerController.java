package my.reservetable.member.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.domain.Owner;
import my.reservetable.member.dto.request.OwnerSignupRequest;
import my.reservetable.member.dto.response.OwnerResponse;
import my.reservetable.member.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    /*
     * 사장님회원
        - 가입/수정/조회
     * 일반회원 -> 따로 만들기
        - 가입/수정/조회
    */

    private final OwnerService ownerService;

    @GetMapping("/{id}")
    public OwnerResponse getOwnerById(@PathVariable Long id){
        return ownerService.findOwnerById(id);
    }

    @GetMapping("/owner/{ownerId}")
    public OwnerResponse getOwnerByOwnerId(@PathVariable String ownerId){
        return ownerService.findOwnerByOwnerId(ownerId);
    }

    @PostMapping("/signUp")
    public OwnerResponse signUpOwner(OwnerSignupRequest request){
        return ownerService.signupOwner(request);
    }

}
