package my.reservetable.waiting.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.request.WaitingRegisterRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.service.WaitingByUserService;
import my.reservetable.waiting.service.WaitingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waitings/user")
@Tag(name = "Waiting for User", description = "Waiting API")
public class WaitingByUserController {

    private final WaitingByUserService waitingByUserService;
    private final WaitingService waitingService;

    @GetMapping("/{userId}")
    public List<WaitingResponse> getAllMyWaitings(@PathVariable Long userId){
        return waitingByUserService.getAllMyWaitings(userId);
    }
/*
    @GetMapping("/now")
    public WaitingResponse getMyNowWaitings(@Valid MyWaitingRequest request){
        return waitingByUserService.getMyNowWaiting(request);
    }
    */
    @GetMapping("/{userId}/now")
    public WaitingResponse getMyNowWaitings(@PathVariable Long userId, @RequestParam Long shopId){
        return waitingByUserService.getMyNowWaiting(userId, shopId);
    }

    @PostMapping("/register")
    public WaitingResponse registerWaiting(@Valid @RequestBody WaitingRegisterRequest request){
        return waitingByUserService.registerWaiting(request);
    }

    // TODO : PatchMapping
    @GetMapping("/change/{waitingId}")
    public WaitingResponse cancelMyWaiting(@PathVariable Long waitingId){
        return waitingService.changeWaitingStatus(waitingId, WaitingStatus.CANCEL);
    }

}
