package my.reservetable.shop.service;


import lombok.RequiredArgsConstructor;
import my.reservetable.member.entity.Owner;
import my.reservetable.member.repository.OwnerRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.dto.ShopRegisterRequest;
import my.reservetable.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private ShopRepository shopRepository;
    private OwnerRepository ownerRepository;

    @Transactional
    public String registerShop(ShopRegisterRequest request){

        // 사장님 정보 조회
        Owner owner = ownerRepository.getReferenceById(request.getOwner().getId());
        Shop shop = shopRepository.save(request.toEntity(owner));
        return shop.getName();

    }

}
