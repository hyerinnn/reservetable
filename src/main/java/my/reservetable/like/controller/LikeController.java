package my.reservetable.like.controller;

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

    @PostMapping("/like")
    public int likeShop(@Valid @RequestBody LikeRequest request){
        return likeService.likeShop(request);
    }

    @GetMapping("/member/{memberId}")
    public LikesWithCountResponse getLikesByMember(@PathVariable Long memberId){
        return likeService.getLikesByMember(memberId);
    }

    @GetMapping("/shop/{shopId}")
    public int getCountLikesByShop(Long shopId){
        return likeService.getCountLikesByShop(shopId);
    }

}
