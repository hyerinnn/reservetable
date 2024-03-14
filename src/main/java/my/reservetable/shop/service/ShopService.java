package my.reservetable.shop.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.dto.request.ShopRegisterRequest;
import my.reservetable.shop.dto.request.ShopUpdateRequest;
import my.reservetable.shop.dto.response.ShopResponse;
import my.reservetable.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final OwnerRepository ownerRepository;

    public List<ShopResponse> getAllShops(){
        List<Shop> shops = shopRepository.findAll();
        return shops.stream().map(ShopResponse::toDto).collect(Collectors.toList());
    }

    public ShopResponse getShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()-> new EntityNotFoundException("매장을 찾을 수 없습니다."));
        return ShopResponse.toDto(shop);
    }

    @Transactional
    public ShopResponse registerShop(ShopRegisterRequest request){
        Owner owner = ownerRepository.findByOwnerId(request.getOwnerId())
                .orElseThrow(()-> new IllegalArgumentException("사장님 정보를 찾을 수 없습니다."));

        Shop shop = shopRepository.save(request.toEntity(owner));
        return ShopResponse.toDto(shop);
    }

    @Transactional
    public ShopResponse updateShop(Long shopId, ShopUpdateRequest request){
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()-> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        shop.update(
                request.getDescription(),
                request.getStatus(),
                request.getOpenTime(),
                request.getLastOrderTime(),
                request.getWaitingYn()
        );
        return ShopResponse.toDto(shop);
    }

}
