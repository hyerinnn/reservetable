package my.reservetable.owner.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import my.reservetable.owner.dto.request.OwnerSignupRequest;
import my.reservetable.owner.dto.request.OwnerUpdateRequest;
import my.reservetable.owner.dto.response.OwnerResponse;
import my.reservetable.owner.service.OwnerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@Tag(name = "Owner", description = "Owner API")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/owner/{ownerId}")
    public OwnerResponse getOwnerByOwnerId(@PathVariable String ownerId){
        return ownerService.findOwnerByOwnerId(ownerId);
    }

    @PostMapping("/owner/signUp")
    public OwnerResponse signUpOwner(@Validated @RequestBody OwnerSignupRequest request){
        return ownerService.signupOwner(request);
    }

    @PutMapping("/owner/update")
    public OwnerResponse updateOwner(@RequestBody OwnerUpdateRequest request){
        return ownerService.updateOwner(request);
    }

}
