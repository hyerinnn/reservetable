package my.reservetable.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "OwnerMember", description = "OwnerMember API")
public class OwnerController {

    private final MemberService memberService;

/*
    @GetMapping("/owners/{id}")
    public MemberResponse getMember(@PathVariable Long id){
        return memberService.getMemberById(id);
    }

    @PutMapping("/owners/{id}")
    public MemberResponse updateOwner(@Valid @RequestBody MemberUpdateRequest request,
                                     @PathVariable Long id){
        return memberService.update(request);
    }
*/

/*    @GetMapping("/owner/{ownerId}")
    public OwnerResponse getOwnerByOwnerId(@PathVariable Long ownerId){
        return ownerService.findOwnerByOwnerId(ownerId);
    }*/

/*
    @PostMapping("/owner/signUp")
    public OwnerResponse signUpOwner(@Valid @RequestBody OwnerSignupRequest request){
        return ownerService.signupOwner(request);
    }
*/

/*    @PutMapping("/owner/update")
    public OwnerResponse updateOwner(@Valid @RequestBody OwnerUpdateRequest request){
        return ownerService.updateOwner(request);
    }*/

}
