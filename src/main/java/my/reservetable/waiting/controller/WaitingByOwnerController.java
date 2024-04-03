package my.reservetable.waiting.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.response.WaitingOwnerResponse;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.service.WaitingByOwnerService;
import my.reservetable.waiting.service.WaitingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waitings/owner")
@Tag(name = "Waiting for Owner", description = "Waiting API")
public class WaitingByOwnerController {

    private final WaitingByOwnerService waitingByOwnerService;
    private final WaitingService waitingService;

    /**
     * 현재 웨이팅의 수 조회
     * */
    @GetMapping("/{shopId}/now/count")
    public int getCountNowWaitingsByShopId(@PathVariable Long shopId){
        return waitingByOwnerService.getCountNowWaitingsByShopId(shopId);
    }

    /**
     * 현재 웨이팅의 목록 조회
     * */
    @GetMapping("/{shopId}/now")
    public List<WaitingOwnerResponse> getNowAllWaitingsByShopId(@PathVariable Long shopId){
        return waitingByOwnerService.getNowWaitingsByShopId(shopId);
    }

    /**
     * 가게별 전체 웨이팅 목록 조회
     * */
    @GetMapping("/{shopId}/all")
    public List<WaitingOwnerResponse> getAllWaitingsByShopId(@PathVariable Long shopId){
        return waitingByOwnerService.getWaitingByShopId(shopId);
    }

    @GetMapping("/change/{waitingId}")
    public WaitingResponse changeWaitingStatus(@PathVariable Long waitingId, @RequestParam WaitingStatus status){
        return waitingService.changeWaitingStatus(waitingId, status);
    }


}
