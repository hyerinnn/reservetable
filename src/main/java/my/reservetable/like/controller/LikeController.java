package my.reservetable.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.like.dto.LikeRequest;
import my.reservetable.like.dto.LikesWithCountResponse;
import my.reservetable.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@Tag(name = "Like", description = "Like API")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "좋아요", description = "찜하기 버튼 클릭 시, 찜하기")
    @PostMapping("/like")
    public void createLike(@Valid @RequestBody LikeRequest request){
        likeService.createLike(request);
    }

    @Operation(summary = "좋아요 취소", description = "찜하기 취소")
    @DeleteMapping("/like")
    public void cancelLike(@Valid @RequestBody LikeRequest request){
        likeService.cancelLike(request);
    }

    @Operation(summary = "나의 '좋아요'목록 조회", description = "내가 '좋아요'한 shop의 목록과 수를 조회한다.")
    @GetMapping("/member/{memberId}")
    public LikesWithCountResponse getLikesByMember(@PathVariable Long memberId){
        return likeService.getLikesByMember(memberId);
    }

    @Operation(summary = "가게의 좋아요 수 조회", description = "특정 가게의 좋아요 수를 조회한다.")
    @GetMapping("/shop/{shopId}")
    public int getCountLikesByShop(Long shopId){
        return likeService.getCountLikesByShop(shopId);
    }

}
