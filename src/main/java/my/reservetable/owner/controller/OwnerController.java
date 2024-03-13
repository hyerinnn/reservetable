package my.reservetable.owner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.service.OwnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@Tag(name = "Owner", description = "Owner API")
public class OwnerController {

    /*
     * 사장님회원
        - 가입/수정/조회
     * 일반회원 -> 따로 만들기
        - 가입/수정/조회
    */

    private final OwnerService ownerService;

    @Operation(summary = "id로 사장회원 조회")
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