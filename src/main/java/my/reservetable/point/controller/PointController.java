package my.reservetable.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import my.reservetable.point.service.PointService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
@Tag(name = "Point", description = "Point API")
public class PointController {

    private final PointService pointService;

    @Operation(summary = "포인트 조회", description = "내 포인트 조회한다.")
    @GetMapping
    public int getPoint(@RequestParam Long memberId){
        return pointService.getPoint(memberId);
    }

    @Operation(summary = "일반 포인트 적립", description = "포인트를 적립한다.")
    @PostMapping
    public int addPoint(@RequestParam Long memberId){
        return pointService.addPoint(memberId);
    }

    @Operation(summary = "포인트 사용", description = "포인트를 차감한다.")
    @PatchMapping
    public int subtractPoint(@RequestParam Long memberId, int point){
        return pointService.subtractPoint(memberId, point);
    }
}
