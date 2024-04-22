package my.reservetable.shop.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.member.domain.Member;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.dto.request.ShopRegisterRequest;
import my.reservetable.shop.dto.request.ShopUpdateRequest;
import my.reservetable.shop.dto.response.ShopDetailResponse;
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
    private final MemberRepository memberRepository;

    //TODO : 검색 필터링 필요(queryDSL 적용시 개발예정)
    public List<ShopDetailResponse> getAllShops(){
        List<Shop> shops = shopRepository.findAll();
        return shops.stream().map(ShopDetailResponse::toDto).collect(Collectors.toList());
    }

    public ShopDetailResponse getShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()-> new NotFoundEntityException("매장을 찾을 수 없습니다."));
        return ShopDetailResponse.toDto(shop);
    }

    public List<ShopDetailResponse> getShopsByOwner(Long ownerId){
        Member ownerMember = memberRepository.findById(ownerId)
                .orElseThrow(()-> new NotExistMemberException("사장님 정보를 찾을 수 없습니다."));
        List<ShopDetailResponse> shops = shopRepository.findByMember(ownerMember)
                .stream().map(ShopDetailResponse::toDto).collect(Collectors.toList());
        return shops;
    }

    @Transactional
    public ShopDetailResponse registerShop(ShopRegisterRequest request){
        Member ownerMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(()-> new NotExistMemberException("사장님 정보를 찾을 수 없습니다."));
        Shop shop = shopRepository.save(request.toEntity(ownerMember));
        return ShopDetailResponse.toDto(shop);
    }

    @Transactional
    public ShopDetailResponse updateShop(Long shopId, ShopUpdateRequest request){
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(()-> new NotFoundEntityException("매장을 찾을 수 없습니다."));

        shop.update(
                request.getDescription(),
                request.getStatus(),
                request.getOpenTime(),
                request.getLastOrderTime(),
                request.getWaitingYn()
        );
        return ShopDetailResponse.toDto(shop);
    }

}
