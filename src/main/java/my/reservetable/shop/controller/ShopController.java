package my.reservetable.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.dto.request.ShopRegisterRequest;
import my.reservetable.shop.dto.request.ShopUpdateRequest;
import my.reservetable.shop.dto.response.ShopDetailResponse;
import my.reservetable.shop.service.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
@Tag(name = "Shop", description = "Shop API")
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "전체 가게목록 조회", description = "전체 가게목록을 조회한다.")
    @GetMapping("/all")
    public Page<ShopDetailResponse> getAllShops(
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        return shopService.getAllShops(pageable);
    }

    @Operation(summary = "특정 가게 조회", description = "특정 가게를 조회한다.")
    @GetMapping("/{shopId}")
    public ShopDetailResponse getShop(@PathVariable Long shopId){
        return shopService.getShop(shopId);
    }

    @Operation(summary = "특정 사장님의 가게목록 조회", description = "특정 사장님의 가게목록을 조회한다.")
    @GetMapping("/all/owner/{ownerId}")
    public List<ShopDetailResponse> getShopByOwner(@PathVariable Long ownerId){
        return shopService.getShopsByOwner(ownerId);
    }

    @Operation(summary = "가게 등록", description = "가게를 등록한다.")
    @PostMapping("/owner")
    public ShopDetailResponse registerShop(@Valid @RequestBody ShopRegisterRequest request){
        return shopService.registerShop(request);
    }

    @Operation(summary = "가게정보 수정", description = "가게 정보를 수정한다.")
    @PutMapping("/owner/{shopId}")
    public ShopDetailResponse updateShop(@PathVariable Long shopId, @Valid @RequestBody ShopUpdateRequest request){
        return shopService.updateShop(shopId, request);
    }

    @Operation(summary = "가게 상태 변경", description = "가게 상태를 변경한다.")
    @PatchMapping("/owner/{shopId}/status")
    public ShopDetailResponse changeShopStatus(@PathVariable Long shopId, @RequestParam ShopStatus status){
        return shopService.changeShopStatus(shopId, status);
    }

}
