package my.reservetable.waiting.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "나의 전체 웨이팅목록 조회", description = "나의 전체 웨이팅 목록을 조회한다.")

    @GetMapping("/{userId}")
    public List<WaitingResponse> getAllMyWaitings(@PathVariable Long userId){
        return waitingByUserService.getAllMyWaitings(userId);
    }

    @Operation(summary = "나의 현재 웨이팅 조회", description = "특정가게의 내 현재 웨이팅을 조회한다.")
    @GetMapping("/{userId}/now")
    public WaitingResponse getMyNowWaitings(@PathVariable Long userId, @RequestParam Long shopId){
        return waitingByUserService.getMyNowWaiting(userId, shopId);
    }

    @Operation(summary = "웨이팅등록", description = "특정가게에 웨이팅을 등록한다.")
    @PostMapping("/register")
    public WaitingResponse registerWaiting(@Valid @RequestBody WaitingRegisterRequest request){
        return waitingByUserService.registerWaiting(request);
    }

    @Operation(summary = "웨이팅 상태변경", description = "나의 현재 웨이팅 상태를 변경한다.")
    @PatchMapping("/{waitingId}")
    public WaitingResponse cancelMyWaiting(@PathVariable Long waitingId){
        return waitingService.changeWaitingStatus(waitingId, WaitingStatus.CANCEL);
    }

}
