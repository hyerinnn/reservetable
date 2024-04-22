package my.reservetable.shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.shop.dto.request.ShopRegisterRequest;
import my.reservetable.shop.dto.request.ShopUpdateRequest;
import my.reservetable.shop.dto.response.ShopDetailResponse;
import my.reservetable.shop.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
@Tag(name = "Shop", description = "Shop API")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/all")
    public List<ShopDetailResponse> getAllShops(){
        return shopService.getAllShops();
    }

    @GetMapping("/{shopId}")
    public ShopDetailResponse getShop(@PathVariable Long shopId){
        return shopService.getShop(shopId);
    }

    @GetMapping("/owner/{ownerId}")
    public List<ShopDetailResponse> getShopByOwner(@PathVariable Long ownerId){
        return shopService.getShopsByOwner(ownerId);
    }

    @PostMapping("/owner")
    public ShopDetailResponse registerShop(@Valid @RequestBody ShopRegisterRequest request){
        return shopService.registerShop(request);
    }

    @PutMapping("/owner/{shopId}")
    public ShopDetailResponse updateShop(@PathVariable Long shopId, @Valid @RequestBody ShopUpdateRequest request){
        return shopService.updateShop(shopId, request);
    }

}
