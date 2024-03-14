package my.reservetable.shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.shop.dto.request.ShopRegisterRequest;
import my.reservetable.shop.dto.request.ShopUpdateRequest;
import my.reservetable.shop.dto.response.ShopResponse;
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
    public List<ShopResponse> getAllShops(){
        return shopService.getAllShops();
    }

    @GetMapping("/shop/{shopId}")
    public ShopResponse getShop(@PathVariable Long shopId){
        return shopService.getShop(shopId);
    }

    @PostMapping("/register/shop")
    public ShopResponse registerShop(@Valid @RequestBody ShopRegisterRequest request){
        return shopService.registerShop(request);
    }

    @PutMapping("/update/shop/{shopId}")
    public ShopResponse updateShop(@PathVariable Long shopId, @RequestBody ShopUpdateRequest request){
        return shopService.updateShop(shopId, request);
    }

}
